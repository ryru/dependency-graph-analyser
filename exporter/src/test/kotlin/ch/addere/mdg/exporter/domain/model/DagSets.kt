package ch.addere.mdg.exporter.domain.model

import ch.addere.mdg.domain.model.Configuration
import ch.addere.mdg.domain.model.Dependency
import ch.addere.mdg.domain.model.Module
import java.util.*


fun minimalDag(): SortedSet<Dependency> {
    val m1 = Module("m1")
    val m2 = Module("m2")
    val m3 = Module("m3")
    val m4 = Module("m4")

    val c1 = Configuration("c1")
    val c2 = Configuration("c2")

    val d1 = Dependency(m1, m2, c1)
    val d2 = Dependency(m2, m3, c2)
    val d3 = Dependency(m1, m4, c1)
    val d4 = Dependency(m4, m2, c1)

    return sortedSetOf(d1, d2, d3, d4)
}

fun minimalDirect(): SortedSet<Dependency> {
    val m1 = Module("m1")
    val m2 = Module("m2")
    val m4 = Module("m4")

    val c1 = Configuration("c1")

    val d1 = Dependency(m1, m2, c1)
    val d3 = Dependency(m1, m4, c1)

    return sortedSetOf(d1, d3)
}

fun minimalIndirect(): SortedSet<Dependency> {
    val m2 = Module("m2")
    val m3 = Module("m3")
    val m4 = Module("m4")

    val c1 = Configuration("c1")
    val c2 = Configuration("c2")

    val d2 = Dependency(m2, m3, c2)
    val d4 = Dependency(m4, m2, c1)

    return sortedSetOf(d2, d4)
}
