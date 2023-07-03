package ch.addere.mdg.domain.model.extractor

import ch.addere.mdg.domain.model.Configuration
import ch.addere.mdg.domain.model.Dependency
import ch.addere.mdg.domain.model.Module

private val M1 = Module("m1")
private val M2 = Module("m2")
private val C1 = Configuration("c1")
private val D1 = Dependency(M1, M2, C1)

class DependencyExtractorTest {

//    @Test
//    fun `test kotlin single dependency on one line`() {
//        val dependencyString = "dependencies { c1(project(\":m2\")) }"
//
//        val dep = DependencyExtractor(M1, setOf(M1, M2))
//        val result = dep.findDependenciesInKotlinBuild(dependencyString)
//
//        assertThat(result).containsExactlyInAnyOrder(D1)
//    }
//
//    @Test
//    fun `test kotlin single dependency on multiple line`() {
//        val dependencyString = """
//            dependencies {
//                c1(project(":m2"))
//            }
//        """.trimIndent()
//
//        val dep = DependencyExtractor(M1, setOf(M1, M2))
//        val result = dep.findDependenciesInKotlinBuild(dependencyString)
//
//        assertThat(result).containsExactlyInAnyOrder(D1)
//    }
//
//    @Test
//    fun `test kotlin multiple dependency on multiple line`() {
//        val dependencyString = """
//            dependencies {
//                c1(project(":m2"))
//                c2(project(":m3"))
//            }
//        """.trimIndent()
//
//        val dep = DependencyExtractor(M1, setOf(M1, M2))
//        val result = dep.findDependenciesInKotlinBuild(dependencyString)
//
//        assertThat(result).containsExactlyInAnyOrder(D1)
//    }
}
