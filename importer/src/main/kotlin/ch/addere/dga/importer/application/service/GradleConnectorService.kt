package ch.addere.dga.importer.application.service

import ch.addere.dga.dependencymodel.DependencyModel
import org.gradle.tooling.GradleConnector
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.absolutePathString
import kotlin.io.path.createTempDirectory

const val SCRIPT_FILE_NAME = "init.gradle.kts"
const val SCRIPT_PARENT_DIR = "dga"

class GradleConnectorService(val gradleFolder: File) {

    fun connectToGradle(): DependencyModel {

        val pathToInitScript = createInitScript()

        val connector = GradleConnector.newConnector()
        connector.forProjectDirectory(gradleFolder)

        var model: DependencyModel?

        connector.connect().use { con ->

            val customModelBuilder = con.model(DependencyModel::class.java)
            customModelBuilder.withArguments("--init-script", pathToInitScript.absolutePathString())

            model = customModelBuilder.get()
        }

        deleteInitScript(pathToInitScript)
        return model!!
    }

    private fun createInitScript(): Path {
        val osTempDir = createTempDirectory().parent
        val scriptDir = Files.createDirectories(Path("$osTempDir/$SCRIPT_PARENT_DIR/"))
        val initScript = File("$scriptDir/$SCRIPT_FILE_NAME")
        initScript.writeText(javaClass.getResource("/$SCRIPT_FILE_NAME")!!.readText())
        return initScript.toPath()
    }

    private fun deleteInitScript(pathToInitScript: Path) {
        if (pathToInitScript.endsWith(SCRIPT_FILE_NAME)) {
            Files.delete(pathToInitScript)
        }
        if (pathToInitScript.parent.endsWith(SCRIPT_PARENT_DIR)) {
            Files.delete(pathToInitScript.parent)
        }
    }
}
