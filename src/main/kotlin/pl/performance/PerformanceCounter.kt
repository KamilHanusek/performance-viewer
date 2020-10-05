package pl.performance

import kotlin.system.measureTimeMillis

class PerformanceCounter(
    private val TEST_COUNTS: Set<Int> = DEFAULT_TEST_SET
) {
    companion object {
        val DEFAULT_TEST_SET =
            setOf(1000, 2000, 3000, 4000, 5000, 10000, 20000, 30000, 40000, 50000, 60000, 70000, 80000, 90000, 100_000)
        val DEFAULT_TEST_SET_SIZE = DEFAULT_TEST_SET.size
    }

    fun count(vararg jobToPerform: PerformJob): List<JobForCountWithTime> {
        return jobToPerform.map { job ->
            System.gc()
            val timesByCount = TEST_COUNTS.map { count ->
                val spentTime = measureTimeMillis { job.runXTimes(count) }
                JobForCountWithTime.CountTime(
                    count = count,
                    timeInMillis = spentTime
                )
            }
            JobForCountWithTime(job.name, timesByCount)
        }
    }

    data class JobForCountWithTime(
        val jobName: String,
        val testCountsWithTimeTime: List<CountTime>,
    ) {

        data class CountTime(
            val timeInMillis: Long,
            val count: Int
        )
    }
}

private fun PerformJob.runXTimes(count: Int) {
    (1..count).forEach { this.performance() }
}

