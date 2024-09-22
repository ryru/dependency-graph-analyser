import org.apache.tools.ant.filters.ReplaceTokens

plugins {
    id("dga.library-conventions")
}

group = "ch.addere.dga.importer"
version = property("cliVersion").toString()

description = "Imports project information of to be analysed Gradle project"

dependencies {
    implementation(project(":core"))
    implementation(project(":connector-model"))

    implementation("org.gradle:gradle-tooling-api:8.9")
    runtimeOnly("org.slf4j:slf4j-simple:1.7.36")
}

tasks.withType<Jar> {
    val connectorPluginVersion = project.findProperty("connectorPluginVersion").toString()

    doFirst {
        filesMatching("init.gradle.kts") {
            filter(ReplaceTokens::class, "tokens" to mapOf("version" to connectorPluginVersion))
        }
    }
}
