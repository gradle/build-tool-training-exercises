## Gradle Build Tool Plugin Development - Exercise 3

<p align="left">
<img width="10%" height="10%" src="https://user-images.githubusercontent.com/120980/174325546-8558160b-7f16-42cb-af0f-511849f22ebc.png">
</p>

This is a hands-on exercise to go along with the
**Gradle Build Tool Plugin Development** training module. In this exercise you
will get familiar with the following topics:

* Create task
* Register task
* Add unit test
* Add functional test

---
### Prerequisites

* Completed [exercise 2](../exercise2/README.md)
* You can perform the exercises in the same Gradle project used in exercise 2

---
### Create task

Create an abstract Kotlin class called `CoverageLockInTask` which extends from
`DefaultTask`.

Add the following inputs:

* counter
* goal
* onCi
* reportXmlFile

Add a `coverageFile` output file. Then add a function annotated as the action.

```kotlin
abstract class CoverageLockInTask : DefaultTask() {

    @get:Input
    abstract val counter: Property<String>
    
    //...
}
```

One you are done defining the properties, fill in the implementation:

```kotlin
@TaskAction
fun perform() {
    if (!onCi.get()) {
        logger.lifecycle("Not on CI machine, skipping...")
        return
    }

    if (!reportXmlFile.get().asFile.exists()) {
        logger.lifecycle("No Jacoco XML report file found")
        return
    }

    val currentCoverage : Float? = parseCoveragePercentage(reportXmlFile.get().asFile, createXmlParser())

    if (currentCoverage == null) {
        logger.lifecycle("No coverage info found for $counter")
        return
    }

    lockInGains(currentCoverage)

    // Git commit and push here.
}

private fun createXmlParser(): XmlParser {
    val parser = XmlParser(false, true, true)
    parser.setFeature("http://apache.org/xml/features/disallow-doctype-decl", false)
    parser.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

    return parser
}

private fun parseCoveragePercentage(reportFile: File, parser: XmlParser): Float? {
    val rootNode : groovy.util.Node = parser.parse(reportFile)
    val counters : NodeList = rootNode.get("counter") as NodeList

    counters.forEach { it as groovy.util.Node
        val type : String = it.get("@type") as String
        if (type == counter.get()) {
            val missed : Float = (it.get("@missed") as String).toFloat()
            val covered : Float = (it.get("@covered") as String).toFloat()

            val total : Float = missed + covered
            val percent : Float = covered / total
            return String.format("%.2f", percent).toFloat()
        }
    }

    return null
}

private fun lockInGains(currentCoverage: Float) {
    val lockInFile : File = coverageFile.get().asFile

    if (!lockInFile.exists()) {
        // If no coverage file exists, just lock in the current coverage.
        updateLockInFile(currentCoverage, lockInFile)
        return
    }

    val fileValue = try {
        lockInFile.readText().toFloat()
    } catch (e: NumberFormatException) {
        project.logger.error("Invalid coverage lock file: " + lockInFile.absolutePath)
        updateLockInFile(currentCoverage, lockInFile)
        return
    }

    if (currentCoverage > fileValue) {
        logger.lifecycle("Coverage has increased from: $fileValue to: $currentCoverage")

        if (fileValue >= goal.get()) {
            logger.lifecycle("Goal of: " + goal.get().toString() + " already achieved")
            return
        }

        updateLockInFile(currentCoverage, lockInFile)
        return
    }
}

private fun updateLockInFile(currentCoverage: Float, lockInFile: File) {
    // If we are at or above the goal, cap the coverage.
    var valueToWrite = currentCoverage
    if (currentCoverage > goal.get()) {
        valueToWrite = goal.get()
    }

    logger.lifecycle("Locking in coverage of: " + valueToWrite.toString() + " to file: " + lockInFile.absolutePath)
    lockInFile.writeText(valueToWrite.toString())
}
```

If you get stuck you can refer to the [solution](solution/).

### Register task

In `CoveragelockPlugin` register a task called `lockInCoverageGains` of type
`CoverageLockInTask`. Set the task properties using the `extension`.

```kotlin
project.tasks.register("lockInCoverageGains", CoverageLockInTask::class.java) { task ->
    task.counter.set(extension.counter.get())
    // Set other properties...
    task.reportXmlFile.set(
        project.tasks.named("jacocoTestReport", JacocoReport::class.java)
            .map { it.reports.xml.outputLocation.get() }
    )
}
```

If you get stuck you can refer to the [solution](solution/).

### Add unit test

Add the following unit test:

```kotlin
@Test fun `plugin registers task`() {
    val project = ProjectBuilder.builder().build()
    project.plugins.apply("application")
    project.plugins.apply("com.gradlelab.coveragelock")

    // Write assertions here
}
```

Run the tests:

```bash
./gradle :plugin:test
```

If you get stuck you can refer to the [solution](solution/).

### Add functional tests

Create a new test project called `coverage_increases` with the following configuration:

```kotlin
coverageLockIn {
    coverageFile.set(layout.projectDirectory.file("coverage.txt"))
    onCi.set(true)
}
```

Write a functional test that uses the above project:

```kotlin
@Test fun `coverage increases`() {
    setupTestProject("coverage_increases")
    val log : String = runAndGetLogs(true, "lockInCoverageGains");
    assertTrue(log.contains("Coverage has increased from"))
    assertTrue(log.contains("Locking in coverage of"))
    // Verify file has been modified.
    assertEquals(0.5f, File(tempFolder.root, "app/coverage.txt").readText().toFloat())
}
```

Note that it even verifies the contents of the coverage file.

Run the tests:

```bash
./gradle :plugin:functionalTest
```

If you get stuck you can refer to the [solution](solution/).
