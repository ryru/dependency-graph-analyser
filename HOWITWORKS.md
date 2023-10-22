# How the Dependency Graph Analyser DGA Works

_Dependency Graph Analyser (DGA)_ utilises
the [Gradle Tooling API](https://docs.gradle.org/current/userguide/third_party_integration.html#how_to_integrate_with_gradle)
to access Gradle project build information. This project consists of three parts: The CLI
application with the business logic, a Gradle plugin
which is injected into the project to be analysed and the data model of the project. This model is
exported by tHe Gradle plugin and processed by the application.

