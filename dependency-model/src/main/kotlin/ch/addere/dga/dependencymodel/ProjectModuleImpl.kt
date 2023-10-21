package ch.addere.dga.dependencymodel

import java.io.Serializable

data class ProjectModuleImpl(override val projectModule: String) : ProjectModule, Serializable,
    Comparable<ProjectModule> {

    override fun compareTo(other: ProjectModule): Int {
        return this.projectModule.compareTo(other.projectModule)
    }
}
