## Advanced Dependency Management - Exercise 2

<p align="left">
<img width="10%" height="10%" src="https://user-images.githubusercontent.com/120980/174325546-8558160b-7f16-42cb-af0f-511849f22ebc.png">
</p>

This is a hands-on exercise to go along with the
**Advanced Dependency Management** training module. In this exercise
you will go over the following:

* Publishing platform
* Consuming published platform

---
### Prerequisites

* Completed exercise 1

---
### Publishing platform

Open the project in the `lab` folder. Open `myplatform/build.gradle.kts`
and apply the following plugins to the build file:

1. java-platform
2. maven-publish

Now set the following variables:

* `group` to "com.gradlelab"
* `version` to "1.3"

Add a constraint for `gson`:

```kotlin
dependencies {
    constraints {
        api("com.google.code.gson:gson:2.8.3")
    }
}
```

Now add configuration for publishing:

```kotlin
publishing {
    publications {
        create<MavenPublication>("myPlatform") {
            from(components["javaPlatform"])
        }
    }
    repositories {
        maven {
            url = uri("/tmp/repo2")
        }
    }
}
```

You can execute the `publish` task now:

```bash
./gradlew :myplatform:publish
```

Notice the published catalog:

```bash
ls -ltr /tmp/repo2/com/gradlelab/myplatform/1.3/
```

---
### Consuming published platform

Open the `consumer` project. Open the `app/build.gradle.kts` file and
add `/tmp/repo2` as a repository:

```kotlin
repositories {
    mavenCentral()
    maven {
        url = uri("/tmp/repo2")
    }
}
```

Now add dependencies to the published platform and to `gson`. For the
`gson` dependency don't specify a version, it will come from the platform.

```kotlin
dependencies {
    implementation(platform("com.gradlelab:myplatform:1.3"))
    implementation("com.google.code.gson:gson")
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.1")
}
```

Ensure things are working by executing the `test` task:

```bash
./gradlew :app:test
```

Also inspect the dependencies:

```bash
./gradlew :app:dep
```

<p align="right">
<a href="https://github.com/gradle/build-tool-training-exercises/tree/main/Adv_Dependency_Management/exercise3">Exercise 3 >></a>
</p>
