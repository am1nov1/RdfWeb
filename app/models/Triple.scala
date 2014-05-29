package models

/**
 * A facade around an RDF triple.
 */
abstract class Triple {
  val s: String
  val p: String
  val o: String
}
