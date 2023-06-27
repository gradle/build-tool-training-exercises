plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
}

dependencies {
    implementation("org.barfuin.gradle.taskinfo:org.barfuin.gradle.taskinfo.gradle.plugin:2.1.0")
    implementation("com.github.johnrengelman.shadow:com.github.johnrengelman.shadow.gradle.plugin:7.1.2")
}
