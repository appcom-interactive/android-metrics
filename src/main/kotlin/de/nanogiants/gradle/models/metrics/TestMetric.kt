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

class TestMetric(private val project: Project, private val isAndroidModule: Boolean) :
  Metric(NAME, "/build/test-results") {

  companion object {

    const val NAME = "test"
  }

  override fun file(): File = fileDirectory()

  override fun map(): TestOutEntity {
    val testDirectory = if (isAndroidModule) {
      val testTask =
        project.tasks.filter { it.name.startsWith("test") && it.name.contains("DebugUnitTest") }.toTypedArray()
          .first {
            File(file(), it.name).exists()
          }
      File(file(), testTask.name)
    } else {
      File(file(), "test")
    }

    println("map $testDirectory")

    var testOutEntity = TestOutEntity(0, 0, 0, 0, 0.0)

    if (testDirectory.exists()) {
      val files = testDirectory.walkTopDown().filter {
        with(it.name) {
          startsWith("TEST-") && endsWith(".xml")
        }
      }.toList()

      files.forEach {
        println("read ${it.name}")
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