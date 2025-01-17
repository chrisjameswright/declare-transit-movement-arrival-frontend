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

import org.scalacheck.Arbitrary
import pages._

trait PageGenerators {

  implicit lazy val arbitraryPlaceOfNotificationPage: Arbitrary[PlaceOfNotificationPage.type] =
    Arbitrary(PlaceOfNotificationPage)

  implicit lazy val arbitraryIsTraderAddressPlaceOfNotificationPage: Arbitrary[IsTraderAddressPlaceOfNotificationPage.type] =
    Arbitrary(IsTraderAddressPlaceOfNotificationPage)

  implicit lazy val arbitraryIsTranshipmentPage: Arbitrary[IsTranshipmentPage.type] =
    Arbitrary(IsTranshipmentPage)

  implicit lazy val arbitraryIncidentInformationPage: Arbitrary[IncidentInformationPage.type] =
    Arbitrary(IncidentInformationPage)

  implicit lazy val arbitraryEventReportedPage: Arbitrary[EventReportedPage.type] =
    Arbitrary(EventReportedPage)

  implicit lazy val arbitraryEventPlacePage: Arbitrary[EventPlacePage.type] =
    Arbitrary(EventPlacePage)

  implicit lazy val arbitraryEventCountryPage: Arbitrary[EventCountryPage.type] =
    Arbitrary(EventCountryPage)

  implicit lazy val arbitraryIncidentOnRoutePage: Arbitrary[IncidentOnRoutePage.type] =
    Arbitrary(IncidentOnRoutePage)

  implicit lazy val arbitraryTraderNamePage: Arbitrary[TraderNamePage.type] =
    Arbitrary(TraderNamePage)

  implicit lazy val arbitraryTraderEoriPage: Arbitrary[TraderEoriPage.type] =
    Arbitrary(TraderEoriPage)

  implicit lazy val arbitraryTraderAddressPage: Arbitrary[TraderAddressPage.type] =
    Arbitrary(TraderAddressPage)

  implicit lazy val arbitraryAuthorisedLocationPage: Arbitrary[AuthorisedLocationPage.type] =
    Arbitrary(AuthorisedLocationPage)

  implicit lazy val arbitraryCustomsSubPlacePage: Arbitrary[CustomsSubPlacePage.type] =
    Arbitrary(CustomsSubPlacePage)

  implicit lazy val arbitraryPresentationOfficePage: Arbitrary[PresentationOfficePage.type] =
    Arbitrary(PresentationOfficePage)

  implicit lazy val arbitraryGoodsLocationPage: Arbitrary[GoodsLocationPage.type] =
    Arbitrary(GoodsLocationPage)

  implicit lazy val arbitraryMovementReferenceNumberPage: Arbitrary[MovementReferenceNumberPage.type] =
    Arbitrary(MovementReferenceNumberPage)
}
