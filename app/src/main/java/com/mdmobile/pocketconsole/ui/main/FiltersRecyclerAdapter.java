package com.mdmobile.pocketconsole.ui.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Adapter that populates filter recycler view
 */

public class FiltersRecyclersAdapter extends RecyclerView.Adapter<FiltersRecyclersAdapter.ChipViewHolder> {
    public FiltersRecyclersAdapter(Context context) {
        super(context);
    }

    public static class ChipViewHolder extends ViewHolder{

        public ChipViewHolder(View itemView) {
            super(itemView);
        }
    }
}
