package pl.performance

import kotlin.system.measureTimeMillis

internal class PerformanceCounter {

    internal fun count(vararg jobToPerform: PerformJob): List<JobForCountWithTime> {
        return jobToPerform.map { job ->
            System.gc()
            val timesByCount = job.tasks.map { task ->
                val spentTime = measureTimeMillis { task.performance() }
                JobForCountWithTime.CountTime(
                        count = task.xAxisValues,
                        timeInMillis = spentTime
                )
            }
            JobForCountWithTime(job.id, timesByCount)
        }
    }

    internal data class JobForCountWithTime(
            val jobName: String,
            val testCountsWithTimeTime: List<CountTime>,
    ) {

        internal data class CountTime(
                val timeInMillis: Long,
                val count: Number
        )
    }
}


