/**
 * Created by appcom interactive GmbH on 2019-08-24.
 * Copyright © 2019 appcom interactive GmbH. All rights reserved.
 */

package de.nanogiants.gradle.entities.write

class JacocoOutEntity(
  val name: String,
  val instruction: ValueEntity,
  val branch: ValueEntity
) : MetricEntity()

data class ValueEntity(var all: Int, var covered: Int, var missed: Int, var percentage: Int) {

  constructor(all: Int, covered: Int, missed: Int) : this(all, covered, missed, 0) {
    percentage = calculatePercentage(covered, missed)
  }

  private fun calculatePercentage(covered: Int, missed: Int): Int {
    val all = covered + missed
    if (all == 0) return 0
    return (100 * covered) / all
  }

  operator fun plusAssign(other: ValueEntity) {
    all += other.all
    covered += other.covered
    missed += other.missed
    percentage = calculatePercentage(covered, missed)
  }
}