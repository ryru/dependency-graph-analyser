rootProject.name = "demo"
val modules = arrayOf("app", "list", "utilities")

modules.forEach {
    include(it)
    project(":$it").buildFileName = "${it}.gradle.kts"
}
