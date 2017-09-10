package com.mdmobile.pocketconsole.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.TextView;

import com.mdmobile.pocketconsole.provider.McContract;

/**
 * Responsible to populate DS info
 */

public class DsInfoAdapter extends ServerDetailsAdapter {

    public DsInfoAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        if(cursor == null || !cursor.moveToFirst()){
            return;
        }
        String serverName = cursor.getString(cursor.getColumnIndex(McContract.DeploymentServer.NAME));
        TextView textView = (TextView)view.findViewWithTag("listViewItem");
        textView.setText(serverName);
    }
}
