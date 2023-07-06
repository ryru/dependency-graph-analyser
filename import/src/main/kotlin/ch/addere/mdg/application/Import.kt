package ch.addere.mdg.application

import ch.addere.mdg.domain.model.BuildFile
import ch.addere.mdg.domain.model.SettingsFile
import ch.addere.mdg.domain.model.graph.ModuleDependencyDag

interface Import {

    /**
     * Read all dependencies of all given gradle build files into a DAG.
     */
    fun readProject(settingsFile: SettingsFile, buildFiles: Set<BuildFile>): ModuleDependencyDag
}
