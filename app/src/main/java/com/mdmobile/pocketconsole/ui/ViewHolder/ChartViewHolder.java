package com.mdmobile.pocketconsole.ui.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.mdmobile.pocketconsole.R;


public class ChartViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{
    public ImageView refreshButton;
    public FrameLayout chartContainer;

    public ChartViewHolder(View itemView) {
        super(itemView);
        chartContainer = (FrameLayout) itemView.findViewById(R.id.chart_container);
        refreshButton = (ImageView) itemView.findViewById(R.id.chart_refresh_button);
        refreshButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

    }
}
