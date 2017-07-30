package com.mdmobile.pocketconsole.ui.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mdmobile.pocketconsole.R;

/**
 * Represent a view holder which can recycle a view made of 2 ImageViews and a TextViews
 * To be used in RecyclerViews
 */

public class ImageTextImageViewHolder extends RecyclerView.ViewHolder {

    public final ImageView deviceIconView, optionIconView;
    public final TextView deviceNameView;
    public final View coloredMarkerView;

    public ImageTextImageViewHolder(View view) {
        super(view);
        coloredMarkerView = view.findViewById(R.id.list_item_colored_marker);
        deviceIconView = (ImageView) view.findViewById(R.id.list_item_device_icon);
        optionIconView = (ImageView) view.findViewById(R.id.list_item_menu_icon);
        deviceNameView = (TextView) view.findViewById(R.id.list_item_device_name);
    }


}
