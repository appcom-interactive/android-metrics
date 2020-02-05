/**
 * Created by appcom interactive GmbH on 05.02.20.
 * Copyright © 2020 appcom interactive GmbH. All rights reserved.
 */
package de.nanogiants.gradle.metrics

data class ItemSummary(var tests: Int) {

  fun addTests(count: Int) {
    tests += count
  }
}