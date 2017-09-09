package com.mdmobile.pocketconsole.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.TextView;


public class ServerDetailsAdapter extends CursorAdapter implements ListAdapter {


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
        textView.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//        viewGroup.addView(textView);
        return textView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
//        ((TextView)view).setText(cursor.getPosition());
    }


}
