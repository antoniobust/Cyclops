package com.mdmobile.pocketconsole.ui.logIn;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mdmobile.pocketconsole.R;


public class AddNewUserFragment extends Fragment {

    public AddNewUserFragment() {
        //Empty constructor required
    }

    public static AddNewUserFragment newInstance() {
        return new AddNewUserFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_user, container, false);
    }
}
