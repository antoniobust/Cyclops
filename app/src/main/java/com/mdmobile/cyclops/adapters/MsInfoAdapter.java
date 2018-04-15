package com.mdmobile.cyclops.adapters;

import android.content.Context;
import android.database.Cursor;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;

import com.mdmobile.cyclops.R;
import com.mdmobile.cyclops.dataModel.api.ServerInfo;
import com.mdmobile.cyclops.provider.McContract;

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

        if (getCursor() == null || !getCursor().moveToPosition(position)) {
            return;
        }
        String serverName = getCursor().getString(getCursor().getColumnIndex(McContract.MsInfo.NAME));
        String status = getCursor().getString(getCursor().getColumnIndex(McContract.MsInfo.STATUS));


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
                c.getString(c.getColumnIndex(McContract.MsInfo.FULLY_QUALIFIED_NAME)),
                c.getString(c.getColumnIndex(McContract.MsInfo.DESCRIPTION)),
                c.getString(c.getColumnIndex(McContract.MsInfo.STATUS_TIME)),
                c.getString(c.getColumnIndex(McContract.MsInfo.MAC_ADDRESS)),
                c.getString(c.getColumnIndex(McContract.MsInfo.NAME)),
                c.getString(c.getColumnIndex(McContract.MsInfo.STATUS)),
                c.getInt(c.getColumnIndex(McContract.MsInfo.PORT_NUMBER)),
                c.getInt(c.getColumnIndex(McContract.MsInfo.TOTAL_USER_COUNT)));
        mClickCallback.itemCLicked(parcel);
    }
}
