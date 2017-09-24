package com.mdmobile.pocketconsole;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mdmobile.pocketconsole.adapters.MsInfoAdapter;
import com.mdmobile.pocketconsole.adapters.ServerDetailsAdapter;
import com.mdmobile.pocketconsole.gson.ServerInfo;

/**
 * A placeholder fragment containing a simple view.
 */
public class ServerDetailsFragment extends Fragment {

    private final static String SERVER_PARCELABLE_ARGUMENT = "ServerParcelableArgument";
    private ServerDetailsAdapter serverDetailsAdapter;

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
            serverDetailsAdapter = new ServerDetailsAdapter(getContext(),(ServerInfo.ManagementServer) parcel);
        } else {
            serverDetailsAdapter = new ServerDetailsAdapter(getContext(),(ServerInfo.DeploymentServer) parcel);
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
}
