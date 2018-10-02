package com.mdmobile.cyclops.adapters;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.mdmobile.cyclops.R;
import com.mdmobile.cyclops.apiManager.ApiRequestManager;
import com.mdmobile.cyclops.dataModel.Server;
import com.mdmobile.cyclops.dataTypes.ApiActions;
import com.mdmobile.cyclops.provider.McContract;
import com.mdmobile.cyclops.security.ServerNotFound;
import com.mdmobile.cyclops.ui.dialogs.MessageDialog;
import com.mdmobile.cyclops.ui.dialogs.ScriptDialog;
import com.mdmobile.cyclops.ui.main.MainActivity;
import com.mdmobile.cyclops.utils.Logger;
import com.mdmobile.cyclops.utils.ServerUtility;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Adapter bound to list of devices in main activity
 */

public class DevicesListAdapter extends RecyclerView.Adapter<DevicesListAdapter.ViewHolder> {

    private static String LOG_TAG = DevicesListAdapter.class.getSimpleName();
    private Cursor data;
    private String selected;
    private DeviceSelected mSelectionCallback;
    private Context mContext;

    public DevicesListAdapter(Context context, @Nullable Cursor cursor, DeviceSelected callback) {
        mContext = context;

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
        View view = LayoutInflater.from(mContext).inflate(R.layout.device_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (!data.moveToPosition(position)) {
            //Error nameView
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

//        //Set transition Name for shared element transition
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
        PopupMenu menu = new PopupMenu(mContext, view, Gravity.START);
        menu.inflate(R.menu.device_fab_action_menu);
        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Server activeServer;
                try {
                    activeServer = ServerUtility.getActiveServer();
                } catch (ServerNotFound e) {
                    e.printStackTrace();
                    return false;
                }
                switch (menuItem.getItemId()) {
                    case R.id.action_checkin:
                        //Check in action
                        ApiRequestManager.getInstance().requestAction(activeServer, selected, ApiActions.CHECKIN, null, null);
                        break;
                    case R.id.action_send_script:
                        //Script action
                        ScriptDialog.newInstance(selected).show(((MainActivity) mContext).getSupportFragmentManager(), null);
                        break;
                    case R.id.action_locate:
                        //Localize action
                        ApiRequestManager.getInstance().requestAction(activeServer, selected, ApiActions.LOCATE, null, null);
                        break;
                    case R.id.action_send_message:
                        //send message action
                        MessageDialog.Companion.newInstance(selected)
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
                        + " device Name = " + data.getString(data.getColumnIndex(McContract.Device.COLUMN_DEVICE_NAME)), Log.VERBOSE);

                //Report back to main activity item selected
                if (mSelectionCallback != null) {
                    mSelectionCallback.onDeviceSelected(
                            data.getString(data.getColumnIndex(McContract.Device.COLUMN_DEVICE_ID)),
                            data.getString(data.getColumnIndex(McContract.Device.COLUMN_DEVICE_NAME)));
                }

//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    ActivityOptionsCompat options = ActivityOptionsCompat.
//                            makeSceneTransitionAnimation((MainActivity) nameView.getContext(), holder.deviceIconView, holder.deviceIconView.getTransitionName());
//                    nameView.getContext().startActivity(intent, options.toContentValues());
//                } else {
//                    nameView.getContext().startActivity(intent);
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
            deviceIconView = view.findViewById(R.id.list_item_device_icon);
            optionIconView = view.findViewById(R.id.list_item_menu_icon);
            deviceNameView = view.findViewById(R.id.list_item_device_name);
            arrowIconView = view.findViewById(R.id.list_item_arrow_icon);

            view.setOnClickListener(deviceClickListener);
            arrowIconView.setOnClickListener(deviceClickListener);
            optionIconView.setOnClickListener(optionClickListener);
        }
    }
}
