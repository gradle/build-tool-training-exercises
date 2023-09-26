## Advanced Dependency Management - Exercise 4

<p align="left">
<img width="10%" height="10%" src="https://user-images.githubusercontent.com/120980/174325546-8558160b-7f16-42cb-af0f-511849f22ebc.png">
</p>

This is a hands-on exercise to go along with the
**Advanced Dependency Management** training module. In this exercise
you will go over the following:

* Creating and using dependency locking
* Updating dependency locking

---
### Prerequisites

* Completed exercise 3
* You can perform the exercises in the same Gradle project used in the previous exercises

---
### Create and use Dependency Locking

In the `app` subproject, add the following configuration:

```kotlin
configurations {
    compileClasspath {
        resolutionStrategy.activateDependencyLocking()
    }
    runtimeClasspath {
        resolutionStrategy.activateDependencyLocking()
    }
}
```

Run the following command to create a lock file:

```bash
./gradlew :app:dependencies --write-locks
```

Inspect the `app/gradle.lockfile` file. Now inspect the dependencies:

```bash
./gradlew :app:dependencies --configuration=runtimeClasspath
```

Notice that the `compileClasspath` and `runtimeClasspath` dependencies
are now all affected by constraints.

<p align="center">
<img width="70%" height="70%" src="https://user-images.githubusercontent.com/120980/200872754-4119d5cf-df51-4560-b28c-901c608844de.png">
</p>

### Updating Dependency Locking

Update the `guice` dependency version to "5.0.0":

```kotlin
implementation("com.google.inject:guice:5.0.0")
```

Notice that Gradle will not use this version yet since the lockfile exists, and
a constraint bumps up the version to "5.1.0":

```bash
./gradlew :app:dependencies
```

<p align="center">
<img width="70%" height="70%" src="https://user-images.githubusercontent.com/120980/200874487-1823eecb-4b4c-4523-a3f1-0a2471ac78e0.png">
</p>

Update the lockfile and then inspect the dependencies to see the update take
effect:

```bash
./gradlew :app:dependencies --write-locks
./gradlew :app:dependencies
```
