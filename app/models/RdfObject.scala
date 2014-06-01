package models

/**
 * A facade around a RDF subject.
 */
class RdfObject(i: String, l: String) {
    val id: String = i
    val label: String = l
}

object RdfObjectOrderingByLabel extends Ordering[RdfObject] {
    def compare(a: RdfObject, b: RdfObject) = a.label compare b.label
}