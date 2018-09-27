package com.mdmobile.cyclops.ui.main.server;

import android.os.Bundle;
import android.os.Parcelable;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mdmobile.cyclops.R;
import com.mdmobile.cyclops.adapters.ServerDetailsAdapter;
import com.mdmobile.cyclops.dataModel.api.ServerInfo;

/**
 * A placeholder fragment containing a simple view.
 */
public class ServerDetailsFragment extends Fragment {

    private final static String SERVER_PARCELABLE_ARGUMENT = "ServerParcelableArgument";
    private ServerDetailsAdapter serverDetailsAdapter;
    private String actionBarTitle;

    public ServerDetailsFragment() {
    }

    public static ServerDetailsFragment newInstance(Parcelable serverParcel) {
        Bundle args = new Bundle();
        args.putParcelable(SERVER_PARCELABLE_ARGUMENT, serverParcel);
        ServerDetailsFragment fragment = new ServerDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Parcelable parcel =getArguments().getParcelable(SERVER_PARCELABLE_ARGUMENT);
        if(parcel instanceof ServerInfo.ManagementServer) {
            ServerInfo.ManagementServer serverInfo = (ServerInfo.ManagementServer) parcel;
            serverDetailsAdapter = new ServerDetailsAdapter(getContext(),(ServerInfo.ManagementServer) parcel);
            actionBarTitle = serverInfo.getName();
        } else {
            ServerInfo.DeploymentServer serverInfo = (ServerInfo.DeploymentServer) parcel;
            serverDetailsAdapter = new ServerDetailsAdapter(getContext(),(ServerInfo.DeploymentServer) parcel);
            actionBarTitle = serverInfo.getName();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_server_details, container, false);
        RecyclerView recycler = (RecyclerView) rootView.findViewById(R.id.server_details_recycler);
        recycler.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recycler.setAdapter(serverDetailsAdapter);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(actionBarTitle);
    }
}
