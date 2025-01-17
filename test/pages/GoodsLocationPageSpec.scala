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

package pages

import models.GoodsLocation
import models.UserAnswers
import org.scalacheck.Arbitrary.arbitrary
import pages.behaviours.PageBehaviours

class GoodsLocationPageSpec extends PageBehaviours {

  "GoodsLocationPage" - {

    beRetrievable[GoodsLocation](GoodsLocationPage)

    beSettable[GoodsLocation](GoodsLocationPage)

    beRemovable[GoodsLocation](GoodsLocationPage)

    "must remove Authorised Location Code when the user selected Border Force office" in {

      forAll(arbitrary[UserAnswers]) {
        answers =>
          val result = answers.set(GoodsLocationPage, GoodsLocation.BorderForceOffice).success.value

          result.get(AuthorisedLocationPage) must not be defined
      }
    }

    "must remove Customs Sub Place when the user selects Authorised Consignees Location" in {

      forAll(arbitrary[UserAnswers]) {
        answers =>
          val result = answers.set(GoodsLocationPage, GoodsLocation.AuthorisedConsigneesLocation).success.value

          result.get(CustomsSubPlacePage) must not be defined
      }
    }
  }
}
