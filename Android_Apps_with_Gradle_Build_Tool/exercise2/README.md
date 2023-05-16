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

* [OPTIONAL] Completed exercise 1
* Open the `lab` project and perform the exercise in there

---
### Create version catalog

In the `gradle` folder create a file called `libs.versions.toml`. Add the
following dependency modules to it.

```text
[versions]
androidMaterial = "1.8.0"
# Plugin versions
androidPlugin = "7.4.1"

[libraries]
# Just to show how to use version.ref in libraries
android-material = { group = "com.google.android.material", name = "material", version.ref = "androidMaterial" }
androidx-appcompat = "androidx.appcompat:appcompat:1.6.1"
androidx-constraintlayout = "androidx.constraintlayout:constraintlayout:2.1.4"
androidx-core-ktx = "androidx.core:core-ktx:1.7.0"
androidx-test-espresso = "androidx.test.espresso:espresso-core:3.5.1"
androidx-test-junit = "androidx.test.ext:junit:1.1.5"
junit = "junit:junit:4.13.2"

[bundles]
androidx-tests = ["androidx-test-junit", "androidx-test-espresso"]

[plugins]
android-application = { id = "com.android.application", version.ref = "androidPlugin" }
android-library = { id = "com.android.library", version.ref = "androidPlugin" }
kotlin-android = { id = "org.jetbrains.kotlin.android" , version = "1.8.0" }
```

### Using the version catalog

Update the `build.gradle.kts` file as follows:

* Switch to using the version catalog and plugin alias

```kotlin
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
}
```

Update the `app/build.gradle.kts` file as follows:

* Switch the apply plugin section to use the version catalog

```kotlin
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}
```

* Switch the dependencies section to use the version catalog

```kotlin
dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.android.material)
    implementation(libs.androidx.constraintlayout)

    testImplementation(libs.junit)

    androidTestImplementation(libs.bundles.androidx.tests)
}
```

Click on the `Sync Gradle` button in Android Studio to ensure things are
correctly configured. If there is an issue refer to the
[solution folder](solution/).

<p align="center">
<img width="25%" height="25%" src="https://user-images.githubusercontent.com/120980/228761993-442b1761-bbeb-428b-a401-de0286be7ee3.png">
</p>

<p align="right">
<a href="https://github.com/gradle/build-tool-training-exercises/tree/main/Android_Apps_with_Gradle_Build_Tool/exercise3">Exercise 3 >></a>
</p>
