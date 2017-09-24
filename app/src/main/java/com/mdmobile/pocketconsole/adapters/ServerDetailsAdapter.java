package com.mdmobile.pocketconsole.adapters;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Adapter responsible to populate a server
 */

public class ServerDetailsAdapter extends RecyclerView.Adapter<ServerDetailsAdapter.ViewHolder> {

    Cursor mCursor;

    public ServerDetailsAdapter(Cursor cursor) {
        mCursor = cursor;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (mCursor != null && mCursor.getCount() > 0) {
            holder.textView.setText("Hellooo");
        }
    }


    @Override
    public int getItemCount() {
//        return mCursor == null ? 0 : mCursor.getCount();
        return 100;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
