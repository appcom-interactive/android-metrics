/**
 * Created by appcom interactive GmbH on 05.02.20.
 * Copyright © 2020 appcom interactive GmbH. All rights reserved.
 */
package de.nanogiants.gradle.utils

import java.util.concurrent.TimeUnit

fun String.runCmd() {
  val parts = this.split("\\s".toRegex())
  val process = ProcessBuilder(*parts.toTypedArray())
    .redirectOutput(ProcessBuilder.Redirect.INHERIT)
    .redirectError(ProcessBuilder.Redirect.INHERIT)
    .start()
  if (!process.waitFor(10, TimeUnit.SECONDS)) {
    process.destroy()
    throw RuntimeException("execution timed out: $this")
  }
  if (process.exitValue() != 0) {
    throw RuntimeException("execution failed with code ${process.exitValue()}: $this")
  }
}
