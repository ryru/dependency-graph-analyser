package ch.addere.mdg.domain.model

import java.io.File

abstract class BuildFile(val origin: Module, val buildFile: File) {

    fun getDependencies(): Set<Dependency> {
        return buildFile.bufferedReader().use { it ->
            val dependencyBlock = it.lines()
                .dropWhile { line -> !line.matches(Regex("^dependencies.*")) }
                .filter { line -> line.contains("project") }
                .map(::transformToModuleAndConfiguration)
                .map { Dependency(origin, it.first, it.second) }
                .toList()
            dependencyBlock
        }.toSet()
    }

    protected abstract fun transformToModuleAndConfiguration(input: String): Pair<Module, Configuration>
}
