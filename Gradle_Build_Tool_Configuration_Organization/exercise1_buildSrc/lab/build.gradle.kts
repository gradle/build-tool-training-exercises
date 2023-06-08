plugins {
    `maven-publish`
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "jacoco")

    tasks.named<JacocoReport>("jacocoTestReport") {
        dependsOn(tasks.named("test"))
        reports {
            xml.required.set(true)
        }
    }

    tasks.named<JacocoCoverageVerification>("jacocoTestCoverageVerification") {
        violationRules {
            rule {
                limit {
                    counter = "LINE"
                    value = "COVEREDRATIO"
                    minimum = "0.5".toBigDecimal()
                }
            }
        }
    }

    tasks.named("check") {
        dependsOn("jacocoTestCoverageVerification")
    }

    // This configuration should be applied to some of the subprojects only.
    val publishProjects = arrayOf("subproject2", "subproject3")
    if (publishProjects.contains(project.name)) {
        apply(plugin = "maven-publish")

        publishing {
            publications {
                create<MavenPublication>("library") {
                    from(components["java"])
                }
            }
            repositories {
                maven {
                    url = uri(layout.buildDirectory.dir("repo"))
                }
            }
        }
    }
}
