/**
 * Created by appcom interactive GmbH on 04.02.20.
 * Copyright © 2020 appcom interactive GmbH. All rights reserved.
 */
package de.nanogiants.gradle.extensions

import org.gradle.api.Project

open class UnifyExtension {

  companion object {

    const val NAME = "unify"
  }

  var taskName: String = ""
}

internal fun Project.unify(): UnifyExtension =
  extensions.getByName(UnifyExtension.NAME) as? UnifyExtension
    ?: throw IllegalStateException("${UnifyExtension.NAME} is not of the correct type")