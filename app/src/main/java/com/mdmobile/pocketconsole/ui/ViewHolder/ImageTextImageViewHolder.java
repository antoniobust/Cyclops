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

    final ImageView image1View, image2View;
    final TextView descriptionView;

    public ImageTextImageViewHolder(View view) {
        super(view);
        image1View = (ImageView) view.findViewById(R.id.list_item_icon1);
        image2View = (ImageView) view.findViewById(R.id.list_item_icon2);
        descriptionView = (TextView) view.findViewById(R.id.list_item_description);
    }


}
