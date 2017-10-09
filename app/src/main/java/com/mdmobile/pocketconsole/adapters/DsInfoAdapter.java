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
 * Responsible to populate DS info
 */

public class DsInfoAdapter extends ServerListAdapter implements View.OnClickListener {

    private Context mContext;
    private ServerListAdapter.onClick mCallback;

    public DsInfoAdapter(Context context, Cursor c, onClick onItemClicked) {
        super(c);
        mContext = context;
        mCallback = onItemClicked;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        ServerListAdapter.Holder holder = super.onCreateViewHolder(parent, viewType);
        holder.setClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        if (getCursor() == null || !getCursor().moveToPosition(position)) {
            return;
        }
        String serverName = getCursor().getString(getCursor().getColumnIndex(McContract.DsInfo.NAME));
        String status = getCursor().getString(getCursor().getColumnIndex(McContract.DsInfo.STATUS));


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
        Parcelable parcel = new ServerInfo.DeploymentServer(
                c.getString(c.getColumnIndex(McContract.DsInfo.PRIMARY_MANAGEMENT_ADDRESS)),
                c.getString(c.getColumnIndex(McContract.DsInfo.SECONDARY_MANAGEMENT_ADDRESS)),
                c.getString(c.getColumnIndex(McContract.DsInfo.PRIMARY_AGENT_ADDRESS)),
                c.getString(c.getColumnIndex(McContract.DsInfo.SECONDARY_AGENT_ADDRESS)),
                c.getString(c.getColumnIndex(McContract.DsInfo.DEVICE_MANAGEMENT_ADDRESS)),
                c.getString(c.getColumnIndex(McContract.DsInfo.NAME)),
                c.getString(c.getColumnIndex(McContract.DsInfo.STATUS)),
                (c.getInt(c.getColumnIndex(McContract.DsInfo.CONNECTED)) == 1),
                c.getInt(c.getColumnIndex(McContract.DsInfo.PULSE_TIMEOUT)), c.getInt(c.getColumnIndex(McContract.DsInfo.RULE_RELOAD)),
                c.getInt(c.getColumnIndex(McContract.DsInfo.SCHEDULE_INTERVAL)),
                c.getInt(c.getColumnIndex(McContract.DsInfo.MIN_THREADS)), c.getInt(c.getColumnIndex(McContract.DsInfo.MAX_THREADS)),
                c.getInt(c.getColumnIndex(McContract.DsInfo.MAX_BURST_THREADS)), c.getInt(c.getColumnIndex(McContract.DsInfo.PULSE_WAIT_INTERVAL)),
                c.getInt(c.getColumnIndex(McContract.DsInfo.DEVICES_CONNECTED)), c.getInt(c.getColumnIndex(McContract.DsInfo.MANAGERS_CONNECTED)),
                c.getInt(c.getColumnIndex(McContract.DsInfo.QUEUE_LENGTH)), c.getInt(c.getColumnIndex(McContract.DsInfo.CURRENT_THREAD_COUNT)));
        mCallback.itemCLicked(parcel);
    }
}
