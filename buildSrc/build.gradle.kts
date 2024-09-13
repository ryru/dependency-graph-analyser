plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
}

dependencies {
    implementation("com.gradle.publish:plugin-publish-plugin:1.3.0")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:2.0.20")
}

kotlin {
    jvmToolchain(21)
}
