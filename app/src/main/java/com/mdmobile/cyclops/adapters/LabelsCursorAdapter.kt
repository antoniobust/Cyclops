package com.mdmobile.cyclops.adapters

import android.database.Cursor
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mdmobile.cyclops.util.LabelHelper

/**
 * Custom adapter used to populate 2 columns lists -> in a row the first cell is the property Name provided from a String[],
 * the corresponding second cell is the property value provided from Cursors
 */

class LabelsCursorAdapter(private val data: Cursor?, private val labels: Array<String>) : RecyclerView.Adapter<LabelsCursorAdapter.LabelValueHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LabelValueHolder {
        val view = LayoutInflater.from(parent.context).inflate(android.R.layout.test_list_item, parent, false)
        return LabelValueHolder(view)
    }

    override fun onBindViewHolder(holder: LabelValueHolder, position: Int) {
        //if we are on even position set the label text, otherwise the data corresponding
        if (position % 2 == 0) {
            holder.textView.text = labels[position / 2]
            holder.textView.setTypeface(holder.textView.typeface, Typeface.BOLD)
        } else if (data != null && data.moveToPosition(0)) {
            if (data.getColumnIndex(LabelHelper.getInternalLabelFor(labels[position / 2])) > 0) {
                holder.textView.text = data.getString(data.getColumnIndex(LabelHelper.getInternalLabelFor(labels[position / 2])))
            }
        }
    }

    override fun getItemCount(): Int {
        return labels.size * 2
    }


    inner class LabelValueHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal val textView: TextView = itemView.findViewById(android.R.id.text1)
    }
}
