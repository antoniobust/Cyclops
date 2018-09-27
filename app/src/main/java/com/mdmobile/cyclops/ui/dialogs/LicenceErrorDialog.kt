package com.mdmobile.cyclops.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.DialogFragment
import com.mdmobile.cyclops.R
import com.mdmobile.cyclops.ui.main.MainActivity

class LicenceErrorDialog : DialogFragment() {
    companion object {
        private const val RETRY_EXTRA_KEY = "retry_extra_key"
        fun newInstance(retry: Boolean): LicenceErrorDialog {
            val b = Bundle()
            b.putBoolean(RETRY_EXTRA_KEY, retry)
            val frag = LicenceErrorDialog()
            frag.arguments = b
            frag.isCancelable = false
            return frag
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val retry = if (arguments == null) {
            false
        } else {
            arguments!!.getBoolean(RETRY_EXTRA_KEY, false)
        }

        val dialog = AlertDialog.Builder(context)
                .setTitle(R.string.licence_error_dialog_title)
                .setMessage(R.string.licence_error_message)

        if (retry) {
            dialog.setPositiveButton(R.string.retry_label) { _, _ ->
                (activity as MainActivity).checkLicence()
            }
        } else {
            dialog.setPositiveButton(R.string.play_store_label) { _, _ ->
                val appPackageName = context?.packageName
                try {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$appPackageName")))
                } catch (anfe: android.content.ActivityNotFoundException) {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")))
                }
            }
        }

        return dialog.create()
    }
}