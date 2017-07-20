package com.mdmobile.pocketconsole.ui.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

import com.mdmobile.pocketconsole.R;
import com.mdmobile.pocketconsole.apiManager.ApiRequestManager;

/**
 * Alert dialog shown to uninstall an application
 */

public class ConfirmActionDialog extends DialogFragment implements
        Dialog.OnClickListener {

    public final static String DO_NOT_SHOW_ARG_KEY = "doNotShowArgKey";
    private final static String DIALOG_TITLE_ARG_KEY = "titleArgKey";
    private final static String DIALOG_MESSAGE_ARG_KEY = "messageArgKey";
    private final static String POSITIVE_BUTTON_LABEL_ARG_KEY = "positiveButtonArgKey";
    private final static String NEGATIVE_BUTTON_LABEL_ARG_KEY = "negativeButtonArgKey";
    private final static String ICON_RESOURCE_ID = "iconResourceID";
    boolean doNotShow = false;
    private String title, description, positiveButtonLabel, negativeButtonLabel;
    private boolean doNotShowEnabled;
    private int iconResource;

    public ConfirmActionDialog() {

    }

    public static ConfirmActionDialog newInstance(@NonNull String dialogTitle, @NonNull String message,
                                                  int iconResource, @Nullable String positiveButtonLabel,
                                                  @Nullable String negativeButtonLabel, boolean doNotShowOption) {
        final ConfirmActionDialog dialog = new ConfirmActionDialog();
        Bundle args = new Bundle();
        args.putString(DIALOG_TITLE_ARG_KEY, dialogTitle);
        args.putString(DIALOG_MESSAGE_ARG_KEY, message);
        args.putInt(ICON_RESOURCE_ID, iconResource);
        args.putString(POSITIVE_BUTTON_LABEL_ARG_KEY, positiveButtonLabel);
        args.putString(NEGATIVE_BUTTON_LABEL_ARG_KEY, negativeButtonLabel);
        args.putBoolean(DO_NOT_SHOW_ARG_KEY, doNotShowOption);
        dialog.setArguments(args);
        return dialog;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        title = getArguments().getString(DIALOG_TITLE_ARG_KEY);
        description = getArguments().getString(DIALOG_MESSAGE_ARG_KEY);
        iconResource = getArguments().getInt(ICON_RESOURCE_ID, -1);
        positiveButtonLabel = getArguments().getString(POSITIVE_BUTTON_LABEL_ARG_KEY);
        negativeButtonLabel = getArguments().getString(NEGATIVE_BUTTON_LABEL_ARG_KEY);
        doNotShowEnabled = getArguments().getBoolean(DO_NOT_SHOW_ARG_KEY);


        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setTitle(title)
                .setPositiveButton(positiveButtonLabel, this)
                .setNegativeButton(negativeButtonLabel, this)
                .setTitle(title)
                .setMessage(description)
                .setMultiChoiceItems(new CharSequence[]{getString(R.string.do_not_ask_again_label)}, new boolean[]{false}, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int selectedIndex, boolean checked) {
                        if (selectedIndex == 0) {
                            doNotShow = checked;
                        }
                    }
                }).create();

        if (iconResource != -1) {
            dialogBuilder.setIcon(iconResource);
        }

        return dialogBuilder.create();
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
