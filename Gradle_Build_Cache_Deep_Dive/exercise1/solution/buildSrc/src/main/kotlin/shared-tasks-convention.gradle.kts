import org.gradle.api.tasks.PathSensitivity
import kotlin.random.Random

/*
This just guarantees that everyone running the lab pushes their own changes
into the remote cache, rather than getting hits from someone else's run.
 */
tasks.register("generateLocalUniqueValue") {
    description = "Ensures unique cache experience for hands-on lab"

    val outputFile = layout.projectDirectory.file("local.txt")
    onlyIf {
        !outputFile.asFile.exists()
    }
    outputs.file(outputFile)
    doLast {
        val bytes = ByteArray(20)
        Random.nextBytes(bytes)
        outputFile.asFile.writeBytes(bytes)
    }
}

tasks.register<Zip>("zipUniqueValue") {
    // Inputs
    from(tasks.named("generateLocalUniqueValue"))

    // Outputs
    destinationDirectory.set(layout.buildDirectory)
    archiveFileName.set("unique.zip")
    // Enable working with cache.
    outputs.cacheIf { true }
}

tasks.register<Exec>("helloFile") {
    workingDir = layout.projectDirectory.asFile
    commandLine("bash", "-c", "mkdir -p build; person=`cat name.txt`; echo \"hello \$person\" > build/hello.txt")

    inputs.file(layout.projectDirectory.file("name.txt"))
        .withPropertyName("helloInput")
        .withPathSensitivity(PathSensitivity.RELATIVE)

    outputs.file(layout.buildDirectory.file("hello.txt"))
        .withPropertyName("helloOutput")

    outputs.cacheIf { true }
}

listOf("compileJava", "compileTestJava", "test", "zipUniqueValue", "helloFile").forEach {
    tasks.named(it) {
        inputs.files(tasks.named("generateLocalUniqueValue"))
            .withPathSensitivity(PathSensitivity.NONE)
            .withPropertyName("uniqueValue")
    }
}
