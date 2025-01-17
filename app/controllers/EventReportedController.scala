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

package controllers

import controllers.actions._
import forms.EventReportedFormProvider
import javax.inject.Inject
import models.{Mode, MovementReferenceNumber}
import navigation.Navigator
import pages.EventReportedPage
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.libs.json.Json
import play.api.mvc.{Action, AnyContent, MessagesControllerComponents}
import renderer.Renderer
import repositories.SessionRepository
import uk.gov.hmrc.play.bootstrap.controller.FrontendBaseController
import uk.gov.hmrc.viewmodels.{NunjucksSupport, Radios}

import scala.concurrent.{ExecutionContext, Future}

class EventReportedController @Inject()(override val messagesApi: MessagesApi,
                                        sessionRepository: SessionRepository,
                                        navigator: Navigator,
                                        identify: IdentifierAction,
                                        getData: DataRetrievalActionProvider,
                                        requireData: DataRequiredAction,
                                        formProvider: EventReportedFormProvider,
                                        val controllerComponents: MessagesControllerComponents,
                                        renderer: Renderer)(implicit ec: ExecutionContext)
    extends FrontendBaseController
    with I18nSupport
    with NunjucksSupport {

  private val form = formProvider()

  def onPageLoad(mrn: MovementReferenceNumber, mode: Mode): Action[AnyContent] =
    (identify andThen getData(mrn) andThen requireData).async {
      implicit request =>
        val preparedForm = request.userAnswers.get(EventReportedPage) match {
          case None        => form
          case Some(value) => form.fill(value)
        }

        val json = Json.obj(
          "form"   -> preparedForm,
          "mode"   -> mode,
          "mrn"    -> mrn,
          "radios" -> Radios.yesNo(preparedForm("value"))
        )

        renderer.render("eventReported.njk", json).map(Ok(_))
    }

  def onSubmit(mrn: MovementReferenceNumber, mode: Mode): Action[AnyContent] =
    (identify andThen getData(mrn) andThen requireData).async {
      implicit request =>
        form
          .bindFromRequest()
          .fold(
            formWithErrors => {

              val json = Json.obj(
                "form"   -> formWithErrors,
                "mode"   -> mode,
                "mrn"    -> mrn,
                "radios" -> Radios.yesNo(formWithErrors("value"))
              )

              renderer.render("eventReported.njk", json).map(BadRequest(_))
            },
            value =>
              for {
                updatedAnswers <- Future.fromTry(request.userAnswers.set(EventReportedPage, value))
                _              <- sessionRepository.set(updatedAnswers)
              } yield Redirect(navigator.nextPage(EventReportedPage, mode, updatedAnswers))
          )
    }
}
