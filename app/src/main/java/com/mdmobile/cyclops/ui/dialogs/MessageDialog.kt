package com.mdmobile.cyclops.ui.dialogs

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.DialogFragment

import com.mdmobile.cyclops.R
import com.mdmobile.cyclops.apiManager.ApiRequestManager
import com.mdmobile.cyclops.dataTypes.ApiActions
import com.mdmobile.cyclops.utils.ServerUtility

import com.mdmobile.cyclops.ui.main.deviceDetails.DeviceDetailsActivity.DEVICE_ID_EXTRA_KEY


/**
 * Dialog shown to let the user send a script to a device
 */

class MessageDialog : DialogFragment(), DialogInterface.OnClickListener, TextWatcher {


    private var editText: EditText = EditText(context)
    internal var alertDialog: AlertDialog? = null
    private var deviceID: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        deviceID = arguments!!.getString(DEVICE_ID_EXTRA_KEY)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val container = (context as Activity).window.decorView as ViewGroup
        val inflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val view = inflater.inflate(R.layout.dialog_send_message, container, false)

        editText = view.findViewById(R.id.message_edit_text)
        editText.addTextChangedListener(this)

//        alertDialog = (AlertDialog) getDialog();
        //        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);

        return AlertDialog.Builder(context)
                .setView(view)
                .setTitle(R.string.send_message_dialog_title)
                .setMessage(R.string.message_dialog_description)
                .setPositiveButton(R.string.dialog_send_button_label, this)
                .setNegativeButton(R.string.dialog_cancel_label, this).create()
    }


    override fun onClick(dialogInterface: DialogInterface, i: Int) {
        if (i == DialogInterface.BUTTON_POSITIVE) {
            val message = editText.text.toString()
            ApiRequestManager.getInstance().requestAction(ServerUtility.getActiveServer(), deviceID!!, ApiActions.SEND_MESSAGE, message, null)
        } else {
            dialogInterface.dismiss()
        }
    }

    override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

    override fun onTextChanged(charSequence: CharSequence, start: Int, before: Int, count: Int) {
        //        if (charSequence.length() == 0) {
        //            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
        //        } else {
        //            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);

        //        }
    }

    override fun afterTextChanged(editable: Editable) {

    }

    companion object {

        fun newInstance(devID: String): MessageDialog {
            val args = Bundle()
            args.putString(DEVICE_ID_EXTRA_KEY, devID)
            val dialog = MessageDialog()
            dialog.arguments = args
            return dialog
        }
    }
}

