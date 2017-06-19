package com.mdmobile.pocketconsole.ui.Dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;

import com.mdmobile.pocketconsole.R;

/**
 * Dialog to set a folder to be displayed in main screen
 */

public class PinFolderDialog extends DialogFragment implements Dialog.OnClickListener {

    public PinFolderDialog (){

    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.pin_folder_dialog,null);
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.pin_a_folder_dialog_title)
                .setView(view).create();

        return alertDialog;
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {

    }
}
