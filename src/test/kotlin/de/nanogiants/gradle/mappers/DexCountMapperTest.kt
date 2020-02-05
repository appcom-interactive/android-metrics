/**
 * Created by appcom interactive GmbH on 21.01.20.
 * Copyright © 2020 appcom interactive GmbH. All rights reserved.
 */
package de.nanogiants.gradle.mappers

import org.junit.jupiter.api.Test
import java.io.File

class DexCountMapperTest {

  private val mapper = DexCountMapper()

  @Test
  fun `map file to dexcount entity`() {
    val file = File("src/test/resources/json/DexCountEntity.json")

    val entity = mapper.toModel(file)

    assert(entity.methods == 2024)
    assert(entity.fields == 64)
  }
}