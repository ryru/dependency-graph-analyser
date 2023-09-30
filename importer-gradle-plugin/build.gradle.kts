plugins {
    id("ch.addere.mdg.kotlin-library-conventions")
    `java-gradle-plugin`
    `maven-publish`
}

group = "ch.addere.mdg.importergradleplugin"
version = "1.0.0"

dependencies {
    implementation(project(":importer-gradle-model"))
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
