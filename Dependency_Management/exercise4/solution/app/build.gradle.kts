plugins {
    application
    id("shared-tasks-convention")
}

repositories {
    mavenCentral()
}

java {
    consistentResolution {
        useRuntimeClasspathVersions()
    }
}

dependencies {
    implementation(project(":model"))
    implementation(libs.guice)
    runtimeOnly(libs.checkerqual)

    constraints {
        implementation(libs.guava.app)
        /*implementation("com.google.guava:guava") {
            version {
                //strictly("[28.0-jre, 30.0-jre]")
                strictly("[28.0-jre, 28.5-jre]")
            }
        }*/
    }
}

application {
    mainClass.set("com.gradle.lab.App")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
