package ch.addere.mdg.domain.model

data class Module(val name: String) : Comparable<Module> {

    override fun compareTo(other: Module): Int {
        return this.name.compareTo(other.name)
    }
}
