/**
 * Created by appcom interactive GmbH on 05.02.20.
 * Copyright © 2020 appcom interactive GmbH. All rights reserved.
 */
package de.nanogiants.gradle.utils

import java.io.BufferedReader
import java.io.IOException
import java.util.concurrent.TimeUnit

internal fun String.getInteger(): Int {
  this.runCommand()?.run {
    return readText().trim().toInt()
  }
  return 1
}

internal fun String.get(): String {
  this.runCommand()?.run {
    return readText().trim()
  }
  return ""
}

internal fun String.runCommand(): BufferedReader? = try {
  val parts = this.split("\\s".toRegex())
  val process = ProcessBuilder(*parts.toTypedArray())
    .start()
  process.waitFor(10, TimeUnit.SECONDS)
  process.inputStream.bufferedReader()
} catch (e: IOException) {
  e.printStackTrace()
  null
}

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
