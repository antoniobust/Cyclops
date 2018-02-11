package com.mdmobile.pocketconsole.ui.deviceDetails;

import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
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
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mdmobile.pocketconsole.R;
import com.mdmobile.pocketconsole.apiManager.ApiRequestManager;
import com.mdmobile.pocketconsole.provider.McContract;
import com.mdmobile.pocketconsole.ui.main.MainActivity;
import com.mdmobile.pocketconsole.utils.DbData;
import com.mdmobile.pocketconsole.utils.GeneralUtility;
import com.mdmobile.pocketconsole.utils.Logger;

import java.util.HashMap;

import static com.mdmobile.pocketconsole.ui.deviceDetails.DeviceDetailsActivity.DEVICE_ID_EXTRA_KEY;
import static com.mdmobile.pocketconsole.ui.deviceDetails.DeviceDetailsActivity.DEVICE_NAME_EXTRA_KEY;
import static com.mdmobile.pocketconsole.ui.deviceDetails.DeviceDetailsActivity.EXTRA_DEVICE_ICON_TRANSITION_NAME_KEY;
import static com.mdmobile.pocketconsole.ui.deviceDetails.DeviceDetailsActivity.EXTRA_DEVICE_NAME_TRANSITION_NAME_KEY;


public class DeviceDetailsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, View.OnClickListener,
        SwipeRefreshLayout.OnRefreshListener {
    private final String LOG_TAG = DeviceDetailsFragment.class.getSimpleName();
    private final int LOAD_PROFILE = 111, LOAD_APPS = 112, LOAD_INFO = 113;
    private String deviceName;
    private String deviceId;
    private String iconTransitionName;
    private String nameTransitionName;
    private View rootView;
    private ImageView batteryView, wifiView, simView, ramView, sdCardView;
    private SwipeRefreshLayout swipeLayout;

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

    // -- Interface methods
    @Override
    public void onRefresh() {
        Logger.log(LOG_TAG, "Device " + deviceId + " info update requested", Log.VERBOSE);
        ApiRequestManager.getInstance().getDeviceInfo(deviceId);
        swipeLayout.setRefreshing(false);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri uri;
        switch (id) {
            case LOAD_INFO:
                uri = McContract.Device.buildUriWithDeviceID(deviceId);
                return new CursorLoader(getContext().getApplicationContext(), uri, null, null, null, null);
            case LOAD_PROFILE:
                return new CursorLoader(getContext(),
                        McContract.Profile.buildUriWithDeviceId(deviceId),
                        null, null, null, McContract.Profile.ASSIGNMENT_DATE + " DESC");
            case LOAD_APPS:
                uri = McContract.InstalledApplications.buildUriWithDevId(deviceId);
                return new CursorLoader(getContext().getApplicationContext(), uri, null, null, null, null);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        switch (loader.getId()) {
            case LOAD_INFO:
                Cursor c = data;
                setCharts(c);
                setDeviceInfoCard(c);
                break;
            case LOAD_PROFILE: {
                if (data != null && data.getCount() == 0) {
                    ApiRequestManager.getInstance().getDeviceProfiles(deviceId);
                    return;
                }

                GridLayout gridLayout = rootView.findViewById(R.id.device_details_profiles_grid_view);
                String[] columns = {McContract.Profile.NAME,
                        McContract.Profile.STATUS};
                setCards(data, gridLayout, columns);
                break;
            }
            case LOAD_APPS: {
                if (data != null && data.getCount() == 0) {
                    ApiRequestManager.getInstance().getDeviceInstalledApps(deviceId);
                    return;
                }

                GridLayout gridLayout = rootView.findViewById(R.id.device_details_apps_grid_view);
                String[] columns = {McContract.InstalledApplications.APPLICATION_NAME,
                        McContract.InstalledApplications.APPLICATION_STATUS};
                setCards(data, gridLayout, columns);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unsupported id: " + loader.getId());
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }


    // -- Lifecycle methods

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
        rootView = inflater.inflate(R.layout.fragment_device_details, container, false);


        Toolbar toolbar = rootView.findViewById(R.id.toolbar);
        if (GeneralUtility.isTabletMode(getContext())) {
            toolbar.setVisibility(View.GONE);
        }

        ActionBar actionBar;
        ((DeviceDetailsActivity) getActivity()).setSupportActionBar(toolbar);
        actionBar = ((DeviceDetailsActivity) getActivity()).getSupportActionBar();

        if (actionBar != null) {
            actionBar.setTitle(deviceName);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        swipeLayout = rootView.findViewById(R.id.device_info_swipe_to_refresh);
        swipeLayout.setOnRefreshListener(this);

        ImageView titleIconView = rootView.findViewById(R.id.device_detail_icon);
        TextView titleView = rootView.findViewById(R.id.device_detail_title_view);
        TextView subtitleView = rootView.findViewById(R.id.device_detail_subtitle_view);
        CardView infoCard = rootView.findViewById(R.id.device_details_info_card);
        CardView appsCard = rootView.findViewById(R.id.device_details_apps_card);
        CardView profilesCard = rootView.findViewById(R.id.device_details_profiles_card);
        batteryView = rootView.findViewById(R.id.device_details_battery);
        wifiView = rootView.findViewById(R.id.device_details_wifi);
        simView = rootView.findViewById(R.id.device_details_simcard);
        ramView = rootView.findViewById(R.id.device_details_memory);
        sdCardView = rootView.findViewById(R.id.device_details_sdcard);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            titleIconView.setTransitionName(iconTransitionName);
            titleView.setTransitionName(nameTransitionName);
        }

        infoCard.setOnClickListener(this);
        appsCard.setOnClickListener(this);
        profilesCard.setOnClickListener(this);

        titleIconView.setImageResource(R.drawable.ic_phone_android);
        titleView.setText(deviceName);
        subtitleView.setText(deviceId);


        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.device_action_menu, menu);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

//        getActivity().supportStartPostponedEnterTransition();
        getLoaderManager().initLoader(LOAD_INFO, null, this);
        getLoaderManager().initLoader(LOAD_PROFILE, null, this);
        getLoaderManager().initLoader(LOAD_APPS, null, this);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (MainActivity.TABLET_MODE) {
                hideDetailsFragment();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void setDeviceInfoCard(Cursor c) {
        GridLayout infoGrid = getActivity().findViewById(R.id.device_details_info_grid);
        TextView devName = getActivity().findViewById(R.id.device_detail_title_view);

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

    private void setCards(Cursor c, GridLayout gridLayout, String... columnName) {
        if (!c.moveToFirst()) {
            return;
        }

        for (int i = 1; i < gridLayout.getChildCount() - 1; i = i + 2) {
            if (!c.moveToPosition(i - 1)) {
                return;
            }
            ((TextView) gridLayout.getChildAt(i)).setText(c.getString(c.getColumnIndex(columnName[0])));
            ((TextView) gridLayout.getChildAt(i + 1)).setText(c.getString(c.getColumnIndex(columnName[1])));
            if (!c.moveToNext()) {
                break;
            }
        }
    }

    private void setCharts(Cursor data) {
        data.moveToFirst();
        Bundle extraInfo = DbData.getDeviceExtraInfo(data.getString(24));

        String value = extraInfo.getString("tBatteryStatus");
        if (value == null) {
            value = "0";
        }
        int val = Integer.valueOf(value);
        LayerDrawable clipBar = (LayerDrawable) batteryView.getDrawable();

        Drawable barFilling = clipBar.findDrawableByLayerId(R.id.horizontal_bar_filling);
        if (val > 0 && val < 20) {
            barFilling.setTint(getContext().getResources().getColor(android.R.color.holo_red_light));
        }
        barFilling.setLevel(val * 100);
    }


    @Override
    public void onClick(View v) {
        FragmentTransaction transaction = (getActivity()).getSupportFragmentManager().beginTransaction();
        Fragment newFrag;

        switch (v.getId()) {
            case R.id.device_details_info_card:
                newFrag = FullDeviceInfoFragment.Companion.newInstance(deviceId);
                break;
            case R.id.device_details_profiles_card:
                newFrag = ProfilesFragment.newInstance(deviceId);
                break;
            case R.id.device_details_apps_card:
                newFrag = InstalledAppsFragment.newInstance(deviceId);
                break;
            default:
                Logger.log(LOG_TAG, v.getId() + " view not supported in OnClick", Log.ERROR);
                return;
        }
        transaction.addSharedElement(v, ViewCompat.getTransitionName(v));
        transaction.addToBackStack(DeviceDetailsFragment.class.getSimpleName());
        transaction.replace(R.id.device_details_fragment_container, newFrag).commit();
    }

    private void hideDetailsFragment() {
//        LinearLayout mainContainer = (LinearLayout) getActivity().findViewById(R.id.main_activity_linear_layout);
//        SwipeRefreshLayout deviceList = (SwipeRefreshLayout) mainContainer.findViewById(R.id.devices_swipe_refresh);
//        CardView detailsContainer = (CardView) mainContainer.findViewById(R.id.main_activity_device_details_container);
//
//        if (detailsContainer.getVisibility() == View.VISIBLE) {
//            detailsContainer.setVisibility(View.GONE);
//        }
//
//        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(getResources().getDimensionPixelSize(R.dimen.main_fragment_with), ViewGroup.LayoutParams.WRAP_CONTENT);
//        layoutParams.gravity = Gravity.CENTER;
//        mainContainer.setLayoutParams(layoutParams);
//
//        deviceList.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        deviceList.requestLayout();
    }
}
