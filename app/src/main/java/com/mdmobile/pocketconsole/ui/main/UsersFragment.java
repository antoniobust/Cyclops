package com.mdmobile.pocketconsole.ui.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mdmobile.pocketconsole.R;


public class UsersFragment extends Fragment {

    public UsersFragment() {
        // Required empty public constructor
    }

    public static UsersFragment newInstance() {

        return new UsersFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        //Apply style to fragment
//        final Context contextThemeWrapper = new android.view.ContextThemeWrapper(getActivity(), R.style.AppTheme_MainActivity_Fragment);
//        //Clone inflater using the contextTHemeWrapper
//        inflater = inflater.cloneInContext(contextThemeWrapper);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_users, container, false);
    }
}
