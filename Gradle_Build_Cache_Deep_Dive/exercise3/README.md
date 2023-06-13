## Gradle Build Cache Deep Dive - Exercise 3

<p align="left">
<img width="10%" height="10%" src="https://user-images.githubusercontent.com/120980/174325546-8558160b-7f16-42cb-af0f-511849f22ebc.png">
</p>

This is a hands-on exercise to go along with the
**Gradle Build Cache Deep Dive** training module. In this exercise
you will go over the following:

* Enable caching for Custom Task with existing wiring for inputs and outputs
* Using Runtime API to wire inputs and outputs

---
### Prerequisites

* Completed [exercise 2](../exercise2/README.md)
* You can perform the exercises in the same Gradle project used in exercise 1.

---
### Disable Remote Cache

Update the `settings.gradle.kts` file to disable the remote cache and enable the local cache:

```kotlin
buildCache {
    local {
        isEnabled = true
        //...
    }
    remote {
        isEnabled = false
        //...
    }
}
```

---
### Enable Caching for Zip Task

Custom tasks based on the `Zip` task type will already have wiring for inputs
and outputs. The task works for incremental builds, you just have to configure
it to work with caches.

Add the following task to the `app` subproject:

```kotlin
tasks.register<Zip>("zipUniqueValue") {
    dependsOn("generateLocalUniqueValue")
    // Inputs
    from(layout.projectDirectory.file("local.txt"))

    // Outputs
    destinationDirectory.set(layout.buildDirectory)
    archiveFileName.set("unique.zip")
    // Enable working with cache.
    // Add your work here.
}
```

Before enabling the task to work with caching, let's run it a couple of times
to verify it works with incremental builds, but not with caching. The first
time its action will execute, the second time however it will re-use the
output from the build directory.

```diff
$ ./gradlew :app:zipUniqueValue
> Task :app:generateLocalUniqueValue UP-TO-DATE
- Task :app:zipUniqueValue

$ ./gradlew :app:zipUniqueValue
> Task :app:generateLocalUniqueValue UP-TO-DATE
> Task :app:zipUniqueValue UP-TO-DATE
```

If you run clean and the task, you will see if never fetches the previous
output from the cache. Using the **-i** flag will show that the task
isn't configured to work with the cache.

```diff
$ ./gradlew :app:clean :app:zipUniqueValue
# Task :app:clean
> Task :app:generateLocalUniqueValue UP-TO-DATE
- Task :app:zipUniqueValue
```

```bash
$ ./gradlew :app:clean :app:zipUniqueValue -i
...
...
...
> Task :app:zipUniqueValue
Caching disabled for task ':app:zipUniqueValue' because:
  Not worth caching
Task ':app:zipUniqueValue' is not up-to-date because:
  Output property 'archiveFile' file /Users/.../app/build/unique.zip has been removed.
```

Add configuration for enabling working with the cache by adding
`outputs.cacheIf { true }`.

Now run the clean and task twice, on the second time you will see it used the
cache.

```diff
$ ./gradlew :app:clean :app:zipUniqueValue
# Task :app:clean
# Task :app:generateLocalUniqueValue UP-TO-DATE
- Task :app:zipUniqueValue

$ ./gradlew :app:clean :app:zipUniqueValue
# Task :app:clean
# Task :app:generateLocalUniqueValue UP-TO-DATE
! Task :app:zipUniqueValue FROM-CACHE
```

Now let's change the input wiring and tell Gradle that the inputs
are actually the outputs of the generateLocalUniqueValue task.
This way we can remove the `dependsOn` configuration. We can do this by
adding `from(tasks.named("generateLocalUniqueValue"))`.
The task now looks as follows:

```kotlin
tasks.register<Zip>("zipUniqueValue") {
    // Inputs
    from(tasks.named("generateLocalUniqueValue"))

    // Outputs
    destinationDirectory.set(layout.buildDirectory)
    archiveFileName.set("unique.zip")
    // Enable working with cache.
    outputs.cacheIf { true }
}
```

If you want to see everything that goes into the cache key you can pass the
`-Dorg.gradle.caching.debug=true` flag.

```bash
$ ./gradlew :app:clean :app:zipUniqueValue -Dorg.gradle.caching.debug=true
```

### Enable Caching for Exec Task

There can be custom tasks that have no existing wiring for inputs and outputs.
For these tasks we can use the Runtime API to do the wiring, and then
enable caching for the task.

Create a file in the `app` subproject called `name.txt` and put your full name
as the contents of the file.

Now add the following task to the `app` subproject:

```kotlin
tasks.register<Exec>("helloFile") {
    workingDir = layout.projectDirectory.asFile
    commandLine("bash", "-c", "mkdir -p build; person=`cat name.txt`; echo \"hello \$person\" > build/hello.txt")
}
```

Now run the task twice, you will see that it always exeutes the action. It
doesn't even have incremental build support. This is because Gradle doesn't
know what the inputs and outputs are.

```diff
$ ./gradlew :app:helloFile
- Task :app:helloFile

$ ./gradlew :app:helloFile
- Task :app:helloFile
```

Use the Runtime API to wire the input file which is `app/name.txt` and the
output file which is `app/build/hello.txt`. You can refer to the
[Runtime API docs](https://docs.gradle.org/current/userguide/build_cache.html#using_the_runtime_api)
for help. If you get stuck refer to [the solution](solution/app/build.gradle.kts).

Now when you run the task twice, the second time it will use the output from
the previous run.

```diff
$ ./gradlew :app:helloFile
- Task :app:helloFile

$ ./gradlew :app:helloFile
> Task :app:helloFile UP-TO-DATE
```

Next enable caching for the task the same way as for the previous custom task.
Now remove the output file manually and run the task, you can pass the **-i**
flag if you want. It will put the output in the cache.

```diff
$ rm app/build/hello.txt
$ ./gradlew :app:helloFile
- Task :app:helloFile
```

Repeat, delete the output file and run the task. It will fetch the output from
the cache this time.

```diff
$ rm app/build/hello.txt
$ ./gradlew :app:helloFile
! Task :app:helloFile FROM-CACHE
```
