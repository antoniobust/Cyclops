package com.mdmobile.pocketconsole.UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.mdmobile.pocketconsole.R;

import static com.mdmobile.pocketconsole.utils.GeneralUtility.validateUrl;


public class LoginConfigureServerFragment extends Fragment {

    public EditText serverAddressEditText;

    public LoginConfigureServerFragment() {
        //Empty constructor required
    }

    public static LoginConfigureServerFragment newInstance() {
        return new LoginConfigureServerFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.server_name_fragment, container, false);
        serverAddressEditText = (EditText) rootView.findViewById(R.id.server_address_text_view);

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
