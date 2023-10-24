## Introduction to Gradle Build Tool for Developers - Exercise 3

<p align="left">
<img width="10%" height="10%" src="https://user-images.githubusercontent.com/120980/174325546-8558160b-7f16-42cb-af0f-511849f22ebc.png">
</p>

This is a hands-on exercise to go along with the
**Introduction to Gradle Build Tool for Developers** training module. In this exercise
you will get familiar with the following topics:

* See dependencies for a project
* Adding dependencies
* Examining dependency conflict resolution

---
### Prerequisites

* Completed [exercise 2](../exercise2/README.md)

---
### See Dependencies for a Project

Open the `library1`, `library2` and `application` projects and explore them.

Notice `library1` has a `list` subproject. Publish it:

```bash
./gradlew :list:publish
```

Notice `library2` has a `utilities` subproject that depends on `library1`'s `list`.
Publish `utilities`:

```bash
./gradlew :utilities:publish
```

Notice `application` has one subproject called `app` that depends on `utilities`. It actually
also depends on `list`, but it is pulled in transitively.

Open the terminal run
`./gradlew :app:dependencies`. You will see dependencies grouped by
configurations. Notice both `utilities` and `list` as dependencies.

<p align="center">
<img width="60%" height="60%" src="https://github.com/gradle/build-tool-training-exercises/assets/120980/fe328a55-1ab0-494a-b807-4c65a379cc31">
</p>

To see the dependencies of just the `cppCompileDebugMacos` configuration run
`./gradlew :app:dependencies --configuration=cppCompileDebugMacos`

### Adding Dependencies

Let's look at the scenario where `utilities` did not have an `api` dependency on `list`,
and therefore `app` had to directly depend on `list`.

In `utilities` change the `list` dependency configuration to `implementation`. Publish the library
again:

```bash
./gradlew :utilities:publish
```

Notice when you build `app` it fails now:

```bash
./gradlew :app:build
```

In the `app` subproject, add an `implementation` dependency to `list` version `3.2.1`.

Now if you build `app` it succeeds:

```bash
./gradlew :app:build
```

### Examining Dependency Conflict Resolution

Undo the change to `utilities` so `list` is once again an `api` dependency.
Publish `utilities` again.

```bash
./gradlew :utilities:publish
```

In `list`, increase the version to `3.2.2` and publish it:

```bash
./gradlew :list:publish
```

Now update `app` to depend on `list` version `3.2.2`. Notice when you inspect
the dependencies of `app`, it will show that while `utilities` depended on `list`
version `3.2.1`, since `app` had a dependency on `3.2.2`, that version is used since
it is higher.

<p align="center">
<img width="60%" height="60%" src="https://github.com/gradle/build-tool-training-exercises/assets/120980/1d5366b6-6b02-40df-96be-3fd64777e319">
</p>
