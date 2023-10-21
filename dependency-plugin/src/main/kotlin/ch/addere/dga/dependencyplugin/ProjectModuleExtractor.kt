package ch.addere.dga.dependencyplugin

import ch.addere.dga.dependencymodel.ProjectModule
import ch.addere.dga.dependencymodel.ProjectModuleImpl
import org.gradle.api.Project


fun extractProjectModules(project: Project): Set<ProjectModule> {
    return project.subprojects
        .map { ProjectModuleImpl(it.name) }
        .toSortedSet()
}
