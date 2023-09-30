package ch.addere.mdg.importergradlemodel

/**
 * Represents a Gradle dependency.
 */
interface Dependency {

    val name: String
    val group: String?
    val version: String?
}
