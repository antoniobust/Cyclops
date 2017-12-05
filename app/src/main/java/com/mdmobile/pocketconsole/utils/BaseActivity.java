package com.mdmobile.pocketconsole.utils;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.mdmobile.pocketconsole.ui.logIn.LoginActivity;

/**
 * Every Activity should extend this base Activity
 * It handles:
 * 1. checks for account validity
 */

public class BaseActivity extends AppCompatActivity {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        if (!checkActiveUser()) {
            redirectToLoginPage();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!checkActiveUser()) {
            redirectToLoginPage();
        }
    }

    private boolean checkActiveUser() {
        return UserUtility.checkAnyUserLoggedIn();
    }

    private void redirectToLoginPage(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
