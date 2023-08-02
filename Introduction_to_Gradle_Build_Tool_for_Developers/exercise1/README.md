## Introduction to Gradle Build Tool for Developers - Exercise 1

<p align="left">
<img width="10%" height="10%" src="https://user-images.githubusercontent.com/120980/174325546-8558160b-7f16-42cb-af0f-511849f22ebc.png">
</p>

This is a hands-on exercise to go along with the
**Introduction to Gradle Build Tool for Developers** training module. In this exercise
you will get familiar with the following topics:

* Initializing new Gradle project
* Opening a Gradle project in IntelliJ IDE
* Explore Gradle files

---
### Prerequisites

* JDK 1.8+ and Gradle build tool installed
  * https://gradle.org/install/
* A good editor such as IntelliJ IDE
  * https://www.jetbrains.com/idea/download/

---
### Initialize new Gradle Project

Open a terminal and create a folder called `lab`, then go inside it. Initialize a new Gradle
project by running the `gradle init` command and enter the following responses
for the questions:

* Type of project to generate: application
* Language: Java
* Split functionality across multiple subprojects?: no
* Build script DSL: Kotlin
* Test framework: JUnit Jupiter
* Project name: lab
* Source package: com.gradle.lab
* Java version: Execute `java -version` to find version
* Use new APIs?: no

```
$ gradle init

Select type of project to generate:
  1: basic
  2: application
  3: library
  4: Gradle plugin
Enter selection (default: basic) [1..4] 2

Select implementation language:
  1: C++
  2: Groovy
  3: Java
  4: Kotlin
  5: Scala
  6: Swift
Enter selection (default: Java) [1..6] 

Generate multiple subprojects for application? (default: no) [yes, no] 
Select build script DSL:
  1: Groovy
  2: Kotlin
Enter selection (default: Groovy) [1..2] 2

Select test framework:
  1: JUnit 4
  2: TestNG
  3: Spock
  4: JUnit Jupiter
Enter selection (default: JUnit Jupiter) [1..4] 

Project name (default: lab): 
Source package (default: lab): com.gradle.lab
Enter target version of Java (min. 7) (default: 11):
Generate build using new APIs and behavior (some features may change in the next minor release)? (default: no) [yes, no]
```

Ensure the generated files in the solution directory look as follows:

```
$ ls -ltr
total 32
drwxr-xr-x  3 adayal  staff    96 May 12 06:35 gradle
-rwxr-xr-x  1 adayal  staff  8070 May 12 06:35 gradlew
-rw-r--r--  1 adayal  staff  2763 May 12 06:35 gradlew.bat
-rw-r--r--  1 adayal  staff   372 May 12 06:38 settings.gradle.kts
drwxr-xr-x  4 adayal  staff   128 May 12 06:38 app
```

---
### Open Project in Editor and Explore Files

Launch your Editor and open the `lab` folder.

Look at the following files:
* `settings.gradle.kts`
* `app/build.gradle.kts`
* `gradle/wrapper/gradle-wrapper.properties`

In the `app/build.gradle.kts` file hover over the `mavenCentral()` word and
notice the help tooltip popup.

<p align="center">
<img width="60%" height="60%" src="https://user-images.githubusercontent.com/120980/174327421-da264871-bad8-4d36-bd23-e156eeeebbcb.png">
</p>

Notice sample sources and tests have been created:
* `app/src/main/java/com/gradle/lab/App.java`
* `app/src/test/java/com/gradle/lab/AppTest.java`

<p align="center">
<img width="60%" height="60%" src="https://user-images.githubusercontent.com/120980/174327548-d03ad27f-32c7-4e44-a111-04de4cc21fa3.png">
</p>

---
### Gradle Wrapper Distributions

Gradle wrapper distributions are stored in the `~/.gradle/wrapper/dists` folder
in your home directory.

**Note**: You may first have to execute the wrapper script to trigger a download of
Gradle to the distributions folder by running `./gradlew :app:run`.

```
$ ls -ltr ~/.gradle/wrapper/dists/
total 0
drwxr-xr-x  3 adayal  staff  96 May 12 07:02 gradle-7.4.2-bin
```

<p align="right">
<a href="https://github.com/gradle/build-tool-training-exercises/tree/main/Introduction_to_Gradle_Build_Tool_for_Developers/exercise2">Exercise 2 >></a>
</p>
