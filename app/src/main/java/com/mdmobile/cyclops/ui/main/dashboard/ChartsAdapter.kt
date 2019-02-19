package com.mdmobile.cyclops.ui.main.dashboard

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mdmobile.cyclops.CyclopsApplication.Companion.applicationContext
import com.mdmobile.cyclops.R
import com.mdmobile.cyclops.dataModel.chart.Chart
import com.mdmobile.cyclops.ui.main.dashboard.statistics.StatDataEntry
import com.mdmobile.cyclops.util.GeneralUtility
import com.mdmobile.cyclops.util.LabelHelper


/**
 * Chart recycler nameView adapter
 */

class ChartsAdapter(private var chartsDataList: ArrayList<kotlin.Pair<String, ArrayList<StatDataEntry>>> = ArrayList<kotlin.Pair<String, ArrayList<StatDataEntry>>>())
    : RecyclerView.Adapter<ChartsAdapter.ChartViewHolder>(), View.OnClickListener,
        android.widget.PopupMenu.OnMenuItemClickListener {

    private val LOG_TAG = ChartsAdapter::class.java.simpleName
    private val colors = arrayOf(R.color.blue_dark, R.color.teal, R.color.colorPrimary, R.color.red,
            R.color.orange, R.color.yellow, R.color.dark_grey).toIntArray()


    override fun onClick(view: View) {

    }

    override fun onMenuItemClick(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_remove_chart) {
            return true
        }
        return false
    }

    override fun getItemViewType(position: Int): Int {
        return 0
    }

    override fun getItemCount(): Int {
        return if (chartsDataList.isEmpty()) 0 else chartsDataList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChartViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.chart_recycler_item, parent, false)
        return ChartViewHolder(item)
    }

    override fun onBindViewHolder(holder: ChartViewHolder, position: Int) {
        if (itemCount == 0) {
            return
        }
        //TODO: this will always create a pie chart -> fix this
        val chart = ChartFactory.instantiate(holder.chartContainer.context,
                holder.itemViewType).createChart(holder.chartContainer.context) as PieChart
        createPieChart(holder, chart, position)
        chart.setOnChartValueSelectedListener(holder)
        chart.id = R.id.chart
        holder.chartContainer.addView(chart)
        holder.optionButton.setOnClickListener { v ->
            val popup = PopupMenu(v.context, v)
            popup.inflate(R.menu.chart_card_menu)
            popup.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.action_remove_chart -> {
                        removeChart(holder.adapterPosition)
                        return@OnMenuItemClickListener true
                    }
                }
                false
            })
            popup.show()
        }

    }

    fun updateData(chartsDataList: ArrayList<kotlin.Pair<String, ArrayList<StatDataEntry>>>) {
        this.chartsDataList = chartsDataList
        notifyDataSetChanged()
    }

    fun addChart(chartDataList: kotlin.Pair<String, ArrayList<StatDataEntry>>, position: Int) {
        chartsDataList.add(chartDataList)
        notifyItemInserted(position)
    }

    private fun removeChart(adapterPosition: Int) {
        chartsDataList.removeAt(adapterPosition)
        val prefCurrentValue: String? = applicationContext.getSharedPreferences(
                applicationContext.getString(R.string.general_shared_preference), Context.MODE_PRIVATE)
                .getString(applicationContext.getString(R.string.charts_preference), String())
        val listType = object : TypeToken<List<Chart>>() {}.type
        val gson = Gson()
        var chartList: ArrayList<Chart> = ArrayList()
        if (prefCurrentValue != null && prefCurrentValue.isNotEmpty()) {
            chartList = gson.fromJson(prefCurrentValue, listType)
        }
        chartList.removeAt(adapterPosition)
        val jsonString = gson.toJson(chartList, listType)
        GeneralUtility.setSharedPreference(applicationContext, applicationContext.getString(R.string.charts_preference), jsonString)
        notifyItemRemoved(adapterPosition)
    }

    private fun createPieChart(holder: ChartViewHolder, pieChart: PieChart, position: Int) {
        val pieEntries = ArrayList<PieEntry>()
        val pieDataSet: PieDataSet
        val pieData = PieData()
        val chartData = chartsDataList[position]

        if (chartData.second.size > 0) {
            for (i in chartData.second.indices) {
                pieEntries.add(PieEntry(chartData.second[i].value.toFloat(), chartData.second[i].label))
            }
            pieDataSet = PieDataSet(pieEntries, null)

            pieDataSet.setColors(colors, holder.itemView.context)
            pieData.addDataSet(pieDataSet)
            pieData.setValueTextSize(0f)
            pieChart.data = pieData
        }

        val legend = pieChart.legend
        legend.isWordWrapEnabled = true
        legend.form = Legend.LegendForm.CIRCLE
        legend.formSize = 8f
        legend.xEntrySpace = 12f
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
        legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        legend.maxSizePercent = 0.2f

        var chartProperty = chartsDataList[position].first
        chartProperty = LabelHelper.getUiLabelFor(chartProperty)
        val descriptionLabel = Description()
        descriptionLabel.isEnabled = true
        descriptionLabel.text = chartProperty
        descriptionLabel.typeface = Typeface.create("roboto_bold", Typeface.BOLD)
        descriptionLabel.textSize = 12f
        descriptionLabel.textColor = R.color.colorPrimaryDark
        pieChart.description = descriptionLabel

        pieChart.setCenterTextTypeface(Typeface.create("roboto_bold", Typeface.BOLD))
        pieChart.setCenterTextColor(R.color.colorPrimaryDark)
        pieChart.setCenterTextSize(32f)
        pieChart.holeRadius = 50f
        pieChart.transparentCircleRadius = 60f
        pieChart.setTransparentCircleAlpha(70)
        pieChart.setOnChartValueSelectedListener(holder)

        pieChart.setNoDataText(holder.chartContainer.context.getString(R.string.no_data_found_chart))
        pieChart.setNoDataTextColor(holder.itemView.context.resources.getColor(R.color.colorPrimary))
    }

    class ChartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), OnChartValueSelectedListener {
        var optionButton: ImageButton = itemView.findViewById(R.id.chart_option_button)
        var chartContainer: FrameLayout = itemView.findViewById(R.id.chart_container)

        override fun onValueSelected(e: Entry, h: Highlight) {
            val value = h.y.toInt().toString()

            (chartContainer.findViewById<View>(R.id.chart) as PieChart).centerText = value

        }

        override fun onNothingSelected() {
            val chart = chartContainer.findViewById<PieChart>(R.id.chart)
            if (chart != null) {
                chart.centerText = ""
            }
        }
    }
}