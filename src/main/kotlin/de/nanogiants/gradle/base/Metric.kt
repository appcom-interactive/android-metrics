/**
 * Created by appcom interactive GmbH on 2019-08-24.
 * Copyright © 2019 appcom interactive GmbH. All rights reserved.
 */

package de.nanogiants.gradle.base

import de.nanogiants.gradle.entities.write.MetricEntity
import java.io.File

abstract class Metric(val key: String, val path: String) {

  var moduleDir: String = ""

  fun fileDirectory(): File = File("$moduleDir$path")

  fun exists(): Boolean = fileDirectory().isDirectory

  fun name() = key

  abstract fun file(): File

  abstract fun map(): MetricEntity
}