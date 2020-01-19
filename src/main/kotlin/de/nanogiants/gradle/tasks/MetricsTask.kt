/**
 * Created by appcom interactive GmbH on 2019-08-24.
 * Copyright © 2019 appcom interactive GmbH. All rights reserved.
 */

package de.nanogiants.gradle.tasks

import com.android.build.gradle.AppPlugin
import com.android.build.gradle.LibraryPlugin
import de.nanogiants.gradle.Module
import de.nanogiants.gradle.entities.write.MetricEntity
import de.nanogiants.gradle.extensions.MetricsExtension
import de.nanogiants.gradle.metrics.AndroidTestMetric
import de.nanogiants.gradle.metrics.DexCountMetric
import de.nanogiants.gradle.metrics.JacocoMetric
import de.nanogiants.gradle.metrics.MetricSummary
import de.nanogiants.gradle.metrics.TestMetric
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
    group = "help"
    description = "Retrieve all metrics from project and extract to metric.json"

    val metricSummary = MetricSummary(mutableMapOf(), mutableMapOf())
    val ignoreModules = project.extensions.getByType(MetricsExtension::class.java).ignoreModules

    println("Find metrics for:")
    project.subprojects.filterNot { it.name.contains("-test") || ignoreModules.contains(it.name) }.forEach {
      val availableMetrics = mutableMapOf<String, MetricEntity>()
      val modulePath = it.projectDir.path
      val moduleName = it.name
      val moduleType = with(it.plugins) {
        when {
          hasPlugin(AppPlugin::class.java) -> Module.APP
          hasPlugin(LibraryPlugin::class.java) -> Module.LIBRARY
          else -> Module.KOTLIN
        }
      }

      println("- $moduleName")

      val metricList = when (moduleType) {
        Module.APP -> listOf(DexCountMetric(it), JacocoMetric(it, true), TestMetric(it, true), AndroidTestMetric())
        Module.LIBRARY -> listOf(JacocoMetric(it, true), TestMetric(it, true), AndroidTestMetric())
        Module.KOTLIN -> listOf(JacocoMetric(it, false), TestMetric(it, false))
      }

      metricList.forEach { metric ->
        metric.moduleDir = modulePath
        if (metric.exists()) availableMetrics[metric.name()] = metric.map()
      }

      metricSummary.data[moduleName] = availableMetrics
    }

    metricSummary.writeFile(project)
  }
}