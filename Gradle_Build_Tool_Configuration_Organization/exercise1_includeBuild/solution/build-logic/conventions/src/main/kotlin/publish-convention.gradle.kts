plugins {
    id("maven-publish")
}

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