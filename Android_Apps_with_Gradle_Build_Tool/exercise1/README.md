## Android Apps with Gradle Build Tool - Exercise 1

<p align="left">
<img width="10%" height="10%" src="https://user-images.githubusercontent.com/120980/174325546-8558160b-7f16-42cb-af0f-511849f22ebc.png">
</p>

This is a hands-on exercise to go along with the
**Android Apps with Gradle Build Tool** training module. In this exercise
you will go over the following:

* Create a new app with Android Studio
* Convert the build configuration to Kotlin

---
### Prerequisites

* Recent version of Android Studio installed
    * https://developer.android.com/studio
* Some experience creating Android apps
* Understanding of core Gradle build concepts
* Kotlin experience is a plus but not required

---
### Create a new app with Android Studio

Open Android Studio and select the option to create a new project.
For the template select `Phone and Tablet`, and `Empty Activity` for
the second option.

<p align="center">
<img width="75%" height="75%" src="https://user-images.githubusercontent.com/120980/220085094-bd8264cd-8e7b-4e63-a6bf-e16d33a1d7f0.png">
</p>

Click on `Next`. On the next screen, enter `MyCalculator` for the name and
select Kotlin as the language. Click on `Finish`.

<p align="center">
<img width="75%" height="75%" src="https://user-images.githubusercontent.com/120980/220793195-031bd704-ef3b-46ea-af29-7b8c47e6dabf.png">
</p>

### Convert build configuration to Kotlin

Rename the following .gradle files to .gradle.kts:
* settings.gradle.kts
* build.gradle.kts
* app/build.gradle.kts

Update the configurations from the Groovy configuration files and convert as per:
https://developer.android.com/studio/build/migrate-to-kts.

Update the JDK version to 11 in `app/build.gradle.kts`.

Click on the `Sync Gradle`button in Android Studio to ensure things are
correctly configured. If there is an issue refer to the
[solution folder](solution/).
