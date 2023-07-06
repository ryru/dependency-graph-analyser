package ch.addere.mdg.domain.model

import java.io.File

abstract class BuildFile(private val origin: Module, private val buildFile: File) {

    fun getDependencies(): Set<Dependency> {
        return buildFile.bufferedReader().use { it ->
            val includeBlock = it.lines()
                .dropWhile { line -> !line.matches(Regex("^dependencies.*")) }
                .filter { line -> line.contains("project") }
                .map(::transformToModuleAndConfiguration)
                .map { Dependency(origin, it.first, it.second) }
                .toList()
            includeBlock
        }.toSet()
    }

    protected abstract fun transformToModuleAndConfiguration(input: String): Pair<Module, Configuration>
}
