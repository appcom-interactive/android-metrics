/**
 * Created by appcom interactive GmbH on 10.01.20.
 * Copyright © 2020 appcom interactive GmbH. All rights reserved.
 */
package de.nanogiants.gradle.extensions

import org.gradle.api.Project

open class MetricsExtension {

  companion object {

    const val NAME = "metrics"
  }

  var ignoreModules = emptyList<String>()
  var groupModules = emptyList<String>()
}

internal fun Project.metrics(): MetricsExtension =
  extensions.getByName(MetricsExtension.NAME) as? MetricsExtension
    ?: throw IllegalStateException("${MetricsExtension.NAME} is not of the correct type")