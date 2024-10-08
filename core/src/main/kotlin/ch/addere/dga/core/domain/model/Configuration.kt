package ch.addere.dga.core.domain.model

data class Configuration(val name: String) : Comparable<Configuration> {

    override fun compareTo(other: Configuration): Int {
        return this.name.compareTo(other.name)
    }
}
