package com.mdmobile.pocketconsole.ui.deviceDetails;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mdmobile.pocketconsole.R;


public class FullDeviceInfoFragment extends Fragment {
    public FullDeviceInfoFragment() {
    }

    public static FullDeviceInfoFragment newInstance() {
        return new FullDeviceInfoFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.full_device_information_fragment, container, false);
    }
}
