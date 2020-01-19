/**
 * Created by appcom interactive GmbH on 2020-01-17.
 * Copyright © 2020 appcom interactive GmbH. All rights reserved.
 */

package de.nanogiants.metrics.test

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import de.nanogiants.metrics.data.TestLogic
import de.nanogiants.metrics.data.TestModel

class TestActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContentView(R.layout.activity_test)

    doSomething()

    val model = TestModel("test", 1)
    val computedModel = TestLogic().computeModel(model)

    println(model)
    println(computedModel)
  }

  private fun doSomething() {
    val foo = 1
    val bar = 3
    val sum = foo + bar
    println("sum $sum")
  }
}