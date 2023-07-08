package ch.addere.mdg.application

import ch.addere.mdg.domain.model.Project
import ch.addere.mdg.domain.model.graph.ModuleDependencyDag

class ImportImpl : Import {

    override fun readProject(project: Project): ModuleDependencyDag {
        val dag = ModuleDependencyDag()
        project.settingsFile.modules.forEach { dag.addModule(it) }
        project.buildFiles.flatMap { it.dependencies }.forEach { dag.addDependency(it) }
        return dag
    }
}
