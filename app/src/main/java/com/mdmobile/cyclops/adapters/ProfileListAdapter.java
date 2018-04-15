package com.mdmobile.cyclops.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mdmobile.cyclops.R;
import com.mdmobile.cyclops.dataModel.api.Profile;

import java.util.ArrayList;

/**
 * Adapter responsible of populating profiles list
 */

public class ProfileListAdapter extends RecyclerView.Adapter<ProfileListAdapter.ViewHolder> {
    private ArrayList<Profile> profiles;

    public ProfileListAdapter(ArrayList<Profile> profiles) {
        this.profiles = profiles;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (profiles == null) {
            //TODO:show empty view
            return;
        }
        Profile p = profiles.get(position);
        String profileName = p.getName();
        String profileStatus = p.getStatus();
        holder.profileNameView.setText(profileName);
        holder.profileStatusView.setText(profileStatus);
    }

    @Override
    public int getItemCount() {
        return profiles == null ? 0 : profiles.size();
    }

    public ArrayList<Profile> swapData(ArrayList<Profile> profiles) {
        ArrayList<Profile> oldData = this.profiles;
        this.profiles = profiles;
        if (this.profiles != null) {
            this.notifyDataSetChanged();
        }
        return oldData;
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
