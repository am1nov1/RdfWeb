package models

import com.hp.hpl.jena.rdf.model.Literal
import com.hp.hpl.jena.rdf.model.Model
import com.hp.hpl.jena.rdf.model.ModelFactory
import com.hp.hpl.jena.rdf.model.Property
import com.hp.hpl.jena.rdf.model.RDFNode
import com.hp.hpl.jena.rdf.model.Resource
import com.hp.hpl.jena.rdf.model.Statement
import com.hp.hpl.jena.rdf.model.StmtIterator
import java.io._
import models.RdfObject
import scala.collection.JavaConversions._
import scala.collection.mutable.MutableList

/**
 * A facade around the RDF triplestore
 */
object Triplestore {

  // TODO: Get the RDF files from somewhere. Assets?
  val rdf_file_input_stream = new FileInputStream("/Users/lenny/Code/GitHub/Rdf/public.ttl")

  /**
   * The Apache Jena RDF model.
   * @see <http://jena.apache.org/documentation/javadoc/jena/com/hp/hpl/jena/rdf/model/Model.html>
   */
  val model: Model = ModelFactory.createDefaultModel()

  // Read the RDF files.
  model.read(rdf_file_input_stream, null, "TTL")

  /**
   * Returns a list of all RDF objects.
   */
  def objects(): Seq[RdfObject] = {

    val objects: MutableList[RdfObject] = new MutableList

    // Iterate over all RDF objects.
    for (o <- model.listObjects()) {
      objects += new RdfObject(o.toString())
    }

    objects.toList
  }

  /**
   * Returns a list of all RDF predicates.
   */
  /*
  def predicates(): List[String] = {

    val predicates: MutableList[String] = new MutableList

    // Iterate over all RDF objects.
    for (o <- model.listPredicates()) {
      predicates += o.toString()
    }

    predicates.toList
  }
  */

  /**
   * Returns a list of all RDF subjects.
   */
  def subjects(): Seq[String] = {

    val subjects: MutableList[String] = new MutableList

    // Iterate over all RDF objects.
    for (o <- model.listSubjects()) {
      subjects += o.toString()
    }

    subjects.toList
  }

}
