/**
 * Created by appcom interactive GmbH on 2019-08-24.
 * Copyright © 2019 appcom interactive GmbH. All rights reserved.
 */

package de.nanogiants.gradle.metrics

import com.google.gson.GsonBuilder
import de.nanogiants.gradle.entities.write.DexCountOutEntity
import de.nanogiants.gradle.entities.write.JacocoOutEntity
import de.nanogiants.gradle.entities.write.MetricEntity
import de.nanogiants.gradle.entities.write.TestOutEntity
import de.nanogiants.gradle.entities.write.ValueEntity
import org.gradle.api.Project

data class MetricSummary(
  val data: MutableMap<String, Map<String, MetricEntity>>,
  val project: MutableMap<String, MetricEntity>
) {

  fun generateProjectData() {
    val jacocoInstruction = ValueEntity(0, 0, 0, 0)
    val jacocoBranch = ValueEntity(0, 0, 0, 0)
    var dexCountEntity = DexCountOutEntity(0, 0)
    var testOutEntity = TestOutEntity(0, 0, 0, 0, 0.0)

    data.forEach { (_, metric) ->
      metric.forEach { (_, data) ->
        when (data) {
          is JacocoOutEntity -> {
            jacocoInstruction += data.instruction
            jacocoBranch += data.branch
          }
          is DexCountOutEntity -> {
            dexCountEntity = DexCountOutEntity(data.methods, data.fields)
          }
          is TestOutEntity -> {
            testOutEntity += data
          }
        }
      }
    }

    project[JacocoMetric.NAME] = JacocoOutEntity("all", jacocoInstruction, jacocoBranch)
    project[DexCountMetric.NAME] = dexCountEntity
    project[TestMetric.NAME] = testOutEntity
  }

  fun writeFile(project: Project) {
    generateProjectData()
    val gson = GsonBuilder().setPrettyPrinting().create()
    val jsonString: String = gson.toJson(this)
    project.file("${project.buildDir}/metric.json").writeText(jsonString)
  }
}