package com.mdmobile.pocketconsole.adapters;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mdmobile.pocketconsole.R;
import com.mdmobile.pocketconsole.provider.McContract;
import com.mdmobile.pocketconsole.ui.ViewHolder.ImageTextImageViewHolder;
import com.mdmobile.pocketconsole.ui.deviceDetails.DeviceDetailsActivity;
import com.mdmobile.pocketconsole.ui.main.MainActivity;
import com.mdmobile.pocketconsole.utils.Logger;

import static com.mdmobile.pocketconsole.ui.deviceDetails.DeviceDetailsActivity.EXTRA_DEVICE_ICON_TRANSITION_NAME_KEY;
import static com.mdmobile.pocketconsole.ui.deviceDetails.DeviceDetailsActivity.EXTRA_DEVICE_NAME_TRANSITION_NAME_KEY;

/**
 * Adapter bound to list of devices in main activity
 */

public class DevicesListAdapter extends RecyclerView.Adapter<ImageTextImageViewHolder> {

    private static String LOG_TAG = DevicesListAdapter.class.getSimpleName();
    private Cursor data;

    public DevicesListAdapter(@Nullable Cursor cursor) {
        if (cursor != null) {
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
    public void onBindViewHolder(final ImageTextImageViewHolder holder, int position) {
        if (!data.moveToPosition(position)) {
            //Error view
        }

        holder.descriptionView.setText(data.getString(data.getColumnIndex(McContract.Device.COLUMN_DEVICE_NAME)));
        Drawable dot;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            dot = holder.descriptionView.getContext().getResources().getDrawable(R.drawable.connectivity_status_dot, null);
        } else {
            dot = holder.descriptionView.getContext().getResources().getDrawable(R.drawable.connectivity_status_dot);
        }
        holder.descriptionView.setCompoundDrawablePadding(50);

        if (data.getInt(data.getColumnIndex(McContract.Device.COLUMN_AGENT_ONLINE)) == 1) {
            ((GradientDrawable) dot).setColor(holder.descriptionView.getContext().getResources().getColor(R.color.darkGreen));
        } else {
            ((GradientDrawable) dot).setColor(Color.LTGRAY);
        }

        holder.descriptionView.setCompoundDrawablesWithIntrinsicBounds(dot, null, null, null);
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

        //Set transition name for shared element transition
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            String devID = data.getString(data.getColumnIndex(McContract.Device.COLUMN_DEVICE_ID));
            holder.image1View.setTransitionName("icon_" + devID);
            holder.descriptionView.setTransitionName("name_" + devID);
        }

        //Set click listener
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data.moveToPosition(holder.getAdapterPosition());
                Logger.log(LOG_TAG, "Clicked on item:" + holder.getAdapterPosition()
                        + " device name = " + data.getString(data.getColumnIndex(McContract.Device.COLUMN_DEVICE_NAME)), Log.VERBOSE);
                Intent intent = new Intent(view.getContext(), DeviceDetailsActivity.class);
                intent.putExtra(DeviceDetailsActivity.DEVICE_NAME_EXTRA_KEY, data.getString(data.getColumnIndex(McContract.Device.COLUMN_DEVICE_NAME)));
                intent.putExtra(DeviceDetailsActivity.DEVICE_ID_EXTRA_KEY, data.getString(data.getColumnIndex(McContract.Device.COLUMN_DEVICE_ID)));
                intent.putExtra(EXTRA_DEVICE_ICON_TRANSITION_NAME_KEY, ViewCompat.getTransitionName(holder.image1View));
                intent.putExtra(EXTRA_DEVICE_NAME_TRANSITION_NAME_KEY, ViewCompat.getTransitionName(holder.descriptionView));


//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    ActivityOptionsCompat options = ActivityOptionsCompat.
//                            makeSceneTransitionAnimation((MainActivity) view.getContext(), holder.image1View, holder.image1View.getTransitionName());
//                    view.getContext().startActivity(intent, options.toBundle());
//                } else {
                    view.getContext().startActivity(intent);
//                }
            }
        });
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
