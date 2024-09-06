package ch.addere.dga.importer

import ch.addere.dga.core.domain.model.Configuration
import ch.addere.dga.core.domain.model.Dependency
import ch.addere.dga.core.domain.model.Module
import java.io.File

abstract class BuildFile(val origin: Module, private val buildFile: File) {

    val dependencies: Set<Dependency>

    init {
        dependencies = initialiseDependencies()
    }

    private fun initialiseDependencies(): Set<Dependency> {
        return buildFile.bufferedReader().use { it ->
            it.lines()
                .dropWhile { line -> !line.matches(Regex("^dependencies.*")) }
                .takeWhile { line -> !line.matches(Regex("}")) }
                .filter { line -> line.matches(Regex(".*project\\(.*")) }
                .map(::transformToModuleAndConfiguration)
                .map { Dependency(origin, it.first, it.second) }
                .toList()
        }.toSet()
    }

    protected abstract fun transformToModuleAndConfiguration(input: String): Pair<Module, Configuration>
}
