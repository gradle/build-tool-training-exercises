import com.theokanning.openai.completion.CompletionChoice
import com.theokanning.openai.completion.CompletionRequest
import com.theokanning.openai.service.OpenAiService
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import java.util.function.Consumer
import java.io.File

abstract class GenerateCode : DefaultTask() {

    @get:Input
    abstract val apiToken : Property<String>

    @get:Input
    abstract val prompt : Property<String>

    @get:OutputDirectory
    abstract val generatedSourcesDir: DirectoryProperty

    @TaskAction
    fun generateCode() {
        val outDir = File(generatedSourcesDir.get().asFile, "com/gradle/lab")
        outDir.mkdirs()
        val out = File(outDir, "Message.java")

        val service = OpenAiService(apiToken.get())

        val completionRequest = CompletionRequest.builder()
            .model("text-davinci-003")
            .prompt(prompt.get())
            .echo(false)
            .maxTokens(500)
            .temperature(1.0)
            .topP(1.0)
            .n(1)
            .build()

        service.createCompletion(completionRequest).choices.forEach(Consumer { x: CompletionChoice? ->
            if (x != null) {
                out.writeText(x.text)
            }
        })
    }
}