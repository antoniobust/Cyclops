package com.mdmobile.pocketconsole.UI;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.mdmobile.pocketconsole.Adapters.LogInViewPagerAdapter;
import com.mdmobile.pocketconsole.R;

public class LoginActivity extends AppCompatActivity {

    private final String SERVER_ADDRESS_KEY = "serverAddressKey", CLIENT_ID_KEY = "clientIdKey", API_SECRET_KEY = "apiSecretKey",
            USER_NAME_KEY = "userNameKey", PASSWORD_KEY = "passwordKey";
    ViewPager viewPager;
    TabLayout dotsIndicator;
    EditText serverAddressEditText, clientIdEditText, apiSecretEditText, userNameEditText, passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Instantiate needed views
        viewPager = (ViewPager) findViewById(R.id.login_view_pager);
        dotsIndicator = (TabLayout) findViewById(R.id.login_view_pager_dots_indicator);
        serverAddressEditText = (EditText) findViewById(R.id.server_address_text_view);
        clientIdEditText = (EditText) findViewById(R.id.client_id_text_view);
        apiSecretEditText = (EditText) findViewById(R.id.api_secret_text_view);
        passwordEditText = (EditText) findViewById(R.id.password_text_view);
        userNameEditText = (EditText) findViewById(R.id.user_name_text_view);

        //Configure viewPager
        setViewPager();

        if (savedInstanceState != null) {
            restoreViewsState(savedInstanceState);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //Save state for all text views
        if (serverAddressEditText != null && serverAddressEditText.getText() != null) {
            outState.putString(SERVER_ADDRESS_KEY, serverAddressEditText.getText().toString());
        }
        if (clientIdEditText != null && clientIdEditText.getText() != null) {
            outState.putString(CLIENT_ID_KEY, clientIdEditText.getText().toString());
        }
        if (apiSecretEditText != null && apiSecretEditText.getText() != null) {
            outState.putString(API_SECRET_KEY, apiSecretEditText.getText().toString());
        }
        if (userNameEditText != null && userNameEditText.getText() != null) {
            outState.putString(USER_NAME_KEY, userNameEditText.getText().toString());
        }
        if (passwordEditText != null && passwordEditText.getText() != null) {
            outState.putString(PASSWORD_KEY, passwordEditText.getText().toString());
        }

    }

    private void setViewPager(){
        //Set adapter to viewPager and adjust margin between pages
        viewPager.setAdapter(new LogInViewPagerAdapter(getSupportFragmentManager()));
        viewPager.setPageMargin(80);

        //Attach custom indicator to view pager
        dotsIndicator.setupWithViewPager(viewPager, true);

        //Adjust margin between tabs in tabLayout
        View dot;
        //Get dots container
        ViewGroup dotsContainer = (ViewGroup) dotsIndicator.getChildAt(0);
        //Instantiate margin to apply
        ViewGroup.MarginLayoutParams p;

        //Navigate through all the indicator dots and apply margins
        for (int i = 0; i < dotsIndicator.getTabCount(); i++) {
            dot = dotsContainer.getChildAt(i);
            p = (ViewGroup.MarginLayoutParams) dot.getLayoutParams();
            p.setMargins(5, 0, 5, 0);
            dot.requestLayout();
        }
    }

    private void restoreViewsState(Bundle savedInstanceState) {
        if (savedInstanceState.containsKey(SERVER_ADDRESS_KEY)) {
            serverAddressEditText.setText(savedInstanceState.getString(SERVER_ADDRESS_KEY));
        }
        if (savedInstanceState.containsKey(CLIENT_ID_KEY)) {
            clientIdEditText.setText(savedInstanceState.getString(CLIENT_ID_KEY));
        }
        if (savedInstanceState.containsKey(API_SECRET_KEY)) {
            apiSecretEditText.setText(savedInstanceState.getString(API_SECRET_KEY));
        }
        if (savedInstanceState.containsKey(USER_NAME_KEY)) {
            userNameEditText.setText(savedInstanceState.getString(USER_NAME_KEY));
        }
        if (savedInstanceState.containsKey(PASSWORD_KEY)) {
            passwordEditText.setText(savedInstanceState.getString(PASSWORD_KEY));
        }
    }

}

