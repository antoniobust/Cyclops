package com.mdmobile.pocketconsole.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.CursorAdapter;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import com.mdmobile.pocketconsole.R;
import com.mdmobile.pocketconsole.gson.InstalledApp;
import com.mdmobile.pocketconsole.ui.Dialogs.UninstallAppDialog;
import com.mdmobile.pocketconsole.ui.ViewHolder.InstalledAppViewHolder;
import com.mdmobile.pocketconsole.ui.deviceDetails.DeviceDetailsActivity;
import com.mdmobile.pocketconsole.ui.deviceDetails.InstalledAppsFragment;

import static android.R.attr.dial;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;
import static java.security.AccessController.getContext;


public class InstalledAppsAdapter extends CursorAdapter implements PopupMenu.OnMenuItemClickListener{

private Context mContext;
    private String selected;

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
    public void bindView(View view, final Context context, Cursor cursor) {

        final InstalledAppViewHolder viewHolder = (InstalledAppViewHolder) view.getTag();

        viewHolder.appNameView.setText(cursor.getString(1));
        viewHolder.appStatusView.setText(cursor.getString(2));
        viewHolder.appIdView.setText(cursor.getString(3));

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                selected = viewHolder.appIdView.getText().toString();
                PopupMenu menu = new PopupMenu(view.getContext(),view, Gravity.CENTER_HORIZONTAL);
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
            //Check for alert dialog showing preference
            if (mContext.getSharedPreferences(mContext.getString(R.string.shared_preference), Context.MODE_PRIVATE)
                    .getBoolean(mContext.getString(R.string.uninstall_app_dialog_disabled_pref), true)) {
                UninstallAppDialog dialog = new UninstallAppDialog();
                Bundle args = new Bundle(1);
                args.putString(UninstallAppDialog.APP_NAME_ARG_KEY,selected);
                dialog.setArguments(args);

                dialog.show(((DeviceDetailsActivity) mContext).getSupportFragmentManager(), null);
            }
            return true;
        }
        return false;
    }
}
