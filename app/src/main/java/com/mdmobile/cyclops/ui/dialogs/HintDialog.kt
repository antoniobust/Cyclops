package com.mdmobile.cyclops.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import com.mdmobile.cyclops.R

class HintDialog : DialogFragment() {

    companion object {
        private const val MSG_EXTRA_KEY = "hint_extra_key"
        fun newInstance(hintText: String): HintDialog {
            val b = Bundle()
            b.putString(MSG_EXTRA_KEY, hintText)
            val frag = HintDialog()
            frag.arguments = b
            return frag
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(context)
                .setTitle(R.string.tip)
                .setMessage(arguments?.getString(MSG_EXTRA_KEY))
                .setPositiveButton(R.string.ok_label, null)
                .create()
    }
}