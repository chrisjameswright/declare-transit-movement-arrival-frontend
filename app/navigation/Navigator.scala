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

package navigation

import javax.inject.{Inject, Singleton}
import play.api.mvc.Call
import controllers.routes
import pages._
import models._

@Singleton
class Navigator @Inject()() {

  private val normalRoutes: Page => UserAnswers => Call = {
    case MovementReferenceNumberPage => ua => routes.GoodsLocationController.onPageLoad(ua.id, NormalMode)
    case GoodsLocationPage => ua => goodsLocationPageRoutes(ua)
    case PresentationOfficePage => ua => routes.CustomsSubPlaceController.onPageLoad(ua.id, NormalMode)
    case CustomsSubPlacePage => ua => routes.TraderNameController.onPageLoad(ua.id, NormalMode)
    case TraderNamePage => ua => routes.TraderEoriController.onPageLoad(ua.id, NormalMode)
    case TraderAddressPage => ua => routes.IncidentOnRouteController.onPageLoad(ua.id, NormalMode)
    case TraderEoriPage => ua => routes.TraderAddressController.onPageLoad(ua.id, NormalMode)
    case IncidentOnRoutePage => incidentOnRouteRoute
    case EventCountryPage => ua => routes.EventPlaceController.onPageLoad(ua.id, NormalMode)
    case EventPlacePage => ua => routes.EventReportedController.onPageLoad(ua.id, NormalMode)
    case EventReportedPage => ua => routes.IsTranshipmentController.onPageLoad(ua.id, NormalMode)
    case IsTranshipmentPage => isTranshipmentRoute
    case IncidentInformationPage => ua => routes.SealsChangedController.onPageLoad(ua.id, NormalMode)
    case SealsChangedPage => ua => routes.CheckEventAnswersController.onPageLoad(ua.id)
    case _ => _ => routes.IndexController.onPageLoad()
  }

  private val checkRouteMap: Page => UserAnswers => Call = {
    case GoodsLocationPage => goodsLocationCheckRoute
    case page if eventsPages(page) => ua => routes.CheckEventAnswersController.onPageLoad(ua.id)
    case _                 => ua => routes.CheckYourAnswersController.onPageLoad(ua.id)
  }

  def nextPage(page: Page, mode: Mode, userAnswers: UserAnswers): Call = mode match {
    case NormalMode =>
      normalRoutes(page)(userAnswers)
    case CheckMode =>
      checkRouteMap(page)(userAnswers)
  }

  private def eventsPages(page: Page): Boolean = {
    page match {
      case EventCountryPage | EventPlacePage | EventReportedPage | IsTranshipmentPage | IncidentInformationPage | SealsChangedPage => true
      case _ => false
    }

  }
  private def goodsLocationPageRoutes(ua: UserAnswers): Call = {
    if (ua.get(GoodsLocationPage).contains(GoodsLocation.BorderForceOffice)) {
      routes.PresentationOfficeController.onPageLoad(ua.id, NormalMode)
    } else {
      routes.UseDifferentServiceController.onPageLoad(ua.id)
    }
  }

  private def incidentOnRouteRoute(ua: UserAnswers): Call = ua.get(IncidentOnRoutePage) match {
    case Some(true)  => routes.EventCountryController.onPageLoad(ua.id, NormalMode)
    case Some(false) => routes.CheckYourAnswersController.onPageLoad(ua.id)
    case None        => routes.SessionExpiredController.onPageLoad()
  }

  private def isTranshipmentRoute(ua: UserAnswers): Call = ua.get(EventReportedPage) match {
    case Some(true)  => routes.SealsChangedController.onPageLoad(ua.id, NormalMode)
    case Some(false) => routes.IncidentInformationController.onPageLoad(ua.id, NormalMode)
    case None        => routes.SessionExpiredController.onPageLoad()
  }

  private def goodsLocationCheckRoute(ua: UserAnswers): Call = {

    import GoodsLocation._

    (ua.get(GoodsLocationPage), ua.get(AuthorisedLocationPage), ua.get(CustomsSubPlacePage)) match {
      case (Some(BorderForceOffice), _, None)         => routes.CustomsSubPlaceController.onPageLoad(ua.id, CheckMode)
      case (Some(AuthorisedConsigneesLocation), _, _) => routes.UseDifferentServiceController.onPageLoad(ua.id)
      case _                                          => routes.CheckYourAnswersController.onPageLoad(ua.id)
    }
  }
}