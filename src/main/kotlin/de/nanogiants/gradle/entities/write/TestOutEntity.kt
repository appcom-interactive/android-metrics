/**
 * Created by appcom interactive GmbH on 2020-01-10.
 * Copyright © 2020 appcom interactive GmbH. All rights reserved.
 */

package de.nanogiants.gradle.entities.write

class TestOutEntity(
  private val tests: Int,
  private val skipped: Int,
  private val failed: Int,
  private val errors: Int,
  private val durationInSeconds: Double
) :
  MetricEntity() {

  operator fun plus(entity: TestOutEntity): TestOutEntity = TestOutEntity(
    tests + entity.tests,
    skipped + entity.skipped,
    failed + entity.failed,
    errors + entity.errors,
    durationInSeconds + entity.durationInSeconds
  )
}