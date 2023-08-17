## Gradle Build Cache Deep Dive - Exercise 2

<p align="left">
<img width="10%" height="10%" src="https://user-images.githubusercontent.com/120980/174325546-8558160b-7f16-42cb-af0f-511849f22ebc.png">
</p>

This is a hands-on exercise to go along with the
**Gradle Build Cache Deep Dive** training module. In this exercise
you will go over the following:

* Practice troubleshooting cache misses

---
### Prerequisites

* Completed [exercise 1](../exercise1/README.md)
* You can perform the exercises in the same Gradle project used in exercise 1.

---
### Debugging Test Behavior

Open `AppTest.java` and uncomment the `writeToResourcesDir` test. Execute
the `:app:test` task a couple of times. Notice the task is no longer be able to
be `UP-TO-DATE`.

Perform a build scan comparison to help determine which inputs are changing.
Correct the test behavior to address this.

---
### Debugging Custom Task Behavior

Open `shared-tasks-convention` and uncomment the `genTestInfo` task along with
the configuration modification to the `helloFile` task.

Execute the `helloFile` task a few times. Notice the task is not always `UP-TO-DATE`.

Perform a build scan comparison to help determine which inputs are changing.
Correct the task behavior to address this.
