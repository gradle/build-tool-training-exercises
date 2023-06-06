## Android Apps with Gradle Build Tool - Exercise 5

<p align="left">
<img width="10%" height="10%" src="https://user-images.githubusercontent.com/120980/174325546-8558160b-7f16-42cb-af0f-511849f22ebc.png">
</p>

This is a hands-on exercise to go along with the
**Android Apps with Gradle Build Tool** training module. In this exercise
you will go over the following:

* Use version catalog in shared configuration
* Use community plugins in shared configuration
* Create more common configuration
* Use new common configuration

---
### Prerequisites

* Completed exercise 3
* You can use the same project that was used for exercise 3

---
### Use same version catalog

In `build-logic/settings.gradle.kts` under the `dependencyResolutionManagement` section
add configuration to use the existing version catalog. You can refer
[to the docs](https://docs.gradle.org/current/userguide/platforms.html#sec:importing-catalog-from-file).
The file should now look like:

It should look like:
```kotlin
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
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
* android library
* kotlin library

The file should now look like:

```kotlin
plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(plugin(libs.plugins.jacocolog))
    implementation(plugin(libs.plugins.android.library))
    implementation(plugin(libs.plugins.kotlin.android))
}

fun DependencyHandlerScope.plugin(plugin: Provider<PluginDependency>) =
    plugin.map { "${it.pluginId}:${it.pluginId}.gradle.plugin:${it.version}" }
```

---
### Move jacocolog plugin to shared configuration

In `build-logic/conventions/src/main/kotlin/kotlin-test-coverage.gradle.kts` apply
the `jacocolog` plugin and remove it from the `:math` modules build files.

**Note**: Do not use the `alias` method.

```kotlin
plugins {
    id("jacoco")
    id("org.barfuin.gradle.jacocolog")
}
```

---
### Create shared configuration for android feature modules

Create a file called `build-logic/conventions/src/main/kotlin/base-android-library.gradle.kts`.
Extract common android library configuration into it.

**Note:** Again for the plugins, don't use the `alias` method.

**Note:** Do not move the `namespace` since that is module specific.

```kotlin
plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    // NOTE: leave namespace in module build file.
    compileSdk = 33

    defaultConfig {
        minSdk = 24
        targetSdk = 33

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}
```

Now move the common android library dependencies over. You will have to get a
reference to the version catalog and use the `findLibrary` method.

```kotlin
val libs = the<VersionCatalogsExtension>().named("libs")
dependencies {
    implementation(libs.findBundle("androidx.core").get())
    
    testImplementation(libs.findLibrary("junit").get())

    androidTestImplementation(libs.findBundle("androidx.tests").get())
}
```

### Apply shared configuration

Now apply the `shared` convention plugins to the `:feature` modules while removing
the common configuration from the build files.

```kotlin
plugins {
   id("base-android-library")
}
```

### Run app

Run the app to make sure things are still working.
