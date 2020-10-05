package pl.performance

object DataCreator {
    fun <T> create(count: Int, creator: () -> T): Collection<T> {
        return (1..count).map { creator() }
    }
}