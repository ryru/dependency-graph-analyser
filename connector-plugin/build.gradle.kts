plugins {
    id("ch.addere.dga.kotlin-library-conventions")
    `java-gradle-plugin`
    `maven-publish`
    id("com.gradle.plugin-publish") version "1.2.1"
}

description = "Gradle plugin that is injected into to be analysed Gradle projects"

group = "ch.addere.dga.connectorplugin"
version = property("connectorPluginVersion").toString()

dependencies {
    implementation(project(":connector-model"))
}

gradlePlugin {
    website = "https://github.com/ryru/dependency-graph-analyser"
    vcsUrl = "https://github.com/ryru/dependency-graph-analyser.git"
    plugins {
        create("gradleDgaPlugin") {
            id = "ch.addere.dga.connectorplugin"
            displayName = "DGA Connector Plugin"
            description =
                "Small helper plugin that is injected into existing Gradle projects to analyse the dependency structure."
            tags = listOf("dependencyAnalyser", "gradleBuild", "mermaid")
            implementationClass = "ch.addere.dga.connectorplugin.ConnectorPlugin"
        }
    }
}

publishing {
    repositories {
        maven {
            name = "pluginRepo"
            url = uri(layout.projectDirectory.dir("../build/pluginRepo"))
        }

        publications {
            create<MavenPublication>("maven") {
                from(components["kotlin"])
            }
        }
    }
}

tasks.withType<PublishToMavenLocal> {
    dependsOn(tasks.getByPath(":connector-model:publishToMavenLocal"))
}
