tasks.withType<JavaCompile>().configureEach {
    options.isDebug = false
}

tasks.register<Zip>("app2zip") {
    from(tasks.named("test")) { include("**/*.xml") }
    archiveFileName.set("test-results.zip")
    destinationDirectory.set(layout.buildDirectory)
}
