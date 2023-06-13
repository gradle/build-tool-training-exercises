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
listOf("compileJava", "compileTestJava", "test").forEach {
    tasks.named(it) {
        inputs.files(tasks.named("generateLocalUniqueValue"))
            .withPathSensitivity(PathSensitivity.NONE)
            .withPropertyName("uniqueValue")
    }
}
