package ch.addere.mdg.application

import ch.addere.mdg.domain.model.BuildFile
import ch.addere.mdg.domain.model.SettingsFile
import ch.addere.mdg.domain.model.graph.ModuleDependencyDag

class ImportImpl : Import {

    override fun readProject(
        settingsFile: SettingsFile,
        buildFiles: Set<BuildFile>
    ): ModuleDependencyDag {
        val dag = ModuleDependencyDag()
        settingsFile.getModules().forEach { dag.addModule(it) }
        buildFiles.flatMap { it.getDependencies() }.forEach { dag.addDependency(it) }
        return dag
    }
}
