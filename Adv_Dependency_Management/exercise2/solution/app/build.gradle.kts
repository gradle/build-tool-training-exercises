plugins {
    application
    id("shared-tasks-convention")
}

repositories {
    mavenCentral()
}

dependencies {
    constraints {
        implementation("com.google.guava:guava") {
            version {
                // strictly("[28.0-jre, 30.0-jre]")
                strictly("[28.0-jre, 28.5-jre]")
            }

        }
    }

    implementation(project(":model"))
    implementation("com.google.inject:guice:5.1.0")
}

application {
    mainClass.set("com.gradle.lab.App")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
