package pl.performance

data class PerformJob private constructor(
        val id: String,
        val xAxisName: String?,
        val performance: () -> Unit
) {
    constructor(
            name: String,
            performance: () -> Unit
    ) : this(name, null, performance)

    init {

    }
}