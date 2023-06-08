plugins {
    id("java")
}

buildscript {
    repositories {
        maven {
            url = uri("https://plugins.gradle.org/m2/")
        }
    }
    dependencies {
        classpath("org.barfuin.gradle.jacocolog:gradle-jacoco-log:3.1.0")
        classpath("org.barfuin.gradle.taskinfo:gradle-taskinfo:2.1.0")
    }
}

subprojects {
    apply(plugin = "java-library")
    apply(plugin = "jacoco")
    apply(plugin = "org.barfuin.gradle.taskinfo")
    apply(plugin = "org.barfuin.gradle.jacocolog")

    version = "1.0"

    repositories {
        mavenCentral()
    }

    dependencies {
        implementation("com.google.guava:guava:31.1-jre")

        testImplementation("org.junit.jupiter:junit-jupiter:5.9.1")
    }

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(11))
        }
    }

    tasks.named<Test>("test") {
        useJUnitPlatform()
        maxParallelForks = (Runtime.getRuntime().availableProcessors() / 2).takeIf { it > 0 } ?: 1
    }

    tasks.named<Javadoc>("javadoc") {
        title = project.name + " docsz"
    }

    tasks.register<Javadoc>("testJavadoc") {
        title = project.name + " test docs"
        source = sourceSets.test.get().allJava
    }

    tasks.withType<JavaCompile>().configureEach {
        options.compilerArgs.add("-Xlint:none")
    }

    tasks.named<Jar>("jar") {
        manifest {
            attributes(
                "Implementation-Title" to project.name,
                "Implementation-Version" to version
            )
        }
    }

    tasks.named<JacocoReport>("jacocoTestReport") {
        dependsOn("test")

        reports.xml.required.set(true)
    }

    tasks.named<JacocoCoverageVerification>("jacocoTestCoverageVerification") {
        dependsOn("jacocoTestReport")

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

    tasks.register<Zip>("zip1") {
        from(tasks.named("test")) { include("**/*.xml") }
        archiveFileName.set("test-results.zip")
        destinationDirectory.set(layout.buildDirectory)
    }

    tasks.register<Zip>("zip2") {
        from(tasks.named("test")) { include("**/*.xml") }
        archiveFileName.set("test-results.zip")
        destinationDirectory.set(layout.buildDirectory)
    }

    tasks.register<Zip>("zip3") {
        from(tasks.named("test")) { include("**/*.xml") }
        archiveFileName.set("test-results.zip")
        destinationDirectory.set(layout.buildDirectory)
    }

    tasks.register<Zip>("zip4") {
        from(tasks.named("test")) { include("**/*.xml") }
        archiveFileName.set("test-results.zip")
        destinationDirectory.set(layout.buildDirectory)
    }

    tasks.register<Zip>("zip5") {
        from(tasks.named("test")) { include("**/*.xml") }
        archiveFileName.set("test-results.zip")
        destinationDirectory.set(layout.buildDirectory)
    }
}