plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

rootProject.name = "dependency-graph-analyser"
include(
    "app",
    "connector-model",
    "connector-plugin",
    "core",
    "importer",
)
