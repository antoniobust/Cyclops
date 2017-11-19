package com.mdmobile.pocketconsole.ui.main.server;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.mdmobile.pocketconsole.R;
import com.mdmobile.pocketconsole.provider.McContract;

public class ServerDetailsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Parcelable serverParcel = getIntent().getParcelableExtra(McContract.DEPLOYMENT_SERVER_TABLE_NAME);
        setContentView(R.layout.activity_server_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportFragmentManager().beginTransaction().replace(
                R.id.server_details_fragment_container, ServerDetailsFragment.newInstance(serverParcel)).commit();
    }

}
