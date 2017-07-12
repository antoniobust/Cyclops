package com.mdmobile.pocketconsole.ui.deviceDetails;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

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

        final FloatingActionButton mainFab = (FloatingActionButton) findViewById(R.id.details_main_fab);
        final FloatingActionButton subFab1 = (FloatingActionButton) findViewById(R.id.sub_fab1);
        final FloatingActionButton subFab2 = (FloatingActionButton) findViewById(R.id.sub_fab2);
        final FloatingActionButton subFab3 = (FloatingActionButton) findViewById(R.id.sub_fab3);
        final FloatingActionButton subFab4 = (FloatingActionButton) findViewById(R.id.sub_fab4);
        final TextView label1 = (TextView) findViewById(R.id.fab_label1);
        final TextView label2 = (TextView) findViewById(R.id.fab_label2);
        final TextView label3 = (TextView) findViewById(R.id.fab_label3);
        final TextView label4 = (TextView) findViewById(R.id.fab_label4);



        //Set up fabs
        mainFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation fabAnimation = AnimationUtils.loadAnimation(view.getContext(), R.anim.show_mini_fabs);
                Animation labelAnimation = new TranslateAnimation(100, 0, 0, 0);
                labelAnimation.setInterpolator(view.getContext(), android.R.anim.accelerate_decelerate_interpolator);
                labelAnimation.setDuration(400);
                labelAnimation.setRepeatMode(Animation.REVERSE);

                fabAnimation.setRepeatMode(Animation.REVERSE);
                if (subFab1.getVisibility() == View.GONE) {
                    subFab1.setVisibility(View.VISIBLE);
                    subFab2.setVisibility(View.VISIBLE);
                    subFab3.setVisibility(View.VISIBLE);
                    subFab4.setVisibility(View.VISIBLE);
                    subFab1.startAnimation(fabAnimation);
                    subFab2.startAnimation(fabAnimation);
                    subFab3.startAnimation(fabAnimation);
                    subFab4.startAnimation(fabAnimation);

                    label1.setVisibility(View.VISIBLE);
                    label1.setAnimation(labelAnimation);
                    label2.setVisibility(View.VISIBLE);
                    label2.setAnimation(labelAnimation);
                    label3.setVisibility(View.VISIBLE);
                    label3.setAnimation(labelAnimation);
                    label4.setVisibility(View.VISIBLE);
                    label4.setAnimation(labelAnimation);


                } else {
                    subFab1.setVisibility(View.GONE);
                    label1.setVisibility(View.GONE);
                    subFab2.setVisibility(View.GONE);
                    label2.setVisibility(View.GONE);
                    subFab3.setVisibility(View.GONE);
                    label3.setVisibility(View.GONE);
                    subFab4.setVisibility(View.GONE);
                    label4.setVisibility(View.GONE);

                }
            }
        });
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            if (deviceName.length() > 25) {
                actionBar.setTitle(deviceName.substring(0, 25).concat(" ..."));
            } else {
                actionBar.setTitle(deviceName);
            }
        }

        //attach device fragment
        DeviceDetailsFragment detailsActivityFragment = DeviceDetailsFragment.newInstance();
        Bundle args = new Bundle();
        args.putString(DEVICE_NAME_EXTRA_KEY, deviceName);
        args.putString(DEVICE_ID_EXTRA_KEY, deviceId);
        detailsActivityFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().replace(R.id.device_details_fragment_container, detailsActivityFragment).commit();
    }
}
