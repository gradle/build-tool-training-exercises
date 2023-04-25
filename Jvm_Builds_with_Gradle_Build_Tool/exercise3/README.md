## JVM Builds with Gradle Build Tool - Exercise 3

<p align="left">
<img width="10%" height="10%" src="https://user-images.githubusercontent.com/120980/174325546-8558160b-7f16-42cb-af0f-511849f22ebc.png">
</p>

This is a hands-on exercise to go along with the
**JVM Builds with Gradle Build Tool** training module. In this exercise
you will get familiar with the following topics:

* Create a separate group of tests using JVM suites
* Configure test coverage threshold

---
### Prerequisites

* Completed exercise 2

---
### Separate Slow Test

Inspect the tests associated with the subproject in the `test/java` directory.
Notice one of the tests has a `sleep` statement in it, which is to simulate a
slow test.

<p align="center">
<img width="60%" height="60%" src="https://user-images.githubusercontent.com/120980/189359390-1ea1719a-9710-42a2-a7fb-fc18d0ac4f50.png">
</p>

Execute the `test` task to see the slow test in action.

```bash
./gradlew :<subproject>:clean :<subproject>:test
```

Create a separate directory under `<subproject>/src` called `intTest`, also create `java/com/gradle/lab` directories under `intTest`.
Move the slow test to `java/com/gradle/lab` under this directory.

<p align="center">
<img width="40%" height="40%" src="https://user-images.githubusercontent.com/120980/189359908-981e0b3f-2861-4e8c-bcce-bc6f7221455a.png">
</p>

Now register a `JVM test suite` for this separated test directory.

```kotlin
testing {
    suites {
        // Your configuration goes here.
    }
}
```

*Hint:* You can refer to the
[JVM test suite documentation](https://docs.gradle.org/current/userguide/jvm_test_suite_plugin.html#configure_source_directories_of_a_test_suite).
If you get stuck you can refer to the
[solution](solution/buildSrc/src/main/kotlin/shared-build-conventions.gradle.kts#L102).

Verify a new `SourceSet` has been created for the suite:

```bash
./gradlew :<subproject>:sourceSetsInfo
```

<p align="center">
<img width="35%" height="35%" src="https://user-images.githubusercontent.com/120980/189360440-c5277412-2369-4e49-bb2b-8d629203aad2.png">
</p>

Execute the `test` task to see that it runs faster now.

```bash
./gradlew :<subproject>:clean :<subproject>:test
```

See the new `intTest` task created under the `verification` group:

```bash
./gradlew :<subproject>:tasks --all
```

<p align="center">
<img width="35%" height="35%" src="https://user-images.githubusercontent.com/120980/189360806-4638cd7f-1a3c-4740-bd13-6f7a85e4aa40.png">
</p>

Execute the `intTest` task to execute the slow test.

```bash
./gradlew :<subproject>:clean :<subproject>:intTest
```

### Measure Test Coverage

Add the `jacoco` plugin to the shared convention plugin. Now update the
task dependencies as follows:

* `jacocoTestReport` depends on `test`
* `jacocoTestCoverageVerification` depends on `jacocoTestReport`
* `check` depends on `jacocoTestCoverageVerification`

Run the `check` task and verify `jacocoTestReport` and
`jacocoTestCoverageVerification` are executed.

```bash
./gradlew :<subproject>:check
```

<p align="center">
<img width="40%" height="40%" src="https://user-images.githubusercontent.com/120980/189361400-a66a8444-fc22-4bd5-9cad-130b1113032f.png">
</p>

Open `<subproject>/build/reports/jacoco/test/html/index.html` in a browser
and inspect the coverage.

<p align="center">
<img width="70%" height="70%" src="https://user-images.githubusercontent.com/120980/189361696-92474256-1854-4b74-aac1-54e9c7a3b4d9.png">
</p>

### Enforce Test Coverage

Now add a rule to enforce that line coverage should be at least 70%.

*Hint:* Refer to the
[jacoco documentation](https://docs.gradle.org/current/userguide/jacoco_plugin.html#sec:jacoco_report_violation_rules).
If you get stuck you can refer to the
[solution](solution/buildSrc/src/main/kotlin/shared-build-conventions.gradle.kts#L112).

Run the `check` task again and watch the build fail due to insufficient
coverage.

```bash
./gradlew :<subproject>:check
```

<p align="center">
<img width="70%" height="70%" src="https://user-images.githubusercontent.com/120980/189362103-32c48823-f4ec-4b7c-bb78-4209f2cc5dc0.png">
</p>

Change the line coverage to 40%. Run the `check` task again and verify it
succeeds now.

```bash
./gradlew :<subproject>:check
```

<p align="right">
<a href="https://github.com/gradle/build-tool-training-exercises/tree/main/Jvm_Builds_with_Gradle_Build_Tool/exercise4">Exercise 4 >></a>
</p>
