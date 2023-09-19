## Gradle Build Tool Performance Optimization - Exercise 1

<p align="left">
<img width="10%" height="10%" src="https://user-images.githubusercontent.com/120980/174325546-8558160b-7f16-42cb-af0f-511849f22ebc.png">
</p>

This is a hands-on exercise to go along with the
**Gradle Build Tool Performance Optimization** training module. In this exercise
you will go over the following:

* Using `tasks.register` to define custom tasks
* Enabling parallel execution for tasks
* Enabling parallel execution for tests
* Enable and use Local Cache

---
### Prerequisites

* JDK 1.8+ and latest Gradle build tool installed
  * https://gradle.org/install/
* Gradle Build Tool experience
    * Knowledge of core concepts
    * Authoring build files
    * Kotlin experience a plus but not required
* Basic experience with Java software development

---
### Using tasks.register to define custom tasks

Open the project in the `lab` folder. Execute the following:

```bash
./gradlew :app:help --scan
```

Notice that the task takes a long time to execute. In the build scan go to
`Performance -> Configuration` and examine `app/build.gradle.kts`. Notice it says
the `printClasspath` task is created immediately.

Open `app/build.gradle.kts` and examine how the task `printClasspath` is defined:

```kotlin
tasks.create("printClasspath") {
    ...
}
```

Change it to be defined using `tasks.register`. Now execute the help task again:

```bash
./gradlew :app:help
```

Notice it executes much faster now.

---
### Enabling parallel execution for tasks

Execute the `clean` and then the `build` task on the root project:

```bash
./gradlew clean
./gradlew build --scan
```

This will execute the `build` task on all the subprojects. Open the build
scan and look at the `Timeline`. Notice that all the tasks are executed
serially.

Add the following to `gradle.properties`:

```properties
org.gradle.parallel=true
```

Execute the `clean` and then the `build` task on the root project:

```bash
./gradlew clean
./gradlew build --scan
```

Open the build  scan and look at the `Timeline`. Notice that now many tasks
are executed in parallel and the time taken difference.

---
### Enabling parallel execution for tests

In the previous build scan, go to `Performance -> Task execution`. Notice the
test tasks took a long time.

Open `task-config-convention.gradle.kts` which is under `buildSrc`. Add the following
configuration to it:

```kotlin
tasks.withType<Test>().configureEach {
    maxParallelForks = (Runtime.getRuntime().availableProcessors() / 2).coerceAtLeast(1)
}
```

This will enable test classes to be executed in parallel during the test task.

Now execute the `clean` task followed by the `test` task on the root project:

```bash
./gradlew clean
./gradlew test --scan
```

Open the build scan and go to `Performance -> Task execution`. Notice the
test tasks took a significantly shorter time.

---
### Enable and use Local Cache

Execute the `clean` task followed by the `test` task on the root project:

```bash
./gradlew clean test
```

Do this again:

```bash
./gradlew clean test
```

Notice the tasks all execute again. Now go to the `gradle.properties` file and
add the following:

```properties
org.gradle.caching=true
```

Now execute the tasks twice:

```bash
./gradlew clean test
./gradlew clean test
```

Notice on the second execution the outcome label `FROM-CACHE` and the overall
execution time is much shorter.
