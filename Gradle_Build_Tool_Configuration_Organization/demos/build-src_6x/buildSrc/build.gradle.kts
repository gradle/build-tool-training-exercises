plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
}

dependencies {
    implementation("org.barfuin.gradle.taskinfo:org.barfuin.gradle.taskinfo.gradle.plugin:2.1.0")
    implementation("org.barfuin.gradle.jacocolog:org.barfuin.gradle.jacocolog.gradle.plugin:3.1.0")
}