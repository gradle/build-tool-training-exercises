## Advanced Dependency Management - Exercise 5

<p align="left">
<img width="10%" height="10%" src="https://user-images.githubusercontent.com/120980/174325546-8558160b-7f16-42cb-af0f-511849f22ebc.png">
</p>

This is a hands-on exercise to go along with the
**Advanced Dependency Management** training module. In this exercise
you will go over the following:

* Creating a version catalog
* Updating build files to use the catalog

---
### Prerequisites

* Completed exercise 4
* You can perform the exercises in the same Gradle project used in the previous exercises

---
### Create Version Catalog

In the `gradle` folder create a file called `libs.versions.toml`. Add all the
dependency modules to it.

```text
[versions]
json = "20220924"
guice = "5.1.0"
checker-qual = "3.28.0"

[libraries]
json = { module = "org.json:json", version.ref = "json" }
guava = { module = "com.google.guava:guava", version = { strictly = "[28.0-jre, 30.0-jre]", prefer="29.0-jre" } }
guava-constraints = { module = "com.google.guava:guava", version = { strictly = "[28.0-jre, 28.5-jre]" } }
guice = { module = "com.google.inject:guice", version.ref = "guice" }
checker-qual = { module = "org.checkerframework:checker-qual", version.ref = "checker-qual" }
```

Update the dependencies and constraints in `app` and `model` to use the version
catalog. If you get stuck you can refer to the solution.
