/**
 * Created by appcom interactive GmbH on 2019-08-24.
 * Copyright © 2019 appcom interactive GmbH. All rights reserved.
 */

package de.nanogiants.gradle.models

import de.nanogiants.gradle.entities.write.*
import de.nanogiants.gradle.models.metrics.DexCountMetric
import de.nanogiants.gradle.models.metrics.JacocoMetric
import de.nanogiants.gradle.models.metrics.TestMetric

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
}