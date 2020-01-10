/**
 * Created by appcom interactive GmbH on 10.01.20.
 * Copyright © 2020 appcom interactive GmbH. All rights reserved.
 */
package de.nanogiants.gradle

import org.gradle.api.Plugin
import org.gradle.api.internal.project.ProjectInternal

class AndroidMetricsPlugin : Plugin<ProjectInternal> {

  override fun apply(target: ProjectInternal) {
    with(target) {
      tasks.register(Constants.TASK_NAME, MetricsTask::class.java)
      extensions.create(Constants.EXTENSION_NAME, MetricsExtension::class.java, target)
    }
  }
}