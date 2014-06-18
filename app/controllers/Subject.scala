package controllers

import java.io._
import models.RdfSubjectDetails
import models.RdfTriple
import models.Triplestore
import play.api._
import play.api.libs.json._
import play.api.mvc._

object Subject extends Controller {

    // JSON serialization for `RdfTriple`.
    implicit val rdf_triple_writes = new Writes[RdfTriple] {
        def writes(rdf_triple: RdfTriple) = Json.obj(
            "s" -> rdf_triple.subject_id, "p" -> rdf_triple.predicate_id, "o" -> rdf_triple.object_id)
    }

    // JSON serialization for `RdfSubjectDetails`.
    implicit val rdf_subject_details_writes = new Writes[RdfSubjectDetails] {
        def writes(rdf_subject_details: RdfSubjectDetails) = Json.obj(
            "id" -> rdf_subject_details.id, "label" -> rdf_subject_details.label, "descr" -> rdf_subject_details.description, "in" -> Json.toJson(rdf_subject_details.incoming_triples), "out" -> Json.toJson(rdf_subject_details.outgoing_triples))
    }

    def show(subject_id: String) = Action {

        val subject = Triplestore.subject_details(subject_id)

        Ok(Json.toJson(subject))
    }
}
