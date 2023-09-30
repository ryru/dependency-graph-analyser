package ch.addere.mdg.importergradleplugin

import ch.addere.mdg.importergradlemodel.ProjectModule
import ch.addere.mdg.importergradlemodel.ProjectModuleImpl
import org.gradle.api.Project


fun extractProjectModules(project: Project): Set<ProjectModule> {
    return project.subprojects
        .map { ProjectModuleImpl(it.name) }
        .toSortedSet()
}
