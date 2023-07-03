package ch.addere.mdg.domain.model.extractor

import ch.addere.mdg.domain.model.Module
import java.util.*
import kotlin.text.RegexOption.DOT_MATCHES_ALL

private const val REGEX_KOTLIN = "^\\s*include\\(\\s*(.*)\\)"
private const val REGEX_GROOVY = "^\\s*include\\s*(.*)"

fun findModulesInKotlinSettings(input: String): Set<Module> {
    return toModules(input, REGEX_KOTLIN)
}

fun findModulesInGroovySettings(input: String): Set<Module> {
    return toModules(input, REGEX_GROOVY)
}

private fun toModules(input: String, regex: String): Set<Module> {
    val match = regex.toRegex(DOT_MATCHES_ALL).matchEntire(input)
    return Optional.ofNullable(match).map { toModule(it.value) }.orElse(emptySet())
}

private fun toModule(input: String): Set<Module> {
    val stringOfModules = removeSyntaxClutter(input)
    val listOfModules = splitToModuleNames(stringOfModules)
    return listOfModules.map { Module(it) }.toSet()
}

private fun removeSyntaxClutter(input: String): String {
    return input
        .replace("\n", "")
        .replace("include", "")
        .replace("\"", "")
        .replace("'", "")
        .replace("(", "")
        .replace(")", "")
}

private fun splitToModuleNames(input: String): List<String> {
    return input.split(",").map { it.trim() }.filter { it.isNotBlank() }
}
