package com.mdmobile.pocketconsole.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.mdmobile.pocketconsole.R;
import com.mdmobile.pocketconsole.provider.McContract;

/**
 * Abstract class to be implemented form MS and DS adapters
 */

public  abstract class ServerDetailsAdapter extends CursorAdapter implements ListAdapter {


    public ServerDetailsAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }


    @Override
    public Object getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        TextView textView = new TextView(context);
        textView.setTag("listViewItem");
        textView.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return textView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }

    @Override
    public abstract void bindView(View view, Context context, Cursor cursor);

    @Override
    public int getCount() {
        return getCursor() == null ? 0 : getCursor().getCount();
    }


}
