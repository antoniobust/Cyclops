package com.mdmobile.pocketconsole.adapters;

import android.content.Context;
import android.database.Cursor;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;

import com.mdmobile.pocketconsole.R;
import com.mdmobile.pocketconsole.gson.ServerInfo;
import com.mdmobile.pocketconsole.provider.McContract;

/**
 * Responsible to populate MS info
 */

public class MsInfoAdapter extends ServerListAdapter implements View.OnClickListener {

    private Context mContext;
    private ServerListAdapter.onClick mClickCallback;
    public MsInfoAdapter(Context context, Cursor c, ServerListAdapter.onClick callback) {
        super(c);
        mContext = context;
        mClickCallback = callback;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        Holder holder =  super.onCreateViewHolder(parent, viewType);
        holder.setClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        if (getCursor() == null || !getCursor().moveToFirst()) {
            return;
        }
        String serverName = getCursor().getString(getCursor().getColumnIndex(McContract.ManagementServer.NAME));
        String status = getCursor().getString(getCursor().getColumnIndex(McContract.ManagementServer.STATUS));


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

    @Override
    public void onClick(View view) {
        int adapterPosition = ((Holder)view.getTag()).getAdapterPosition();
        Cursor c = getCursor();
        c.moveToPosition(adapterPosition);
        Parcelable parcel = new ServerInfo.ManagementServer(
                c.getString(c.getColumnIndex(McContract.ManagementServer.FULLY_QUALIFIED_NAME)),c.getString(c.getColumnIndex(McContract.ManagementServer.DESCRIPTION)),
                c.getString(c.getColumnIndex(McContract.ManagementServer.STATUS_TIME)),c.getString(c.getColumnIndex(McContract.ManagementServer.MAC_ADDRESS)),
                c.getString(c.getColumnIndex(McContract.ManagementServer.NAME)),c.getString(c.getColumnIndex(McContract.ManagementServer.STATUS)),
                c.getInt(c.getColumnIndex(McContract.ManagementServer.PORT_NUMBER)),c.getInt(c.getColumnIndex(McContract.ManagementServer.TOTAL_USER_COUNT)));
        mClickCallback.itemCLicked(parcel);
    }
}
