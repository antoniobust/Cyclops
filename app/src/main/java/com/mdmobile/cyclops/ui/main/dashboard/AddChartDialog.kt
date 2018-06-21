package com.mdmobile.cyclops.ui.main.dashboard

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Spinner
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mdmobile.cyclops.R
import com.mdmobile.cyclops.dataModel.chart.Chart
import com.mdmobile.cyclops.utils.GeneralUtility
import com.mdmobile.cyclops.utils.LabelHelper
import com.mdmobile.cyclops.utils.Logger

/**
 * Dialog to be presented in order to create a new chart
 */
class AddChartDialog : DialogFragment(), AdapterView.OnItemSelectedListener, DialogInterface.OnClickListener, TextWatcher {
    private var selectedChartType: Int = -1
    private lateinit var property1TextView: AutoCompleteTextView
    private lateinit var property2TextView: AutoCompleteTextView
    private lateinit var chartTypeSpinner: Spinner
    private lateinit var dialog: AlertDialog
    private val APPLY_LABEL_VISIBILITY_KEY = "APPLY_VISIBILITY_KEY"
    private val LOG_TAG = AddChartDialog::class.java.simpleName

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
            property2TextView.visibility = View.GONE
        } else if (property2TextView.visibility == View.GONE) {
            property2TextView.visibility = View.VISIBLE
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        //There are not empty entries that user could select
    }

    override fun onClick(dialog: DialogInterface?, which: Int) {
        if (property1TextView.text.isEmpty() || which == Dialog.BUTTON_NEGATIVE) {
            Logger.log(LOG_TAG, "Chart canceled returning", Log.VERBOSE)
            return
        } else if (which == Dialog.BUTTON_POSITIVE) {
            val prefCurrentValue: String = context!!.getSharedPreferences(getString(R.string.general_shared_preference), Context.MODE_PRIVATE)
                    .getString(getString(R.string.charts_preference), String())
            val listType = object : TypeToken<List<Chart>>() {}.type
            val gson = Gson()
            var chartList: ArrayList<Chart> = ArrayList()
            if (prefCurrentValue.isNotEmpty()) {
                chartList = gson.fromJson(prefCurrentValue, listType)
            }
            chartList.add(getCurrentValues())
            val jsonString = gson.toJson(chartList, listType)
            GeneralUtility.setSharedPreference(context, getString(R.string.charts_preference), jsonString)

            Logger.log(LOG_TAG,
                    "Added:\n${jsonString.replace("},{", "}\n{", true)}\n in saved charts",
                    Log.VERBOSE)

        }
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        //Do not need to perform any action
    }

    override fun afterTextChanged(s: Editable?) {
        val enabled: Boolean = !LabelHelper.getInternalLabelFor(property1TextView.text.toString()).isEmpty()

        if (property2TextView.visibility == View.GONE) {
            dialog.getButton(Dialog.BUTTON_POSITIVE).isEnabled = enabled
        } else if (property2TextView.visibility == View.VISIBLE) {
            dialog.getButton(Dialog.BUTTON_POSITIVE).isEnabled =
                    enabled && LabelHelper.getInternalLabelFor(property2TextView.text.toString()).isEmpty()
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        //Do not need to perform any action
    }

    // -- Lifecycle methods
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val container: ViewGroup = activity!!.window.decorView as ViewGroup
        val layoutInflater: LayoutInflater = context!!.getSystemService(Activity.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val rootView: View = layoutInflater.inflate(R.layout.dialog_add_chart, container, false)

        property1TextView = rootView.findViewById(R.id.chart_property_1)
        property2TextView = rootView.findViewById(R.id.chart_property_2)

        chartTypeSpinner = rootView.findViewById(R.id.chart_type_spinner)
        val spinnerAdapter: ArrayAdapter<CharSequence> =
                ArrayAdapter.createFromResource(context, R.array.chart_types_label, android.R.layout.simple_dropdown_item_1line)

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        chartTypeSpinner.adapter = spinnerAdapter
        chartTypeSpinner.onItemSelectedListener = this

        val properties = LabelHelper.getStatisticProperties()
        val labelsList: ArrayList<String> = ArrayList()
        properties.mapTo(labelsList) { it.uiLabel }
        property1TextView.setAdapter(ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, labelsList))
        property2TextView.setAdapter(ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, labelsList))


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
        property1TextView.addTextChangedListener(this);
        property2TextView.addTextChangedListener(this);
    }

    override fun onPause() {
        super.onPause()
        property1TextView.removeTextChangedListener(this);
        property2TextView.removeTextChangedListener(this);

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(APPLY_LABEL_VISIBILITY_KEY, dialog.getButton(Dialog.BUTTON_POSITIVE).isEnabled);
    }

    private fun getCurrentValues(): Chart {
        val firstProperty = LabelHelper.getInternalLabelFor(property1TextView.text.toString())
        if (property2TextView.visibility == View.GONE) {
            return Chart(chartTypeSpinner.selectedItemPosition, firstProperty)
        }
        return Chart(chartTypeSpinner.selectedItemPosition, firstProperty,
                LabelHelper.getInternalLabelFor(property2TextView.text.toString()))
    }
}