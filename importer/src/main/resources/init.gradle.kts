initscript {
    repositories {
        mavenLocal()
        mavenCentral()
    }

    dependencies {
        classpath("ch.addere.mdg.importergradleplugin:importer-gradle-plugin:1.0.0")
    }
}

allprojects {
    apply<ch.addere.mdg.importergradleplugin.ToolingApiCustomModelPlugin>()
}
