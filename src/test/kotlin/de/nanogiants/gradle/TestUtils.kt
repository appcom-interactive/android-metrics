/**
 * Created by appcom interactive GmbH on 14.01.20.
 * Copyright © 2020 appcom interactive GmbH. All rights reserved.
 */
package de.nanogiants.gradle

import org.gradle.testfixtures.ProjectBuilder

internal fun project() = ProjectBuilder.builder().build().also { project ->
  //  project.pluginManager.apply("de.nanogiants.gradle.android-metrics")
  project.pluginManager.apply(AndroidMetricsPlugin::class.java)
}