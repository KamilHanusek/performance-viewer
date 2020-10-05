package pl.performance

import org.knowm.xchart.SwingWrapper
import org.knowm.xchart.XYChart
import org.knowm.xchart.XYChartBuilder
import org.knowm.xchart.style.Styler

object PerformanceViewer {
    private val performanceCounter: PerformanceCounter = PerformanceCounter()

    fun performAndDisplay(xAxisName: String, vararg jobToPerform: PerformJob) {
        val performanceResult = performanceCounter.count(*jobToPerform)
        val chart = XYChart(XYChartBuilder().also {
            it.xAxisTitle(xAxisName)
            it.yAxisTitle("Time in miliseconds")
            it.theme(Styler.ChartTheme.GGPlot2)
        })
        chart.styler.legendPosition = Styler.LegendPosition.InsideN
        performanceResult.forEach { resultJob ->
            val counts = resultJob.testCountsWithTimeTime.map { it.count }
            val timesInMillis = resultJob.testCountsWithTimeTime.map { it.timeInMillis }
            chart.addSeries(resultJob.jobName, counts, timesInMillis)
        }

        SwingWrapper(chart).displayChart()
    }
}
