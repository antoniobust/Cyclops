package com.mdmobile.cyclops.adapters;

import android.content.Context;
import android.database.Cursor;
import androidx.cursoradapter.widget.CursorAdapter;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import com.mdmobile.cyclops.R;
import com.mdmobile.cyclops.api.ApiRequestManager;
import com.mdmobile.cyclops.security.ServerNotFound;
import com.mdmobile.cyclops.ui.dialogs.ConfirmActionDialog;
import com.mdmobile.cyclops.ui.ViewHolder.InstalledAppViewHolder;
import com.mdmobile.cyclops.ui.main.deviceDetails.DeviceDetailsActivity;
import com.mdmobile.cyclops.util.GeneralUtility;
import com.mdmobile.cyclops.util.ServerUtility;


public class InstalledAppsAdapter extends CursorAdapter implements PopupMenu.OnMenuItemClickListener, ConfirmActionDialog.ConfirmAction {

    private Context mContext;
    private String packageName;
    private String devId;

    public InstalledAppsAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        mContext = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.installed_app_view, parent, false);
        InstalledAppViewHolder viewHolder = new InstalledAppViewHolder(view);
        view.setTag(viewHolder);
        return view;
    }

    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {

        final InstalledAppViewHolder viewHolder = (InstalledAppViewHolder) view.getTag();

        viewHolder.appNameView.setText(cursor.getString(1));
        viewHolder.appStatusView.setText(cursor.getString(2));
        viewHolder.appIdView.setText(cursor.getString(3));

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //Store info required in case user press uninstall app
                packageName = viewHolder.appIdView.getText().toString();
                devId = cursor.getString(4);
                PopupMenu menu = new PopupMenu(view.getContext(), view, Gravity.CENTER_HORIZONTAL);
                menu.inflate(R.menu.uninstall_app_context_menu);
                menu.setOnMenuItemClickListener(InstalledAppsAdapter.this);
                menu.show();
                return true;
            }
        });
    }


    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.uninstall_app_action) {
            //Check for alert dialog showing preference - Show confirmation or execute action
            if (mContext.getSharedPreferences(mContext.getString(R.string.general_shared_preference), Context.MODE_PRIVATE)
                    .getBoolean(mContext.getString(R.string.uninstall_app_confirm_disabled_pref), false)) {
                actionConfirmed(true);
            } else {
                ConfirmActionDialog.newInstance(
                        mContext.getString(R.string.uninstall_app_dialog_title),
                        String.format(mContext.getString(R.string.uninstall_app_dialog_description), packageName),
                        R.drawable.ic_delete_forever, mContext.getString(R.string.uninstall_label),
                        mContext.getString(R.string.dialog_cancel_label), true, this)
                        .show(((DeviceDetailsActivity) mContext).getSupportFragmentManager(), null);
            }
            return true;
        }
        return false;
    }

    //Confirmation dialog callback
    @Override
    public void actionConfirmed(boolean doNotShowAgain) {
        try {
            ApiRequestManager.getInstance().uninstallApplication(ServerUtility.getActiveServer(), devId, packageName);
            //Set show dialog preference
            if (doNotShowAgain) {
                String prefKey = mContext.getString(R.string.uninstall_app_confirm_disabled_pref);
                GeneralUtility.setSharedPreference(mContext, prefKey, true);
            }
        }catch (ServerNotFound e){
            e.printStackTrace();
        }
    }

    @Override
    public void actionCanceled() {
    }
}
