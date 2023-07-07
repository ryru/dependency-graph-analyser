package ch.addere.mdg.domain.model

import assertk.assertThat
import assertk.assertions.containsExactlyInAnyOrder
import assertk.assertions.isInstanceOf
import assertk.assertions.isNotNull
import assertk.assertions.isTrue
import assertk.assertions.messageContains
import ch.addere.mdg.domain.model.GradleDsl.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import java.io.File

private val M1 = Module("m1")
private val M2 = Module("m2")
private val M3 = Module("m3")
private val M4 = Module("m4")

class ProjectTest {

    @TempDir
    @JvmField
    var tempFolder: File? = null

    @Test
    fun `test non Gradle project settings file throws`() {
        val nonGradleSettingsFile = getEmptyFile()

        assertk.assertFailure { Project(nonGradleSettingsFile) }
            .isInstanceOf(IllegalArgumentException::class)
            .messageContains("file '$nonGradleSettingsFile' seems not to be a Gradle project settings file")

    }

    @Test
    fun `test folder without Gradle project settings throws`() {
        val nonGradleSettingsFolder = getEmptyDir()

        assertk.assertFailure { Project(nonGradleSettingsFolder) }
            .isInstanceOf(IllegalArgumentException::class)
            .messageContains("path '$nonGradleSettingsFolder' seems not to contain a Gradle project settings file")

    }

    @ParameterizedTest
    @EnumSource(GradleDsl::class)
    fun `test does not throw on valid Gradle settings file`(dsl: GradleDsl) {
        buildProject(dsl)
        val gradleSettingsFile = tempFolder!!.resolve("settings.gradle${dsl.extension}")

        val project = Project(gradleSettingsFile)

        assertThat(project).isNotNull()
        assertThat(project.settingsFile.getModules())
            .containsExactlyInAnyOrder(M1, M2, M3, M4)
        assertThat(project.buildFiles)
            .transform { buildFile -> buildFile.map { it.origin } }
            .containsExactlyInAnyOrder(M1, M2, M3, M4)
        assertThat(project.buildFiles)
            .transform { buildFile -> buildFile.map { it.buildFile } }
            .isNotNull()
    }

    @ParameterizedTest
    @EnumSource(GradleDsl::class)
    fun `test does not throw on root folder with valid Gradle settings file`(dsl: GradleDsl) {
        buildProject(dsl)
        val rootFolder = tempFolder!!

        val project = Project(rootFolder)

        assertThat(project).isNotNull()
        assertThat(project.settingsFile.getModules())
            .containsExactlyInAnyOrder(M1, M2, M3, M4)
        assertThat(project.buildFiles)
            .transform { buildFile -> buildFile.map { it.origin } }
            .containsExactlyInAnyOrder(M1, M2, M3, M4)
        assertThat(project.buildFiles)
            .transform { buildFile -> buildFile.map { it.buildFile } }
            .isNotNull()
    }

    private fun getEmptyFile(): File {
        val someFile = tempFolder!!.resolve("some-file.txt")
        someFile.writeText("some text")

        assertThat(someFile.isFile).isTrue()
        return someFile
    }

    private fun getEmptyDir(): File {
        val someDir = tempFolder!!
        assertThat(someDir.isDirectory).isTrue()
        return someDir
    }

    private fun buildProject(dsl: GradleDsl) {
        createModuleDirectories()
        when (dsl) {
            GROOVY -> createGroovyProject()
            KOTLIN -> createKotlinProject()
        }
    }

    private fun createModuleDirectories() {
        tempFolder!!.resolve(M1.name).mkdir()
        tempFolder!!.resolve(M2.name).mkdir()
        tempFolder!!.resolve(M3.name).mkdir()
        tempFolder!!.resolve(M4.name).mkdir()
    }

    private fun createGroovyProject() {
        val groovySettings = tempFolder!!.resolve("settings.gradle")
        createGroovySettingsFile(groovySettings)

        tempFolder!!.resolve("${M1.name}/build.gradle").createNewFile()
        val m2 = tempFolder!!.resolve("${M2.name}/build.gradle")
        m2.writeText(
            """
                dependencies {
                    api project(':m1') 
                }
        """.trimIndent()
        )
        val m3 = tempFolder!!.resolve("${M3.name}/build.gradle")
        m3.writeText(
            """
                dependencies {
                    api project(':m1') 
                    implementation project(':m2') 
                }
        """.trimIndent()
        )
        val m4 = tempFolder!!.resolve("${M4.name}/build.gradle")
        m4.writeText(
            """
                dependencies {
                    api project(':m1')
                    implementation project(":m2') 
                    implementation project(':m3') 
                }
        """.trimIndent()
        )
    }

    private fun createKotlinProject() {
        val kotlinSettings = tempFolder!!.resolve("settings.gradle.kts")
        createKotlinSettingsFile(kotlinSettings)

        tempFolder!!.resolve("${M1.name}/build.gradle.kts").createNewFile()
        val m2 = tempFolder!!.resolve("${M2.name}/build.gradle.kts")
        m2.writeText(
            """
                dependencies {
                    api(project(":m1")) 
                }
        """.trimIndent()
        )
        val m3 = tempFolder!!.resolve("${M3.name}/build.gradle.kts")
        m3.writeText(
            """
                dependencies {
                    api(project(":m1")) 
                    implementation(project(":m2")) 
                }
        """.trimIndent()
        )
        val m4 = tempFolder!!.resolve("${M4.name}/build.gradle.kts")
        m4.writeText(
            """
                dependencies {
                    api(project(":m1")) 
                    implementation(project(":m2")) 
                    implementation(project(":m3")) 
                }
        """.trimIndent()
        )
    }

    private fun createGroovySettingsFile(settingsFile: File) {
        settingsFile.writeText(
            """
                rootProject.name = 'test'
                include('m1', 'm2', 'm3', 'm4')
            """.trimIndent()
        )
    }

    private fun createKotlinSettingsFile(settingsFile: File) {
        settingsFile.writeText(
            """
                rootProject.name = "test"
                include("m1", "m2", "m3", "m4")
            """.trimIndent()
        )
    }
}
