package ch.addere.mdg.application

import ch.addere.mdg.ch.addere.mdg.domain.model.BuildFile
import ch.addere.mdg.ch.addere.mdg.domain.model.SettingsFile
import ch.addere.mdg.domain.model.graph.ModuleDependencyDag

class ImportImpl(private val dag: ModuleDependencyDag) : Import {

    override fun readModulesFromSettings(settingsFile: SettingsFile): ModuleDependencyDag {
        settingsFile.getModules().forEach { dag.addModule(it) }
        return dag
    }

    override fun readDependenciesFromBuildFiles(buildFiles: Set<BuildFile>): ModuleDependencyDag {
        buildFiles.flatMap { it.getDependencies() }.forEach { dag.addDependency(it) }
        return dag
    }
}
