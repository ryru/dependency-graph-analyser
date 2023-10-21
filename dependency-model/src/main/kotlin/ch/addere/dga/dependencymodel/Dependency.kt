package ch.addere.dga.dependencymodel

/**
 * Represents a Gradle dependency.
 */
interface Dependency {

    val name: String
    val group: String?
    val version: String?
}
