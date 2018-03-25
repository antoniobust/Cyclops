package com.mdmobile.cyclopes.ui.logIn;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mdmobile.cyclopes.R;
import com.mdmobile.cyclopes.apiManager.ApiRequestManager;
import com.mdmobile.cyclopes.interfaces.NetworkCallBack;
import com.mdmobile.cyclopes.provider.McContract;
import com.mdmobile.cyclopes.utils.Logger;
import com.mdmobile.cyclopes.utils.ServerUtility;

import static com.mdmobile.cyclopes.utils.ServerUtility.SERVER_ADDRESS_KEY;


public class AddNewUserFragment extends Fragment implements View.OnClickListener {

    private static final String LOG_TAG = AddNewUserFragment.class.getSimpleName();
    private static final String USER_NAME_KEY = "USER_NAME_KEY";
    private static final String PASSWORD_KEY = "PASSWORD_KEY";
    private TextView userNameView, passwordView;
    private Spinner serverSpinner;
    private Button loginButton;

    public AddNewUserFragment() {
        //Empty constructor required
    }

    public static AddNewUserFragment newInstance() {
        return new AddNewUserFragment();
    }

    // -- Interface methods
    // LOGIN onClick function
    @Override
    public void onClick(View v) {
        String userName = userNameView.getText().toString();
        String password = passwordView.getText().toString();
        if (!(userName.length() > 0 && password.length() > 0)) {
            return;
        }
        Bundle serverInfo = ServerUtility.getServer();

        if (serverInfo == null) {
            Toast.makeText(getContext(), "Add server", Toast.LENGTH_SHORT).show();
            return;
        }

        Logger.log(LOG_TAG, "Requesting token...", Log.VERBOSE);
        ApiRequestManager.getInstance().getToken(
                serverInfo.getString(SERVER_ADDRESS_KEY),
                serverInfo.getString(McContract.ServerInfo.CLIENT_ID),
                serverInfo.getString(McContract.ServerInfo.CLIENT_SECRET),
                userName, password, (NetworkCallBack) getActivity());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_user, container, false);
        userNameView = rootView.findViewById(R.id.user_name_text_view);
        passwordView = rootView.findViewById(R.id.password_text_view);
        serverSpinner = rootView.findViewById(R.id.login_servers_spinner);
        loginButton = rootView.findViewById(R.id.login_button);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(USER_NAME_KEY)) {
                userNameView.setText(savedInstanceState.getString(USER_NAME_KEY));
            }
            if (savedInstanceState.containsKey(PASSWORD_KEY)) {
                passwordView.setText(savedInstanceState.getString(PASSWORD_KEY));
            }
        }

        loginButton.setOnClickListener(this);
        serverSpinner.setAdapter(null);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().findViewById(R.id.add_user_button).setVisibility(View.INVISIBLE);
        getActivity().findViewById(R.id.add_server_button).setVisibility(View.VISIBLE);
    }
}
