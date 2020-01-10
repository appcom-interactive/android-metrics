/**
 * Created by appcom interactive GmbH on 2019-08-24.
 * Copyright © 2019 appcom interactive GmbH. All rights reserved.
 */

package de.nanogiants.gradle.entities.read

data class DexCountEntity(val name: String, val methods: Int, val fields: Int, val children: List<DexCountEntity>)