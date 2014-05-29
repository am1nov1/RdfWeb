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

object Triplestore {

  // TODO: Get the RDF files from somewhere.
  val rdf_file_input_stream = new FileInputStream("/Users/lenny/Code/GitHub/Rdf/public.ttl")

  // Create the model and load the RDF files.
  val model = ModelFactory.createDefaultModel()
  model.read(rdf_file_input_stream, null, "TTL")

  def objects {

    val string_builder = new StringBuilder()

    // Iterate over all RDF objects.
    for (o <- model.listObjects()) {
      string_builder ++= o.toString()
    }

  }

}
