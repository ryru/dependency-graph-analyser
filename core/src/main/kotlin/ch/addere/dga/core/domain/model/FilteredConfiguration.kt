package ch.addere.dga.core.domain.model

data class FilteredConfiguration(val isApplicable: Boolean, val filtered: List<Configuration>) {

    fun contains(configuration: Configuration): Boolean = filtered.contains(configuration)
}
