package pl.performance

data class SeparatedJob(
        val xAxisName: String,
        val jobs: Collection<PerformJob>
) {
    constructor(xAxisName: String, job: PerformJob): this(xAxisName, listOf(job))
}