package models

/**
 * A facade around an RDF triple.
 */
class RdfTriple(subj: String, pred: String, obj: String) {
    val s: String = subj
    val p: String = pred
    val o: String = obj
}
