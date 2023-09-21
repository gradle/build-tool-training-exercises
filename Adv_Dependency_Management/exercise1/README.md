## Advanced Dependency Management - Exercise 1

<p align="left">
<img width="10%" height="10%" src="https://user-images.githubusercontent.com/120980/174325546-8558160b-7f16-42cb-af0f-511849f22ebc.png">
</p>

This is a hands-on exercise to go along with the
**Advanced Dependency Management** training module. In this exercise
you will go over the following:

* Publishing version catalog
* Consuming published version catalog

---
### Prerequisites

* JDK 1.8+ and latest Gradle build tool installed
    * https://gradle.org/install/
* Gradle Build Tool experience
    * Knowledge of core concepts
    * Authoring build files
    * Kotlin experience a plus but not required
* Basic experience with Java software development

---
### Publishing version catalog

Open the project in the `lab` folder. Notice the version catalog in
`gradle/libs.versions.toml`.

Open `mycatalog/build.gradle.kts` and apply the following plugins to it:

1. version-catalog
2. maven-publish

**Note**: You will have to surround them with back-ticks.

Now set the following variables:

* `group` to "com.gradlelab"
* `version` to "1.2"

Now add configuration to define the catalog to be published:

```kotlin
catalog {
    versionCatalog {
        from(files("../gradle/libs.versions.toml"))
    }
}
```

Finally add configuration for publishing:

```kotlin
publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["versionCatalog"])
        }
    }
    repositories {
        maven {
            url = uri("/tmp/repo")
        }
    }
}
```

You can execute the `publish` task now:

```bash
./gradlew :mycatalog:publish
```

Notice the published catalog:

```bash
ls -ltr /tmp/repo/com/gradlelab/mycatalog/1.2/
```

---
### Consuming published version catalog

Open the `consumer` project. Update the settings file to use the published
version catalog:

```kotlin
dependencyResolutionManagement {
    repositories {
        maven {
            url = uri("/tmp/repo")
        }
    }
    versionCatalogs {
        create("mycatalog") {
            from("com.gradlelab:mycatalog:1.2")
        }
    }
}
```

Hit the `sync` button to ensure the editor picks up the configuration.

Now open `app/build.gradle.kts` and configure the dependencies as follows:

```kotlin
dependencies {
    implementation(mycatalog.bundles.core.libs)
    testImplementation(mycatalog.junit)
}
```

Ensure things are working by executing the `test` task:

```bash
./gradlew :app:test
```

<p align="right">
<a href="https://github.com/gradle/build-tool-training-exercises/tree/main/Adv_Dependency_Management/exercise2">Exercise 2 >></a>
</p>
