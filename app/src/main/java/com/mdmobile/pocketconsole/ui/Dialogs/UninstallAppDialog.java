package com.mdmobile.pocketconsole.ui.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import com.mdmobile.pocketconsole.R;
import com.mdmobile.pocketconsole.apiHandler.ApiRequestManager;

/**
 * Alert dialog shown to uninstall an application
 */

public class UninstallAppDialog extends DialogFragment implements
        Dialog.OnClickListener {

    public final static String APP_NAME_ARG_KEY = "AppNameArgKey";
    boolean doNotShow = false;
    private String appName;

    public UninstallAppDialog() {

    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        appName = getArguments().getString(APP_NAME_ARG_KEY);


        return new AlertDialog.Builder(getActivity()).setTitle(R.string.sort_devices_menu_label)
                .setPositiveButton(R.string.uninstall_label, this)
                .setNegativeButton(R.string.dialog_cancel_label, this)
                .setIcon(getResources().getDrawable(R.drawable.ic_delete_forever))
                .setTitle(R.string.uninstall_app_dialog_title)
                .setMessage(getString(R.string.uninstall_app_dialog_description, appName))
                .setMultiChoiceItems(new CharSequence[]{getString(R.string.do_not_ask_again_label)}, new boolean[]{false}, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int selectedIndex, boolean checked) {
                        if (selectedIndex == 0) {
                            doNotShow = checked;
                        }
                    }
                })
                .create();
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        if (i == Dialog.BUTTON_POSITIVE) {
            //Set show dialog preference
            if (doNotShow) {
                String preferenceKey = getContext().getString(R.string.shared_preference);
                String uninstallAppDialogPreference = getContext().getString(R.string.uninstall_app_dialog_disabled_pref);
                getContext().getApplicationContext()
                        .getSharedPreferences(preferenceKey, Context.MODE_PRIVATE).edit()
                        .putBoolean(uninstallAppDialogPreference, true).apply();
            }

            //Send uninstall app request
            ApiRequestManager.getInstance(getContext());

        } else {
            dialogInterface.dismiss();
        }
    }


}
