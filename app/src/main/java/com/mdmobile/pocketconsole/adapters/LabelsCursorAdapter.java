package com.mdmobile.pocketconsole.adapters;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Custom adapter used to populate 2 columns lists -> in a row the first cell is the property name provided from a String[],
 * the corresponding second cell is the property value provided from Cursors
 */

public class LabelsCursorAdapter extends RecyclerView.Adapter<LabelsCursorAdapter.LabelValueHolder> {

    String[] labels;
    Cursor data;

    public LabelsCursorAdapter(Cursor data, @NonNull String[] labels) {
        this.labels = labels;
        this.data = data;
    }

    @Override
    public LabelValueHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.test_list_item, parent, false);
        return new LabelValueHolder(view);
    }

    @Override
    public void onBindViewHolder(LabelValueHolder holder, int position) {
        if (position % 2 == 0) {
            holder.textView.setText(labels[position]);
        } else if (data != null && data.moveToPosition(position)) {
            holder.textView.setText(data.getString(position));
        }
    }

    @Override
    public int getItemCount() {
        return labels.length;
    }


    public class LabelValueHolder extends RecyclerView.ViewHolder {

        TextView textView;

        LabelValueHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(android.R.id.text1);
        }
    }
}
