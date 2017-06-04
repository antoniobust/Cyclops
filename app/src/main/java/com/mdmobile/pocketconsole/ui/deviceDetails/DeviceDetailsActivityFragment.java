package com.mdmobile.pocketconsole.ui.deviceDetails;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mdmobile.pocketconsole.R;


public class DeviceDetailsActivityFragment extends Fragment {

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

        titleIconView.setImageResource(R.drawable.ic_phone_android);
        titleView.setText(deviceName);
        subtitleView.setText(deviceId);
        return rootView;
    }
}
