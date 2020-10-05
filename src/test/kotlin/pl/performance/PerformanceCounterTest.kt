package pl.performance

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotEmpty
import org.junit.jupiter.api.Test
import org.knowm.xchart.SwingWrapper
import org.knowm.xchart.XYChart
import kotlin.random.Random

internal class PerformanceCounterTest {

    @Test
    fun `can make test performance`() {
        // given
        val data = DataCreator.create(100) {
            Random.nextInt()
        }
        val jobToPerform = PerformJob("something 1") {
            val randomInt = Random.nextInt()
            data.firstOrNull { randomInt == it }
        }
        val hashMap = hashMapOf(*data.map { it to it }.toTypedArray())
        val jobToPerform2 = PerformJob("something 2") {
            val randomInt = Random.nextInt()
            hashMap.get(randomInt)
        }

        // when
        val performanceResult = PerformanceCounter().count(jobToPerform, jobToPerform2)
        val chart = XYChart(performanceResult.size, PerformanceCounter.DEFAULT_TEST_SET_SIZE)
        performanceResult.forEach { (jobName, something) -> chart.addSeries(jobName, something.map { it.count }, something.map { it.timeInMillis }) }
        SwingWrapper(chart).displayChart()

        // then
        assertThat(performanceResult.size).isEqualTo(2)
        assertThat(performanceResult.first().testCountsWithTimeTime).isNotEmpty()
        assertThat(performanceResult.first().testCountsWithTimeTime.size).isEqualTo(PerformanceCounter.DEFAULT_TEST_SET_SIZE)
    }
}