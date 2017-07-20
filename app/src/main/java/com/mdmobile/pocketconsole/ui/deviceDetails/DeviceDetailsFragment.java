package com.mdmobile.pocketconsole.ui.deviceDetails;

import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayout;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mdmobile.pocketconsole.R;
import com.mdmobile.pocketconsole.apiManager.ApiRequestManager;
import com.mdmobile.pocketconsole.provider.McContract;
import com.mdmobile.pocketconsole.utils.GeneralUtility;

import java.util.HashMap;

import static com.mdmobile.pocketconsole.ui.deviceDetails.DeviceDetailsActivity.DEVICE_ID_EXTRA_KEY;
import static com.mdmobile.pocketconsole.ui.deviceDetails.DeviceDetailsActivity.DEVICE_NAME_EXTRA_KEY;
import static com.mdmobile.pocketconsole.ui.deviceDetails.DeviceDetailsActivity.EXTRA_DEVICE_ICON_TRANSITION_NAME_KEY;
import static com.mdmobile.pocketconsole.ui.deviceDetails.DeviceDetailsActivity.EXTRA_DEVICE_NAME_TRANSITION_NAME_KEY;


public class DeviceDetailsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, View.OnClickListener {
    private String deviceName;
    private String deviceId;
    private String iconTransitionName;
    private String nameTransitionName;

    public DeviceDetailsFragment() {
    }

    public static DeviceDetailsFragment newInstance(@NonNull String deviceId, @NonNull String deviceName,
                                                    @Nullable String iconSharedTransactionName, @Nullable String devNameSharedTransactionNAme) {
        Bundle args = new Bundle();
        args.putString(DEVICE_ID_EXTRA_KEY, deviceId);
        args.putString(DEVICE_NAME_EXTRA_KEY, deviceName);
        if (iconSharedTransactionName != null && devNameSharedTransactionNAme != null) {
            args.putString(EXTRA_DEVICE_ICON_TRANSITION_NAME_KEY, iconSharedTransactionName);
            args.putString(EXTRA_DEVICE_NAME_TRANSITION_NAME_KEY, devNameSharedTransactionNAme);
        }
        DeviceDetailsFragment fragment = new DeviceDetailsFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        deviceId = getArguments().getString(DEVICE_ID_EXTRA_KEY);
        deviceName = getArguments().getString(DEVICE_NAME_EXTRA_KEY);
        nameTransitionName = getArguments().getString(EXTRA_DEVICE_NAME_TRANSITION_NAME_KEY, null);
        iconTransitionName = getArguments().getString(EXTRA_DEVICE_ICON_TRANSITION_NAME_KEY, null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_device_details, container, false);


        ImageView titleIconView = (ImageView) rootView.findViewById(R.id.device_detail_icon);
        TextView titleView = (TextView) rootView.findViewById(R.id.device_detail_title_view);
        TextView subtitleView = (TextView) rootView.findViewById(R.id.device_detail_subtitle_view);
        CardView infoCard = (CardView) rootView.findViewById(R.id.device_details_info_card);
        CardView appsCard = (CardView) rootView.findViewById(R.id.device_details_apps_card);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            titleIconView.setTransitionName(iconTransitionName);
            titleView.setTransitionName(nameTransitionName);
        }


        infoCard.setOnClickListener(this);
        appsCard.setOnClickListener(this);


        titleIconView.setImageResource(R.drawable.ic_phone_android);
        titleView.setText(deviceName);
        subtitleView.setText(deviceId);



        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        getActivity().supportStartPostponedEnterTransition();
        getLoaderManager().initLoader(10, null, this);
        getLoaderManager().initLoader(11, null, this);
        getLoaderManager().initLoader(12, null, this);

        ApiRequestManager.getInstance(getContext()).getDeviceInstalledApps(deviceId);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri uri;
        switch (id) {
            case 10:
                //Return device information
                uri = McContract.Device.builUriWithDeviceID(deviceId);
                return new CursorLoader(getContext().getApplicationContext(), uri, null, null, null, null);
            case 11:
                //Return Profiles information
                //TODO: implement this
                return null;
            case 12:
                uri = McContract.InstalledApplications.buildUriWithDevId(deviceId);
                return new CursorLoader(getContext().getApplicationContext(), uri, null, null, null, null);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        switch (loader.getId()) {
            case 10:
                setDeviceInfoCard(data);
                break;
            case 11:
                break;
            case 12:
                setInstalledAppInfoCard(data);
                break;
            default:
                throw new UnsupportedOperationException("Unsupported id: " + loader.getId());
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    private void setDeviceInfoCard(Cursor c) {
        GridLayout infoGrid = (GridLayout) getActivity().findViewById(R.id.device_details_info_grid);
        TextView devName = (TextView) getActivity().findViewById(R.id.device_detail_title_view);

        if (c.moveToFirst()) {
            Boolean online = c.getInt(c.getColumnIndex(McContract.Device.COLUMN_AGENT_ONLINE)) == 1;
            String platform = c.getString(c.getColumnIndex(McContract.Device.COLUMN_PLATFORM));
            String osVersion = c.getString(c.getColumnIndex(McContract.Device.COLUMN_OS_VERSION));
            String hostName = c.getString(c.getColumnIndex(McContract.Device.COLUMN_HOST_NAME));
            HashMap<String, String> extraInfo = GeneralUtility
                    .formatDeviceExtraInfo(c.getString(c.getColumnIndex(McContract.Device.COLUMN_EXTRA_INFO)));

            Drawable dot;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                dot = getContext().getResources().getDrawable(R.drawable.connectivity_status_dot, null);
            } else {
                dot = getContext().getResources().getDrawable(R.drawable.connectivity_status_dot);
            }
            devName.setCompoundDrawablePadding(50);

            if (online) {
                ((GradientDrawable) dot).setColor(getContext().getResources().getColor(R.color.darkGreen));
            } else {
                ((GradientDrawable) dot).setColor(Color.LTGRAY);
            }
            devName.setCompoundDrawablesWithIntrinsicBounds(null, null, dot, null);

            ((TextView) infoGrid.getChildAt(1)).setText(getString(R.string.info_operating_system_label));
            ((TextView) infoGrid.getChildAt(2)).setText(platform.concat(" ").concat(osVersion));
            ((TextView) infoGrid.getChildAt(3)).setText(getString(R.string.info_last_agent_check_in_label));
            ((TextView) infoGrid.getChildAt(4)).setText(extraInfo.get("tLastCheckInTime"));
            ((TextView) infoGrid.getChildAt(5)).setText(getString(R.string.MobiControl));
            ((TextView) infoGrid.getChildAt(6)).setText(extraInfo.get("tAgentVersion"));
            ((TextView) infoGrid.getChildAt(7)).setText(getString(R.string.info_hostname_label));
            ((TextView) infoGrid.getChildAt(8)).setText(hostName);
        }
    }

    private void setInstalledAppInfoCard(Cursor c) {
        GridLayout infoGrid = (GridLayout) getActivity().findViewById(R.id.device_details_apps_grid_view);

        if (!c.moveToFirst()) {
            return;
        }

        for (int i = 1; i < infoGrid.getChildCount() - 1; i = i + 2) {
            ((TextView) infoGrid.getChildAt(i)).setText(c.getString(c.getColumnIndex(McContract.InstalledApplications.APPLICATION_NAME)));
            ((TextView) infoGrid.getChildAt(i + 1)).setText(c.getString(c.getColumnIndex(McContract.InstalledApplications.APPLICATION_STATUS)));
            if (!c.moveToNext()) {
                break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        FragmentTransaction transaction = (getActivity()).getSupportFragmentManager().beginTransaction();

        switch (v.getId()) {
            case R.id.device_details_info_card:
                transaction.replace(R.id.device_details_fragment_container, FullDeviceInfoFragment.newInstance())
                        .commit();
                break;
            case R.id.device_details_profiles_card:
                break;
            case R.id.device_details_apps_card:


                InstalledAppsFragment fragment = InstalledAppsFragment.newInstance();
                Bundle args = new Bundle();
                args.putString(DEVICE_ID_EXTRA_KEY, deviceId);
                fragment.setArguments(args);

//                fragment.setSharedElementEnterTransition(TransitionInflater.from(getContext()).inflateTransition(android.R.transition.explode));

//                transaction.setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_in_left);
                transaction.addSharedElement(v, ViewCompat.getTransitionName(v));
                transaction.addToBackStack(DeviceDetailsFragment.class.getSimpleName());
                transaction.replace(R.id.device_details_fragment_container, fragment)
                        .commit();
                break;
        }
    }
}
