/**
 * Created by appcom interactive GmbH on 10.01.20.
 * Copyright © 2020 appcom interactive GmbH. All rights reserved.
 */
package de.nanogiants.gradle

import org.gradle.api.Project

open class MetricsExtension {

  var ignoreModules = emptyList<String>()
}

internal fun Project.metrics(): MetricsExtension =
  extensions.getByName(Constants.EXTENSION_NAME) as? MetricsExtension
    ?: throw IllegalStateException("${Constants.EXTENSION_NAME} is not of the correct type")