package ch.addere.mdg.domain.model

data class Dependency(
    val origin: Module,
    val destination: Module,
    val configuration: Configuration
) : Comparable<Dependency> {

    override fun compareTo(other: Dependency): Int {
        return compareBy(
            Dependency::origin,
            Dependency::destination,
            Dependency::configuration
        ).compare(this, other)
    }
}
