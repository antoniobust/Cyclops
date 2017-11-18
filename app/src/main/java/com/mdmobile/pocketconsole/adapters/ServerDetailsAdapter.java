package com.mdmobile.pocketconsole.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mdmobile.pocketconsole.R;
import com.mdmobile.pocketconsole.dataModels.api.ServerInfo;

/**
 * Adapter responsible to populate a server
 */

public class ServerDetailsAdapter extends RecyclerView.Adapter<ServerDetailsAdapter.ViewHolder> {

    ServerInfo.DeploymentServer dsInfo;
    ServerInfo.ManagementServer msInfo;
    Context mContext;
    String[] infoLabels;
    String[] serverValues;


    public ServerDetailsAdapter(Context mContext, ServerInfo.DeploymentServer serverInfo) {
        this.dsInfo = serverInfo;
        this.mContext = mContext.getApplicationContext();
        infoLabels = this.mContext.getResources().getStringArray(R.array.ds_information_labels);
        serverValues = serverInfo.infoToArray();
    }

    public ServerDetailsAdapter(Context mContext, ServerInfo.ManagementServer serverInfo) {
        this.msInfo = serverInfo;
        this.mContext = mContext.getApplicationContext();
        infoLabels = this.mContext.getResources().getStringArray(R.array.ms_information_labels);
        serverValues = serverInfo.infoToArray();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        populateInfo(holder, position);
    }


    @Override
    public int getItemCount() {
        return infoLabels.length * 2;
    }

    private void populateInfo(ViewHolder viewHolder, int position) {
        if (position % 2 == 0) {
            viewHolder.textView.setText(infoLabels[position / 2]);
        } else {
            viewHolder.textView.setText(serverValues[position / 2]);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(android.R.id.text1);
        }
    }
}
