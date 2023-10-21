plugins {
    id("ch.addere.dga.kotlin-application-conventions")
}

dependencies {
    api(project(":exporter"))
    api(project(":importer"))

    implementation("com.github.ajalt.clikt:clikt:4.0.0")
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
