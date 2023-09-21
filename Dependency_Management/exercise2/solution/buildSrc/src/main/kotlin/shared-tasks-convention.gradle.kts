tasks.register("inspectDepConfigs") {
    doLast {
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
