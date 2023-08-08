## Using Version Catalog in includeBuild

<p align="left">
<img width="10%" height="10%" src="https://user-images.githubusercontent.com/120980/174325546-8558160b-7f16-42cb-af0f-511849f22ebc.png">
</p>

This is a hands-on exercise to go along with the
**Gradle Build Tool Build Organization** training module. In this exercise
you will go over the following:

* Use version catalog in shared configuration
* Use community plugins in shared configuration

---
### Prerequisites

* Completed exercise 1
* You can use the same project that was used for exercise 1

---
### Use same version catalog

Create a `gradle/libs.versions.toml` file and add the following contents:

```toml
[versions]
# Plugin versions
jacocolog = "3.1.0"

[libraries]
guava = "com.google.guava:guava:32.1.2-jre"

# Plugins - just to show how to do it this way
jacoco-log = { module = "org.barfuin.gradle.jacocolog:org.barfuin.gradle.jacocolog.gradle.plugin", version.ref = "jacocolog" }

[plugins]
jacocolog = { id = "org.barfuin.gradle.jacocolog", version.ref = "jacocolog" }
```

In `build-logic/settings.gradle.kts` under the `dependencyResolutionManagement` section
add configuration to use the existing version catalog. You can refer
[to the docs](https://docs.gradle.org/current/userguide/platforms.html#sec:importing-catalog-from-file).
The file should now look like:

It should look like:
```kotlin
dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}

rootProject.name = "build-logic"
include(":conventions")
```

---
### Add community plugins as dependencies

In `build-logic/conventions/build.gradle.kts` add the following helper function:

```kotlin
fun DependencyHandlerScope.plugin(plugin: Provider<PluginDependency>) =
    plugin.map { "${it.pluginId}:${it.pluginId}.gradle.plugin:${it.version}" }
```

Now add a `dependencies` section and add the following plugins as `implementation`
dependencies using the helper function above:
* jacocolog

The file should now look like:

```kotlin
plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
}

dependencies {
    implementation(plugin(libs.plugins.jacocolog))
}

fun DependencyHandlerScope.plugin(plugin: Provider<PluginDependency>) =
    plugin.map { "${it.pluginId}:${it.pluginId}.gradle.plugin:${it.version}" }
```

---
### Using community plugin in shared configuration

In the `test-coverage-convention` shared configuration apply the `jacocolog` plugin.

**Note**: Do not use the `alias` method.

```kotlin
plugins {
    id("jacoco")
    id("org.barfuin.gradle.jacocolog")
}
```

---
### Using library in shared configuration

Add the `guava` library as a dependency to the `base-java-convention` shared
configuration.

You will have to get a reference to the version catalog and use the
`findLibrary` method.

```kotlin
val libs = the<VersionCatalogsExtension>().named("libs")
dependencies {
    implementation(libs.findLibrary("guava").get())
}
```

### Run app

Run the app to make sure things are still working.
