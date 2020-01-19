/**
 * Created by appcom interactive GmbH on 2019-08-24.
 * Copyright © 2019 appcom interactive GmbH. All rights reserved.
 */

package de.nanogiants.gradle.metrics

import com.getkeepsafe.dexcount.DexCountTask
import de.nanogiants.gradle.base.Metric
import de.nanogiants.gradle.entities.write.DexCountOutEntity
import de.nanogiants.gradle.mappers.DexCountMapper
import org.gradle.api.Project
import java.io.File

class DexCountMetric(private val project: Project) : Metric(NAME, "/build/outputs/dexcount") {

  companion object {

    const val NAME = "dexcount"
  }

  private val dexCountFile: String by lazy {
    val tasks = project.tasks.withType(DexCountTask::class.java)
    tasks.forEach {
      val dexCountOutput = it.outputFile.get().asFile
      if (dexCountOutput.exists()) {
        return@lazy dexCountOutput.name
      }
    }
    return@lazy ""
  }

  override fun file(): File = File(fileDirectory(), dexCountFile)

  override fun map(): DexCountOutEntity = DexCountMapper().toModel(file()).run {
    DexCountOutEntity(methods, fields)
  }
}
