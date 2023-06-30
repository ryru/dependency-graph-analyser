#!/usr/bin/env kotlin

import java.io.File
import java.util.Optional
import kotlin.text.Charsets.UTF_8

enum class Configuration(val typeName: String) {
    API("api"),
    APPLICATION("application"),
    APPLICATION_ARM64("applicationArm64"),
    APPLICATION_DIST("applicationDist"),
    APPLICATION_X64("applicationX64"),
    IMPLEMENTATION("implementation"),
    TEST_IMPLEMENTATION("testImplementation"),
}

data class Module(val name: String) {
    companion object {
        fun createInstance(name: String) =
            Module(name.replace(":", "").replace("\"", "").replace(")", ""))
    }

    fun getNameAsKey(): String = name.replace("-", "_")
}

data class Dependency(val configuration: Optional<Configuration>, val module: Module) {
    companion object {
        fun createInstance(configurationString: String, module: Module) = run {
            val configuration: Optional<Configuration> = try {
                val config = Configuration.values().first { it.typeName == configurationString }
                Optional.of(config)
            } catch (_: IllegalArgumentException) {
                Optional.empty()
            }
            Dependency(configuration, module)
        }
    }
}

private val a: List<File> = findGradleBuildFiles(args[0])
private val b: Map<String, File> = mapSubmoduleToBuildFile(a)
b.forEach(::println)
private val c: Map<String, List<String>> =
    b.map { it.key to extractDependencyTypesFromBuildFile(it.value) }.toMap()
c.forEach(::println)
private val d: Map<String, List<String>> = reduceToProjectConfigurations(c)
private val e = convert(d)

println("stateDiagram-v2")
printStateModules(e)
printRelations(e)


fun findGradleBuildFiles(path: String): List<File> =
    File(path).walk().filter { it.name.endsWith("gradle.kts") }.toList()

fun mapSubmoduleToBuildFile(buildFiles: List<File>): Map<String, File> =
    buildFiles.associateBy { buildFile -> buildFile.parent.substringAfterLast("/") }

fun extractDependencyTypesFromBuildFile(build: File): List<String> =
    build.bufferedReader(UTF_8).lines().map(String::trim).filter(isConfiguration()).toList()

fun isConfiguration(): (String) -> Boolean = { it.matches(matchAllConfigurations()) }

fun matchAllConfigurations(): Regex =
    Regex("^(" + Configuration.values().joinToString("|") { it.typeName } + ").*")

fun reduceToProjectConfigurations(modules: Map<String, List<String>>): Map<String, List<String>> =
    modules.map { module -> module.key to module.value.filter(isProject()).toList() }.toMap()

fun isProject(): (String) -> Boolean = { it.contains("project") }

fun convert(modules: Map<String, List<String>>): Map<Module, List<Dependency>> =
    modules.map { module ->
        println("--- module : $module")
        Module(module.key) to module.value.map {
            val configurationString = it.substringBefore("(")
            val moduleName = it.substringAfter("\"").substringBefore("\"")
            println("### configurationString: $configurationString / moduleName: $moduleName")
            Dependency.createInstance(configurationString, Module.createInstance(moduleName))
        }.toList()
    }.toMap()

fun printStateModules(graph: Map<Module, List<Dependency>>) {
    graph.forEach { println("    state \"${it.key.name}\" as ${it.key.getNameAsKey()}") }
}

fun printRelations(graph: Map<Module, List<Dependency>>) {
    graph.forEach { module ->
        val fromModule = module.key.getNameAsKey()
        module.value.forEach { relation ->
            relation.configuration.ifPresent {
                println("    $fromModule --> ${relation.module.getNameAsKey()}: ${it.typeName}")
            }
        }
    }
}
