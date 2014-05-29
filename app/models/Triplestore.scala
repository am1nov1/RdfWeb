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
import scala.collection.JavaConversions._
import scala.collection.mutable.MutableList

/**
 * A facade around the RDF triplestore
 */
object Triplestore {

  // TODO: Get the RDF files from somewhere. Assets?
  val rdf_file_input_stream = new FileInputStream("/Users/lenny/Code/GitHub/Rdf/public.ttl")

  // Create the RDF model and load the RDF files.
  val model = ModelFactory.createDefaultModel()
  model.read(rdf_file_input_stream, null, "TTL")

  /**
   * Returns a list of all RDF objects.
   */
  def objects(): List[String] = {

    val objects: MutableList[String] = new MutableList

    // Iterate over all RDF objects.
    for (o <- model.listObjects()) {
      objects += o.toString()
    }

    objects.toList
  }

}
