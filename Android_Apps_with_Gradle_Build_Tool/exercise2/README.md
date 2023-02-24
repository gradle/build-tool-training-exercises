## Android Apps with Gradle Build Tool - Exercise 2

<p align="left">
<img width="10%" height="10%" src="https://user-images.githubusercontent.com/120980/174325546-8558160b-7f16-42cb-af0f-511849f22ebc.png">
</p>

This is a hands-on exercise to go along with the
**Android Apps with Gradle Build Tool** training module. In this exercise
you will go over the following:

* Creating and using a version catalog

---
### Prerequisites

* Completed exercise 1
* You can perform the exercises in the same project used in exercise 1

---
### Create version catalog

In the `gradle` folder create a file called `libs.versions.toml`. Add the
following dependency modules to it.

```text
[versions]
android-material = "1.8.0"
# Plugin versions
android-plugin = "7.4.1"

[libraries]
# Just to show how to use version.ref in libraries
android-material = { module = "com.google.android.material:material", version.ref = "android-material" }
androidx-appcompat = "androidx.appcompat:appcompat:1.6.1"
androidx-constraintlayout = "androidx.constraintlayout:constraintlayout:2.1.4"
androidx-core-kts = "androidx.core:core-ktx:1.7.0"
androidx-test-espresso = "androidx.test.espresso:espresso-core:3.5.1"
androidx-test-junit = "androidx.test.ext:junit:1.1.5"

[bundles]
androidx-tests = ["androidx-test-junit", "androidx-test-espresso"]

[plugins]
android-application = { id = "com.android.application", version.ref = "android-plugin" }
android-library = { id = "com.android.library", version.ref = "android-plugin" }
kotlin-android = { id = "org.jetbrains.kotlin.android" , version = "1.8.0" }
```

### Using the version catalog

Update the `build.gradle.kts` file as follows:

* Switch to using the version catalog and plugin alias

Update the `app/build.gradle.kts` file as follows:

* Switch the apply plugin section to use the version catalog
* Switch the dependencies section to use the version catalog

Click on the `Sync Gradle` button in Android Studio to ensure things are
correctly configured. If there is an issue refer to the
[solution folder](solution/).
