package pl.performance

import org.knowm.xchart.SwingWrapper
import org.knowm.xchart.XYChart
import org.knowm.xchart.XYChartBuilder
import kotlin.random.Random

class Application {
}

fun main() {
    // given
    val data = DataCreator.create(100_000) {
        Random.nextInt()
    }
    val jobToPerform = PerformJob( "something 1") {
        val randomInt = Random.nextInt()
        data.firstOrNull { randomInt == it }
    }
    val jobToPerform2 = PerformJob(
        name = "something 2"
    ) {
        val hashMap = hashMapOf(*data.map { it to it }.toTypedArray())
        val randomInt = Random.nextInt()
        hashMap.get(randomInt)
    }

    // when
    val performanceResult = PerformanceCounter().count(jobToPerform, jobToPerform2)
    val chart = XYChart(XYChartBuilder().also {
        it.xAxisTitle("Ilość wykonań")
        it.yAxisTitle("Czas w milisekundach")
    })
    performanceResult.forEach { (jobName, something) ->
        chart.addSeries(
            jobName,
            something.map { it.count },
            something.map { it.timeInMillis }
        )
    }
    SwingWrapper(chart).displayChart()
}

fun main(value: Array<String>) {
    // given
    val data = "hahaha"
    val jobToPerform = PerformJob("kotlin one string") {
        "something${data}${data}something"
    }
    val jobToPerform2 = PerformJob("adding words like in java") {
        "something".let { it + data + data + it }
    }
    val jobToPerform3 = PerformJob("creating new string") {
        "something".let { java.lang.String(it + data + data + it) }
    }
    val jobToPerform4 = PerformJob("creating string buffer") {
        "something".let { StringBuilder().append(it).append(data).append(data).append(it).toString() }
    }
    // when
    val charts = (1..4).map {
        val performanceResult = PerformanceCounter().count(
            jobToPerform2,
            jobToPerform2.copy(name = jobToPerform2.name + 2),
            jobToPerform2.copy(name = jobToPerform2.name + 3),
            jobToPerform2.copy(name = jobToPerform2.name + 4)
        )
        val chart = XYChart(XYChartBuilder().also {
            it.xAxisTitle("Ilość wykonań")
            it.yAxisTitle("Czas w milisekundach")
        })
        performanceResult.forEach { (jobName, something) ->
            chart.addSeries(
                jobName,
                something.map { it.count },
                something.map { it.timeInMillis }
            )
        }
        chart
    }
    SwingWrapper(charts).displayChartMatrix()
}