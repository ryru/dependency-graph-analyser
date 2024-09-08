pluginManagement {
    includeBuild("build-logic")
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.4.0"
}

rootProject.name = "dependency-graph-analyser"
include(
    "app",
    "connector-model",
    "connector-plugin",
    "core",
    "importer",
)
