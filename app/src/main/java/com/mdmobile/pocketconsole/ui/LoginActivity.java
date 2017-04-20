package com.mdmobile.pocketconsole.ui;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.mdmobile.pocketconsole.R;
import com.mdmobile.pocketconsole.adapters.LogInViewPagerAdapter;

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

        //Instantiate views
        viewPager = (ViewPager) findViewById(R.id.login_view_pager);
        dotsIndicator = (TabLayout) findViewById(R.id.login_view_pager_dots_indicator);

        //Configure viewPager
        setViewPager();
    }


    //LogIn Button OnClick function
    public void registerApiClient(View v) {

        Bundle serverConfiguration = new Bundle();

        //Get all fragments in the viewPager
        ViewGroup[] displayedFrags = new ViewGroup[viewPager.getChildCount()];
        String tempString, tempString1;

        for (int i = 0; i < viewPager.getChildCount(); i++) {

            tempString = null;
            tempString1 = null;

            switch (i) {
                case 0:

                    tempString = ((TextView) viewPager.getChildAt(i).findViewById(R.id.server_address_text_view)).getText().toString();

                    if (tempString.equals("")) {
                        showInvalidInputSneakBar();
                        break;
                    } else {
                        serverConfiguration
                                .putString(SERVER_ADDRESS_KEY, tempString);
                        continue;
                    }
                case 1:
                    tempString = ((TextView) viewPager.getChildAt(i).findViewById(R.id.client_id_text_view)).getText().toString();
                    tempString1 = ((TextView) viewPager.getChildAt(i).findViewById(R.id.api_secret_text_view)).getText().toString();
                    if (tempString.equals("") || tempString1.equals("")) {
                        showInvalidInputSneakBar();
                        break;
                    } else {
                        serverConfiguration.putString(CLIENT_ID_KEY, tempString);
                        serverConfiguration.putString(API_SECRET_KEY, tempString);
                        continue;
                    }
                case 2:
                    tempString = ((TextView) viewPager.getChildAt(i).findViewById(R.id.user_name_text_view)).getText().toString();
                    tempString1 = ((TextView) viewPager.getChildAt(i).findViewById(R.id.password_text_view)).getText().toString();
                    if (tempString.equals("") || tempString1.equals("")) {
                        showInvalidInputSneakBar();
                        break;
                    } else {
                        serverConfiguration.putString(USER_NAME_KEY, tempString);
                        serverConfiguration.putString(PASSWORD_KEY, tempString);
                    }
            }
        }

    }

    private void setViewPager() {
        //Set adapter to viewPager
        viewPager.setAdapter(new LogInViewPagerAdapter(getSupportFragmentManager()));
        //Adjust margin between pages
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

    private void showInvalidInputSneakBar() {
        Snackbar.make(viewPager, "Invalid input", Snackbar.LENGTH_SHORT).show();
    }

}

