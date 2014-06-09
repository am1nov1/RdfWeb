package controllers

import java.io._
import models.RdfSubjectDetails
import models.RdfTriple
import models.Triplestore
import play.api._
import play.api.libs.json._
import play.api.mvc._

object Application extends Controller {

    def index_html = Action {

        Ok(views.html.index())
    }

    def objects_html = Action {

        val objects = Triplestore.objects()

        Ok(views.html.objects(objects))
    }

    def predicates_html = TODO

    def subject(subject_id: String) = Action {

        val subject = Triplestore.subject_details(subject_id)

        Ok(JsObject(Seq()))
        //Ok(Json.toJson(subject))
    }

    def subject_html(subject_id: String) = Action {

        val subject = Triplestore.subject_details(subject_id)

        Ok(views.html.subject(subject))
    }

    def subjects_html = Action {

        val subjects = Triplestore.subjects()

        Ok(views.html.subjects(subjects.mkString(", ")))
    }
}
