package com.mdmobile.pocketconsole.adapters;

import android.content.Context;
import android.database.Cursor;

import com.mdmobile.pocketconsole.R;
import com.mdmobile.pocketconsole.provider.McContract;

/**
 * Responsible to populate DS info
 */

public class DsInfoAdapter extends ServerDetailsAdapter {

    private Context mContext;

    public DsInfoAdapter(Context context, Cursor c) {
        super(c);
        mContext = context;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        if (getCursor() == null || !getCursor().moveToFirst()) {
            return;
        }
        String serverName = getCursor().getString(getCursor().getColumnIndex(McContract.DeploymentServer.NAME));
        String status = getCursor().getString(getCursor().getColumnIndex(McContract.DeploymentServer.STATUS));


        holder.serverNameView.setText(serverName);
        switch (status) {
            case "Stopped":
                holder.statusIndicator.setBackgroundColor(mContext.getResources().getColor(android.R.color.holo_red_dark));
                break;
            case "Started":
                holder.statusIndicator.setBackgroundColor(mContext.getResources().getColor(R.color.darkGreen));
                break;
            case "Disabled":
                holder.statusIndicator.setBackgroundColor(mContext.getResources().getColor(R.color.yellow));
                break;
            case "Unlicensed":
                holder.statusIndicator.setBackgroundColor(mContext.getResources().getColor(R.color.iosSpaceGrey));
                break;
            case "Deleted":
                holder.statusIndicator.setBackgroundColor(mContext.getResources().getColor(android.R.color.holo_red_dark));
                break;
            case "StartedButNotRegistered":
                holder.statusIndicator.setBackgroundColor(mContext.getResources().getColor(R.color.yellow));
                break;
            case "Offline":
                holder.statusIndicator.setBackgroundColor(mContext.getResources().getColor(R.color.grey));
                break;
            case "Unknown":
                holder.statusIndicator.setBackgroundColor(mContext.getResources().getColor(R.color.grey));
                break;
        }
    }
}
