/**
 * Created by appcom interactive GmbH on 14.01.20.
 * Copyright © 2020 appcom interactive GmbH. All rights reserved.
 */
package de.nanogiants.gradle.metrics

import de.nanogiants.gradle.base.Metric
import de.nanogiants.gradle.entities.write.TestOutEntity
import de.nanogiants.gradle.mappers.TestMapper
import java.io.File

class AndroidTestMetric : Metric(NAME, "/build/outputs/androidTest-results/connected/flavors") {

  companion object {

    const val NAME = "androidTest"
  }

  override fun file(): File = fileDirectory()

  override fun map(): TestOutEntity {
    var testOutEntity = TestOutEntity(0, 0, 0, 0, 0.0)
    val testDir = file().walkBottomUp().filter { it.isDirectory }.first().name
    val testDirectory = File(file(), testDir)

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
          durationInSeconds = (time.toDouble() / 1000)
        )
      }
      testOutEntity += outEntity
    }

    return testOutEntity
  }
}