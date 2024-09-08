package ch.addere.dga.importer.infrastructure

import ch.addere.dga.connectormodel.DependencyModel
import ch.addere.dga.importer.domain.service.GradleConnectorService
import org.gradle.tooling.GradleConnector
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.absolutePathString
import kotlin.io.path.createTempDirectory

class GradleConnectorServiceImpl : GradleConnectorService {

    private val scriptFileName = "init.gradle.kts"
    private val scriptParentDir = "dga"

    override fun connectToGradle(gradleProjectFolder: File): DependencyModel {

        val pathToInitScript = createInitScript()

        val connector = GradleConnector.newConnector()
        connector.forProjectDirectory(gradleProjectFolder)

        val dependencyModel: DependencyModel = connector.connect().use { con ->
            val customModelBuilder = con.model(DependencyModel::class.java)
            customModelBuilder.withArguments("--init-script", pathToInitScript.absolutePathString())
            customModelBuilder.get()
        }

        deleteInitScript(pathToInitScript)
        return dependencyModel
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
