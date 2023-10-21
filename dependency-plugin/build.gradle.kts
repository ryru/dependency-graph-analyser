plugins {
    id("ch.addere.dga.kotlin-library-conventions")
    `java-gradle-plugin`
    `maven-publish`
}

group = "ch.addere.dga.plugin"
version = "0.1.0"

dependencies {
    implementation(project(":dependency-model"))
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
    dependsOn(tasks.getByPath(":dependency-model:publishToMavenLocal"))
}
