package ch.addere.mdg.graph.domain.model

data class Module(val name: String) : Comparable<Module> {

    override fun compareTo(other: Module): Int {
        return this.name.compareTo(other.name)
    }
}
