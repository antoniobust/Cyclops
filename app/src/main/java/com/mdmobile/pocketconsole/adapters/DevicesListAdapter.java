package com.mdmobile.pocketconsole.adapters;

import android.database.Cursor;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mdmobile.pocketconsole.R;
import com.mdmobile.pocketconsole.provider.McContract;
import com.mdmobile.pocketconsole.ui.ViewHolder.ImageTextImageViewHolder;

import static android.R.attr.data;

/**
 * Adapter bound to list of devices in main activity
 */

public class DevicesListAdapter extends RecyclerView.Adapter<ImageTextImageViewHolder> {

    private Cursor data;

    public DevicesListAdapter(@Nullable Cursor cursor) {
        data = cursor;
    }

    @Override
    public ImageTextImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_img_txt_img, parent,false);
        return new ImageTextImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageTextImageViewHolder holder, int position) {
        if(data == null){
            return;
        }
        if (!data.move(position)) {
            //Show error view
        } else {
            holder.descriptionView.setText(data.getString(data.getColumnIndex(McContract.Device.COLUMN_DEVICE_NAME)));
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.getCount();
    }

    public void changeCursor(Cursor cursor) {
        Cursor old = swapCursor(cursor);
        if (old != null) {
            old.close();
        }
    }

    public Cursor swapCursor(Cursor cursor) {
        if (data == cursor) {
            return null;
        }
        Cursor oldCursor = data;
        this.data = cursor;
        if (cursor != null) {
            this.notifyDataSetChanged();
        }
        return oldCursor;
    }
}
