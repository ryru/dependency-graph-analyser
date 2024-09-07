plugins {
    id("ch.addere.dga.kotlin-application-conventions")
}

description = "CLI application project"

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
