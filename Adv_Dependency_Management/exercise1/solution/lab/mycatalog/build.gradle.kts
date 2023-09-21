plugins {
    `version-catalog`
    `maven-publish`
}

group = "com.gradlelab"
version = "1.2"

catalog {
    versionCatalog {
        from(files("../gradle/libs.versions.toml"))
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["versionCatalog"])
        }
    }
    repositories {
        maven {
            url = uri("/tmp/repo")
        }
    }
}
