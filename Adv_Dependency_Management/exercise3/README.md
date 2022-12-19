## Advanced Dependency Management - Exercise 3

<p align="left">
<img width="10%" height="10%" src="https://user-images.githubusercontent.com/120980/174325546-8558160b-7f16-42cb-af0f-511849f22ebc.png">
</p>

This is a hands-on exercise to go along with the
**Advanced Dependency Management** training module. In this exercise
you will go over the following:

* Enabling consistent resolution

---
### Prerequisites

* Completed exercise 2
* You can perform the exercises in the same Gradle project used in the previous exercises

---
### Different Dependency Graphs

Add the following dependency to the `app` subproject:

```kotlin
runtimeOnly("org.checkerframework:checker-qual:3.28.0")
```

Now notice the version of `checker-qual` used will be different in the
`compileClasspath` and `runtimeClasspath`:

```bash
./gradlew :app:dependencies --configuration=compileClasspath
```

<p align="center">
<img width="70%" height="70%" src="https://user-images.githubusercontent.com/120980/208412679-5d1887c8-ae70-4962-80c7-7d5b62d44d0a.png">
</p>

```bash
./gradlew :app:dependencies --configuration=runtimeClasspath
```

<p align="center">
<img width="70%" height="70%" src="https://user-images.githubusercontent.com/120980/208412875-db4c800f-1ccc-4330-bd92-dc7047a5c624.png">
</p>

### Consistent Resolution

Add the following configuration to the `app` subproject:

```kotlin
java {
    consistentResolution {
        useRuntimeClasspathVersions()
    }
}
```

This ensures the versions of libraries at `runtime` are used in other
dependency configurations.

Check the `compileClasspath` and `runtimeClasspath` dependencies again
and notice the version of `checker-qual` is now same due to a new
constraint added to the `compileClasspath`

```bash
./gradlew :app:dependencies --configuration=compileClasspath
```

<p align="center">
<img width="70%" height="70%" src="https://user-images.githubusercontent.com/120980/208413886-6cb15399-a1b8-4daf-a4aa-5db039c004de.png">
</p>
