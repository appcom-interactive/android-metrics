/**
 * Created by appcom interactive GmbH on 2019-08-24.
 * Copyright © 2019 appcom interactive GmbH. All rights reserved.
 */

package de.nanogiants.gradle.mappers

import java.io.File

abstract class BaseMapper<T> {

  abstract fun toModel(data: File): T
}