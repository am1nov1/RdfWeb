package models

/**
 * A facade around an RDF triple.
 */
class RdfTriple(subj: String, pred: String, obj: String) {
    var s: String = subj
    var p: String = pred
    var o: String = obj
}
