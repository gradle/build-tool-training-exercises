## Gradle Build Tool Performance Optimization - Exercise 2

<p align="left">
<img width="10%" height="10%" src="https://user-images.githubusercontent.com/120980/174325546-8558160b-7f16-42cb-af0f-511849f22ebc.png">
</p>

This is a hands-on exercise to go along with the
**Gradle Build Tool Performance Optimization** training module. In this exercise
you will go over the following:

* Repository content filtering

---
### Prerequisites

* Completed [exercise 1](../exercise1/README.md)
* You can perform the exercises in the same Gradle project used in exercise 1.

---
### Repository content filtering

Execute the `:app:compileJava` task. Notice that **even though build caching is enabled**
every time you execute the task it needs to check if there are new versions of
dependencies available. This is because we are using `dynamic versions` and also
not caching configurations:

```kotlin
implementation("android.arch.core:common:1.1.1+")
implementation("android.arch.lifecycle:common:1.1.1+")
...
```

```kotlin
configurations.all {
    resolutionStrategy.cacheDynamicVersionsFor(0, "minutes")
    resolutionStrategy.cacheChangingModulesFor(0, "minutes")
}
```

We have a lot of repositories defined in the `app` subproject, so Gradle Build Tool
has to make a number of network calls to each repository to check if any one of them
has new versions available. Execute the `:app:compileJava` task with a build scan:

```bash
./gradlew :app:compileJava --scan
```

Go to `Performance -> Network activity`. Notice the number of network calls.
Go to `Performance -> Dependency resolution`. Notice the time taken.

<p align="center">
<img width="60%" height="60%" src="https://github.com/gradle/build-tool-training-exercises/assets/120980/97863615-8eb1-4c11-b618-da3a8c3bbcc8">
</p>

We want many of the android dependencies to be fetched **only** from maven central
and the google repository. Add
[exclude rules](https://docs.gradle.org/current/userguide/declaring_repositories.html#sec:declaring-repository-filter)
for the other repositories:

```kotlin
repositories {
    mavenCentral()
    maven {
        url = uri("https://packages.atlassian.com/maven-public/")
        content {
            // Use excludeGroupAndSubgroups for android, androidx and com.google
        }
    }
    maven {
        url = uri("https://packages.atlassian.com/maven-public-snapshot/")
        content {
            // Use excludeGroupAndSubgroups for android, androidx and com.google
        }
    }
    maven {
        url = uri("https://packages.atlassian.com/maven-external/")
        content {
            // Use excludeGroupAndSubgroups for android, androidx and com.google
        }
    }
    maven {
        url = uri("https://repository.jboss.org/nexus/content/repositories/releases/")
        content {
            // Use excludeGroupAndSubgroups for android, androidx and com.google
        }
    }
    google()
}
```

Execute the `:app:compileJava` task with a build scan:

```bash
./gradlew :app:clean
./gradlew :app:compileJava --scan
```

Go to `Performance -> Network activity`. Notice the number of network calls is significantly less.
Go to `Performance -> Dependency resolution`. Notice the time taken is also significantly less.
