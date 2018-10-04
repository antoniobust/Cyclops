package com.mdmobile.cyclops.ui.logIn;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mdmobile.cyclops.R;
import com.mdmobile.cyclops.api.ApiRequestManager;
import com.mdmobile.cyclops.interfaces.NetworkCallBack;
import com.mdmobile.cyclops.security.ServerNotFound;
import com.mdmobile.cyclops.utils.Logger;
import com.mdmobile.cyclops.utils.ServerUtility;

import java.util.Objects;


public class AddNewUserFragment extends Fragment {

    private static final String LOG_TAG = AddNewUserFragment.class.getSimpleName();
    private static final String USER_NAME_KEY = "USER_NAME_KEY";
    private static final String PASSWORD_KEY = "PASSWORD_KEY";
    private TextView userNameView, passwordView;
    private LoginActivity hostingActivity;
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
        hostingActivity = (LoginActivity) getActivity();

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(USER_NAME_KEY)) {
                userNameView.setText(savedInstanceState.getString(USER_NAME_KEY));
            }
            if (savedInstanceState.containsKey(PASSWORD_KEY)) {
                passwordView.setText(savedInstanceState.getString(PASSWORD_KEY));
            }
        }
        LoginActivity hosting = ((LoginActivity) Objects.requireNonNull(getActivity()));
        hosting.actionChip.setText(R.string.logIn_label);
        hosting.actionChip
                .setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        passwordView.setOnTouchListener(pwdVisibilityListener);
        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public void logIn() {
        String userName = userNameView.getText().toString();
        String password = passwordView.getText().toString();
        if (!(userName.length() > 0 && password.length() > 0)) {
            return;
        }

        Logger.log(LOG_TAG, "Requesting token...", Log.VERBOSE);
        hostingActivity.actionChip.setVisibility(View.GONE);
        hostingActivity.progressBar.setVisibility(View.VISIBLE);
        try {
            ApiRequestManager.getInstance().getToken(
                    ServerUtility.getActiveServer(),
                    userName, password, (NetworkCallBack) getActivity());
        } catch (ServerNotFound e) {
            Toast.makeText(getContext(), "Add at least one instance...", Toast.LENGTH_LONG).show();
            hostingActivity.progressBar.setVisibility(View.GONE);
            hostingActivity.actionChip.setVisibility(View.VISIBLE);
        }
    }

}
