## Using includeBuild to apply common configuration

In this exercise you will practice using `includeBuild` to apply common
configuration between subprojects.

---
### Create build-logic project

* Create a top-level directory called `build-logic`
* Under there create another directory called `conventions.`
* Create a file called `build-logic/settings.gradle.kts`
* Create a file called `build-logic/conventions/build.gradle.kts`

```
├── build-logic
│   ├── conventions
│   │   └── build.gradle.kts
│   └── settings.gradle.kts
├── app
├── gradle
├── subproject1
├── subproject2
├── subproject3
├── build.gradle.kts 
└── settings.gradle.kts
```

In `build-logic/settings.gradle.kts` put the following:

```kotlin
rootProject.name = "build-logic"
include(":conventions")
```

Update the top-level `settings.gradle.kts` file to use the `build-logic` project
as a repository for plugins in the `pluginManagement` section:

```kotlin
pluginManagement {
    includeBuild("build-logic")
}
```

Perform a `Gradle sync` in the editor. The `build-logic` project should now be
recognized as a project in the editor.

---
### Conventions subproject configuration

Add the following to `build-logic/conventions/build.gradle.kts`:
* Apply the `kotlin-dsl` plugin
* Add a `repositories` section and add `gradlePluginPortal()` to it

The file should now have the following contents:

```kotlin
plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
}
```

Perform a `Gradle sync` in the editor.

Now create the folders `src/main/kotlin` under `build-logic/conventions`.

### Migrate rootproject common configuration

Now you can create files for common configuration under `build-logic/conventions/src/main/kotlin`.
Look at the rootproject `build.gradle.kts`. Migrate the common configuration defined
there.

*Hint*: You want to create 2 files, one for test coverage and the other for
publishing configuration. You can call them `test-coverage-convention.gradle.kts`
and `publishing-convention.gradle.kts`.

*Hint*: Don't forget to apply the convention plugins to the subprojects.

After you are done, you can delete the rootproject `build.gradle.kts`.
Execute `./gradlew :app:run` to ensure there are no issues.

If you get stuck you can refer to the [solution](solution/).

### Migrate other common configuration

Notice that for the 3 subprojects named `subproject1`, `subproject2` and `subproject3`
there is a lot of common configuration. Move these to a common configuration file
as well. You can call it `base-java-convention.gradle.kts`.

Execute `./gradlew :app:run` to ensure there are no issues.

If you get stuck you can refer to the [solution](solution/).
