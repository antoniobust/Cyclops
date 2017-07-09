package com.mdmobile.pocketconsole.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mdmobile.pocketconsole.R;
import com.mdmobile.pocketconsole.ui.ViewHolder.InstalledAppViewHolder;


public class InstalledAppsAdapter extends CursorAdapter {

    public InstalledAppsAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.installed_app_view, parent, false);
        InstalledAppViewHolder viewHolder = new InstalledAppViewHolder(view);
        view.setTag(viewHolder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        InstalledAppViewHolder viewHolder = (InstalledAppViewHolder) view.getTag();

        viewHolder.appNameView.setText(cursor.getString(1));
        viewHolder.appIdView.setText(cursor.getString(2));
        viewHolder.appStatusView.setText(cursor.getString(3));
    }
}
