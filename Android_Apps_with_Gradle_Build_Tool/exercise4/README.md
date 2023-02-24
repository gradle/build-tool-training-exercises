## Android Apps with Gradle Build Tool - Exercise 4

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

* Completed exercise 3
* You can perform the exercises in the same project used in exercise 3

---
### Update version catalog

* Add the jacocolog plugin version `3.1.0` as a library. You can
reference the information
[here](https://plugins.gradle.org/plugin/org.barfuin.gradle.jacocolog) and
[here](https://mvnrepository.com/artifact/org.barfuin.gradle.jacocolog/org.barfuin.gradle.jacocolog.gradle.plugin/3.0.0-RC2) to figure out the group and name.
* Add the [android library plugin](https://maven.google.com/web/index.html?q=android.library.gradle.plugin#com.android.library:com.android.library.gradle.plugin:7.4.1)
as a library

If you get stuck you can refer to the [solution](solution/gradle/libs.versions.toml).

---
### Create build-logic project

Create a top-level directory called `build-logic`. Put a `settings.gradle.kts`
file under there. Have the file reference the existing version catalog. If
you get stuck you can reference the [solution](solution/build-logic/settings.gradle.kts).

---
### Create conventions sub-project

Under the `build-logic` directory, create a `conventions` directory. Put a
`build.gradle.kts` file there. It should have 3 sections:

* Apply `kotlin-dsl` plugin
* Add `implementation` dependency on jacocolog and android library using the version catalog

If you get stuck you can refer to the [solution](solution/build-logic/conventions/build.gradle.kts).

Now create `src/main/kotlin` directories with
[kotlin-test-coverage.gradle.kts](solution/build-logic/conventions/src/main/kotlin/kotlin-test-coverage.gradle.kts)
and
[base-android-library.gradle.kts](solution/build-logic/conventions/src/main/kotlin/base-android-library.gradle.kts)
files with the contents linked.

Notice that the `dependencies` section uses the version catalog.

### Apply shared configuration

Update the top-level [settings.gradle.kts](solution/settings.gradle.kts) file to
use the shared configuration in `build-logic` using `includeBuild` in the
`pluginManagement` section.

Now apply the shared convention plugins to the relevant sub-projects. Remove
the configuration from the sub-project build files. Notice that for the feature
sub-projects the `namespace` should remain.

### Run app

Run the app to make sure things are still working. You can also run the `jacocoTestReport` task.

