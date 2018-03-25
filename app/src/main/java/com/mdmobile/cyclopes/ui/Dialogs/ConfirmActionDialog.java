package com.mdmobile.cyclopes.ui.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;

import com.mdmobile.cyclopes.R;
import com.mdmobile.cyclopes.utils.GeneralUtility;

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
    private static ConfirmAction actionCallback;
    private String title, description, positiveButtonLabel, negativeButtonLabel;
    private boolean doNotShowEnabled;
    private int iconResource;
    private CheckBox checkBox;

    public ConfirmActionDialog() {

    }

    public static ConfirmActionDialog newInstance(@NonNull String dialogTitle, @NonNull String message,
                                                  int iconResource, @Nullable String positiveButtonLabel,
                                                  @Nullable String negativeButtonLabel, boolean doNotShowOption, @NonNull ConfirmAction callback) {
        final ConfirmActionDialog dialog = new ConfirmActionDialog();
        Bundle args = new Bundle();
        args.putString(DIALOG_TITLE_ARG_KEY, dialogTitle);
        args.putString(DIALOG_MESSAGE_ARG_KEY, message);
        args.putInt(ICON_RESOURCE_ID, iconResource);
        args.putString(POSITIVE_BUTTON_LABEL_ARG_KEY, positiveButtonLabel);
        args.putString(NEGATIVE_BUTTON_LABEL_ARG_KEY, negativeButtonLabel);
        args.putBoolean(DO_NOT_SHOW_ARG_KEY, doNotShowOption);
        dialog.setArguments(args);
        actionCallback = callback;
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
                .setMultiChoiceItems(new CharSequence[]{getString(R.string.do_not_ask_again_label)}, new boolean[]{false}, null).create();

        if (iconResource != -1) {
            dialogBuilder.setIcon(iconResource);
        }
        if (doNotShowEnabled) {
            int padding = GeneralUtility.dpToPx(getContext(), 24);
            //Create container with check box
            FrameLayout viewContainer = new FrameLayout(getContext());
            FrameLayout.LayoutParams frameLayoutParam = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            frameLayoutParam.setMargins(padding, padding, padding, padding);
            viewContainer.setLayoutParams(frameLayoutParam);

            checkBox = new CheckBox(getContext());
            checkBox.setChecked(false);
            checkBox.setText(R.string.do_not_ask_again_label);

            viewContainer.addView(checkBox, frameLayoutParam);
            if (savedInstanceState != null && savedInstanceState.containsKey(DO_NOT_SHOW_ARG_KEY)) {
                checkBox.setChecked(savedInstanceState.getBoolean(DO_NOT_SHOW_ARG_KEY));
            }
            //Add container to dialog
            dialogBuilder.setView(viewContainer);
        }


        return dialogBuilder.create();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (checkBox != null) {
            outState.putBoolean(DO_NOT_SHOW_ARG_KEY, checkBox.isChecked());
        }
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        if (i == Dialog.BUTTON_POSITIVE) {
            //Send confirmation back to the creator
            actionCallback.actionConfirmed(checkBox.isChecked());
        } else {
            dialogInterface.dismiss();
            //Send action canceled back to creator
            actionCallback.actionCanceled();
        }
    }

    public interface ConfirmAction {
        void actionConfirmed(boolean doNotShowAgain);

        void actionCanceled();
    }
}
