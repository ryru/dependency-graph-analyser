package ch.addere.dga.app.domain.model

import ch.addere.dga.graph.domain.model.Module

data class FilterConfig(
    private val modules: List<Module>?,
    private val originModules: List<Module>?,
    private val destinationModules: List<Module>?
) {

    fun modules(): List<Module> {
        return modules ?: emptyList()
    }

    fun originModules(): List<Module> {
        return originModules ?: emptyList()
    }

    fun destinationModules(): List<Module> {
        return destinationModules ?: emptyList()
    }
}
