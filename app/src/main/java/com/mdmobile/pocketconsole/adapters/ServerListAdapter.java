package com.mdmobile.pocketconsole.adapters;

import android.database.Cursor;
import android.os.Parcelable;
import android.provider.BaseColumns;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mdmobile.pocketconsole.R;

/**
 * Abstract class to be implemented form MS and DS adapters
 */

public abstract class ServerListAdapter extends RecyclerView.Adapter<ServerListAdapter.Holder> {

    private static String LOG_TAG = ServerListAdapter.class.getSimpleName();
    private Cursor mCursor;


    ServerListAdapter(Cursor c) {
        mCursor = c;
    }

    @Override
    public long getItemId(int position) {
        if (mCursor != null && mCursor.moveToPosition(position)) {
            return mCursor.getLong(mCursor.getColumnIndex(BaseColumns._ID));
        }
        return super.getItemId(position);
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.server_list_item, parent, false);
        return new Holder(view);
    }

    @Override
    public abstract void onBindViewHolder(Holder holder, int position);

    public Cursor getCursor() {
        return mCursor;
    }

    @Override
    public int getItemCount() {
        return mCursor == null ? 0 : mCursor.getCount();
    }

    public Cursor swapCursor(Cursor cursor) {
        if (mCursor == cursor) {
            return null;
        }
        Cursor oldCursor = mCursor;
        this.mCursor = cursor;
        if (cursor != null) {
            this.notifyDataSetChanged();
        }
        return oldCursor;
    }


    public interface onClick {
        void itemCLicked(Parcelable serverParcel);
    }

    class Holder extends RecyclerView.ViewHolder {

        TextView serverNameView;
        View statusIndicator;
        ImageView imageView;

        Holder(final View itemView) {
            super(itemView);
            itemView.setTag(this);
            serverNameView = (TextView) itemView.findViewById(R.id.server_list_item_server_name);
            statusIndicator = itemView.findViewById(R.id.server_list_item_colored_marker);
            imageView = (ImageView) itemView.findViewById(R.id.list_item_arrow_icon);
        }

        public void setClickListener(View.OnClickListener listener){
            itemView.setOnClickListener(listener);
        }

    }
}

