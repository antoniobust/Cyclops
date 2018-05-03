package com.mdmobile.cyclops.adapters;


import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mdmobile.cyclops.R;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {

    Cursor mCursor;

    public UserListAdapter(Cursor c) {
        mCursor = c;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (mCursor == null || !mCursor.moveToPosition(position)) {
            return;
        }

        String serverName = mCursor.getString(0);
        holder.nameView.setText(serverName);
        if (mCursor.getInt(1) == 1) {
            holder.nameView.setCompoundDrawablesWithIntrinsicBounds(null,
                    null, holder.nameView.getContext().getResources().getDrawable(R.drawable.ic_lock), null);
        }
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
            nameView = itemView.findViewById(R.id.user_list_text_view);
        }
    }
}
