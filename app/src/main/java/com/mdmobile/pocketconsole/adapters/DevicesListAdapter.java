package com.mdmobile.pocketconsole.adapters;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.mdmobile.pocketconsole.R;
import com.mdmobile.pocketconsole.apiManager.ApiRequestManager;
import com.mdmobile.pocketconsole.dataTypes.ApiActions;
import com.mdmobile.pocketconsole.provider.McContract;
import com.mdmobile.pocketconsole.ui.Dialogs.MessageDialog;
import com.mdmobile.pocketconsole.ui.Dialogs.ScriptDialog;
import com.mdmobile.pocketconsole.ui.main.MainActivity;
import com.mdmobile.pocketconsole.utils.Logger;

/**
 * Adapter bound to list of devices in main activity
 */

public class DevicesListAdapter extends RecyclerView.Adapter<DevicesListAdapter.ViewHolder> {

    private static String LOG_TAG = DevicesListAdapter.class.getSimpleName();
    private Cursor data;
    private String selected;
    private DeviceSelected mSelectionCallback;

    public DevicesListAdapter(@Nullable Cursor cursor, DeviceSelected callback) {
        if (cursor != null) {
            setHasStableIds(true);
            data = cursor;
            swapCursor(cursor);
        }
        if (callback != null) {
            mSelectionCallback = callback;
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.device_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (!data.moveToPosition(position)) {
            //Error view
        }

        holder.deviceNameView.setText(data.getString(data.getColumnIndex(McContract.Device.COLUMN_DEVICE_NAME)));

        if (data.getInt(data.getColumnIndex(McContract.Device.COLUMN_AGENT_ONLINE)) == 1) {
            holder.coloredMarkerView.setBackgroundColor(holder.deviceNameView.getContext().getResources().getColor(R.color.darkGreen));
        } else {
            holder.coloredMarkerView.setBackgroundColor(Color.LTGRAY);
        }

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

//        //Set transition name for shared element transition
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            String devID = data.getString(data.getColumnIndex(McContract.Device.COLUMN_DEVICE_ID));
//            holder.deviceIconView.setTransitionName("icon_" + devID);
//            holder.deviceNameView.setTransitionName("name_" + devID);
//        }
    }

    @Override
    public long getItemId(int position) {
        if (data.move(position)) {
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
                switch (menuItem.getItemId()) {
                    case R.id.action_checkin:
                        //Check in action
                        ApiRequestManager.getInstance().requestAction(selected, ApiActions.CHECKIN, null, null);
                        break;
                    case R.id.action_send_script:
                        //Script action
                        ScriptDialog.newInstance(selected).show(((MainActivity) mContext).getSupportFragmentManager(), null);
                        break;
                    case R.id.action_locate:
                        //Localize action
                        ApiRequestManager.getInstance().requestAction(selected, ApiActions.LOCATE, null, null);
                        break;
                    case R.id.action_send_message:
                        //send message action
                        MessageDialog.newInstance(selected)
                                .show(((MainActivity) mContext).getSupportFragmentManager(), null);
                        break;
                }
                return false;
            }
        });
        menu.show();
    }

    public interface DeviceSelected {
        void onDeviceSelected(String devId, String devName);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        final ImageView deviceIconView, optionIconView, arrowIconView;
        final TextView deviceNameView;
        final View coloredMarkerView;
        //Set click listener
        View.OnClickListener deviceClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data.moveToPosition(getAdapterPosition());
                Logger.log(LOG_TAG, "Clicked on item:" + getAdapterPosition()
                        + " device name = " + data.getString(data.getColumnIndex(McContract.Device.COLUMN_DEVICE_NAME)), Log.VERBOSE);

                //Report back to main activity item selected
                if (mSelectionCallback != null) {
                    mSelectionCallback.onDeviceSelected(
                            data.getString(data.getColumnIndex(McContract.Device.COLUMN_DEVICE_ID)),
                            data.getString(data.getColumnIndex(McContract.Device.COLUMN_DEVICE_NAME)));
                }

//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    ActivityOptionsCompat options = ActivityOptionsCompat.
//                            makeSceneTransitionAnimation((MainActivity) view.getContext(), holder.deviceIconView, holder.deviceIconView.getTransitionName());
//                    view.getContext().startActivity(intent, options.toBundle());
//                } else {
//                    view.getContext().startActivity(intent);
//                }
            }
        };
        View.OnClickListener optionClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (data.moveToPosition(getAdapterPosition())) {
                    selected = data.getString(data.getColumnIndex(McContract.Device.COLUMN_DEVICE_ID));
                    showActionsMenu(view);
                }
            }
        };

        ViewHolder(View view) {
            super(view);
            coloredMarkerView = view.findViewById(R.id.device_list_item_colored_marker);
            deviceIconView = (ImageView) view.findViewById(R.id.list_item_device_icon);
            optionIconView = (ImageView) view.findViewById(R.id.list_item_menu_icon);
            deviceNameView = (TextView) view.findViewById(R.id.list_item_device_name);
            arrowIconView = (ImageView) view.findViewById(R.id.list_item_arrow_icon);

            view.setOnClickListener(deviceClickListener);
            arrowIconView.setOnClickListener(deviceClickListener);
            optionIconView.setOnClickListener(optionClickListener);
        }
    }
}
