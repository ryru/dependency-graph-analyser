plugins {
    id("ch.addere.mdg.kotlin-library-conventions")
    `maven-publish`
}

group = "ch.addere.mdg.importergradlemodel"
version = "1.0.0"

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
