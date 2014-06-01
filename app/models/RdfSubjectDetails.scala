package models

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