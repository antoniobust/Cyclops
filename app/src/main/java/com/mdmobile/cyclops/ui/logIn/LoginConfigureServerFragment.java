package com.mdmobile.cyclops.ui.logIn;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mdmobile.cyclops.R;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static com.mdmobile.cyclops.utils.GeneralUtility.validateUrl;


public class LoginConfigureServerFragment extends Fragment implements View.OnFocusChangeListener {

    private EditText serverAddressEditText, serverNameEditText;

    public LoginConfigureServerFragment() {
        //Empty constructor required
    }

    public static LoginConfigureServerFragment newInstance() {
        return new LoginConfigureServerFragment();
    }

    //Interfaces
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            return;
        }
        switch (v.getId()) {
            case R.id.instance_name_text_view:
                ((LoginActivity) getActivity()).viewModel.updateInstanceName(((EditText) v).getText().toString());
                break;
            case R.id.instance_address_text_view:
                ((LoginActivity) getActivity()).viewModel.updateInstanceAddress(((EditText) v).getText().toString());
                break;
            default:
                throw new IllegalArgumentException("Unknown view ID: " + v.getId());
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_server_name, container, false);
        serverAddressEditText = rootView.findViewById(R.id.instance_address_text_view);
        serverNameEditText = rootView.findViewById(R.id.instance_name_text_view);

        serverNameEditText.setOnFocusChangeListener(this);
        serverAddressEditText.setOnFocusChangeListener(this);

        return rootView;
    }

    public void serverConnectionTest(View v) {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        String serverUrl = serverAddressEditText.getText().toString();

        serverUrl = validateUrl(serverUrl);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, serverUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }
}
