package com.mdmobile.pocketconsole.ui.Dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioGroup;

import com.mdmobile.pocketconsole.R;

/**
 * Dialog displayed to select how to sort devices displayed in main list
 */

public class SortingDeviceDialog extends DialogFragment implements
        Dialog.OnClickListener, RadioGroup.OnCheckedChangeListener {

    public final static int DEVICE_NAME = 0;
    public final static int DEVICE_NAME_INVERTED = 1;
    public final static int DEVICE_ONLINE = 2;
    public final static int DEVICE_OFFLINE = 3;
    private boolean changed = false;
    private String preferenceKey;
    private String sortPreferenceKey;
    private int currentPreference;
    private RadioGroup radioGroup;

    public SortingDeviceDialog() {

    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        ViewGroup container = (ViewGroup) getActivity().getWindow().getDecorView(;
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.sorting_dialog_fragment, null);
        AlertDialog dialog = new AlertDialog.Builder(getActivity()).setTitle(R.string.sort_devices_menu_label)
                .setPositiveButton(R.string.dialog_apply_label, this)
                .setNegativeButton(R.string.dialog_cancel_label, this)
                .setView(view).create();


        preferenceKey = getContext().getString(R.string.general_shared_preference);
        sortPreferenceKey = getContext().getString(R.string.sorting_shared_preference);
        currentPreference = getContext().getSharedPreferences(preferenceKey, Context.MODE_PRIVATE).getInt(sortPreferenceKey, 0);

        radioGroup = (RadioGroup) view.findViewById(R.id.sorting_option_radio_group);
        radioGroup.check(radioGroup.getChildAt(currentPreference).getId());
        radioGroup.setOnCheckedChangeListener(this);

        return dialog;
    }


    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        if (i == Dialog.BUTTON_POSITIVE && changed) {
            SharedPreferences.Editor preferenceEditor = getContext().getApplicationContext()
                    .getSharedPreferences(preferenceKey, Context.MODE_PRIVATE).edit();

            switch (radioGroup.getCheckedRadioButtonId()) {

                case R.id.sort_option_name:
                    preferenceEditor.putInt(sortPreferenceKey, DEVICE_NAME);
                    break;
                case R.id.sort_option_name_inverted:
                    preferenceEditor.putInt(sortPreferenceKey, DEVICE_NAME_INVERTED);
                    break;
                case R.id.sort_option_online:
                    preferenceEditor.putInt(sortPreferenceKey, DEVICE_ONLINE);
                    break;
                case R.id.sort_option_offline:
                    preferenceEditor.putInt(sortPreferenceKey, DEVICE_OFFLINE);
                    break;
            }
            preferenceEditor.apply();
        } else {
            dialogInterface.dismiss();
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
        changed = true;
    }

}
