plugins {
    id("ch.addere.mdg.kotlin-library-conventions")
    `java-gradle-plugin`
}

dependencies {
    api(project(":graph"))
    api(project(":importer-gradle-model"))
}
