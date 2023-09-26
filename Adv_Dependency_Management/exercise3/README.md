## Advanced Dependency Management - Exercise 3

<p align="left">
<img width="10%" height="10%" src="https://user-images.githubusercontent.com/120980/174325546-8558160b-7f16-42cb-af0f-511849f22ebc.png">
</p>

This is a hands-on exercise to go along with the
**Advanced Dependency Management** training module. In this exercise
you will go over the following:

* Generating dependency checksum metadata
* Trust certain dependency artifacts
* Manually add dependency checksum

---
### Prerequisites

* Completed exercise 2

---
### Generating dependency checksum metadata

Open the project in the `lab` folder. Open `app/build.gradle.kts`
and notice the dependencies coming from the version catalog.

Now generate the verification metadata file that will enable
checksum verification for the dependencies, use `sha1`.

```bash
./gradlew --write-verification-metadata sha1
```

Inspect the generated file `gradle/verification-metadata.xml`.  Execute the
test task and verify things are working:

```bash
./gradlew :app:test
```

Now change one of the `sha1` checksums in the metadata file and execute the test
task:

```bash
./gradlew :app:test
```

Notice the error message. Undo the changes to the `sha1` checksum.

---
### Trust certain dependency artifacts

Run the `sync` task in the editor. Notice the error messages.

Add the following to the metadata file:

```xml
<configuration>
   <trusted-artifacts>
      <trust file=".*-javadoc[.]jar" regex="true"/>
      <trust file=".*-sources[.]jar" regex="true"/>
      <trust file="gradle-[0-9.]+-src.zip" regex="true"/>
      <trust file="groovy-[a-z]*-?[0-9.]+.pom" regex="true"/>
   </trusted-artifacts>
   <verify-metadata>true</verify-metadata>
   <verify-signatures>false</verify-signatures>
</configuration>
```

Run the `sync` task in the editor. There should be no errors now.

---
### Manually add dependency checksum

Add the following dependency to the `app` subproject:

```kotlin
implementation(libs.gson)
```

Execute the test task and notice the errors:

```bash
./gradlew :app:test
```

Manually add entries in the verification file for the relevant files. You can
refer to the outline given below.

*Hint*: Get the `sha1` values from the following locations:
* [gson](https://repo1.maven.org/maven2/com/google/code/gson/gson/2.10.1/)
* [gson-parent](https://repo1.maven.org/maven2/com/google/code/gson/gson-parent/2.10.1/)

```xml
<component group="com.google.code.gson" name="gson" version="2.10.1">
   <artifact name="gson-2.10.1.jar">
      <sha1 value="..." origin="Manual"/>
   </artifact>
</component>
<component group="com.google.code.gson" name="gson" version="2.10.1">
   <artifact name="gson-2.10.1.pom">
      <sha1 value="..." origin="Manual"/>
   </artifact>
</component>
<component group="com.google.code.gson" name="gson-parent" version="2.10.1">
   <artifact name="gson-parent-2.10.1.pom">
      <sha1 value="..." origin="Manual"/>
   </artifact>
</component>
```

Execute the test task, there should be no errors:

```bash
./gradlew :app:test
```

<p align="right">
<a href="https://github.com/gradle/build-tool-training-exercises/tree/main/Adv_Dependency_Management/exercise4">Exercise 4 >></a>
</p>
