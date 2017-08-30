package com.mdmobile.pocketconsole.ui.deviceDetails;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.mdmobile.pocketconsole.R;
import com.mdmobile.pocketconsole.apiManager.ApiRequestManager;
import com.mdmobile.pocketconsole.dataTypes.ApiActions;
import com.mdmobile.pocketconsole.ui.Dialogs.MessageDialog;
import com.mdmobile.pocketconsole.ui.Dialogs.ScriptDialog;

import static android.support.v4.view.ViewCompat.animate;

public class DeviceDetailsActivity extends AppCompatActivity {

    public final static String DEVICE_NAME_EXTRA_KEY = "DeviceNameIntentExtraKey";
    public final static String DEVICE_ID_EXTRA_KEY = "DeviceIdIntentExtraKey";
    public static final String EXTRA_DEVICE_ICON_TRANSITION_NAME_KEY = "DeviceIconTransition";
    public static final String EXTRA_DEVICE_NAME_TRANSITION_NAME_KEY = "DeviceNameTransition";
    FloatingActionButton mainFab;
    FloatingActionButton subFab1;
    FloatingActionButton subFab2;
    FloatingActionButton subFab3;
    FloatingActionButton subFab4;
    TextView label1;
    TextView label2;
    TextView label3;
    TextView label4;
    String deviceName;
    String deviceId;
    private String nameTransitionName;
    private String iconTransitionName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportStartPostponedEnterTransition();

        deviceName = getIntent().getStringExtra(DEVICE_NAME_EXTRA_KEY);
        deviceId = getIntent().getStringExtra(DEVICE_ID_EXTRA_KEY);

        if (getIntent().hasExtra(EXTRA_DEVICE_NAME_TRANSITION_NAME_KEY)
                && getIntent().hasExtra(EXTRA_DEVICE_ICON_TRANSITION_NAME_KEY)) {
            nameTransitionName = getIntent().getStringExtra(EXTRA_DEVICE_NAME_TRANSITION_NAME_KEY);
            iconTransitionName = getIntent().getStringExtra(EXTRA_DEVICE_ICON_TRANSITION_NAME_KEY);
        }

        setContentView(R.layout.activity_device_details);

        mainFab = (FloatingActionButton) findViewById(R.id.details_main_fab);
        subFab1 = (FloatingActionButton) findViewById(R.id.sub_fab1);
        subFab2 = (FloatingActionButton) findViewById(R.id.sub_fab2);
        subFab3 = (FloatingActionButton) findViewById(R.id.sub_fab3);
        subFab4 = (FloatingActionButton) findViewById(R.id.sub_fab4);
        label1 = (TextView) findViewById(R.id.fab_label1);
        label2 = (TextView) findViewById(R.id.fab_label2);
        label3 = (TextView) findViewById(R.id.fab_label3);
        label4 = (TextView) findViewById(R.id.fab_label4);


        //Set up main fab onClick action
        mainFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (subFab1.getVisibility() == View.GONE) {
                    showFabs(view);
                } else {
                    hideFabs();
                }
            }
        });

        //Set up action bar
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
        DeviceDetailsFragment detailsActivityFragment = DeviceDetailsFragment.newInstance(deviceId, deviceName,
                iconTransitionName, nameTransitionName);
//        Bundle args = new Bundle();
//        args.putString(DEVICE_NAME_EXTRA_KEY, deviceName);
//        args.putString(DEVICE_ID_EXTRA_KEY, deviceId);
//        detailsActivityFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().replace(R.id.device_details_fragment_container, detailsActivityFragment).commit();

    }


    //SHow And animate fabs and label helper method
    private void showFabs(View view) {
        Animation miniFabAnimation = AnimationUtils.loadAnimation(view.getContext(), R.anim.show_mini_fabs);
        Animation labelAnimation = new TranslateAnimation(100, 0, 0, 0);
        labelAnimation.setInterpolator(view.getContext(), android.R.anim.accelerate_decelerate_interpolator);
        labelAnimation.setDuration(400);
        labelAnimation.setRepeatMode(Animation.REVERSE);
        miniFabAnimation.setRepeatMode(Animation.REVERSE);

        animate(mainFab)
                .rotation(360.0F)
                .withLayer()
                .setDuration(700L)
                .setInterpolator(new DecelerateInterpolator(5.0f))
                .start();
        //Show and animate mini fabs
        subFab1.setVisibility(View.VISIBLE);
        subFab1.startAnimation(miniFabAnimation);
        subFab2.setVisibility(View.VISIBLE);
        subFab2.startAnimation(miniFabAnimation);
        subFab3.setVisibility(View.VISIBLE);
        subFab3.startAnimation(miniFabAnimation);
        subFab4.setVisibility(View.VISIBLE);
        subFab4.startAnimation(miniFabAnimation);

        //SHow and animate fab's labels
        label1.setVisibility(View.VISIBLE);
        label1.setAnimation(labelAnimation);
        label2.setVisibility(View.VISIBLE);
        label2.setAnimation(labelAnimation);
        label3.setVisibility(View.VISIBLE);
        label3.setAnimation(labelAnimation);
        label4.setVisibility(View.VISIBLE);
        label4.setAnimation(labelAnimation);

        //dim brightness for content behind
        WindowManager.LayoutParams parameters = ((DeviceDetailsActivity) view.getContext()).getWindow().getAttributes();
        parameters.dimAmount = 0.4f;
        getWindow().setAttributes(parameters);
        getWindowManager().updateViewLayout(view.getRootView(), parameters);
    }

    //hide fabs and label helper method
    private void hideFabs() {
        ViewCompat.animate(mainFab)
                .rotation(0.0F)
                .withLayer()
                .setDuration(700L)
                .setInterpolator(new DecelerateInterpolator(5.0f))
                .start();
        //Hide labels and fabs
        subFab1.setVisibility(View.GONE);
        label1.setVisibility(View.GONE);
        subFab2.setVisibility(View.GONE);
        label2.setVisibility(View.GONE);
        subFab3.setVisibility(View.GONE);
        label3.setVisibility(View.GONE);
        subFab4.setVisibility(View.GONE);
        label4.setVisibility(View.GONE);
    }

    public void executeAction(View view) {
        switch (view.getId()) {
            case R.id.sub_fab1:
                //Check in action
                ApiRequestManager.getInstance().requestAction(deviceId, ApiActions.CHECKIN, null, null);
                break;
            case R.id.sub_fab2:
                //Script action
                ScriptDialog.newInstance(deviceId).show(getSupportFragmentManager(), null);
                break;
            case R.id.sub_fab3:
                //Localize action
                ApiRequestManager.getInstance().requestAction(deviceId, ApiActions.LOCATE, null, null);
                break;
            case R.id.sub_fab4:
                //send message action
                MessageDialog.newInstance(deviceId).show(getSupportFragmentManager(), null);
                break;
        }
        hideFabs();
    }
}
