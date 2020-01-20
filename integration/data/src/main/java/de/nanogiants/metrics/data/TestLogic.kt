/**
 * Created by appcom interactive GmbH on 2020-01-19.
 * Copyright © 2020 appcom interactive GmbH. All rights reserved.
 */

package de.nanogiants.metrics.data

class TestLogic {

  fun computeModel(model: TestModel): TestModel {
    if (model.number > 10) {
      model.number -= 2
    } else {
      model.number += 2
    }
    return model
  }
}