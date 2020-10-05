package pl.performance

import org.knowm.xchart.SwingWrapper
import org.knowm.xchart.XYChart
import org.knowm.xchart.XYChartBuilder

class PerformanceViewer(
        private val performanceCounter: PerformanceCounter = PerformanceCounter()
) {

    fun performAndDisplay(vararg jobToPerform: PerformJob) {
        val performanceResult = performanceCounter.count(*jobToPerform)
        val chart = XYChart(XYChartBuilder().also {
            it.xAxisTitle("UNKNOWN")
            it.yAxisTitle("Time in miliseconds")
        })
        performanceResult.forEach { resultJob ->
            val counts = resultJob.testCountsWithTimeTime.map { it.count }
            val timesInMillis = resultJob.testCountsWithTimeTime.map { it.timeInMillis }
            chart.addSeries(resultJob.jobName, counts, timesInMillis)
        }

        SwingWrapper(chart).displayChart()
    }
}