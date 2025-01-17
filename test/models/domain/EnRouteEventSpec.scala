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

package models.domain

import generators.DomainModelGenerators
import models.domain.behaviours.JsonBehaviours
import org.scalacheck.Arbitrary.arbitrary
import org.scalatest.FreeSpec
import org.scalatest.MustMatchers
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks
import play.api.libs.json.JsObject
import play.api.libs.json.JsSuccess
import play.api.libs.json.Json

class EnRouteEventSpec extends FreeSpec with MustMatchers with ScalaCheckPropertyChecks with DomainModelGenerators with JsonBehaviours {

  "must deserialise" in {

    forAll(arbitrary[EnRouteEvent]) {
      enRouteEvent =>
        val json = createEnRouteEventJson(enRouteEvent)
        json.validate[EnRouteEvent] mustEqual JsSuccess(enRouteEvent)
    }
  }

  "must serialise" in {

    forAll(arbitrary[EnRouteEvent]) {
      enRouteEvent =>
        val json = createEnRouteEventJson(enRouteEvent)
        Json.toJson(enRouteEvent) mustEqual json
    }
  }

  def createEnRouteEventJson(enRouteEvent: EnRouteEvent): JsObject =
    Json.obj(
      "place"         -> enRouteEvent.place,
      "countryCode"   -> enRouteEvent.countryCode,
      "alreadyInNcts" -> enRouteEvent.alreadyInNcts,
      "eventDetails"  -> Json.toJson(enRouteEvent.eventDetails),
      "seals"         -> Json.toJson(enRouteEvent.seals)
    )
}
