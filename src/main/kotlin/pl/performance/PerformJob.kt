package pl.performance

data class PerformJob(
        val id: String,
        val tasks: Collection<Task>
) {

    data class Task(
            val xAxisValues: Number,
            val performance: () -> Unit
    )
}