plugins {
    id("ch.addere.dga.kotlin-library-conventions")
    `maven-publish`
}

description = "Data model of the analysed Gradle project"

group = "ch.addere.dga.connectormodel"
version = property("connectorPluginVersion").toString()

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
