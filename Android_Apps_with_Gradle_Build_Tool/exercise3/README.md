## Android Apps with Gradle Build Tool - Exercise 5

<p align="left">
<img width="10%" height="10%" src="https://user-images.githubusercontent.com/120980/174325546-8558160b-7f16-42cb-af0f-511849f22ebc.png">
</p>

This is a hands-on exercise to go along with the
**Android Apps with Gradle Build Tool** training module. In this exercise
you will go over the following:

* Create shared configuration layout
* Create initial common configuration
* Use common configuration

---
### Prerequisites

* Completed exercise 2
* Open the [lab](lab/) project and perform the exercise in there

---
### Create build-logic project

* Create a top-level directory called `build-logic`
* Under there create another directory called `conventions.`
* Create files called `build-logic/settings.gradle.kts` and `build-logic/conventions/build.gradle.kts`.

```
├── build-logic
│   ├── conventions
│   │   └── build.gradle.kts
│   └── settings.gradle.kts
├── app
├── feature
├── math
├── build.gradle.kts 
└── settings.gradle.kts
```

In `build-logic/settings.gradle.kts` put the following:
* `rootProject.name = "build-logic"`
* `include(":conventions")`
* `dependencyResolutionManagement` section, you can copy it from the top-level `settings.gradle.kts`

Then in the `repositories` add `gradlePluginPortal()`. The `build-logic/settings.gradle.kts`
should now have the following contents:

```kotlin
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "build-logic"
include(":conventions")
```

Update the top-level `settings.gradle.kts` file to use the `build-logic` project
as a repository for plugins in the `pluginManagement` section:

```kotlin
pluginManagement {
    includeBuild("build-logic")
    ...
}
```

Perform a `Gradle sync` in the editor. The `build-logic` project should now be
recognized as a project in the editor.

---
### Create initial convention plugin

Under the `build-logic/conventions/build.gradle.kts` apply the `kotlin-dsl` plugin:

```kotlin
plugins {
    `kotlin-dsl`
}
```

Perform a `Gradle sync` in the editor.

Now create `src/main/kotlin` directories.
Extract common test coverage configuration into a file called `kotlin-test-coverage.gradle.kts`.
For now do not move over the `jacocolog` plugin.

```kotlin
plugins {
    id("jacoco")
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
tasks.named("check") {
    dependsOn("jacocoTestReport")
}
```

### Apply shared configuration

Now apply the shared convention plugin to the relevant `:math` modules while removing
the common configuration from the build files.

For `:math:calc` and `:math:game`:

```kotlin
plugins {
   ...
   id("kotlin-test-coverage")
   id("org.barfuin.gradle.jacocolog")
}
```

### Verify shared configuration applied

Run the `jacocoTestReport` task and verify it executes for the two `:math` modules.

<p align="right">
<a href="https://github.com/gradle/build-tool-training-exercises/tree/main/Android_Apps_with_Gradle_Build_Tool/exercise4">Exercise 4 >></a>
</p>
