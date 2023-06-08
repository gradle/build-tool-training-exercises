## Using buildSrc to apply common configuration

In this exercise you will practice using `buildSrc` to apply common
configuration between subprojects.

### Create structure

First create folders under the root called `buildSrc/src/main/kotlin`.

<p align="center">
<img width="60%" height="60%" src="https://user-images.githubusercontent.com/120980/174331968-59facec1-85c9-43f9-bc04-0f5347cdd262.png">
</p>

<p align="center">
<img width="40%" height="40%" src="https://user-images.githubusercontent.com/120980/174332015-5e999f62-ec44-44f2-948c-f376f3c7cf20.png">
</p>

Then create the file `buildSrc/build.gradle.kts` and add the following in it:
* Apply the `kotlin-dsl` plugin
* Add a `repositories` section and add `gradlePluginPortal()` to it

The contents should now be:

```kotlin
plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
}
```

### Migrate rootproject common configuration

Now you can create files for common configuration under `buildSrc/src/main/kotlin`.
Look at the rootproject `build.gradle.kts`. Migrate the common configuration defined
there to `buildSrc`.

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

