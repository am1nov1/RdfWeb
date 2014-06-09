package models

import play.api.libs.json._

/**
 * A facade around RDF subject details.
 */
class RdfSubjectDetails(i: String, l: String, d: String, out: Seq[RdfTriple], in: Seq[RdfTriple]) {
    val id: String = i
    val label: String = l
    val description: String = d
    val outgoing_triples: Seq[RdfTriple] = out
    val incoming_triples: Seq[RdfTriple] = in
}

/*
implicit val rdfSubjectDetailsWrites = new Writes[RdfSubjectDetails] {
  def writes(subject_details: RdfSubjectDetails) = Json.obj(
    "id" -> subject_details.id,
    "label" -> subject_details.label,
    "descr" -> subject_details.description
  )
}
*/ 