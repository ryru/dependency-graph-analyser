package ch.addere.dga.core.domain.service

import ch.addere.dga.core.domain.model.Configuration

interface ConfigurationService {

    /**
     * Returns all modules that matches a partial module name.
     * A partial module name contains at least on '*' asterisk.
     */
    fun resolvePartialConfigurationName(configuration: Configuration): Set<Configuration>
}
