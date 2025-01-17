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

package generators

import models.UserAnswers
import org.scalacheck.Arbitrary.arbitrary
import org.scalacheck.Arbitrary
import org.scalacheck.Gen
import org.scalatest.TryValues
import pages._
import play.api.libs.json.JsPath
import play.api.libs.json.JsValue
import play.api.libs.json.Json

trait UserAnswersGenerator extends TryValues {
  self: Generators =>

  val generators: Seq[Gen[(QuestionPage[_], JsValue)]] =
    arbitrary[(PlaceOfNotificationPage.type, JsValue)] ::
      arbitrary[(IsTraderAddressPlaceOfNotificationPage.type, JsValue)] ::
      arbitrary[(IsTranshipmentPage.type, JsValue)] ::
      arbitrary[(IncidentInformationPage.type, JsValue)] ::
      arbitrary[(EventReportedPage.type, JsValue)] ::
      arbitrary[(EventPlacePage.type, JsValue)] ::
      arbitrary[(EventCountryPage.type, JsValue)] ::
      arbitrary[(IncidentOnRoutePage.type, JsValue)] ::
      arbitrary[(TraderNamePage.type, JsValue)] ::
      arbitrary[(TraderEoriPage.type, JsValue)] ::
      arbitrary[(TraderAddressPage.type, JsValue)] ::
      arbitrary[(AuthorisedLocationPage.type, JsValue)] ::
      arbitrary[(CustomsSubPlacePage.type, JsValue)] ::
      arbitrary[(PresentationOfficePage.type, JsValue)] ::
      arbitrary[(GoodsLocationPage.type, JsValue)] ::
      arbitrary[(MovementReferenceNumberPage.type, JsValue)] ::
      Nil

  implicit lazy val arbitraryUserData: Arbitrary[UserAnswers] = {

    import models._

    Arbitrary {
      for {
        id <- arbitrary[MovementReferenceNumber]
        data <- generators match {
          case Nil => Gen.const(Map[QuestionPage[_], JsValue]())
          case _   => Gen.mapOf(oneOf(generators))
        }
      } yield
        UserAnswers(
          id = id,
          data = data.foldLeft(Json.obj()) {
            case (obj, (path, value)) =>
              obj.setObject(path.path, value).get
          }
        )
    }
  }
}
