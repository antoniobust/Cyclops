package com.mdmobile.cyclops.ui.main.deviceDetails;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mdmobile.cyclops.R;
import com.mdmobile.cyclops.apiManager.ApiRequestManager;
import com.mdmobile.cyclops.dataModel.Server;
import com.mdmobile.cyclops.dataModel.api.InstalledApp;
import com.mdmobile.cyclops.dataModel.api.Profile;
import com.mdmobile.cyclops.dataModel.api.devices.BasicDevice;
import com.mdmobile.cyclops.dataModel.api.devices.DeviceFactory;
import com.mdmobile.cyclops.dataModel.api.devices.IDevice;
import com.mdmobile.cyclops.dataTypes.ApiActions;
import com.mdmobile.cyclops.provider.McContract;
import com.mdmobile.cyclops.ui.dialogs.ConfirmActionDialog;
import com.mdmobile.cyclops.ui.main.MainActivity;
import com.mdmobile.cyclops.utils.GeneralUtility;
import com.mdmobile.cyclops.utils.LabelHelper;
import com.mdmobile.cyclops.utils.Logger;
import com.mdmobile.cyclops.utils.RecyclerEmptyView;
import com.mdmobile.cyclops.utils.ServerUtility;

import java.util.ArrayList;

import static com.mdmobile.cyclops.ui.main.deviceDetails.DeviceDetailsActivity.DEVICE_ID_EXTRA_KEY;
import static com.mdmobile.cyclops.ui.main.deviceDetails.DeviceDetailsActivity.DEVICE_NAME_EXTRA_KEY;
import static com.mdmobile.cyclops.ui.main.deviceDetails.DeviceDetailsActivity.EXTRA_DEVICE_ICON_TRANSITION_NAME_KEY;
import static com.mdmobile.cyclops.ui.main.deviceDetails.DeviceDetailsActivity.EXTRA_DEVICE_NAME_TRANSITION_NAME_KEY;


public class DeviceDetailsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, View.OnClickListener,
        SwipeRefreshLayout.OnRefreshListener {
    private final String LOG_TAG = DeviceDetailsFragment.class.getSimpleName();
    private final int LOAD_PROFILE = 111, LOAD_APPS = 112, LOAD_INFO = 113;
    private String deviceName;
    private String deviceId;
    private String iconTransitionName;
    private String nameTransitionName;
    private CardView profilesCard;
    private RecyclerView devInfoRecycler;
    private RecyclerEmptyView profilesRecycler, installedAppsRecycler;
    private IDevice<BasicDevice> device;
    private ArrayList<String[]> appList;
    private ArrayList<Profile> profiles;
    private ArrayList<InstalledApp> applications;
    private OnCardClick mCallback;
    private View rootView;
    //    private ImageView batteryView, wifiView, simView, ramView, sdCardView;
    private SwipeRefreshLayout swipeLayout;
    private Server activeServer;
    private ProgressBar profileProgressBar, infoProgressBar, appsProgressBar;

    public DeviceDetailsFragment() {
    }

    public static DeviceDetailsFragment newInstance(@NonNull String deviceId, @NonNull String deviceName,
                                                    @Nullable String iconSharedTransactionName, @Nullable String devNameSharedTransactionNAme) {
        Bundle args = new Bundle();
        args.putString(DEVICE_ID_EXTRA_KEY, deviceId);
        args.putString(DEVICE_NAME_EXTRA_KEY, deviceName);
        if (iconSharedTransactionName != null && devNameSharedTransactionNAme != null) {
            args.putString(EXTRA_DEVICE_ICON_TRANSITION_NAME_KEY, iconSharedTransactionName);
            args.putString(EXTRA_DEVICE_NAME_TRANSITION_NAME_KEY, devNameSharedTransactionNAme);
        }
        DeviceDetailsFragment fragment = new DeviceDetailsFragment();
        fragment.setArguments(args);

        return fragment;
    }

    // -- Interface methods
    @Override
    public void onRefresh() {
        Logger.log(LOG_TAG, "Device " + deviceId + " info update requested", Log.VERBOSE);
        ApiRequestManager.getInstance().getDeviceInfo(ServerUtility.getActiveServer(), deviceId);
        swipeLayout.setRefreshing(false);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri uri;
        switch (id) {
            case LOAD_INFO:
                uri = McContract.Device.buildUriWithDeviceID(deviceId);
                return new CursorLoader(getContext().getApplicationContext(), uri, McContract.Device.FULL_PROJECTION,
                        null, null, null);
            case LOAD_PROFILE:
                return new CursorLoader(getContext().getApplicationContext(),
                        McContract.Profile.buildUriWithDeviceId(deviceId),
                        McContract.Profile.FULL_PROJECTION, null, null, McContract.Profile.ASSIGNMENT_DATE + " DESC");
            case LOAD_APPS:
                uri = McContract.InstalledApplications.buildUriWithDevId(deviceId);
                return new CursorLoader(getContext().getApplicationContext(), uri, McContract.InstalledApplications.FULL_PROJECTION,
                        null, null, McContract.InstalledApplications.APPLICATION_NAME + " ASC");
            default:
                throw new UnsupportedOperationException("Loader id:" + id + " not supported");
        }
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {

        switch (loader.getId()) {
            case LOAD_INFO:
                data.moveToFirst();
                device = DeviceFactory.Companion.createDevice(data).get(0);
                setHeader();
                setDeviceInfoCard();
                break;
            case LOAD_PROFILE: {
                setProfilesCard(data);
                break;
            }
            case LOAD_APPS: {
                setAppsCard(data);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unsupported id: " + loader.getId());
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
    }

    // -- Lifecycle methods
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        deviceId = getArguments().getString(DEVICE_ID_EXTRA_KEY);
        deviceName = getArguments().getString(DEVICE_NAME_EXTRA_KEY);
        nameTransitionName = getArguments().getString(EXTRA_DEVICE_NAME_TRANSITION_NAME_KEY, null);
        iconTransitionName = getArguments().getString(EXTRA_DEVICE_ICON_TRANSITION_NAME_KEY, null);
        activeServer = ServerUtility.getActiveServer();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallback = (OnCardClick) getActivity();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_device_details, container, false);

        if (getActivity() != null) {
            Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
            if (GeneralUtility.isTabletMode(getContext())) {
                toolbar.setVisibility(View.GONE);
            }
            ActionBar actionBar;
            ((DeviceDetailsActivity) getActivity()).setSupportActionBar(toolbar);
            actionBar = ((DeviceDetailsActivity) getActivity()).getSupportActionBar();
            if (actionBar != null) {
                actionBar.setTitle(deviceName);
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
        }

//        swipeLayout = rootView.findViewById(R.id.device_info_swipe_to_refresh);
//        swipeLayout.setOnRefreshListener(this);

        View header = getActivity().getFragmentManager().findFragmentById(R.id.details_header).getView();
        ImageView titleIconView = header.findViewById(R.id.device_detail_icon);
        TextView titleView = header.findViewById(R.id.device_detail_title_view);
        TextView subtitleView = header.findViewById(R.id.device_detail_subtitle_view);
        CardView infoCard = rootView.findViewById(R.id.device_details_info_card);
        CardView appsCard = rootView.findViewById(R.id.device_details_apps_card);
        profilesCard = rootView.findViewById(R.id.device_details_profiles_card);
        profileProgressBar = profilesCard.findViewById(R.id.profiles_loading_view);
        infoProgressBar = infoCard.findViewById(R.id.info_loading_view);
        appsProgressBar = appsCard.findViewById(R.id.apps_loading_view);
        devInfoRecycler = infoCard.findViewById(R.id.device_details_info_recycler);
        profilesRecycler = rootView.findViewById(R.id.device_details_profiles_recycler);
        View profileEmptyView = profilesCard.findViewById(R.id.profiles_empty_view);
        installedAppsRecycler = appsCard.findViewById(R.id.device_details_apps_recycler);
        View appsEmptyView = appsCard.findViewById(R.id.apps_empty_view);

        devInfoRecycler.setLayoutManager(new GridLayoutManager(getContext(), 2));
        profilesRecycler.setLayoutManager(new GridLayoutManager(getContext(), 2));
        profilesRecycler.setEmptyView(profileEmptyView);
        installedAppsRecycler.setLayoutManager(new GridLayoutManager(getContext(), 2));
        installedAppsRecycler.setEmptyView(appsEmptyView);


        devInfoRecycler.setAdapter(null);
        profilesRecycler.setAdapter(null);
        installedAppsRecycler.setAdapter(null);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            titleIconView.setTransitionName(iconTransitionName);
            titleView.setTransitionName(nameTransitionName);
        }

        infoCard.setOnClickListener(this);
        appsCard.setOnClickListener(this);

        titleIconView.setImageResource(R.drawable.ic_phone_android);
        titleView.setText(deviceName);
        subtitleView.setText(deviceId);
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.device_actions_menu, menu);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

//        getActivity().supportStartPostponedEnterTransition();
        getLoaderManager().initLoader(LOAD_INFO, null, this);
        infoProgressBar.setVisibility(View.VISIBLE);
        getLoaderManager().initLoader(LOAD_PROFILE, null, this);
        profileProgressBar.setVisibility(View.VISIBLE);
        getLoaderManager().initLoader(LOAD_APPS, null, this);
        appsProgressBar.setVisibility(View.VISIBLE);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (MainActivity.TABLET_MODE) {
                    hideDetailsFragment();
                    return true;
                }
                break;
            case R.id.action_wipe_device: {
                ConfirmActionDialog.newInstance(
                        getString(R.string.confirm_action),
                        getString(R.string.confirm_wipe_message, deviceName),
                        R.drawable.ic_priority, getString(R.string.yes_label), getString(R.string.dialog_cancel_label),
                        false, new ConfirmActionDialog.ConfirmAction() {
                            @Override
                            public void actionConfirmed(boolean doNotShowAgain) {
                                ApiRequestManager.getInstance()
                                        .requestAction(ServerUtility.getActiveServer(), deviceId, ApiActions.WIPE, null, null);
                            }

                            @Override
                            public void actionCanceled() {
                            }
                        }).show(getChildFragmentManager(), "CONFIRM_DIALOG");
                return true;
            }
            case R.id.action_lock_device:
                ApiRequestManager.getInstance()
                        .requestAction(ServerUtility.getActiveServer(), deviceId, ApiActions.LOCK, null, null);
                return true;
            case R.id.action_unenroll_device: {
                ConfirmActionDialog.newInstance(
                        getString(R.string.confirm_action),
                        getString(R.string.confirm_unenroll_message, deviceName),
                        R.drawable.ic_priority, getString(R.string.yes_label), getString(R.string.dialog_cancel_label),
                        false, new ConfirmActionDialog.ConfirmAction() {
                            @Override
                            public void actionConfirmed(boolean doNotShowAgain) {
                                ApiRequestManager.getInstance()
                                        .requestAction(ServerUtility.getActiveServer(), deviceId, ApiActions.UNENROL, null, null);
                            }

                            @Override
                            public void actionCanceled() {
                            }
                        }).show(getChildFragmentManager(), "CONFIRM_DIALOG");
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    //    private void setLevelBars(Cursor data) {
//        data.moveToFirst();
//        Bundle extraInfo = DbData.getDeviceExtraInfo(
//                data.getString(data.getColumnIndex(McContract.Device.COLUMN_EXTRA_INFO)));
//
//        String[] value = {extraInfo.getString(DeviceAttributes.AndroidPlus.BatteryStatus),
//                extraInfo.getString(DeviceAttributes.AndroidPlus.CellularSignalStrength),
//                extraInfo.getString(DeviceAttributes.AndroidPlus.NetworkRSSI),
//                extraInfo.getString(DeviceAttributes.AndroidPlus.Memory)};
//        for (int i = 0; i < value.length; i++) {
//            if (value[i] == null || value[i].equals("N/A")) {
//                value[i] = "0";
//            }
//        }
//
//        int val = Integer.valueOf(value[0]);
//        LayerDrawable clipBar = (LayerDrawable) batteryView.getDrawable();
//        Drawable barFilling = clipBar.findDrawableByLayerId(R.id.horizontal_bar_filling);
//
//        if (val > 0 && val < 20) {
//            barFilling.setTint(getContext().getResources().getColor(android.R.color.holo_red_light));
//        }
//        barFilling.setLevel(val * 100);
//        clipBar.invalidateSelf();
//
//        val = Integer.valueOf(value[1]);
//        clipBar = (LayerDrawable) simView.getDrawable();
//        if (val > 0 && val < 20) {
//            barFilling.setTint(getContext().getResources().getColor(android.R.color.holo_red_light));
//        }
//        barFilling.setLevel(val * 100);
//
//        val = Integer.valueOf(value[2]);
//        val = val * -1;
//        val = 100 - val;
//        clipBar = (LayerDrawable) wifiView.getDrawable();
//        if (val > 0 && val < 20) {
//            barFilling.setTint(getContext().getResources().getColor(android.R.color.holo_red_light));
//        }
//        barFilling.setLevel(val * 100);
//
//    }

    @Override
    public void onClick(View v) {
        Fragment newFrag;
        switch (v.getId()) {
            case R.id.device_details_info_card:
                newFrag = FullDeviceInfoFragment.Companion.newInstance(deviceId, device.getDevice());
                break;
            case R.id.device_details_profiles_card:
                newFrag = ProfilesFragment.newInstance(deviceId, profiles);
                break;
            case R.id.device_details_apps_card:
                newFrag = InstalledAppsFragment.newInstance(deviceId, applications);
                break;
            default:
                Logger.log(LOG_TAG, v.getId() + " view not supported in OnClick", Log.ERROR);
                return;
        }
        mCallback.expandCard(newFrag);

    }

    private void setHeader() {
        if (device == null) {
            return;
        }

        TextView devName = getActivity().findViewById(R.id.device_detail_title_view);
        //Set online dot
        Drawable dot;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            dot = getContext().getResources().getDrawable(R.drawable.connectivity_status_dot, null);
        } else {
            dot = getContext().getResources().getDrawable(R.drawable.connectivity_status_dot);
        }
        devName.setCompoundDrawablePadding(50);
        ImageView rcButton = getActivity().findViewById(R.id.device_detail_remote_control_view);
        if ((((BasicDevice) device.getDevice())).getIsAgentOnline()) {
            ((GradientDrawable) dot).setColor(getContext().getResources().getColor(R.color.darkGreen));
            if (rcButton != null) {
                rcButton.setEnabled(true);
            }
        } else {
            if (rcButton != null) {
                rcButton.setEnabled(false);
            }
            ((GradientDrawable) dot).setColor(Color.LTGRAY);
        }
        devName.setCompoundDrawablesWithIntrinsicBounds(null, null, dot, null);

    }

    private void setDeviceInfoCard() {
        if (device == null) {
            return;
        }
        ArrayList<String[]> infoList = new ArrayList<>();
        //Populates card info
        ContentValues contentValues = device.toContentValues();
        String[] keys = new String[contentValues.size()];
        contentValues.keySet().toArray(keys);
        String label;
        //TODO: need to get EXTRA INFO Properties
        for (String key : keys) {
            label = LabelHelper.Companion.getUiLabelFor(key);
            if (label.equals("")) {
                continue;
            }
            infoList.add(new String[]{label, contentValues.getAsString(key)});
        }
        infoProgressBar.setVisibility(View.GONE);
        devInfoRecycler.setAdapter(new InfoAdapter(infoList, true));
    }

    private void setProfilesCard(Cursor data) {
        if (data == null || data.getCount() == 0) {
            ApiRequestManager.getInstance().getDeviceProfiles(activeServer, deviceId);
            return;
        }
        if (data.getCount() == 1 && data.moveToFirst() &&
                data.getString(data.getColumnIndex(McContract.Profile.REFERENCE_ID)).equals("N/A")) {
            return;
        }

        if (!data.moveToFirst()) {
            return;
        }
        ArrayList<String[]> profileList = new ArrayList<>();
        profiles = new ArrayList<>();
        do {
            profiles.add(new Profile(data.getString(2), data.getString(1), data.getString(5), data.getString(3),
                    data.getInt(6), data.getInt(4) == 1));

            profileList.add(new String[]{data.getString(data.getColumnIndex(McContract.Profile.NAME)),
                    data.getString(data.getColumnIndex(McContract.Profile.STATUS))});
            data.moveToNext();
        } while (!data.isAfterLast());
        profileProgressBar.setVisibility(View.GONE);
        profilesRecycler.setAdapter(new InfoAdapter(profileList, true));
        profilesCard.setOnClickListener(this);
    }

    private void setAppsCard(Cursor data) {
        if (data != null && data.getCount() == 0) {
            ApiRequestManager.getInstance().getDeviceInstalledApps(activeServer, deviceId);
            return;
        }
        if (data == null || !data.moveToFirst()) {
            return;
        }
        appList = new ArrayList<>();
        applications = new ArrayList<>();
        do {
            applications.add(new InstalledApp(data.getString(1), data.getString(4), data.getString(2), data.getString(3)));

            appList.add(new String[]{data.getString(2), data.getString(3)});
            data.moveToNext();
        } while (!data.isAfterLast());
        appsProgressBar.setVisibility(View.GONE);
        installedAppsRecycler.setAdapter(new InfoAdapter(appList, true));
    }

    private void hideDetailsFragment() {
//        LinearLayout mainContainer = (LinearLayout) getActivity().findViewById(R.id.main_activity_linear_layout);
//        SwipeRefreshLayout deviceList = (SwipeRefreshLayout) mainContainer.findViewById(R.id.devices_swipe_refresh);
//        CardView detailsContainer = (CardView) mainContainer.findViewById(R.id.main_activity_device_details_container);
//
//        if (detailsContainer.getVisibility() == View.VISIBLE) {
//            detailsContainer.setVisibility(View.GONE);
//        }
//
//        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(getResources().getDimensionPixelSize(R.dimen.main_fragment_with), ViewGroup.LayoutParams.WRAP_CONTENT);
//        layoutParams.gravity = Gravity.CENTER;
//        mainContainer.setLayoutParams(layoutParams);
//
//        deviceList.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        deviceList.requestLayout();
    }

    public interface OnCardClick {
        void expandCard(Fragment f);
    }
}
