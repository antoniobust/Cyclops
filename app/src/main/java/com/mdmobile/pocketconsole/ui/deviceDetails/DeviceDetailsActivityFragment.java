package com.mdmobile.pocketconsole.ui.deviceDetails;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mdmobile.pocketconsole.R;
import com.mdmobile.pocketconsole.apiHandler.ApiRequestManager;
import com.mdmobile.pocketconsole.provider.McContract;
import com.mdmobile.pocketconsole.utils.GeneralUtility;

import java.util.HashMap;


public class DeviceDetailsActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, View.OnClickListener {

    private String deviceName;
    private String deviceId;

    public DeviceDetailsActivityFragment() {
    }

    public static DeviceDetailsActivityFragment newInstance() {
        return new DeviceDetailsActivityFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(DeviceDetailsActivity.DEVICE_ID_EXTRA_KEY)
                && getArguments().containsKey(DeviceDetailsActivity.DEVICE_NAME_EXTRA_KEY)) {
            deviceId = getArguments().getString(DeviceDetailsActivity.DEVICE_ID_EXTRA_KEY);
            deviceName = getArguments().getString(DeviceDetailsActivity.DEVICE_NAME_EXTRA_KEY);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_device_details, container, false);

        ImageView titleIconView = (ImageView) rootView.findViewById(R.id.device_detail_icon);
        TextView titleView = (TextView) rootView.findViewById(R.id.device_detail_title_view);
        TextView subtitleView = (TextView) rootView.findViewById(R.id.device_detail_subtitle_view);
        CardView infoCard = (CardView) rootView.findViewById(R.id.device_details_info_card);

        infoCard.setOnClickListener(this);

        titleIconView.setImageResource(R.drawable.ic_phone_android);
        titleView.setText(deviceName);
        subtitleView.setText(deviceId);

        getLoaderManager().initLoader(10, null, this);
        getLoaderManager().initLoader(11, null, this);
        getLoaderManager().initLoader(12, null, this);

        ApiRequestManager.getInstance(getContext()).getDeviceInstalledApps(deviceId);

        return rootView;
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
                return new CursorLoader(getContext().getApplicationContext(), uri, null, null, null, McContract.InstalledApplications.APPLICATION_NAME);
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

        if (c.moveToFirst()) {
            String platform = c.getString(c.getColumnIndex(McContract.Device.COLUMN_PLATFORM));
            String osVersion = c.getString(c.getColumnIndex(McContract.Device.COLUMN_OS_VERSION));
            String hostName = c.getString(c.getColumnIndex(McContract.Device.COLUMN_HOST_NAME));
            HashMap<String, String> extraInfo = GeneralUtility
                    .formatDeviceExtraInfo(c.getString(c.getColumnIndex(McContract.Device.COLUMN_EXTRA_INFO)));


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

        for (int i = 1; i < infoGrid.getChildCount(); i = i + 2) {
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
                FullDeviceInfoFragment fragment = FullDeviceInfoFragment.newInstance();
                transaction.addToBackStack(null);
                transaction.replace(R.id.device_details_fragment_container, fragment).commit();

                break;
            case R.id.device_details_profiles_card:
                break;
            case R.id.device_details_apps_card:
                break;
        }
    }
}
