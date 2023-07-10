/*
 * This file was generated by the Gradle 'init' task.
 *
 * This project uses @Incubating APIs which are subject to change.
 */

plugins {
    id("ch.addere.mdg.kotlin-application-conventions")
}

dependencies {
    api(project(":export"))
    api(project(":importer"))

    implementation("com.github.ajalt.clikt:clikt:4.0.0")
    implementation("io.insert-koin:koin-core:3.4.2")
}

application {
    // Define the main class for the application.
    mainClass.set("ch.addere.mdg.app.AppKt")
}

distributions {
    main {
        distributionBaseName.set("dga")
    }
}

tasks.withType<CreateStartScripts> {
    applicationName = "dga"
}
