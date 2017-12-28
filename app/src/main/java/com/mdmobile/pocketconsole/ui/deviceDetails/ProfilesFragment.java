package com.mdmobile.pocketconsole.ui.deviceDetails;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mdmobile.pocketconsole.R;
import com.mdmobile.pocketconsole.adapters.ProfileListAdapter;
import com.mdmobile.pocketconsole.provider.McContract;

/**
 * Show list of profiles applied to the selected device
 */
//TODO: carry on from here
public class ProfilesFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private String devId;
    private RecyclerView profileRecycler;
    ProfileListAdapter adapter;

    public ProfilesFragment() {
        // -- Fragment empty constructor
    }

    public static ProfilesFragment newInstence(@NonNull String devID) {
        Bundle args = new Bundle();
        args.putString(DeviceDetailsActivity.DEVICE_ID_EXTRA_KEY, devID);
        ProfilesFragment frag = new ProfilesFragment();
        frag.setArguments(args);
        return frag;
    }

    // -- Lifecycle methods
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        devId = getArguments().getString(DeviceDetailsActivity.DEVICE_ID_EXTRA_KEY);
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile_list, container, true);
        profileRecycler = rootView.findViewById(R.id.profile_list_recycler);
        adapter = new ProfileListAdapter(null);
        profileRecycler.setAdapter(adapter);
        getLoaderManager().initLoader(100, null, this);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    // - Interface methods

    @Override
    public android.support.v4.content.Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new android.support.v4.content.CursorLoader(getContext(), McContract.Profile.buildUriWithDeviceID(devId), null, null, null, McContract.Profile.NAME);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        adapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
