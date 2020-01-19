/**
 * Created by appcom interactive GmbH on 2019-08-24.
 * Copyright © 2019 appcom interactive GmbH. All rights reserved.
 */

package de.nanogiants.gradle.metrics

import de.nanogiants.gradle.base.Metric
import de.nanogiants.gradle.entities.write.JacocoOutEntity
import de.nanogiants.gradle.entities.write.ValueEntity
import de.nanogiants.gradle.mappers.JacocoMapper
import org.gradle.api.Project
import java.io.File

class JacocoMetric(val project: Project, val isAndroidModule: Boolean) : Metric(
  NAME, "/build/reports/jacoco"
) {

  companion object {

    const val NAME = "jacoco"

    object Types {

      const val INSTRUCTION = "INSTRUCTION"
      const val BRANCH = "BRANCH"
    }
  }

  override fun file(): File = fileDirectory()

  override fun map(): JacocoOutEntity {
    val jacocoFile = if (isAndroidModule) {
      val testTask =
        project.tasks.filter { it.name.startsWith("test") && it.name.contains("DebugUnitTest") }.toTypedArray()
          .first()
      val name = testTask.name.capitalize()

      File(file(), "jacoco${name}Report/jacoco${name}Report.xml")
    } else {
      File(file(), "test/jacocoTestReport.xml")
    }

    var instruction = ValueEntity(0, 0, 0, 0)
    var branch = ValueEntity(0, 0, 0, 0)

    if (jacocoFile.exists()) {
      val entity = JacocoMapper().toModel(jacocoFile)
      with(entity) {
        counters.forEach {
          if (it.type == Types.INSTRUCTION) {
            instruction = ValueEntity(it.covered + it.missed, it.covered, it.missed)
          }
          if (it.type == Types.BRANCH) {
            branch = ValueEntity(it.covered + it.missed, it.covered, it.missed)
          }
        }
      }
      return JacocoOutEntity(entity.name, instruction, branch)
    }
    return JacocoOutEntity(NAME, instruction, branch)
  }
}