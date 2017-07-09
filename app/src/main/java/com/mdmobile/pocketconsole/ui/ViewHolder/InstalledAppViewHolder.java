package com.mdmobile.pocketconsole.ui.ViewHolder;

import android.view.View;
import android.widget.TextView;

import com.mdmobile.pocketconsole.R;

/**
 * View holder used in installed app adapter
 */

public class InstalledAppViewHolder {

    public TextView appNameView, appIdView, appSizeView, appStatusView;

    public InstalledAppViewHolder(View view) {
        appNameView = (TextView) view.findViewById(R.id.installed_app_name_view);
        appIdView = (TextView) view.findViewById(R.id.installed_app_id_view);
        appSizeView = (TextView) view.findViewById(R.id.installed_app_size_view);
        appStatusView = (TextView) view.findViewById(R.id.installed_app_status_view);
    }

}
