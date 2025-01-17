/*
 * Copyright 2019 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package forms

import forms.behaviours.StringFieldBehaviours
import models.MovementReferenceNumber
import org.scalacheck.Arbitrary.arbitrary
import play.api.data.FormError

class MovementReferenceNumberFormProviderSpec extends StringFieldBehaviours {

  val requiredKey = "movementReferenceNumber.error.required"
  val invalidKey  = "movementReferenceNumber.error.invalid"

  val form = new MovementReferenceNumberFormProvider()()

  ".value" - {

    val fieldName = "value"

    behave like fieldThatBindsValidData(
      form,
      fieldName,
      arbitrary[MovementReferenceNumber].map(_.toString)
    )

    behave like mandatoryField(
      form,
      fieldName,
      requiredError = FormError(fieldName, requiredKey)
    )

    "must not bind invalid MRNs" in {

      forAll(arbitrary[String]) {
        value =>
          whenever(value != "" && MovementReferenceNumber(value).isEmpty) {

            val result = form.bind(Map("value" -> value))
            result.errors must contain(FormError("value", invalidKey))
          }
      }
    }

  }
}
