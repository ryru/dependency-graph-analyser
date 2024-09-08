initscript {
    repositories {
        mavenLocal()
        mavenCentral()
    }

    dependencies {
        classpath("ch.addere.dga.connectorplugin:connector-plugin:@version@")
    }
}

allprojects {
    apply<ch.addere.dga.connectorplugin.ConnectorPlugin>()
}
