## Gradle Build Tool Plugin Development - Exercise 2

<p align="left">
<img width="10%" height="10%" src="https://user-images.githubusercontent.com/120980/174325546-8558160b-7f16-42cb-af0f-511849f22ebc.png">
</p>

This is a hands-on exercise to go along with the
**Gradle Build Tool Plugin Development** training module. In this exercise you
will get familiar with the following topics:

* Create and initialize an extension for a plugin
* Define additional configuration in a plugin
* Add unit test
* Add integration test

---
### Prerequisites

* Completed [exercise 1](../exercise1/README.md)
* You can perform the exercises in the same Gradle project used in exercise 1

---
### Create and initialize an extension for a plugin

Add a Kotlin interface called `CoverageLockInExtension`. Add the following
variables using
[managed properties](https://docs.gradle.org/current/userguide/custom_gradle_types.html#managed_properties):

* `coverageFile` which is a `RegularFileProperty`
* `counter` which is a `Property` of `String`
* `goal` which is a `Property` of `Float`
* `onCi` which is a `Property` of `Boolean`
* `internalCurrentCoverage` which is a `Property` of `Float`

In `CoverageLockPlugin` create and initialize the extension:

* `coverageFile` to `coverage_lock_in.txt` in the project directory
* `goal` to `0.8f`
* `counter` to `LINE`
* `onCi` to `false`
* `internalCurrentCoverage` to read `coverageFile` using `providers.fileContents`
* Disallow further changes to `internalCurrentCoverage`

```kotlin
extension.goal.convention(0.8f)
// Initialize other properties
// ...
extension.internalCurrentCoverage.convention(
    project.providers.fileContents(extension.coverageFile)
        .asText.map { it.toFloat() }.orElse(extension.goal))
// Disallow further changes to internalCurrentCoverage
// ...
```

Also remove the task that was added by `gradle init`.

Now add a test to `CoveragelockPluginTest` to ensure the extension is created, add
checks for some of the default values. **Note**: Make sure to also apply the
`application` plugin. Remove the other unit test that was created by
`gradle init`.

```kotlin
@Test fun `plugin creates extension`() {
    val project = ProjectBuilder.builder().build()
    project.plugins.apply("application")
    project.plugins.apply("com.gradlelab.coveragelock")

    
    assertNotNull(project.extensions.findByName("coverageLockIn"))
    // Other assertions
}
```

Confirm the tests pass:

```bash
./gradlew :plugin:test
```

If you get stuck you can refer to the [solution](solution/).

### Define additional configuration in a plugin

In `CoveragelockPlugin` add the following:

* Apply the `jacoco` plugin
* Configure the `jacocoTestReport` task to depend on the `test` task,
be finalized by the `jacocoTestCoverageVerification` task and
[make the xml report required](https://docs.gradle.org/current/userguide/jacoco_plugin.html#sec:jacoco_report_configuration).
* Configure the `jacocoTestCoverageVerification` task to depend on the
`jacocoTestReport` task and
[add a rule that ensures `extension.counter` has `COVEREDRATIO` of at least `extension.internalCurrentCoverage`](https://docs.gradle.org/current/userguide/jacoco_plugin.html#sec:jacoco_report_violation_rules)

```kotlin
project.tasks.named("jacocoTestReport", JacocoReport::class.java) { task ->
    // Write configuration here
}

project.tasks.named("jacocoTestCoverageVerification", JacocoCoverageVerification::class.java) { task ->
    task.dependsOn("jacocoTestReport")

    task.violationRules.rule { rule ->
        // Write rule here
    }
}
```

Now add a test to `CoveragelockPluginTest` to ensure the Jacoco rule is created.
**Note**: Make sure to also apply the `application` plugin.

```kotlin
@Test fun `jacoco rule created`() {
    val project = ProjectBuilder.builder().build()
    project.plugins.apply("application")
    project.plugins.apply("com.gradlelab.coveragelock")

    val task : JacocoCoverageVerification =
        project.tasks.named("jacocoTestCoverageVerification", JacocoCoverageVerification::class.java).get()

    // Additional assertions
}
```

Confirm the tests pass:

```bash
./gradlew :plugin:test
```

Remove the existing tests in `CoveragelockPluginFunctionalTest`. Add the following
helper methods:

```kotlin
private fun runAndGetLogs(expectSuccess: Boolean, taskToRun: String): String {
    val runner = GradleRunner.create()
    runner.forwardOutput()
    runner.withPluginClasspath()
    runner.withProjectDir(tempFolder.root)
    runner.withArguments(taskToRun)
    val result : BuildResult = if (expectSuccess) runner.build() else runner.buildAndFail()

    return result.output
}

private fun setupTestProject(dir : String) {
    val srcDir = File("src/functionalTest/resources", dir)
    srcDir.copyRecursively(tempFolder.root)
}
```

Now create a sample project called `just_plugin_applied` in
`src/functionalTest/resources` that just applies `application` and
`coveragelock` plugin. Write a test that uses this sample project to assert
that when the task `jacocoTestReport` is executed, the build fails and the
output contains the rule violation.

```kotlin
@Test fun `default coverage file is read`() {
    setupTestProject("just_plugin_applied")
    val log : String = runAndGetLogs(false, "jacocoTestReport");
    // Write assertion here
}
```

Confirm the tests pass:

```bash
./gradlew :plugin:functionalTest
```

If you get stuck you can refer to the [solution](solution/).
