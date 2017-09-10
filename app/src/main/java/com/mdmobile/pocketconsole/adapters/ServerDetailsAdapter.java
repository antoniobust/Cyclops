package com.mdmobile.pocketconsole.adapters;

import android.database.Cursor;
import android.provider.BaseColumns;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mdmobile.pocketconsole.R;
import com.mdmobile.pocketconsole.utils.Logger;

import static android.R.attr.data;

/**
 * Abstract class to be implemented form MS and DS adapters
 */

public abstract class ServerDetailsAdapter extends RecyclerView.Adapter<ServerDetailsAdapter.Holder> {

    private static String LOG_TAG = ServerDetailsAdapter.class.getSimpleName();
    private Cursor mCursor;


    public ServerDetailsAdapter(Cursor c) {
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

    public Cursor getCursor(){
        return mCursor;
    }

    @Override
    public int getItemCount() {
        return mCursor == null ? 0 : mCursor.getCount();
    }

    class Holder extends RecyclerView.ViewHolder {

        TextView serverNameView;
        View statusIndicator;
        ImageView imageView;

        public Holder(View itemView) {
            super(itemView);
            serverNameView = (TextView) itemView.findViewById(R.id.server_list_item_server_name);
            statusIndicator = itemView.findViewById(R.id.server_list_item_colored_marker);
            imageView = (ImageView) itemView.findViewById(R.id.list_item_arrow_icon);
        }
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


}
