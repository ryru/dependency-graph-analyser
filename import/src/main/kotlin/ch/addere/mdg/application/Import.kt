package ch.addere.mdg.application

import ch.addere.mdg.ch.addere.mdg.domain.model.BuildFile
import ch.addere.mdg.ch.addere.mdg.domain.model.SettingsFile
import ch.addere.mdg.domain.model.graph.ModuleDependencyDag

interface Import {

    /**
     * Read all available modules into a new and empty DAG from the given gradle settings file.
     */
    fun readModulesFromSettings(settingsFile: SettingsFile): ModuleDependencyDag

    /**
     * Read all dependencies of all given gradle build files into a DAG.
     */
    fun readDependenciesFromBuildFiles(buildFiles: Set<BuildFile>): ModuleDependencyDag
}
