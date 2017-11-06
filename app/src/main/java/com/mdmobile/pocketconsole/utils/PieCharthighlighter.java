package com.mdmobile.pocketconsole.utils;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.highlight.ChartHighlighter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.BarLineScatterCandleBubbleDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;

import java.util.List;

/**
 * Custome highlighter for pie charts in dashboard
 */

public class PieCharthighlighter extends ChartHighlighter {

    PieChart chart;

    public PieCharthighlighter(BarLineScatterCandleBubbleDataProvider chart) {
        super(chart);
        this.chart = (PieChart) chart;
    }

    @Override
    protected List<Highlight> buildHighlights(IDataSet set, int dataSetIndex, float xVal, DataSet.Rounding rounding) {
        List<Highlight> highlights = super.buildHighlights(set, dataSetIndex, xVal, rounding);
        Description description = chart.getDescription();

//        description.setText();
        return null;
    }
}
