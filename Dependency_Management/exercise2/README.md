## Dependency Management - Exercise 2

<p align="left">
<img width="10%" height="10%" src="https://user-images.githubusercontent.com/120980/174325546-8558160b-7f16-42cb-af0f-511849f22ebc.png">
</p>

This is a hands-on exercise to go along with the
**Dependency Management** training module. In this exercise
you will go over the following:

* Observing Gradle's version conflict resolution
* Fine-tuning transitive dependencies using `dependency constraints`
* Using `rich versions` for bucket dependency configurations

---
### Prerequisites

* Completed exercise 1
* You can perform the exercises in the same Gradle project used in exercise 1

---
### Version Conflict Resolution

Add the dependency
[guice version "5.1.0"](https://mvnrepository.com/artifact/com.google.inject/guice/5.1.0)
to the implementation dependency configuration for the `app` subproject.

```kotlin
implementation("com.google.inject:guice:5.1.0")
```

Inspect the `compileClasspath` and `runtimeClasspath` dependency configurations
for the `app` subproject.

```bash
./gradlew :app:dependencies --configuration=compileClasspath
./gradlew :app:dependencies --configuration=runtimeClasspath
```

Notice that `guice` also depends on `guava` but a different version. In the
`compileClasspath` the `guava` dependency only shows up once in the
dependency tree since it is in the `implementation` dependency configuration
in the `model` subproject.

<p align="center">
<img width="70%" height="70%" src="https://user-images.githubusercontent.com/120980/200847832-13874f3b-d45b-4ee1-b291-8bdcc28ff1cd.png">
</p>

However in the `runtimeClasspath` the `guava` dependency shows up twice in the
dependency tree. Notice also that `guice` brings in a higher version of `guava`
than defined in the `model` subproject, so there is a version conflict. Observe
that Gradle picked the higher version.

<p align="center">
<img width="70%" height="70%" src="https://user-images.githubusercontent.com/120980/200848774-3c3db857-5de1-4736-a371-96cbb0408da7.png">
</p>

### Dependency Constraints

Say we wanted to enforce the version of `guava` brought in to be at
most "30.0-jre" as currently defined in the `model` subproject, and
at least "28.0-jre". We can add a constraint that applies to
the transitive dependencies to make this happen. Add the following
to the `app` subproject's dependency definition:

```kotlin
constraints {
    implementation("com.google.guava:guava") { 
        version {
            strictly("[28.0-jre, 30.0-jre]")
        }
    }
}
```

Now inspect the `runtimeClasspath` for the `app` subproject:

```bash
./gradlew :app:dependencies --configuration=runtimeClasspath
```

Notice that version "30.0-jre" of `guava` will now be used.

<p align="center">
<img width="70%" height="70%" src="https://user-images.githubusercontent.com/120980/200854070-e8d91f62-f68c-4114-a90e-45b3bd4d30e3.png">
</p>

### Rich Versions

Let's update the `guava` dependency definition in the `model`
subproject to be between "28.0-jre" and "30.0-jre", while
preferring "29.0-jre":

```kotlin
dependencies {
    //implementation("com.google.guava:guava:30.0-jre")
    implementation("com.google.guava:guava") {
        version {
            strictly("[28.0-jre, 30.0-jre]")
            prefer("29.0-jre")
        }
    }
    api("org.json:json:20220924")
}
```

Inspect the `runtimeClasspath` dependency configuration in the
`model` subproject:

```bash
./gradlew :model:dependencies --configuration=runtimeClasspath
```

Notice that Gradle will bring in `guava` version "29.0-jre" when
building just the `model` subproject.

<p align="center">
<img width="70%" height="70%" src="https://user-images.githubusercontent.com/120980/200856659-ebbe2a8f-3a95-48a9-b407-5673e2eb5384.png">
</p>

Now inspect the `runtimeClasspath` dependency configuration on the
`app` subproject:

```bash
./gradlew :app:dependencies --configuration=runtimeClasspath
```

Notice that Gradle will still bring in `guava` version "30.0-jre". Gradle's
conflict resolution looked at all intents specified which includes the
dependency constraints and the transitive dependency requirements
to pick "30.0-jre".

You can play around with the different constraints both in `model` and
`app` to see how Gradle handles conflicts. For example, in the `app`
subproject update the constraint to allow maximum version "28.5-jre"
and notice which version Gradle picks:

```kotlin
dependencies {
    constraints {
        implementation("com.google.guava:guava") {
            version {
                // strictly("[28.0-jre, 30.0-jre]")
                strictly("[28.0-jre, 28.5-jre]")
            }

        }
    }

    implementation(project(":model"))
    implementation("com.google.inject:guice:5.1.0")
}
```

<p align="right">
<a href="https://github.com/gradle/build-tool-training-exercises/tree/main/Dependency_Management/exercise3">Exercise 3 >></a>
</p>
