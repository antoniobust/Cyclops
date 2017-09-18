package com.mdmobile.pocketconsole;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A placeholder fragment containing a simple view.
 */
public class ServerDetailsFragment extends Fragment {

    public ServerDetailsFragment() {
    }

    public static ServerDetailsFragment newInstance() {
        return new ServerDetailsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_server_details, container, false);
    }
}
