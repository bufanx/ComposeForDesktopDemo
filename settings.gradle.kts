pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
    plugins {
        kotlin("jvm") version "1.5.31"
        id("org.jetbrains.compose") version "1.0.0-alpha4-build362"
    }
}
rootProject.name = "compose_test"

