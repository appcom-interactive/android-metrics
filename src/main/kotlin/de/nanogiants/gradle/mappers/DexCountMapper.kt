/**
 * Created by appcom interactive GmbH on 2019-08-24.
 * Copyright © 2019 appcom interactive GmbH. All rights reserved.
 */

package de.nanogiants.gradle.mappers

import com.google.gson.Gson
import com.google.gson.stream.JsonReader
import de.nanogiants.gradle.entities.read.DexCountEntity
import java.io.File
import java.io.FileReader

class DexCountMapper : BaseMapper<DexCountEntity>() {

  override fun toModel(data: File): DexCountEntity {
    val gson = Gson()
    val reader = JsonReader(FileReader(data))
    return gson.fromJson(reader, DexCountEntity::class.java)
  }
}