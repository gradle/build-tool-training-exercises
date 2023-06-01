## Gradle Build Tool Plugin Development - Exercise 1

<p align="left">
<img width="10%" height="10%" src="https://user-images.githubusercontent.com/120980/174325546-8558160b-7f16-42cb-af0f-511849f22ebc.png">
</p>

This is a hands-on exercise to go along with the
**Gradle Build Tool Plugin Development** training module. In this exercise
you will go over the following:

* Create new plugin project
* Setup project for manual testing of plugin

---
### Prerequisites

* JDK 1.8+ and recent Gradle build tool installed
    * https://gradle.org/install/
* Gradle Build Tool experience
    * Knowledge of core concepts
    * Authoring build files
    * Kotlin experience a plus but not required
* Some experience with Kotlin software development

---
### Create new plugin project

Create a directory called `coveragelock` and in there run `gradle init`
and enter the following responses for the questions:

* Type of project to generate: Gradle plugin (4)
* Language: Kotlin (3)
* Build script DSL: Kotlin (2)
* Use new APIs?: no (leave as default)
* Project name: coveragelock (leave as default)
* Source package: com.gradlelab

```bash
$ mkdir coveragelock
$ cd coveragelock/
$ gradle init

Select type of project to generate:
  1: basic
  2: application
  3: library
  4: Gradle plugin
Enter selection (default: basic) [1..4] 4

Select implementation language:
  1: Groovy
  2: Java
  3: Kotlin
Enter selection (default: Java) [1..3] 3

Select build script DSL:
  1: Groovy
  2: Kotlin
Enter selection (default: Kotlin) [1..2] 2

Generate build using new APIs and behavior (some features may change in the next minor release)? (default: no) [yes, no] 

Project name (default: coveragelock): 

Source package (default: coveragelock): com.gradlelab
```

Open the `coveragelock` project in an editor such as IntelliJ.

Edit the `gradlePlugin` configuration in the  `plugin/build.gradle.kts` file
to have the correct `id` and variable name, as well as add a description:

```kotlin
gradlePlugin {
    val coveragelock by plugins.creating {
        id = "com.gradlelab.coveragelock"
        implementationClass = "com.gradlelab.CoveragelockPlugin"
        description = "Incrementally locks in test coverage gains"
    }
}
```

Edit the `CoverageLockPluginTest` to use the new ID:

```kotlin
@Test fun `plugin registers task`() {
    // Create a test project and apply the plugin
    val project = ProjectBuilder.builder().build()
    project.plugins.apply("com.gradlelab.coveragelock")

    // Verify the result
    assertNotNull(project.tasks.findByName("greeting"))
}
```

Do the same for `CoveragelockPluginFunctionalTest`.

Execute the `test` and `functionalTest` tasks to verify things work:

```bash
./gradlew :plugin:test
./gradlew :plugin:functionalTest
```

### Setup project for manual testing of plugin

Go back up and create another directory called `pluginmanualtest`. Run
`gradle init` and enter the following responses for the questions: 

* Type of project to generate: application (2)
* Language: Java (3)
* Split functionality across multiple subprojects?: no (leave as default)
* Build script DSL: Kotlin (2)
* Use new APIs?: no (leave as default)
* Test framework: JUnit Jupiter (leave as default)
* Project name: pluginmanualtest (leave as default)
* Source package: com.gradlelab

```bash
$ cd ..
$ mkdir pluginmanualtest
$ cd pluginmanualtest
$ gradle init


Select type of project to generate:
  1: basic
  2: application
  3: library
  4: Gradle plugin
Enter selection (default: basic) [1..4] 2

Select implementation language:
  1: C++
  2: Groovy
  3: Java
  4: Kotlin
  5: Scala
  6: Swift
Enter selection (default: Java) [1..6] 3

Split functionality across multiple subprojects?:
  1: no - only one application project
  2: yes - application and library projects
Enter selection (default: no - only one application project) [1..2] 

Select build script DSL:
  1: Groovy
  2: Kotlin
Enter selection (default: Groovy) [1..2] 2

Generate build using new APIs and behavior (some features may change in the next minor release)? (default: no) [yes, no] 
Select test framework:
  1: JUnit 4
  2: TestNG
  3: Spock
  4: JUnit Jupiter
Enter selection (default: JUnit Jupiter) [1..4] 

Project name (default: pluginmanualtest): 
Source package (default: pluginmanualtest): com.gradlelab
```

Open the `pluginmanualtest` project in another editor window.

Include the `coveragelock` project under the `pluginManagement` section
in the `settings.gradle.kts` file:

```kotlin
pluginManagement {
    // Change this to the location on your computer.
    includeBuild("/Users/.../coveragelock")
}
```

Now update the `app/build.gradle.kts` file to apply the `coveragelock` plugin:

```kotlin
plugins {
  application
  id("com.gradlelab.coveragelock")
}
```

Ensure the plugin is being correctly applied by executing the `greeting` task
on the `app` subproject:

```bash
$ ./gradlew :app:greeting

> Task :app:greeting
Hello from plugin 'com.gradlelab.greeting'
```