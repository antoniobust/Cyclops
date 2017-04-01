package com.mdmobile.pocketconsole.UI;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mdmobile.pocketconsole.R;

public class LoginConfigureSecretIdFragment extends Fragment {

    public LoginConfigureSecretIdFragment() {
        //Empty constructor required
    }

    public static LoginConfigureSecretIdFragment newInstance() {
        return new LoginConfigureSecretIdFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.api_client_secret_fragment, container, false);
    }
}
