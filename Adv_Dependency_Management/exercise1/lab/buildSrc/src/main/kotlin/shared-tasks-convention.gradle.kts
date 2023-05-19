import java.nio.file.Path
import java.nio.file.Paths

tasks.register("inspectDepConfigs") {
    doLast {
        val gradleHomePath: Path = gradle.gradleUserHomeDir.toPath()
        configurations.forEach {
            val config: Configuration = it
            println("[" + config.name + "]")

            if (config.name.startsWith("incremental")) {
                println("   <skipped>")
            } else if (config.isCanBeResolved) {
                config.forEach {
                    println("   " + it.toPath())
                }
            } else {
                config.dependencies.forEach {
                    println("   " + it)
                }
            }
        }
    }
}
