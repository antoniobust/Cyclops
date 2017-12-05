package com.mdmobile.pocketconsole.ui.logIn;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.mdmobile.pocketconsole.R;

public class LoginConfigureSecretIdFragment extends Fragment {

    public EditText clientIdEditText, apiSecretEditText;

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
        View rootView = inflater.inflate(R.layout.fragment_api_client_secret, container, false);
        clientIdEditText = rootView.findViewById(R.id.client_id_text_view);
        apiSecretEditText = rootView.findViewById(R.id.api_secret_text_view);
        return rootView;
    }
}
