/**
 * Created by appcom interactive GmbH on 2020-01-10.
 * Copyright © 2020 appcom interactive GmbH. All rights reserved.
 */

package de.nanogiants.gradle.models.metrics

import de.nanogiants.gradle.entities.write.TestOutEntity
import de.nanogiants.gradle.mappers.TestMapper
import de.nanogiants.gradle.models.base.Metric
import org.gradle.api.Project
import java.io.File

class TestMetric(val project: Project, private val isAndroidModule: Boolean) : Metric(
  NAME, "/build/test-results"
) {

  companion object {

    const val NAME = "test"
  }

  override fun file(): File = fileDirectory()

  override fun map(): TestOutEntity {
    val testDirectory = if (isAndroidModule) {
      val testTask =
        project.tasks.filter { it.name.startsWith("test") && it.name.contains("DebugUnitTest") }.toTypedArray()
          .first()
      val name = testTask.name
      File(file(), name)
    } else {
      File(file(), "test")
    }

    var testOutEntity = TestOutEntity(0, 0, 0, 0, 0.0)

    if (testDirectory.exists()) {
      val files = testDirectory.walkTopDown().filter {
        with(it.name) {
          startsWith("TEST-") && endsWith(".xml")
        }
      }.toList()

      files.forEach {
        val outEntity = TestMapper().toModel(it).run {
          TestOutEntity(
            tests = tests.toInt(),
            skipped = skipped.toInt(),
            failed = failures.toInt(),
            errors = errors.toInt(),
            durationInSeconds = time.toDouble()
          )
        }
        testOutEntity += outEntity
      }
    }

    return testOutEntity
  }
}