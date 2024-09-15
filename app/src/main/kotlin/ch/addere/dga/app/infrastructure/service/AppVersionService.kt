package ch.addere.dga.app.infrastructure.service

import ch.addere.dga.app.domain.model.AppVersion

class AppVersionService {

    fun readVersions(): AppVersion {
        val a = javaClass.getResource("/versions.txt")!!.readText().split("\n")
            .filter { it.isNotEmpty() }
        val b = a.associate {
            val (component, version) = it.split("=")
            component.trim() to version.trim()
        }.toMap()
        return AppVersion(b["cli"]!!, b["plugin"]!!)
    }
}
