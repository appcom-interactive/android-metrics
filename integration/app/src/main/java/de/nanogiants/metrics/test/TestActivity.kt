/**
 * Created by appcom interactive GmbH on 2020-01-17.
 * Copyright © 2020 appcom interactive GmbH. All rights reserved.
 */

package de.nanogiants.metrics.test

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class TestActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContentView(R.layout.activity_test)

    doSomething()
  }

  private fun doSomething() {
    val foo = 1
    val bar = 3
    val sum = foo + bar
    println("sum $sum")
  }
}