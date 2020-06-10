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
import de.nanogiants.gradle.extensions.metrics
import de.nanogiants.gradle.utils.GitUtils
import org.gradle.api.Project
import java.io.File

data class MetricSummary(
  val data: MutableMap<String, Map<String, MetricEntity>> = mutableMapOf(),
  val project: MutableMap<String, MetricEntity> = mutableMapOf(),
  val moduleSummary: MutableMap<String, ItemSummary> = mutableMapOf(),
  val branch: String = GitUtils.getBranch(),
  val commitCount: Int = GitUtils.getCommitCount()
) {

  private fun generateProjectData() {
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

  private fun summarizeModules(project: Project) {
    val modules = project.metrics().groupModules
    modules.forEach { moduleGroup ->
      moduleSummary[moduleGroup] = ItemSummary(0)
      data.forEach { (name, metric) ->
        if (name.contains(moduleGroup)) {
          metric.forEach { (_, data) ->
            when (data) {
              is TestOutEntity -> {
                moduleSummary[moduleGroup]?.addTests(data.tests)
              }
            }
          }
        }
      }
    }

    // add no matching modules
    moduleSummary["other"] = ItemSummary(0)
    data.forEach { (name, metric) ->
      if (!modules.any { name.contains(it) }) {
        metric.forEach { (_, data) ->
          when (data) {
            is TestOutEntity -> {
              moduleSummary["other"]?.addTests(data.tests)
            }
          }
        }
      }
    }
  }

  fun writeFile(project: Project) {
    val outputFile = File(project.buildDir.absolutePath, "metric.json")
    generateProjectData()
    summarizeModules(project)
    val gson = GsonBuilder().setPrettyPrinting().create()
    val jsonString: String = gson.toJson(this)
    outputFile.writeText(jsonString)
  }
}