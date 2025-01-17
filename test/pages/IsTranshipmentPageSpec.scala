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

import models.UserAnswers
import org.scalacheck.Arbitrary.arbitrary
import pages.behaviours.PageBehaviours

class IsTranshipmentPageSpec extends PageBehaviours {

  "IsTranshipmentPage" - {

    beRetrievable[Boolean](IsTranshipmentPage)

    beSettable[Boolean](IsTranshipmentPage)

    beRemovable[Boolean](IsTranshipmentPage)

    "must remove incident pages data when user selects option 'Yes' on transhipment page" in {

      forAll(arbitrary[UserAnswers]) {
        userAnswers =>
          val result = userAnswers.set(IsTranshipmentPage, true).success.value

          result.get(IncidentInformationPage) must not be defined
      }
    }
  }
}
