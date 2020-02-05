/**
 * Created by appcom interactive GmbH on 05.02.20.
 * Copyright © 2020 appcom interactive GmbH. All rights reserved.
 */
package de.nanogiants.gradle.utils

object GitUtils {

  fun getCommitCount(): Int = "git rev-list --count HEAD".getInteger()

  fun getSha1(): String = "git rev-parse --short HEAD".get()

  fun getVersionCode(): Int = getCommitCount()

  fun getBranch(): String = "git rev-parse --abbrev-ref HEAD".get()
}