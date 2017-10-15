package com.mdmobile.pocketconsole.ui.Dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.mdmobile.pocketconsole.R;

/**
 * Dialog to set a folder to be displayed in main screen
 */

public class PinFolderDialog extends DialogFragment implements Dialog.OnClickListener, EditText.OnKeyListener {

    String currentPreference;
    String preferenceKey;
    String folderPreferenceKey;
    EditText folderPathView;

    public PinFolderDialog() {

    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        ViewGroup container = (ViewGroup) getActivity().getWindow().getDecorView();
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.pin_folder_dialog, container, false);


        folderPathView = (EditText) view.findViewById(R.id.dialog_folder_path_edit_text);
        folderPathView.setOnKeyListener(this);

        AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.pin_a_folder_dialog_title)
                .setPositiveButton(getString(R.string.dialog_apply_label), this)
                .setNegativeButton(getString(R.string.dialog_cancel_label), this)
                .setView(view).create();

        preferenceKey = getContext().getString(R.string.general_shared_preference);
        folderPreferenceKey = getContext().getString(R.string.folder_preference);
        currentPreference = getContext().getSharedPreferences(preferenceKey, Context.MODE_PRIVATE).getString(folderPreferenceKey, "");

        if (!currentPreference.equals("")) {
            folderPathView.setText(currentPreference);
        }

        if (folderPathView.getText().length() > 0) {
            folderPathView.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.ic_close), null);
        }


        return alertDialog;
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        if (i == Dialog.BUTTON_POSITIVE) {
            String currentSelection = folderPathView.getText().toString();
            if (!currentSelection.equals(currentPreference)) {
                SharedPreferences.Editor preferenceEditor = getContext().getApplicationContext()
                        .getSharedPreferences(preferenceKey, Context.MODE_PRIVATE).edit();
                preferenceEditor.putString(folderPreferenceKey, folderPathView.getText().toString());
                preferenceEditor.apply();
            }
        } else {
            dialogInterface.dismiss();
        }
    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (keyEvent.getAction() == KeyEvent.ACTION_UP) {
            if (folderPathView.getText().length() > 0) {
                folderPathView.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.ic_close), null);
            } else {
                folderPathView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            }
            return true;
        }
        return false;
    }
}
