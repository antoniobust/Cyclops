package com.mdmobile.pocketconsole.ui.deviceDetails;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mdmobile.pocketconsole.R;
import com.mdmobile.pocketconsole.adapters.LabelsCursorAdapter;
import com.mdmobile.pocketconsole.provider.McContract;

import static com.mdmobile.pocketconsole.ui.deviceDetails.DeviceDetailsActivity.DEVICE_ID_EXTRA_KEY;


public class FullDeviceInfoFragment extends Fragment implements android.support.v4.app.LoaderManager.LoaderCallbacks<Cursor> {
    String[] infoLabels;
    String deviceID;
    private RecyclerView infoRecycler;

    public FullDeviceInfoFragment() {
    }

    public static FullDeviceInfoFragment newInstance(@NonNull String deviceID) {
        Bundle args = new Bundle(1);
        args.putString(DEVICE_ID_EXTRA_KEY, deviceID);
        FullDeviceInfoFragment fragment = new FullDeviceInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        deviceID = getArguments().getString(DEVICE_ID_EXTRA_KEY);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_full_device_information, container, false);

        infoRecycler = rootView.findViewById(R.id.device_details_recycler);
        infoRecycler.setLayoutManager(new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL, false));

        infoLabels = getResources().getStringArray(R.array.devices_info_labels);
        LabelsCursorAdapter adapter = new LabelsCursorAdapter(null, infoLabels);

        infoRecycler.setAdapter(adapter);
        getLoaderManager().initLoader(200, null, this);
        return rootView;
    }

    @Override
    public android.support.v4.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new android.support.v4.content.CursorLoader(getContext(), McContract.Device.buildUriWithDeviceID(deviceID), null, null, null, null);
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<Cursor> loader, Cursor data) {
        infoRecycler.swapAdapter(new LabelsCursorAdapter(data, infoLabels), true);
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<Cursor> loader) {
        infoRecycler.swapAdapter(null, false);
    }
}
