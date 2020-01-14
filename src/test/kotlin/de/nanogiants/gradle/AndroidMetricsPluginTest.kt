/**
 * Created by appcom interactive GmbH on 10.01.20.
 * Copyright © 2020 appcom interactive GmbH. All rights reserved.
 */
package de.nanogiants.gradle

import de.nanogiants.gradle.extensions.MetricsExtension
import de.nanogiants.gradle.extensions.metrics
import de.nanogiants.gradle.tasks.MetricsTask
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

internal class AndroidMetricsPluginTest {

  @Test
  fun `apply plugin and verify availability`() {
    val project = project()
    assertDoesNotThrow {
      project.plugins.getPlugin(AndroidMetricsPlugin::class.java)
    }
  }

  @Test
  fun `apply plugin and get extensions`() {
    val project = project()
    assertDoesNotThrow {
      project.metrics()
    }
  }

  @Test
  fun `apply plugin and get single metrics task`() {
    val project = project()
    assert(project.tasks.withType(MetricsTask::class.java).size == 1)
  }

  @Test
  fun `check extension ignore modules not empty`() {
    val project = project()
    project.extensions.getByType(MetricsExtension::class.java).ignoreModules = listOf("base", "base_style")

    assert(project.metrics().ignoreModules.isNotEmpty())
  }
}