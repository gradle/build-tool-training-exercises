allprojects {
    // Only projects that end with "sub4" should get this configuration.
    if (project.name.endsWith("sub4")) {
        tasks.register<Zip>("zip6") {
            from(tasks.named("test")) { include("**/*.xml") }
            archiveFileName.set("test-results.zip")
            destinationDirectory.set(layout.buildDirectory)
        }
    }
}

val zip7Projects = arrayOf("sub5", "sub6")
// Only the above projects under "subprojects" should get this configuration.
for (prjName in zip7Projects) {
    val prj = project(":subprojects:$prjName")

    prj.tasks.register<Zip>("zip7") {
        from(prj.tasks.named("test")) { include("**/*.xml") }
        archiveFileName.set("test-results.zip")
        destinationDirectory.set(prj.layout.buildDirectory)
    }
}
