tasks.register<GenerateCode>("genAiCode") {
    apiToken.set(System.getenv("OPENAI_TOKEN"))
    prompt.set("Create a java class called Message whose constructor takes a string and stores" +
            " it as a private variable called text. It should have a method called getTextWithTime" +
            " that uses org.joda.time.LocalTime to return the current time as a string. It should have a method" +
            " called hasWhitespace that returns true if the variable text has a whitespace character." +
            " It should have a method called getTextHash that returns a hash of the variable text." +
            " Include the import statements. It should be in a package called com.gradle.lab")
    generatedSourcesDir.set(layout.buildDirectory.dir("generated/sources/mlCode"))
}