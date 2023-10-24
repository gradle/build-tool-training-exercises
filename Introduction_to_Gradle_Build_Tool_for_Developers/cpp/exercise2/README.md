## Introduction to Gradle Build Tool for Developers - Exercise 2

<p align="left">
<img width="10%" height="10%" src="https://user-images.githubusercontent.com/120980/174325546-8558160b-7f16-42cb-af0f-511849f22ebc.png">
</p>

This is a hands-on exercise to go along with the
**Introduction to Gradle Build Tool for Developers** training module. In this exercise you
will get familiar with the following topics:

* See available tasks
* Run a build
* Release builds
* Run test task
* Add and use external plugin

---
### Prerequisites

* Completed [exercise 1](../exercise1/README.md)
* You can perform the exercises in the same Gradle project used in exercise 1,
  or open the `lab/settings.gradle.kts` Gradle project and work there.

---
### See Available Tasks

Open a terminal tab in the directory of the project.
Run `./gradlew :app:tasks --all`. Scroll up and explore the tasks.

---
### Run a Build

In the tasks list, notice the `build` task under the `Build tasks` group.

<p align="center">
<img width="40%" height="40%" src="https://github.com/gradle/build-tool-training-exercises/assets/120980/d0744643-1b36-4843-8fc7-c0e4176d02d2">
</p>

Inspect the file `app/src/main/cpp/app.cpp`. Notice the
`main` method is supposed to print a greeting message.

In the terminal, execute the `./gradlew :app:build` command. Notice that
the `build` directory gets created. Explore this directory, notice the
`exe/main/debug` directory with an `app` executable in there.

<p align="center">
<img width="40%" height="40%" src="https://github.com/gradle/build-tool-training-exercises/assets/120980/1a38f972-4f56-4c55-8da3-1a3b7c0348e6">
</p>

In the terminal run the app:

```bash
$ ./app/build/exe/main/debug/app
Hello, World!
```

### Release Builds

Execute the following task:

```bash
./gradlew :app:assembleRelease
```

Notice the `exe/main/release` directory with an `app` executable in there.

---
### Run Test Task and Inspect Failure

Open the file `app/src/test/cpp/app_test.cpp` in the IDE.
Notice there is one test defined. Execute the test task and notice it succeeds.

```bash
./gradlew :app:test
```

Change the test `app_test.cpp` to expect an additional exclamation mark. Execute
the test task again and notice if fails.

```bash
./gradlew :app:test
```

Notice the error message printed in the terminal.

```bash
> Task :app:runTest FAILED
Assertion failed: (greeter.greeting().compare("Hello, World!!") == 0), function main, file app_test.cpp, line 10.
```

---
### Add External Plugin

Open the [Gradle Plugin Portal](https://plugins.gradle.org/) page in a browser.
Search for `task tree`. In the search results notice the information on the
right side which indicates when the plugin was last updated. Click on the
`org.barfuin.gradle.taskinfo` plugin.

<p align="center">
<img width="75%" height="75%" src="https://user-images.githubusercontent.com/120980/174329195-c7843594-5225-47ac-90b3-4c6c0d6166cd.png">
</p>

Copy the `id` line and paste it into the `plugin` section of
`build.gradle.kts`.

<p align="center">
<img width="75%" height="75%" src="https://github.com/gradle/build-tool-training-exercises/assets/120980/2da0c378-3293-4883-a531-1dc5146752c4">
</p>

Go back to the plugin page in the browser. Click on the repository link near
the top of the page.

<p align="center">
<img width="50%" height="50%" src="https://user-images.githubusercontent.com/120980/174329444-a53a3afb-631d-450a-b123-d1202290d201.png">
</p>

In the README, notice the author describes how to invoke the tasks. An argument
has to be passed, which is the task whose dependencies you want to inspect.
Therefore it is easier to run the task in the terminal where you can easily
pass arguments.

<p align="center">
<img width="60%" height="60%" src="https://user-images.githubusercontent.com/120980/174329524-37b7c6e2-4554-40a1-9f36-d142ad73904e.png">
</p>
<p align="center">
<img width="60%" height="60%" src="https://user-images.githubusercontent.com/120980/174329627-f9f4b738-0ce4-4dd8-b179-84b2448b70fb.png">
</p>
<p align="center">
<img width="60%" height="60%" src="https://user-images.githubusercontent.com/120980/174329705-c903cd1b-0ee3-4757-afaf-9782e8fac843.png">
</p>

Open the terminal in the IDE in the lower toolbar. Run `./gradlew :app:tiTree :app:test`
to see the dependencies for the test task.

<p align="center">
<img width="75%" height="75%" src="https://github.com/gradle/build-tool-training-exercises/assets/120980/13c4177d-eec9-467a-8534-a95955cf6945">
</p>

<p align="right">
<a href="https://github.com/gradle/build-tool-training-exercises/tree/main/Introduction_to_Gradle_Build_Tool_for_Developers/cpp/exercise3">Exercise 3 >></a>
</p>
