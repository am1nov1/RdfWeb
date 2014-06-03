package models

/**
 * A facade around an RDF triple.
 */
class RdfTriple(s_id: String, p_id: String, o_id: String, o_label: String, o_lang: String) {
    val subject_id: String = s_id
    val predicate_id: String = p_id
    val object_id: String = o_id
    val object_label: String = o_label
    val object_label_language: String = o_lang
}

object RdfTripleOrderingByLabel extends Ordering[RdfTriple] {
    def compare(a: RdfTriple, b: RdfTriple) = a.predicate_id compare b.predicate_id
}
