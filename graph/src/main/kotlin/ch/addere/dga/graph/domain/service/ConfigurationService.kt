package ch.addere.dga.graph.domain.service

import ch.addere.dga.graph.domain.model.Configuration

interface ConfigurationService {

    /**
     * Returns all modules that matches a partial module name.
     * A partial module name contains at least on '*' asterisk.
     */
    fun resolvePartialConfigurationName(configuration: Configuration): Set<Configuration>
}
