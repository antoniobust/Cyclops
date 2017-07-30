package com.mdmobile.pocketconsole.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import com.mdmobile.pocketconsole.R;
import com.mdmobile.pocketconsole.apiManager.ApiRequestManager;
import com.mdmobile.pocketconsole.dataTypes.ApiActions;
import com.mdmobile.pocketconsole.provider.McContract;
import com.mdmobile.pocketconsole.ui.Dialogs.MessageDialog;
import com.mdmobile.pocketconsole.ui.Dialogs.ScriptDialog;
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
    private String selected;

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

        holder.deviceNameView.setText(data.getString(data.getColumnIndex(McContract.Device.COLUMN_DEVICE_NAME)));
        Drawable dot;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            dot = holder.deviceNameView.getContext().getResources().getDrawable(R.drawable.connectivity_status_dot, null);
        } else {
            dot = holder.deviceNameView.getContext().getResources().getDrawable(R.drawable.connectivity_status_dot);
        }
        holder.deviceNameView.setCompoundDrawablePadding(50);

        if (data.getInt(data.getColumnIndex(McContract.Device.COLUMN_AGENT_ONLINE)) == 1) {
            ((GradientDrawable) dot).setColor(holder.deviceNameView.getContext().getResources().getColor(R.color.darkGreen));
        } else {
            ((GradientDrawable) dot).setColor(Color.LTGRAY);
        }

        holder.deviceNameView.setCompoundDrawablesWithIntrinsicBounds(dot, null, null, null);
        //TODO: column "kind" doesn't get populated from gson, check it
//            String kind =data.getString(data.getColumnIndex(McContract.Device.COLUMN_KIND));
        String platform = data.getString(data.getColumnIndex(McContract.Device.COLUMN_PLATFORM));
        if (platform == null) {
            holder.deviceIconView.setImageResource(R.drawable.ic_phone_android);
        } else switch (platform) {
            case "Android":
                holder.deviceIconView.setImageResource(R.drawable.ic_phone_android);
                break;
            case "iOS":
                holder.deviceIconView.setImageResource(R.drawable.ic_phone_iphone);
                break;
            default:
                holder.deviceIconView.setImageResource(R.drawable.ic_phone_android);
        }

        //Set transition name for shared element transition
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            String devID = data.getString(data.getColumnIndex(McContract.Device.COLUMN_DEVICE_ID));
            holder.deviceIconView.setTransitionName("icon_" + devID);
            holder.deviceNameView.setTransitionName("name_" + devID);
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
                intent.putExtra(EXTRA_DEVICE_ICON_TRANSITION_NAME_KEY, ViewCompat.getTransitionName(holder.deviceIconView));
                intent.putExtra(EXTRA_DEVICE_NAME_TRANSITION_NAME_KEY, ViewCompat.getTransitionName(holder.deviceNameView));


//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    ActivityOptionsCompat options = ActivityOptionsCompat.
//                            makeSceneTransitionAnimation((MainActivity) view.getContext(), holder.deviceIconView, holder.deviceIconView.getTransitionName());
//                    view.getContext().startActivity(intent, options.toBundle());
//                } else {
                    view.getContext().startActivity(intent);
//                }
            }
        });

        holder.optionIconView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(data.moveToPosition(holder.getAdapterPosition())) {
                    selected = data.getString(data.getColumnIndex(McContract.Device.COLUMN_DEVICE_ID));
                    showActionsMenu(view);
                }
            }
        });
    }

    @Override
    public long getItemId(int position) {
        if(data.move(position)){
            return data.getInt(data.getColumnIndex(McContract.Device._ID));
        }
        return -1;
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

    private void showActionsMenu(final View view) {
        PopupMenu menu = new PopupMenu(view.getContext(), view, Gravity.START);
        menu.inflate(R.menu.device_action_menu);
        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                        Context mContext = view.getContext();
        switch (menuItem.getItemId()){
            case R.id.action_checkin:
                //Check in action
                ApiRequestManager.getInstance(mContext).requestAction(selected, ApiActions.CHECKIN, null, null);
                break;
            case R.id.action_send_script:
                //Script action
                ScriptDialog.newInstance(selected).show(((MainActivity)mContext).getSupportFragmentManager(), null);
                break;
            case R.id.action_locate:
                //Localize action
                ApiRequestManager.getInstance(mContext).requestAction(selected, ApiActions.LOCATE, null, null);
                break;
            case R.id.action_send_message:
                //send message action
                MessageDialog.newInstance(selected)
                        .show(((MainActivity)mContext).getSupportFragmentManager(), null);
                break;
        }
                return false;
            }
        });
        menu.show();
    }
}
