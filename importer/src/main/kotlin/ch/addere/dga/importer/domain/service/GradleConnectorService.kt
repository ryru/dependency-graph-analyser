package ch.addere.dga.importer.domain.service

import ch.addere.dga.dependencymodel.DependencyModel
import org.gradle.tooling.GradleConnector
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.absolutePathString
import kotlin.io.path.createTempDirectory

class GradleConnectorService {

    private val scriptFileName = "init.gradle.kts"
    private val scriptParentDir = "dga"


    fun connectToGradle(gradleProjectFolder: File): DependencyModel {

        val pathToInitScript = createInitScript()

        val connector = GradleConnector.newConnector()
        connector.forProjectDirectory(gradleProjectFolder)

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
        val scriptDir = Files.createDirectories(Path("$osTempDir/$scriptParentDir/"))
        val initScript = File("$scriptDir/$scriptFileName")
        initScript.writeText(javaClass.getResource("/$scriptFileName")!!.readText())
        return initScript.toPath()
    }

    private fun deleteInitScript(pathToInitScript: Path) {
        if (pathToInitScript.endsWith(scriptFileName)) {
            Files.delete(pathToInitScript)
        }
        if (pathToInitScript.parent.endsWith(scriptParentDir)) {
            Files.delete(pathToInitScript.parent)
        }
    }
}
