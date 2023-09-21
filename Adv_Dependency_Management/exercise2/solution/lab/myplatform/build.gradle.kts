plugins {
    `java-platform`
    `maven-publish`
}

group = "com.gradlelab"
version = "1.3"

dependencies {
    constraints {
        api("com.google.code.gson:gson:2.8.3")
    }
}

publishing {
    publications {
        create<MavenPublication>("myPlatform") {
            from(components["javaPlatform"])
        }
    }
    repositories {
        maven {
            url = uri("/tmp/repo2")
        }
    }
}
