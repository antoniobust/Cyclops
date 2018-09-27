package com.mdmobile.cyclops.ui.main.deviceDetails;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mdmobile.cyclops.R;
import com.mdmobile.cyclops.dataModel.api.Profile;

import java.util.ArrayList;

/**
 * Show list of profiles applied to the selected device
 */

public class ProfilesFragment extends Fragment {
    InfoAdapter adapter;
    private String devId;
    private ArrayList<Profile> profileList;

    public ProfilesFragment() {
        // -- Fragment empty constructor
    }

    public static ProfilesFragment newInstance(@NonNull String devID, @NonNull ArrayList<Profile> profileList) {
        Bundle args = new Bundle();
        args.putString(DeviceDetailsActivity.DEVICE_ID_EXTRA_KEY, devID);
        args.putParcelableArrayList(DeviceDetailsActivity.DEVICE_PROFILES_EXTRA_KEY, profileList);
        ProfilesFragment frag = new ProfilesFragment();
        frag.setArguments(args);
        return frag;
    }

    // -- Lifecycle methods
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            devId = getArguments().getString(DeviceDetailsActivity.DEVICE_ID_EXTRA_KEY);
            profileList = getArguments().getParcelableArrayList(DeviceDetailsActivity.DEVICE_PROFILES_EXTRA_KEY);
        }
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile_list, container, false);
        RecyclerView profileRecycler = rootView.findViewById(R.id.profile_list_recycler);
        profileRecycler.setLayoutManager(new GridLayoutManager(getContext(),3));

        ArrayList<String[]> info = new ArrayList<>();
        for (Profile p : profileList) {
            info.add(new String[]{p.getName(), p.getStatus(), String.valueOf(p.getVersionNumber())});
        }
        adapter = new InfoAdapter(info, false);
        profileRecycler.setAdapter(adapter);

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
}
