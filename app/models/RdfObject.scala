package models

/**
 * A facade around a RDF subject.
 */
class RdfObject(val id: String, val label: String)

// Enable comparison for sorting of two `RdfObject`.
object RdfObjectOrderingByLabel extends Ordering[RdfObject] {
    def compare(a: RdfObject, b: RdfObject) = a.label compare b.label
}