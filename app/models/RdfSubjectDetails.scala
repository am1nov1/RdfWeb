package models

/**
 * A facade around RDF subject details.
 */
class RdfSubjectDetails(i: String, l: String, d: String) {
    val id: String = i
    val label: String = l
    val description: String = d
}