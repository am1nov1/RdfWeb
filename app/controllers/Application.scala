package controllers

import java.io._
import models.Triple
import models.Triplestore
import play.api._
import play.api.mvc._

object Application extends Controller {

    def index = Action {

        Ok(views.html.index())
    }

    def objects = Action {

        val objects = Triplestore.objects()

        Ok(views.html.objects(objects))
    }

    def predicates = TODO

    def subject(id: String) = TODO

    def subjects = Action {

        val subjects = Triplestore.subjects()

        Ok(views.html.subjects(subjects.mkString(", ")))
    }
}
