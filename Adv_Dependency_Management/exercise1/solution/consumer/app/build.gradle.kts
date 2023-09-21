plugins {
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(mycatalog.bundles.core.libs)
    testImplementation(mycatalog.junit)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

application {
    mainClass.set("com.gradlelab.consumer.App")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
