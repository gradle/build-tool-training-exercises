plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
}

dependencies {
    implementation("org.barfuin.gradle.jacocolog:org.barfuin.gradle.jacocolog.gradle.plugin:3.1.0")
}

/*fun DependencyHandlerScope.plugin(pluginId: String, version: String) =
    "${pluginId}:${pluginId}.gradle.plugin:${version}"*/