package com.mdmobile.cyclops.ui.main;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Adapter that populates filter recycler view
 */

public class FiltersRecyclerAdapter extends RecyclerView.Adapter<FiltersRecyclerAdapter.ChipViewHolder> {
    public FiltersRecyclerAdapter() {
    }

    @Override
    public ChipViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ChipViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ChipViewHolder extends RecyclerView.ViewHolder{

        public ChipViewHolder(View itemView) {
            super(itemView);
        }
    }
}
