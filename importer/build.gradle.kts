plugins {
    id("ch.addere.dga.kotlin-library-conventions")
    `java-gradle-plugin`
}

dependencies {
    api(project(":graph"))
    api(project(":dependency-model"))
}
