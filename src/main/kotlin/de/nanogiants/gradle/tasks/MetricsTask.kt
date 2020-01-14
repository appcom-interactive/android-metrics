/**
 * Created by appcom interactive GmbH on 2019-08-24.
 * Copyright © 2019 appcom interactive GmbH. All rights reserved.
 */

package de.nanogiants.gradle.tasks

import com.google.gson.GsonBuilder
import de.nanogiants.gradle.entities.write.MetricEntity
import de.nanogiants.gradle.extensions.MetricsExtension
import de.nanogiants.gradle.models.MetricSummary
import de.nanogiants.gradle.models.metrics.AndroidTestMetric
import de.nanogiants.gradle.models.metrics.DexCountMetric
import de.nanogiants.gradle.models.metrics.JacocoMetric
import de.nanogiants.gradle.models.metrics.TestMetric
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

open class MetricsTask : DefaultTask() {

  /**
   * TODO: 2020-01-10 missing metrics
   * dependenciesUpdates
   * detekt
   * ktlint
   */

  @TaskAction
  fun run() {
    group = "other"
    description = "Retrieve all metrics from project and extract to metric.json"

    println("aggregate metrics")

    val metricSummary = MetricSummary(mutableMapOf(), mutableMapOf())
    val ignoreModules = project.extensions.getByType(MetricsExtension::class.java).ignoreModules

    println("Find metrics for:")
    project.subprojects.filterNot { it.name.contains("-test") || ignoreModules.contains(it.name) }.forEach {
      val modulePath = it.projectDir.path
      val moduleName = it.name
      val isAndroidModule = it.hasProperty("android")
      val availableMetrics = mutableMapOf<String, MetricEntity>()

      println("- $moduleName")

      if (isAndroidModule) {
        listOf(DexCountMetric(), JacocoMetric(it, true), TestMetric(it, true), AndroidTestMetric())
      } else {
        listOf(JacocoMetric(it, false), TestMetric(it, false))
      }.apply {
        forEach { metric ->
          metric.moduleDir = modulePath
          if (metric.exists()) availableMetrics[metric.name()] = metric.map()
        }
      }

      metricSummary.data[moduleName] = availableMetrics
    }

    metricSummary.generateProjectData()

    val gson = GsonBuilder().setPrettyPrinting().create()
    val jsonString: String = gson.toJson(metricSummary)
    project.file("${project.buildDir}/metric.json").writeText(jsonString)
  }
}