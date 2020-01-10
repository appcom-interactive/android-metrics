/**
 * Created by appcom interactive GmbH on 2019-08-24.
 * Copyright © 2019 appcom interactive GmbH. All rights reserved.
 */

package de.nanogiants.gradle.models

import java.io.File

abstract class Metric(val key: String, val path: String) {

  var moduleDir: String = ""

  fun fileDirectory(): File = File("$moduleDir$path")

  fun exists(): Boolean = fileDirectory().isDirectory

  abstract fun file(): File

  fun name() = key
}