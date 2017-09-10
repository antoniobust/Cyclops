package com.mdmobile.pocketconsole.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.TextView;

import com.mdmobile.pocketconsole.gson.ServerInfo;
import com.mdmobile.pocketconsole.provider.McContract;

/**
 * Responsible to populate MS info
 */

public class MsInfoAdapter extends ServerDetailsAdapter{
    public MsInfoAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        if(cursor == null || !cursor.moveToFirst()){
            return;
        }
        String serverName = cursor.getString(cursor.getColumnIndex(McContract.ManagementServer.NAME));
        TextView textView = (TextView)view.findViewWithTag("listViewItem");
        textView.setText(serverName);
    }
}
