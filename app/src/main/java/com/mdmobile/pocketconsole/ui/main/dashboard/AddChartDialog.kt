package com.mdmobile.pocketconsole.ui.main.dashboard

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.mdmobile.pocketconsole.R

/**
 * Dialog to be presented in order to create a new chart
 */
class AddChartDialog : DialogFragment(), AdapterView.OnItemSelectedListener, DialogInterface.OnClickListener, TextWatcher {
    private var selectedChartType: Int = -1
    private lateinit var property1: AutoCompleteTextView
    private lateinit var property2: AutoCompleteTextView
    private lateinit var dialog: AlertDialog
    private val APPLY_LABEL_VISIBILITY_KEY = "APPLY_VISIBILITY_KEY"

    companion object {
        fun createDialog(): AddChartDialog {
            return AddChartDialog()
        }
    }

    // -- Interface methods
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        //TODO: here we always hide property 2 -> once stacked charts are implemented modify this condition
        selectedChartType = position
        if (position == ChartFactory.PIE_CHART || position == ChartFactory.BAR_CHART || position == ChartFactory.HORIZONTAL_BAR_CHART) {
            property2.visibility = View.GONE
        } else if (property2.visibility == View.GONE) {
            property2.visibility = View.VISIBLE
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        //There are not empty entries that user could select
    }

    override fun onClick(dialog: DialogInterface?, which: Int) {
        if (which == Dialog.BUTTON_POSITIVE) {

        } else if (which == Dialog.BUTTON_NEGATIVE) {
            return
        }
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        //Do not need to perform any action
    }

    override fun afterTextChanged(s: Editable?) {
        if (property2.visibility == View.GONE) {
            dialog.getButton(Dialog.BUTTON_POSITIVE).isEnabled = !TextUtils.isEmpty(property1.text)
        } else if (property2.visibility == View.VISIBLE) {
            dialog.getButton(Dialog.BUTTON_POSITIVE).isEnabled =
                    !TextUtils.isEmpty(property1.text) && !TextUtils.isEmpty(property2.text)
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        //Do not need to perform any action
    }

    // -- Lifecycle methods
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val container: ViewGroup = activity.window.decorView as ViewGroup
        val layoutInflater: LayoutInflater = context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val rootView: View = layoutInflater.inflate(R.layout.dialog_add_chart, container, false)

        property1 = rootView.findViewById(R.id.chart_property_1)
        property2 = rootView.findViewById(R.id.chart_property_2)

        val autoCompleteAdapter:ArrayAdapter<String> = ArrayAdapter(context)


        val chartTypeSpinner: Spinner = rootView.findViewById(R.id.chart_type_spinner)
        val spinnerAdapter: ArrayAdapter<CharSequence> =
                ArrayAdapter.createFromResource(context, R.array.chart_types_label, android.R.layout.simple_dropdown_item_1line)

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        chartTypeSpinner.adapter = spinnerAdapter
        chartTypeSpinner.onItemSelectedListener = this

        dialog = AlertDialog.Builder(context).setTitle("Create a new chart...")
                .setPositiveButton(R.string.dialog_apply_label, this)
                .setNegativeButton(R.string.dialog_cancel_label, this)
                .setView(rootView)
                .create()
        return dialog
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(APPLY_LABEL_VISIBILITY_KEY)) {
                dialog.getButton(Dialog.BUTTON_POSITIVE).isEnabled = savedInstanceState.getBoolean(APPLY_LABEL_VISIBILITY_KEY);
            }
        }
    }

    override fun onResume() {
        super.onResume()
        property1.addTextChangedListener(this);
        property2.addTextChangedListener(this);
    }

    override fun onPause() {
        super.onPause()
        property1.removeTextChangedListener(this);
        property2.removeTextChangedListener(this);

    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putBoolean(APPLY_LABEL_VISIBILITY_KEY, dialog.getButton(Dialog.BUTTON_POSITIVE).isEnabled);
    }

}