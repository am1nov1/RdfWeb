package models

import play.api.libs.json._

/**
 * A facade around RDF subject details.
 */
class RdfSubjectDetails(val id: String, val label: String, val description: String, val outgoing_triples: Seq[RdfTriple], val incoming_triples: Seq[RdfTriple])