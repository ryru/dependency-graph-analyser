package ch.addere.mdg.importer.application

import ch.addere.mdg.graph.domain.model.graph.ModuleDependencyDag
import ch.addere.mdg.importer.domain.model.Project

class ImportImpl : Import {

    override fun readProject(project: Project): ModuleDependencyDag {
        val dag = ModuleDependencyDag()
        project.settingsFile.modules.forEach { dag.addModule(it) }
        project.buildFiles.flatMap { it.dependencies }.forEach { dag.addDependency(it) }
        return dag
    }
}
