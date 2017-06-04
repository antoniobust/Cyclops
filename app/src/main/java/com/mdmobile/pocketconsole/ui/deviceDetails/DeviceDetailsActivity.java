package com.mdmobile.pocketconsole.ui.deviceDetails;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.mdmobile.pocketconsole.R;

public class DeviceDetailsActivity extends AppCompatActivity {

    public final static String DEVICE_NAME_EXTRA_KEY = "DeviceNameIntentExtraKey";
    public final static String DEVICE_ID_EXTRA_KEY = "DeviceUriIntentExtraKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String deviceName = getIntent().getStringExtra(DEVICE_NAME_EXTRA_KEY);
        String deviceId = getIntent().getStringExtra(DEVICE_ID_EXTRA_KEY);

        setContentView(R.layout.activity_device_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            if (deviceName.length() > 20) {
                actionBar.setTitle(deviceName.substring(0, 20).concat(" ..."));
            } else {
                actionBar.setTitle(deviceName);
            }
        }

        //inflate device fragment
        DeviceDetailsActivityFragment detailsActivityFragment = DeviceDetailsActivityFragment.newInstance();
        Bundle args = new Bundle();
        args.putString(DEVICE_NAME_EXTRA_KEY, deviceName);
        args.putString(DEVICE_ID_EXTRA_KEY, deviceId);
        detailsActivityFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().replace(R.id.device_details_fragment_container, detailsActivityFragment).commit();
    }
}
