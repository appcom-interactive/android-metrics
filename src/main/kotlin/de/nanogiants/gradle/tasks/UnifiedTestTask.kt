/**
 * Created by appcom interactive GmbH on 04.02.20.
 * Copyright © 2020 appcom interactive GmbH. All rights reserved.
 */
package de.nanogiants.gradle.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

open class UnifiedTestTask : DefaultTask() {

  companion object {

    const val NAME = "unifiedTest"
  }

  @TaskAction
  fun run() {
    group = "Verification"
    println("unified test successful")
  }
}