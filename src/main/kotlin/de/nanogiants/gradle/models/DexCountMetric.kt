/**
 * Created by appcom interactive GmbH on 2019-08-24.
 * Copyright © 2019 appcom interactive GmbH. All rights reserved.
 */

package de.nanogiants.gradle.models

import de.nanogiants.gradle.entities.write.DexCountOutEntity
import de.nanogiants.gradle.mappers.DexCountMapper
import java.io.File

class DexCountMetric : Metric(NAME, "/build/outputs/dexcount") {

  companion object {

    const val NAME = "dexcount"
  }

  override fun file(): File = File(fileDirectory(), "deDevelopAppcenterDebug.json")

  fun map(): DexCountOutEntity = DexCountMapper().toModel(file()).run {
    DexCountOutEntity(methods, fields)
  }
}
