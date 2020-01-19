/**
 * Created by appcom interactive GmbH on 2020-01-10.
 * Copyright © 2020 appcom interactive GmbH. All rights reserved.
 */

package de.nanogiants.gradle.metrics

import de.nanogiants.gradle.base.Metric
import de.nanogiants.gradle.entities.write.TestOutEntity
import de.nanogiants.gradle.mappers.TestMapper
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import java.io.File

class TestMetric(private val project: Project, private val isAndroidModule: Boolean) :
  Metric(NAME, "/build/test-results") {

  companion object {

    const val NAME = "test"
  }

  override fun file(): File = fileDirectory()

  override fun map(): TestOutEntity {
    var testOutEntity = TestOutEntity(0, 0, 0, 0, 0.0)
    val testDirectory = if (isAndroidModule) {
      val testTask =
        project.tasks.filterIsInstance(Test::class.java).toTypedArray().first { File(file(), it.name).exists() }
      File(file(), testTask.name)
    } else {
      File(file(), "test")
    }

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