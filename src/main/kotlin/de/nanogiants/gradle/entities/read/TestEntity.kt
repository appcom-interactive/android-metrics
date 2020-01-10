/**
 * Created by appcom interactive GmbH on 2020-01-10.
 * Copyright © 2020 appcom interactive GmbH. All rights reserved.
 */

package de.nanogiants.gradle.entities.read

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Root

@Root(name = "testsuite", strict = false)
data class TestEntity(
  @field:Attribute(name = "name")
  @param:Attribute(name = "name") var name: String = "",
  @field:Attribute(name = "tests")
  @param:Attribute(name = "tests") var tests: String = "",
  @field:Attribute(name = "skipped")
  @param:Attribute(name = "skipped") var skipped: String = "",
  @field:Attribute(name = "failures")
  @param:Attribute(name = "failures") var failures: String = "",
  @field:Attribute(name = "errors")
  @param:Attribute(name = "errors") var errors: String = "",
  @field:Attribute(name = "time")
  @param:Attribute(name = "time") var time: String = ""
)