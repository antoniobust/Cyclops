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

/**
 * Adapter bound to list of devices in main activity
 */

public class DevicesListAdapter extends RecyclerView.Adapter<ImageTextImageViewHolder> {

    private Cursor data;

    public DevicesListAdapter(@Nullable Cursor cursor) {
        if(cursor!=null) {
            setHasStableIds(true);
            data = cursor;
            swapCursor(cursor);
        }
    }

    @Override
    public ImageTextImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_img_txt_img, parent, false);
        return new ImageTextImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageTextImageViewHolder holder, int position) {
        if (data.moveToPosition(position)) {
            holder.descriptionView.setText(data.getString(data.getColumnIndex(McContract.Device.COLUMN_DEVICE_NAME)));
            //TODO: column "kind" doesn't get populated from gson, check it
//            String kind =data.getString(data.getColumnIndex(McContract.Device.COLUMN_KIND));
            String platform = data.getString(data.getColumnIndex(McContract.Device.COLUMN_PLATFORM));
            if (platform == null) {
                holder.image1View.setImageResource(R.drawable.ic_phone_android);
            } else switch (platform) {
                case "Android":
                    holder.image1View.setImageResource(R.drawable.ic_phone_android);
                    break;
                case "iOS":
                    holder.image1View.setImageResource(R.drawable.ic_phone_iphone);
                    break;
                default:
                    holder.image1View.setImageResource(R.drawable.ic_phone_android);
            }
        }
    }

    @Override
    public long getItemId(int position) {
        return data.getLong(data.getColumnIndex(McContract.Device.COLUMN_DEVICE_ID));
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.getCount();
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
