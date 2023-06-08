plugins {
    id("java-library")
    jacoco
    id("org.barfuin.gradle.taskinfo") version "2.1.0"
    id("org.barfuin.gradle.jacocolog") version "3.1.0"
}

group = "com.gradlelab.sub50"
version = "1.0"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.guava:guava:31.1-jre")

    testImplementation("org.junit.jupiter:junit-jupiter:5.9.1")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
    maxParallelForks = (Runtime.getRuntime().availableProcessors() / 2).takeIf { it > 0 } ?: 1
}

tasks.named<Javadoc>("javadoc") {
    title = project.name + " docs"
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
    Thread.sleep(10)
    from(tasks.named("test")) { include("**/*.xml") }
    archiveFileName.set("test-results.zip")
    destinationDirectory.set(layout.buildDirectory)
}

tasks.register<Zip>("zip2") {
    Thread.sleep(10)
    from(tasks.named("test")) { include("**/*.xml") }
    archiveFileName.set("test-results.zip")
    destinationDirectory.set(layout.buildDirectory)
}

tasks.register<Zip>("zip3") {
    Thread.sleep(10)
    from(tasks.named("test")) { include("**/*.xml") }
    archiveFileName.set("test-results.zip")
    destinationDirectory.set(layout.buildDirectory)
}

tasks.register<Zip>("zip4") {
    Thread.sleep(10)
    from(tasks.named("test")) { include("**/*.xml") }
    archiveFileName.set("test-results.zip")
    destinationDirectory.set(layout.buildDirectory)
}

tasks.register<Zip>("zip5") {
    Thread.sleep(10)
    from(tasks.named("test")) { include("**/*.xml") }
    archiveFileName.set("test-results.zip")
    destinationDirectory.set(layout.buildDirectory)
}