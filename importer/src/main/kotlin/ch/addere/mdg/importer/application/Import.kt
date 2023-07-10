package ch.addere.mdg.importer.application

import ch.addere.mdg.graph.domain.model.graph.ModuleDependencyDag
import ch.addere.mdg.importer.domain.model.Project

interface Import {

    /**
     * Build a dependency graph of the given Gradle project.
     */
    fun readProject(project: Project): ModuleDependencyDag
}
