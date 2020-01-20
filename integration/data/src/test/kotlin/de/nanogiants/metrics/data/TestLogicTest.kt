/**
 * Created by appcom interactive GmbH on 2020-01-19.
 * Copyright © 2020 appcom interactive GmbH. All rights reserved.
 */

package de.nanogiants.metrics.data

import org.junit.Test

class TestLogicTest {

  private val logic = TestLogic()

  @Test
  fun testComputationNumber() {
    val model = TestModel("", 1)

    val testModel = logic.computeModel(model)

    assert(model.number == testModel.number)
    assert(model.number == 3)
  }

  @Test
  fun testComputationNumber2() {
    val model = TestModel("", 11)

    val testModel = logic.computeModel(model)

    assert(model.number == testModel.number)
    assert(model.number == 9)
  }

  @Test
  fun testComputationText() {
    val model = TestModel("", 1)

    val testModel = logic.computeModel(model)

    assert(testModel.text.isEmpty())
  }

  @Test
  fun testComputationText2() {
    val model = TestModel("test", 1)

    val testModel = logic.computeModel(model)

    assert(testModel.text == "test")
  }
}