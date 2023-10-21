initscript {
    repositories {
        mavenLocal()
        mavenCentral()
    }

    dependencies {
        classpath("ch.addere.dga.plugin:dependency-plugin:0.1.0")
    }
}

allprojects {
    apply<ch.addere.dga.dependencyplugin.ToolingApiCustomModelPlugin>()
}
