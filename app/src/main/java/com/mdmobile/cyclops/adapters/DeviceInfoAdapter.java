package com.mdmobile.cyclops.adapters;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Responsible for populating the full list of device information
 */

public class DeviceInfoAdapter extends RecyclerView.Adapter<ServerDetailsAdapter.ViewHolder> {

    private final int PROPERTY_VALUE_INDEX = 0;
    private String[] properties;
    private Cursor cursor;

    public DeviceInfoAdapter(@NonNull String[] propertiesLabels, Cursor cursor) {
        this.properties = propertiesLabels;
        this.cursor = cursor;
    }

    @Override
    public ServerDetailsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.test_list_item, parent, false);
        return new ServerDetailsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ServerDetailsAdapter.ViewHolder holder, int position) {
        if (position % 2 == 0) {
            holder.textView.setText(properties[position / 2]);
        } else {
            if (cursor != null && cursor.moveToPosition(position)) {
                cursor.getString(PROPERTY_VALUE_INDEX);
            }
        }
    }

    @Override
    public int getItemCount() {
        return properties.length;
    }
}
