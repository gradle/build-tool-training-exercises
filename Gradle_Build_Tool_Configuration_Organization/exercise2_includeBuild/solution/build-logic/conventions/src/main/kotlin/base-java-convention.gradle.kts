plugins {
    id("java-library")
}

group = "com.gradlelab"
version = "1.0"

repositories {
    mavenCentral()
}

val libs = the<VersionCatalogsExtension>().named("libs")
dependencies {
    implementation(libs.findLibrary("guava").get())
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}