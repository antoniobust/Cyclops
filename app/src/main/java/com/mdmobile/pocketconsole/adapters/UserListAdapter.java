package com.mdmobile.pocketconsole.adapters;


import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mdmobile.pocketconsole.R;
import com.mdmobile.pocketconsole.provider.McContract;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {

    Cursor mCursor;

    public UserListAdapter(Cursor c){
        mCursor = c;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_item, parent, false);
        return new ViewHolder(view);    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (mCursor== null || !mCursor.moveToFirst()) {
            return;
        }
        mCursor.moveToPosition(position);
        String serverName = mCursor.getString(mCursor.getColumnIndex(McContract.ServerInfo.NAME));
        holder.nameView.setText(serverName);
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

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView nameView;
        public ViewHolder(View itemView) {
            super(itemView);
            nameView = (TextView) itemView.findViewById(R.id.user_list_text_view);
        }
    }
}
