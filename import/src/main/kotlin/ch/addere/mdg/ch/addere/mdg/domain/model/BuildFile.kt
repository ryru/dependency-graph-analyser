package ch.addere.mdg.ch.addere.mdg.domain.model

import ch.addere.mdg.domain.model.Configuration
import ch.addere.mdg.domain.model.Dependency
import ch.addere.mdg.domain.model.Module
import java.io.File

class BuildFile(private val origin: Module, private val buildFile: File) {

    fun getDependencies(): Set<Dependency> {
        return buildFile.bufferedReader().use { it ->
            val includeBlock = it.lines()
                .dropWhile { line -> !line.matches(Regex("^dependencies.*")) }
                .filter { line -> line.contains("project") }
                .map(::kotlinParser)
                .map { Configuration(it.first) to Module(it.second) }
                .map { Dependency(origin, it.second, it.first) }
                .toList()
            includeBlock
        }.toSet()
    }

    private fun kotlinParser(input: String) =
        input.substringBefore("(").trim() to toDestinationModule(input)

    private fun toDestinationModule(input: String) =
        input.substringAfter(":")
            .replace(")", "")
            .replace("\"", "")
            .trim()
}
