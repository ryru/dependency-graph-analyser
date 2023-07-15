package ch.addere.mdg.graph.application

interface DependencyService {

    /**
     * Returns the number of dependencies.
     */
    fun nofDependencies(): Int

    /**
     * Returns the number of unique dependencies.
     */
    fun nofUniqueDependencies(): Int
}
