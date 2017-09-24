package com.mdmobile.pocketconsole;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mdmobile.pocketconsole.adapters.ServerDetailsAdapter;

/**
 * A placeholder fragment containing a simple view.
 */
public class ServerDetailsFragment extends Fragment {

    public final static String SERVER_PARCELABLE_ARGUMENT = "ServerParcelableArgument";

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_server_details, container, false);
        RecyclerView recycler = (RecyclerView) rootView.findViewById(R.id.server_details_recycler);
        recycler.setLayoutManager(new GridLayoutManager(getContext(), 2));

        recycler.setAdapter(new ServerDetailsAdapter(null));

        return rootView;
    }
}
