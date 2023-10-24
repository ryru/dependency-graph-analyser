package ch.addere.dga.app.domain.model

data class OverviewData(
    val projectName: String,
    val nofModules: Int,
    val nofDependencies: Int,
    val nofUniqueDependencies: Int
)
