package models

/**
 * A facade around an RDF triple.
 */
class RdfTriple(val subject_id: String, val predicate_id: String, val object_id: String, val object_label: String, val object_label_language: String)

// Enable comparison for sorting of two `RdfTripleOrderingByLabel`.
object RdfTripleOrderingByLabel extends Ordering[RdfTriple] {
    def compare(a: RdfTriple, b: RdfTriple) = a.predicate_id compare b.predicate_id
}
