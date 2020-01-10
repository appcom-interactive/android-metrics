/**
 * Created by appcom interactive GmbH on 2019-08-24.
 * Copyright © 2019 appcom interactive GmbH. All rights reserved.
 */

package de.nanogiants.gradle.mappers

import de.nanogiants.gradle.entities.read.JacocoEntity
import org.simpleframework.xml.core.Persister
import java.io.File

class JacocoMapper : BaseMapper<JacocoEntity>() {

  private val serial by lazy {
    Persister()
  }

  override fun toModel(data: File): JacocoEntity = serial.read(JacocoEntity::class.java, data)
}