package com.mdmobile.cyclops.ui.logIn;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mdmobile.cyclops.R;
import com.mdmobile.cyclops.apiManager.ApiRequestManager;
import com.mdmobile.cyclops.dataModel.Server;
import com.mdmobile.cyclops.interfaces.NetworkCallBack;
import com.mdmobile.cyclops.sec.ServerNotFound;
import com.mdmobile.cyclops.utils.Logger;
import com.mdmobile.cyclops.utils.ServerUtility;


public class AddNewUserFragment extends Fragment implements View.OnClickListener {

    private static final String LOG_TAG = AddNewUserFragment.class.getSimpleName();
    private static final String USER_NAME_KEY = "USER_NAME_KEY";
    private static final String PASSWORD_KEY = "PASSWORD_KEY";
    private TextView userNameView, passwordView;
    private Button loginButton;
    private ProgressBar progressBar;
    private View.OnTouchListener pwdVisibilityListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            view.performClick();
            if (motionEvent.getRawX() >= passwordView.getRight() - passwordView.getCompoundDrawables()[2].getBounds().width()) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_UP: {
                        passwordView.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, view.getContext().getDrawable(R.drawable.ic_visibility_off), null);
                        passwordView.setInputType(InputType.TYPE_CLASS_TEXT |
                                InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        return true;
                    }
                    case MotionEvent.ACTION_DOWN: {
                        passwordView.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, view.getContext().getDrawable(R.drawable.ic_visibility_on), null);
                        passwordView.setInputType(InputType.TYPE_CLASS_TEXT);
                        return true;
                    }
                    default:
                        return false;
                }
            }
            return false;
        }
    };

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

        Logger.log(LOG_TAG, "Requesting token...", Log.VERBOSE);
        loginButton.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        try {
            ApiRequestManager.getInstance().getToken(
                    ServerUtility.getActiveServer(),
                    userName, password, (NetworkCallBack) getActivity());
        }catch (ServerNotFound e){
            Toast.makeText(getContext(),"Add at least one instance...",Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);
            loginButton.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_user, container, false);
        userNameView = rootView.findViewById(R.id.user_name_text_view);
        passwordView = rootView.findViewById(R.id.password_text_view);
        loginButton = rootView.findViewById(R.id.login_button);
        progressBar = rootView.findViewById(R.id.login_progress_view);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(USER_NAME_KEY)) {
                userNameView.setText(savedInstanceState.getString(USER_NAME_KEY));
            }
            if (savedInstanceState.containsKey(PASSWORD_KEY)) {
                passwordView.setText(savedInstanceState.getString(PASSWORD_KEY));
            }
        }

        loginButton.setOnClickListener(this);
        passwordView.setOnTouchListener(pwdVisibilityListener);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() != null) {
            getActivity().findViewById(R.id.add_user_button).setVisibility(View.INVISIBLE);
            getActivity().findViewById(R.id.add_server_button).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

}
