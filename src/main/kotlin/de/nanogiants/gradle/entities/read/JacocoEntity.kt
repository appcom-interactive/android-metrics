/**
 * Created by appcom interactive GmbH on 2019-08-24.
 * Copyright © 2019 appcom interactive GmbH. All rights reserved.
 */

package de.nanogiants.gradle.entities.read

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "report", strict = false)
data class JacocoEntity(
  @field:Attribute(name = "name")
  @param:Attribute(name = "name") var name: String = "",
  @field:ElementList(entry = "counter", inline = true, required = false)
  @param:ElementList(entry = "counter", inline = true, required = false) var counters: List<CounterEntity>
)

@Root(name = "counter", strict = false)
data class CounterEntity(
  @field:Attribute(name = "type")
  @param:Attribute(name = "type") var type: String,
  @field:Attribute(name = "missed")
  @param:Attribute(name = "missed") var missed: Int,
  @field:Attribute(name = "covered")
  @param:Attribute(name = "covered") var covered: Int
)