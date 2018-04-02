package com.mdmobile.cyclops.ui.main.deviceDetails

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * This adapter populates device info profiles apps ecc..
 */

class InfoAdapter(val values: ArrayList<Array<String>>, private val isPreview: Boolean) : RecyclerView.Adapter<InfoAdapter.ViewHolder>() {

    private val PREVIEW_ROWS_NUM = 5

    override fun getItemCount(): Int {
        return if (isPreview) {
            if (values.size * getColumnsCount() > PREVIEW_ROWS_NUM * 2) PREVIEW_ROWS_NUM * 2 else values.size * getColumnsCount()
        } else {
            values.size * getColumnsCount()
        }
    }

    private fun getRowsCount(): Int {
        return if (isPreview) PREVIEW_ROWS_NUM else values.size
    }

    private fun getColumnsCount(): Int {
        return if (values.size == 0) 0 else values[0].size
    }

    private fun getRowPosition(viewPosition: Int): Int {
        return if (viewPosition == 0) viewPosition else (viewPosition / getColumnsCount())
    }

    private fun getColumnPosition(viewPosition: Int): Int {
        if (viewPosition == 0) {
            return viewPosition
        }
        return Math.round(viewPosition % getColumnsCount().toDouble()).toInt()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.textView.text = values[getRowPosition(position)][getColumnPosition(position)]
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(android.R.id.text1)
    }

}