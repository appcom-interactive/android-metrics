/**
 * Created by appcom interactive GmbH on 2020-01-10.
 * Copyright © 2020 appcom interactive GmbH. All rights reserved.
 */

package de.nanogiants.gradle.mappers

import de.nanogiants.gradle.entities.read.TestEntity
import org.simpleframework.xml.core.Persister
import java.io.File

class TestMapper : BaseMapper<TestEntity>() {

  private val serial by lazy {
    Persister()
  }

  override fun toModel(data: File): TestEntity = serial.read(TestEntity::class.java, data)
}