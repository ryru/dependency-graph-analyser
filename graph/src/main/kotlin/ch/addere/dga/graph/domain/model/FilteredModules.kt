package ch.addere.dga.graph.domain.model

data class FilteredModules(val isApplicable: Boolean, val filtered: List<Module>) {

    fun contains(module: Module): Boolean = filtered.contains(module)

    fun contains(origin: Module, destination: Module): Boolean =
        filtered.contains(origin) || filtered.contains(destination)
}
