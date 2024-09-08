package ch.addere.dga.connectormodel

/**
 * Represents a Gradle dependency.
 */
interface Dependency {

    val name: String
    val group: String?
    val version: String?
}
