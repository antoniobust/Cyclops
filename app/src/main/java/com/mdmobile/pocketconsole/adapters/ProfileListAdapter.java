package com.mdmobile.pocketconsole.adapters;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mdmobile.pocketconsole.R;
import com.mdmobile.pocketconsole.provider.McContract;

/**
 * Adapter responsible of populating profiles list
 */

public class ProfileListAdapter extends RecyclerView.Adapter<ProfileListAdapter.ViewHolder> {
    private Cursor cursor;

    public ProfileListAdapter(Cursor c) {
        cursor = c;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (cursor == null) {
            //TODO:show empty view
            return;
        }
        cursor.moveToPosition(position);
        String profileName = cursor.getString(cursor.getColumnIndex(McContract.Profile.NAME));
        String profileStatus = cursor.getString(cursor.getColumnIndex(McContract.Profile.STATUS));
        holder.profileNameView.setText(profileName);
        holder.profileStatusView.setText(profileStatus);
    }

    @Override
    public int getItemCount() {
        return cursor == null ? 0 : cursor.getCount();
    }

    public Cursor swapCursor(Cursor c) {
        Cursor oldCursor = cursor;
        cursor = c;
        if (cursor != null) {
            this.notifyDataSetChanged();
        }
        return oldCursor;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView profileNameView, profileStatusView;

        public ViewHolder(View itemView) {
            super(itemView);
            profileNameView = itemView.findViewById(R.id.profile_name_view);
            profileStatusView = itemView.findViewById(R.id.profile_status_view);
        }
    }
}
