package controllers

import java.io._
import models.RdfSubjectDetails
import models.RdfTriple
import models.Triplestore
import play.api._
import play.api.libs.json._
import play.api.mvc._

/**
 * A controller for RDF statements (triples).
 */
object Statement extends Controller {

    /**
     * Creates an RDF statement from the given JSON data.
     */
    /*
    def create() = Action(parse.json) { request =>
        Async {
            val rdf_subject = request.body.\("s")
            val rdf_predicate = request.body.\("p")
            val rdf_object = request.body.\("o")

            // TODO: return a proper JSON result
            Ok("Created")
        }
    }
    */
}
