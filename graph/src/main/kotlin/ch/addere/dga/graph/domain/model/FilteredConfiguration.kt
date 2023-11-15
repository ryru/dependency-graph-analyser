package ch.addere.dga.graph.domain.model

data class FilteredConfiguration(val isApplicable: Boolean, val filtered: List<Configuration>) {

    fun contains(configuration: Configuration): Boolean = filtered.contains(configuration)
}
