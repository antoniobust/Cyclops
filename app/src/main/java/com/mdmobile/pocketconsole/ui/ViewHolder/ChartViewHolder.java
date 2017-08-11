package com.mdmobile.pocketconsole.ui.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import com.mdmobile.pocketconsole.R;


public class ChartViewHolder extends RecyclerView.ViewHolder {
    public Toolbar chartToolbar;
    public FrameLayout chartContainer;

    public ChartViewHolder(View itemView) {
        super(itemView);
        chartContainer = (FrameLayout) itemView.findViewById(R.id.chart_container);
        chartToolbar = (Toolbar) itemView.findViewById(R.id.chart_item_toolbar);
    }
}
