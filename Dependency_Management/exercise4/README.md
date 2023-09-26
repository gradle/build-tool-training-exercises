## Dependency Management - Exercise 4

<p align="left">
<img width="10%" height="10%" src="https://user-images.githubusercontent.com/120980/174325546-8558160b-7f16-42cb-af0f-511849f22ebc.png">
</p>

This is a hands-on exercise to go along with the
**Dependency Management** training module. In this exercise
you will go over the following:

* Creating a version catalog
* Updating build files to use the catalog

---
### Prerequisites

* Completed exercise 4
* You can perform the exercises in the same Gradle project used in the previous exercises

---
### Create Version Catalog

In the `gradle` folder create a file called `libs.versions.toml`. Add the
dependency modules for the `app` subproject to it:

```toml
[libraries]
guice = ""
checkerqual = ""
guava-app = { module = "", version = { strictly = "" } }
```

Update the dependencies and constraints in `app` to use the version
catalog. If you get stuck you can refer to the solution.

Now add the dependency modules for the `model` subproject:

```toml
[libraries]
guava-model = ...
json = ""
```

Update the dependencies and constraints in `model` to use the version
catalog. If you get stuck you can refer to the solution.
