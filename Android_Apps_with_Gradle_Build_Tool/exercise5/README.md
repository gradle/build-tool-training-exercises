## Android Apps with Gradle Build Tool - Exercise 5

<p align="left">
<img width="10%" height="10%" src="https://user-images.githubusercontent.com/120980/174325546-8558160b-7f16-42cb-af0f-511849f22ebc.png">
</p>

This is a hands-on exercise to go along with the
**Android Apps with Gradle Build Tool** training module. In this exercise
you will go over the following:

* Create shared configuration layout
* Create common configuration
* Use common configuration

---
### Prerequisites

* Completed exercise 4
* You can perform the exercises in the same project used in exercise 4

---
### [OPTIONAL] Update version catalog

* Add the jacocolog plugin version `3.1.0` as a library. You can
reference the information
[here](https://mvnrepository.com/artifact/org.barfuin.gradle.jacocolog/org.barfuin.gradle.jacocolog.gradle.plugin/3.0.0-RC2) to figure out the group and name
* Add the [android library plugin](https://maven.google.com/web/index.html?q=android.library.gradle.plugin#com.android.library:com.android.library.gradle.plugin:7.4.1)
as a library
* Add the [kotlin library plugin](https://mvnrepository.com/artifact/org.jetbrains.kotlin.android/org.jetbrains.kotlin.android.gradle.plugin/1.8.0)
as a library

Hint:
```text
jacoco-log = { module = "org.barfuin.gradle.jacocolog:org.barfuin.gradle.jacocolog.gradle.plugin", version.ref = "jacocolog" }
android-library = { module = "com.android.library:com.android.library.gradle.plugin",  version.ref = "androidPlugin" }
kotlin-android = { module = "org.jetbrains.kotlin.android:org.jetbrains.kotlin.android.gradle.plugin", version.ref = "kotlinAndroid" }
```

If you get stuck you can refer to the [solution](solution/gradle/libs.versions.toml).

---
### Create build-logic project

Create a top-level directory called `build-logic`. Put a `settings.gradle.kts`
file under there and for the contents do the following:
* Copy the contents from the top-level settings file without the `pluginManagement` section
* Add `gradlePluginPortal()` as a repository
* Reference
[the existing version catalog](https://docs.gradle.org/current/userguide/platforms.html#sec:importing-catalog-from-file)
* Have only one subproject called `conventions`

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
### Create conventions sub-project

Under the `build-logic` directory, create a `conventions` directory. Put a
`build.gradle.kts` file there. It should have 3 things:

* Apply `kotlin-dsl` plugin
* Add `implementation` dependency on jacocolog, android library and kotlin android using the version catalog
* A helper function to convert depenedency plugin strings to library strings

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

Now create `src/main/kotlin` directories.
Extract common test coverage configuration into a file called `kotlin-test-coverage.gradle.kts`:

**Note**: Don't use the version catalog in the plugins section.

```kotlin
plugins {
    id("jacoco")
    id("org.barfuin.gradle.jacocolog")
}

tasks.named<JacocoReport>("jacocoTestReport") {
    dependsOn(tasks.named("test"))
    reports {
        xml.required.set(true)
    }
}

tasks.named<JacocoCoverageVerification>("jacocoTestCoverageVerification") {
    violationRules {
        rule {
            limit {
                counter = "LINE"
                value = "COVEREDRATIO"
                minimum = "0.5".toBigDecimal()
            }
        }
    }
}

tasks.named("check") {
    dependsOn("jacocoTestCoverageVerification")
}
```

Extract common android configuration into a file called `base-android-library.gradle.kts`:

**Note**: Don't use the version catalog in the plugins section.

```kotlin
plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
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

val libs = the<VersionCatalogsExtension>().named("libs")
dependencies {
    implementation(libs.findLibrary("androidx.core.ktx").get())
    implementation(libs.findLibrary("androidx.appcompat").get())
    implementation(libs.findLibrary("androidx.constraintlayout").get())
    implementation(libs.findLibrary("android.material").get())
    
    testImplementation(libs.findLibrary("junit").get())

    androidTestImplementation(libs.findBundle("androidx.tests").get())
}
```

Notice that the `dependencies` section uses the version catalog.

### Apply shared configuration

Update the top-level `settings.gradle.kts` file to use the shared configuration
in `build-logic` using `includeBuild` in the `pluginManagement` section.

```kotlin
pluginManagement {
    includeBuild("build-logic")
    ...
}
```

Now apply the shared convention plugins to the relevant sub-projects while removing
the common configuration from the build files. Notice that for the feature
sub-projects the `namespace` should remain.

```kotlin
plugins {
   ...
   id("kotlin-test-coverage")
}
```

```kotlin
plugins {
   ...
   id("base-android-library")
}
```

### Run app

Run the app to make sure things are still working. You can also run the `jacocoTestReport` task.

