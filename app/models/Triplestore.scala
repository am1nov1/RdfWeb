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
import scala.collection.mutable.HashMap
import scala.collection.mutable.MutableList

/**
 * A facade around the RDF triplestore
 */
object Triplestore {

    /**
     * The Apache Jena RDF model.
     * @see <http://jena.apache.org/documentation/javadoc/jena/com/hp/hpl/jena/rdf/model/Model.html>
     */
    val model: Model = ModelFactory.createDefaultModel()

    val FOAF: String = "http://xmlns.com/foaf/0.1/"
    val GEO: String = "http://www.w3.org/2003/01/geo/wgs84_pos#"
    val SCHEMA: String = "http://schema.org/"
    val SKOS: String = "http://www.w3.org/2004/02/skos/core#"

    val GEO_LAT: Property = model.createProperty(GEO + "lat")
    val GEO_LONG: Property = model.createProperty(GEO + "long")
    val SCHEMA_ALTERNATE_NAME: Property = model.createProperty(SCHEMA
        + "alternateName")
    val SCHEMA_DESCRIPTION: Property = model.createProperty(SCHEMA
        + "description")
    val SCHEMA_NAME: Property = model.createProperty(SCHEMA + "name")
    val SCHEMA_URL: Property = model.createProperty(SCHEMA + "url")
    val SKOS_ALTLABEL: Property = model.createProperty(SKOS
        + "altLabel")
    val SKOS_NOTE: Property = model.createProperty(SKOS + "note")
    val SKOS_PREFLABEL: Property = model.createProperty(SKOS
        + "prefLabel")

    // TODO: Get the RDF files from somewhere. Assets?
    val rdf_file_input_stream = new FileInputStream("/Users/lenny/Code/GitHub/Rdf/public.ttl")

    // Read the RDF files.
    model.read(rdf_file_input_stream, null, "TTL")

    /**
     * Returns a list of all RDF objects.
     */
    def objects(): Seq[RdfObject] = {

        val objects: MutableList[RdfObject] = new MutableList

        // Iterate over all RDF objects.
        for (o <- model.listObjects()) {

            // Only object, if it is not an RDF literals.
            if (!o.isLiteral())
                objects += new RdfObject("TODO", subject_label(o))
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
     *
     */
    def subject_label(rdf_subject: RDFNode): String = {

        if (rdf_subject.isLiteral())
            return rdf_subject.asLiteral().toString();

        val languages = Seq("de", "", null, "en", "fr", "it", "es", "jp")
        val properties = Seq(SCHEMA_NAME,
            SCHEMA_ALTERNATE_NAME, SKOS_PREFLABEL,
            SKOS_ALTLABEL)

        val resource: Resource = rdf_subject.asResource()

        for (property <- properties) {

            // Get an iterator over the RDF statements where the current RDF
            // subject has the current RDF label property.
            val rdf_statement_iterator: StmtIterator = resource
                .listProperties(property)

            val label_per_language: HashMap[String, String] = new HashMap()

            for (rdf_statement <- rdf_statement_iterator) {

                // Get the RDF statement's RDF object.
                val rdf_object: RDFNode = rdf_statement.getObject()

                // The RDF object is an RDF literal.
                if (rdf_object.isLiteral()) {

                    // Add the literal per language.
                    val literal: Literal = rdf_object.asLiteral()

                    label_per_language.put(literal.getLanguage(),
                        literal.getString())
                }
            }

            // Iterate over the preferred languages.
            for (language <- languages) {

                val maybe_label: Option[String] = label_per_language.get(language)

                for (label <- maybe_label) {
                    return label;
                }
            }
        }

        resource.getLocalName()
    }

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
