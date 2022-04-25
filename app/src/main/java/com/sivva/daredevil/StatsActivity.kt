package com.sivva.daredevil

import adapters.KAdapter
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.MPPointF
import kotlinx.android.synthetic.main.activity_stats.*

class StatsActivity : AppCompatActivity() {
    private lateinit var shared:SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats)
        shared = getSharedPreferences("stats", MODE_PRIVATE)
        configureChart()
        userActivity.text = "User activity  -- "+shared.getInt(KAdapter.ALIVE,0)
        debug.text = "Debug logs -- "+shared.getInt(KAdapter.DEBUG,0)
        error.text = "Error logs -- "+shared.getInt(KAdapter.ERROR,0)
        exception.text = "Exception logs -- "+shared.getInt(KAdapter.EXCEPTION,0)
        configureChartApp()
    }


    private fun configureChart() {
        chart.setUsePercentValues(true)
        chart.description.isEnabled = false
        chart.setExtraOffsets(5f, 10f, 5f, 5f)
        chart.dragDecelerationFrictionCoef = 0.95f
        chart.centerText = "App health"
        chart.isDrawHoleEnabled = true
        chart.setHoleColor(Color.WHITE)
        chart.setTransparentCircleColor(Color.WHITE)
        chart.setTransparentCircleAlpha(110)
        chart.holeRadius = 58f
        chart.transparentCircleRadius = 61f
        chart.setDrawCenterText(true)
        chart.rotationAngle = 0f
        // enable rotation of the chart by touch
        chart.isRotationEnabled = true
        chart.isHighlightPerTapEnabled = true
        chart.animateY(1400, Easing.EaseInOutQuad)
        // chart.spin(2000, 0, 360);
        val l: Legend = chart.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        l.orientation = Legend.LegendOrientation.VERTICAL
        l.setDrawInside(false)
        l.xEntrySpace = 7f
        l.yEntrySpace = 0f
        l.yOffset = 0f
        // entry label styling
        chart.setEntryLabelColor(Color.WHITE)
        chart.setEntryLabelTextSize(12f)
        setData(10, 20f)
    }

    private fun configureChartApp() {
        chart_app.setUsePercentValues(true)
        chart_app.description.isEnabled = false
        chart_app.setExtraOffsets(5f, 10f, 5f, 5f)
        chart_app.dragDecelerationFrictionCoef = 0.95f
        chart_app.centerText = "App health"
        chart_app.isDrawHoleEnabled = true
        chart_app.setHoleColor(Color.WHITE)
        chart_app.setTransparentCircleColor(Color.WHITE)
        chart_app.setTransparentCircleAlpha(110)
        chart_app.holeRadius = 58f
        chart_app.transparentCircleRadius = 61f
        chart_app.setDrawCenterText(true)
        chart_app.rotationAngle = 0f
        // enable rotation of the chart by touch
        chart_app.isRotationEnabled = true
        chart_app.isHighlightPerTapEnabled = true
        chart_app.animateY(1400, Easing.EaseInOutQuad)
        // chart.spin(2000, 0, 360);
        val l: Legend = chart.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        l.orientation = Legend.LegendOrientation.VERTICAL
        l.setDrawInside(false)
        l.xEntrySpace = 7f
        l.yEntrySpace = 0f
        l.yOffset = 0f
        // entry label styling
        chart_app.setEntryLabelColor(Color.WHITE)
        chart_app.setEntryLabelTextSize(12f)
        setDataApp(10, 20f)
    }

    private fun setData(count: Int, range: Float) {
        val entries = ArrayList<PieEntry>()

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        entries.add(
            PieEntry(
                shared.getInt("400",0).toFloat(), "Bad",
                resources.getDrawable(R.drawable.att_list)
            )
        )
        entries.add(
            PieEntry(
                shared.getInt("200",0).toFloat(), "Good",
                resources.getDrawable(R.drawable.att_list)
            )
        )

        val dataSet = PieDataSet(entries, "Network health")
        dataSet.setDrawIcons(false)
        dataSet.sliceSpace = 3f
        dataSet.iconsOffset = MPPointF(0f, 40f)
        dataSet.selectionShift = 5f

        // add a lot of colors
        val colors = ArrayList<Int>()
        colors.add(resources.getColor(R.color.red))
        colors.add(resources.getColor(R.color.textColorSuccess))
        dataSet.colors = colors
        //dataSet.setSelectionShift(0f);
        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter())
        data.setValueTextSize(11f)
        data.setValueTextColor(Color.WHITE)
        chart.data = data

        // undo all highlights
        chart.highlightValues(null)
        chart.invalidate()
    }
    private fun setDataApp(count: Int, range: Float) {
        val entries = ArrayList<PieEntry>()

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        entries.add(
            PieEntry(
                shared.getInt(KAdapter.DEBUG,0).toFloat(), "Debug",
                resources.getDrawable(R.drawable.att_list)
            )
        )
       entries.add(
            PieEntry(
                shared.getInt(KAdapter.ERROR,0).toFloat(), "Error",
                resources.getDrawable(R.drawable.att_list)
            )
        )
       entries.add(
            PieEntry(
                shared.getInt(KAdapter.EXCEPTION,0).toFloat(), "Exception",
                resources.getDrawable(R.drawable.att_list)
            )
        )


        val dataSet = PieDataSet(entries, "App health")
        dataSet.setDrawIcons(false)
        dataSet.sliceSpace = 3f
        dataSet.iconsOffset = MPPointF(0f, 40f)
        dataSet.selectionShift = 5f

        // add a lot of colors
        val colors = ArrayList<Int>()
        colors.add(resources.getColor(R.color.grey))
        colors.add(resources.getColor(R.color.red))
        colors.add(resources.getColor(R.color.black))

        dataSet.colors = colors
        //dataSet.setSelectionShift(0f);
        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter())
        data.setValueTextSize(11f)
        data.setValueTextColor(Color.WHITE)
        chart_app.data = data

        // undo all highlights
        chart_app.highlightValues(null)
        chart_app.invalidate()
    }
}