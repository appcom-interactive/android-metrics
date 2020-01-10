/**
 * Created by appcom interactive GmbH on 10.01.20.
 * Copyright © 2020 appcom interactive GmbH. All rights reserved.
 */
package de.nanogiants.gradle

import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import java.nio.file.Files

internal class MetricsTaskTest {

  @Test
  @Disabled
  fun `apply plugin and run metrics task`() {
    val projectDir = Files.createTempDirectory("")
    val buildScript = projectDir.resolve("build.gradle").toFile()

    buildScript.writeText(
      """
        plugins {
          id 'de.nanogiants.gradle.android-metrics'
        }

        tasks.create('metricsTest') {
          dependsOn 'androidMetrics'
        }
      """.trimIndent()
    )

    val result = GradleRunner.create()
      .withProjectDir(projectDir.toFile())
      .withPluginClasspath()
      .withArguments("metricsTest", "--info", "--stacktrace")
      .build()

    assert(result.task(Constants.TASK_NAME)?.outcome == TaskOutcome.SUCCESS)
    assert(result.output.contains("aggregate metrics"))
  }
}