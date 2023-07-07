package ch.addere.mdg.application

import ch.addere.mdg.domain.model.Project
import ch.addere.mdg.domain.model.graph.ModuleDependencyDag

interface Import {

    /**
     * Build a dependency graph of the given Gradle project.
     */
    fun readProject(project: Project): ModuleDependencyDag
}
