## Introduction to Gradle Build Tool for Developers - Exercise 2

<p align="left">
<img width="10%" height="10%" src="https://user-images.githubusercontent.com/120980/174325546-8558160b-7f16-42cb-af0f-511849f22ebc.png">
</p>

This is a hands-on exercise to go along with the
**Introduction to Gradle Build Tool for Developers** training module. In this exercise you
will get familiar with the following topics:

* See available tasks
* Run an application
* Run test task and inspect report
* Add and use external plugin

---
### Prerequisites

* Completed [exercise 1](../exercise1/README.md)
* You can perform the exercises in the same Gradle project used in exercise 1,
or open the `lab/settings.gradle.kts` Gradle project and work there.

---
### See Available Tasks

Open the Gradle toolbar on right side of the IDE and explore the tasks. Notice
there are tasks under the top level `Tasks` as well as under `app`. Usually
you will want to run the tasks under the specific Gradle project, in this
case `app`. Running tasks under the top level `Tasks` will run the task for
all projects, if there were more than one.

<p align="center">
<img width="60%" height="60%" src="https://user-images.githubusercontent.com/120980/174327720-3ed48e27-bb06-4295-8c83-699f4dc8d519.png">
</p>

Close the Gradle toolbar. Open the `Terminal` tab in the lower toolbar and
run `./gradlew :app:tasks --all`. Scroll up and explore the tasks.

<p align="center">
<img width="60%" height="60%" src="https://user-images.githubusercontent.com/120980/174327952-d0efd1f1-f3fa-4226-9937-ee7d84e488f3.png">
</p>

---
### Run an Application

In the tasks list, notice the `run` task under the `Application tasks` group.

<p align="center">
<img width="40%" height="40%" src="https://user-images.githubusercontent.com/120980/174328444-b67c0ce8-628b-4a2a-a045-bbd949ba2fbc.png">
</p>

Inspect the file `app/src/main/java/com/gradle/lab/App.java`. Notice the
`main` method is supposed to print a greeting message.

<p align="center">
<img width="40%" height="40%" src="https://user-images.githubusercontent.com/120980/174328571-31ca2460-5d66-463c-85d6-baeb9a09a880.png">
</p>

Now look at the `application` block in `build.gradle.kts`, notice that the
`mainClass` is set as the file just viewed.

<p align="center">
<img width="40%" height="40%" src="https://user-images.githubusercontent.com/120980/174328663-11bb1a3e-5678-48c4-b136-22fee57edb3a.png">
</p>

In the terminal, execute the `./gradlew :app:run` command. Notice the greeting
message is printed.

<p align="center">
<img width="40%" height="40%" src="https://user-images.githubusercontent.com/120980/174328747-4e5bb679-7018-4d77-b468-64c42c28ee6d.png">
</p>

---
### Run Test Task and Inspect Report

Open the file `app/src/test/java/com/gradle/lab/AppTest.java` in the IDE.
Notice there is one test defined.

<p align="center">
<img width="60%" height="60%" src="https://user-images.githubusercontent.com/120980/174328837-35f1d9dd-27fd-4dbb-b685-2bd4f6345e82.png">
</p>

Open the Gradle toolbar again and open `app/Tasks/verification`. Double-click
on the `test` task.

<p align="center">
<img width="60%" height="60%" src="https://user-images.githubusercontent.com/120980/174328953-bf706b3d-700e-44ca-b9c0-fd782cce611a.png">
</p>

Notice the task run output on the bottom of the IDE.

<p align="center">
<img width="60%" height="60%" src="https://user-images.githubusercontent.com/120980/174329034-d97609ba-9f7f-46af-9421-ef819303bc57.png">
</p>

Navigate to `app/build/reports/tests/test` and open the `index.html` in a
browser.

<p align="center">
<img width="60%" height="60%" src="https://user-images.githubusercontent.com/120980/174329124-83e2c908-23cc-4d0a-8e8f-3cb8755a3cf1.png">
</p>

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
`build.gradle.kts`. Notice a popup that appears when you make a change to the
Gradle file. Click on the second icon to have the IDE process the changes to
the Gradle config.

<p align="center">
<img width="75%" height="75%" src="https://user-images.githubusercontent.com/120980/174329272-e66bb4eb-1fe7-4187-8718-593cc4f5fa04.png">
</p>

Open the Gradle toolbar and expand `Tasks/other`. Notice the 3 tasks `tiJson`,
`tiOrder` and `tiTree`. These have been added by the plugin.
**Do not run the tasks yet**.

<p align="center">
<img width="25%" height="25%" src="https://user-images.githubusercontent.com/120980/174329359-bbe764f1-94cd-431d-9e16-b3e72d721c29.png">
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
<img width="75%" height="75%" src="https://user-images.githubusercontent.com/120980/174329773-08fff96e-ee9d-46e8-97be-fe62630aa742.png">
</p>

<p align="right">
<a href="https://github.com/gradle/build-tool-training-exercises/tree/main/Introduction_to_Gradle_Build_Tool_for_Developers/exercise3">Exercise 3 >></a>
</p>
