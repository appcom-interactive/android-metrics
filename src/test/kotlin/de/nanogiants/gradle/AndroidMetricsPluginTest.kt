/**
 * Created by appcom interactive GmbH on 10.01.20.
 * Copyright © 2020 appcom interactive GmbH. All rights reserved.
 */
package de.nanogiants.gradle

import org.gradle.testfixtures.ProjectBuilder
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

internal class AndroidMetricsPluginTest {

  @Test
  fun `apply plugin without error`() {
    val project = ProjectBuilder.builder().build()
    project.pluginManager.apply("de.nanogiants.gradle.android-metrics")

    assertDoesNotThrow {
      project.plugins.getPlugin(AndroidMetricsPlugin::class.java)
    }
  }
}