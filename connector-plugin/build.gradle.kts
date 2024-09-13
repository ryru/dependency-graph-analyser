plugins {
    id("dga.plugin-conventions")
}

description = "Gradle plugin that is injected into to be analysed Gradle projects"

group = "ch.addere.dga.connectorplugin"
version = property("connectorPluginVersion").toString()

dependencies {
    compileOnly(project(":connector-model"))
}

/* Include the :connector-model classes into the plugin jar to make it self-contained */
tasks.named<Jar>("jar") {
    from(project.sourceSets["main"].output)
    from(project(":connector-model").sourceSets["main"].output)
}

gradlePlugin {
    website = "https://github.com/ryru/dependency-graph-analyser"
    vcsUrl = "https://github.com/ryru/dependency-graph-analyser.git"
    plugins {
        create("gradleDgaPlugin") {
            id = "ch.addere.dga.connectorplugin"
            displayName = "Dependency Graph Analyser (DGA) Connector Plugin"
            description = """
                The Dependency Graph Analyser (DGA) Connector Plugin is designed for use with Gradle multi-project builds. It collects module dependency data from all Gradle projects within the build and exports this information in a format compatible with the DGA CLI tool for further analysis and visualisation.
                The plugin is typically applied using a Gradle init script, making it easy to integrate into existing builds without modifying individual project configurations.
            """.trimIndent()
            tags = listOf("dependency", "graph", "analyser", "build", "mermaid")
            implementationClass = "ch.addere.dga.connectorplugin.ConnectorPlugin"
        }
    }
}
