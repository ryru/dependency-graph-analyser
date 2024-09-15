import org.apache.tools.ant.filters.ReplaceTokens

plugins {
    id("dga.library-conventions")
    `java-gradle-plugin`
}

group = "ch.addere.dga.importer"
version = property("cliVersion").toString()

description = "Imports project information of to be analysed Gradle project"

dependencies {
    implementation(project(":core"))
    implementation(project(":connector-model"))
}

val connectorPluginVersion = property("connectorPluginVersion").toString()

tasks.withType<Jar> {
    filesMatching("init.gradle.kts") {
        filter(ReplaceTokens::class, "tokens" to mapOf("version" to connectorPluginVersion))
    }
}
