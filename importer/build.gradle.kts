plugins {
    id("ch.addere.dga.kotlin-library-conventions")
    `java-gradle-plugin`
}

description = "Imports project information of to be analysed Gradle project"

dependencies {
    implementation(project(":core"))
    implementation(project(":dependency-model"))
}
