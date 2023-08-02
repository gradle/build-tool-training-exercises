plugins {
    java
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")

    implementation("com.google.guava:guava:30.1.1-jre")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

tasks.register("testWithMsg") {
    group = "verification"
    description = "Runs tests and prints msg when done"
    dependsOn("test")

    doLast {
        println("Tests done!")
    }
}

tasks.register("msgAfterTest") {
    group = "verification"
    description = "Prints msg when tests are done"

    doLast {
        println("Tests done!!")
    }
}

tasks.named("test") {
    finalizedBy("msgAfterTest")
}

tasks.register<Copy>("backupTestXml") {
    from("build/test-results/test")
    into("/tmp/")

    exclude("binary/**")
}
