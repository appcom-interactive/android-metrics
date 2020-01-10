/**
 * Created by appcom interactive GmbH on 2019-08-24.
 * Copyright © 2019 appcom interactive GmbH. All rights reserved.
 */

package de.nanogiants.gradle

import com.google.gson.GsonBuilder
import de.nanogiants.gradle.entities.write.MetricEntity
import de.nanogiants.gradle.models.DexCountMetric
import de.nanogiants.gradle.models.JacocoMetric
import de.nanogiants.gradle.models.MetricSummary
import de.nanogiants.gradle.models.TestMetric
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import java.io.File

open class MetricsTask : DefaultTask() {

  /**
   * TODO: 2020-01-10 missing metrics
   * dependenciesUpdates
   * detekt
   * ktlint
   */

  private val ignoreModules = listOf("base_style", "base")

  @TaskAction
  fun run() {
    println("aggregate metrics")
    val metricSummary = MetricSummary(mutableMapOf(), mutableMapOf())

    println("Find metrics for:")
    project.subprojects.filterNot { it.name.contains("-test") || ignoreModules.contains(it.name) }.forEach {
      val modulePath = it.projectDir.path
      val moduleName = it.name
      val isAndroidModule = it.hasProperty("android")
      val availableMetrics = mutableMapOf<String, MetricEntity>()

      println("- $moduleName")
      if (isAndroidModule) {
        listOf(DexCountMetric(), JacocoMetric(it, true), TestMetric(it, true))
      } else {
        listOf(JacocoMetric(it, false), TestMetric(it, false))
      }.apply {
        forEach { metric ->
          metric.moduleDir = modulePath

          if (metric.exists()) {
            when (metric) {
              is JacocoMetric -> {
                availableMetrics[JacocoMetric.NAME] = metric.map()
              }
              is DexCountMetric -> {
                availableMetrics[DexCountMetric.NAME] = metric.map()
              }
              is TestMetric -> {
                availableMetrics[metric.name()] = metric.map()
              }
            }
          }
        }
      }

      metricSummary.data[moduleName] = availableMetrics
    }

    metricSummary.generateProjectData()

    val gson = GsonBuilder().setPrettyPrinting().create()
    val jsonString: String = gson.toJson(metricSummary)
    val file = File("${project.buildDir}/metric.json")
    file.writeText(jsonString)
  }
}