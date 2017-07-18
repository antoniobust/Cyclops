package com.mdmobile.pocketconsole.ui.Dialogs;

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
import android.widget.Spinner;

import com.mdmobile.pocketconsole.R;
import com.mdmobile.pocketconsole.apiManager.ApiRequestManager;
import com.mdmobile.pocketconsole.dataTypes.ApiActions;
import com.mdmobile.pocketconsole.ui.deviceDetails.DeviceDetailsActivity;

import static com.mdmobile.pocketconsole.ui.deviceDetails.DeviceDetailsActivity.DEVICE_ID_EXTRA_KEY;

/**
 * Dialog shown to let the user send a script to a device
 */

public class ScriptDialog extends android.support.v4.app.DialogFragment implements DialogInterface.OnClickListener, TextWatcher {

    Spinner spinner;
    EditText editText;
    AlertDialog alertDialog;
    String deviceID;

    public ScriptDialog() {

    }

    public static ScriptDialog newInstance(String devID) {
        Bundle args = new Bundle();
        args.putString(DEVICE_ID_EXTRA_KEY, devID);
        ScriptDialog dialog = new ScriptDialog();
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

        ViewGroup container = (ViewGroup) ((DeviceDetailsActivity) getContext()).getWindow().getDecorView();
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.dialog_send_script, container, false);

        spinner = (Spinner) view.findViewById(R.id.preset_scripts_spinner);
        editText = (EditText) view.findViewById(R.id.script_edit_text);
        editText.addTextChangedListener(this);

        Dialog dialog = new AlertDialog.Builder(getContext())
                .setView(view)
                .setTitle(R.string.send_script_dialog_title)
                .setPositiveButton(R.string.dialog_send_button_label, this)
                .setNegativeButton(R.string.dialog_cancel_label, this)
                .setMessage(R.string.script_dialog_description)
                .create();

//        alertDialog = (AlertDialog) getDialog();
//        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);

        return dialog;
    }


    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        if (i == DialogInterface.BUTTON_POSITIVE) {
            String script = editText.getText().toString();
            ApiRequestManager.getInstance(getContext()).requestAction(deviceID, ApiActions.SEND_SCRIPT, script, null);
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

