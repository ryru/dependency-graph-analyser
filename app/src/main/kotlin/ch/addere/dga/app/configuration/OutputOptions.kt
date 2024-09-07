package ch.addere.dga.app.configuration

sealed class OutputOptions {
    data object OutputOptionOverviewOnly : OutputOptions()
    data object OutputOptionModules : OutputOptions()
    data object OutputOptionConfigurations : OutputOptions()
    data object OutputOptionMermaid : OutputOptions()
}
