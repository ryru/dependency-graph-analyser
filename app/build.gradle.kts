import org.apache.tools.ant.filters.ReplaceTokens

plugins {
    id("dga.application-conventions")
}

description = "CLI application project"

group = "ch.addere.dga.app"
version = property("cliVersion").toString()

dependencies {
    implementation(project(":core"))
    implementation(project(":importer"))

    implementation("com.github.ajalt.clikt:clikt:4.4.0")
    implementation("io.insert-koin:koin-core:3.4.2")
}

application {
    // Define the main class for the application.
    mainClass.set("ch.addere.dga.app.AppKt")
}

distributions {
    main {
        distributionBaseName.set("dga")
    }
}

tasks.withType<CreateStartScripts> {
    applicationName = "dga"
}

val cliVersion = property("cliVersion").toString()
val connectorPluginVersion = property("connectorPluginVersion").toString()

tasks.withType<Jar> {
    filesMatching("versions.txt") {
        filter(
            ReplaceTokens::class,
            "tokens" to mapOf(
                "cliVersion" to cliVersion,
                "pluginVersion" to connectorPluginVersion
            )
        )
    }
}
