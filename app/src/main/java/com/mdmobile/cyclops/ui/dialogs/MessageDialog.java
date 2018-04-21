package com.mdmobile.cyclops.ui.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.mdmobile.cyclops.R;
import com.mdmobile.cyclops.apiManager.ApiRequestManager;
import com.mdmobile.cyclops.dataTypes.ApiActions;

import static com.mdmobile.cyclops.ui.main.deviceDetails.DeviceDetailsActivity.DEVICE_ID_EXTRA_KEY;


/**
 * Dialog shown to let the user send a script to a device
 */

public class MessageDialog extends android.support.v4.app.DialogFragment
        implements DialogInterface.OnClickListener, TextWatcher {


    EditText editText;
    AlertDialog alertDialog;
    String deviceID;

    public MessageDialog() {

    }

    public static MessageDialog newInstance(String devID) {
        Bundle args = new Bundle();
        args.putString(DEVICE_ID_EXTRA_KEY, devID);
        MessageDialog dialog = new MessageDialog();
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        deviceID = getArguments().getString(DEVICE_ID_EXTRA_KEY);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        ViewGroup container = (ViewGroup) ((Activity) getContext()).getWindow().getDecorView();
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.dialog_send_message, container, false);

        editText = (EditText) view.findViewById(R.id.message_edit_text);
        editText.addTextChangedListener(this);

        Dialog dialog = new AlertDialog.Builder(getContext())
                .setView(view)
                .setTitle(R.string.send_message_dialog_title)
                .setMessage(R.string.message_dialog_description)
                .setPositiveButton(R.string.dialog_send_button_label, this)
                .setNegativeButton(R.string.dialog_cancel_label, this).create();

//        alertDialog = (AlertDialog) getDialog();
//        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);

        return dialog;
    }


    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        if (i == DialogInterface.BUTTON_POSITIVE) {
            String message = editText.getText().toString();
            ApiRequestManager.getInstance().requestAction(deviceID, ApiActions.SEND_MESSAGE, message, null);
        } else {
            dialogInterface.dismiss();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
//        if (charSequence.length() == 0) {
//            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
//        } else {
//            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);

//        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}

