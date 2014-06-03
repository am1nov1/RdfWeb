package models

import com.hp.hpl.jena.rdf.model.Literal
import com.hp.hpl.jena.rdf.model.Model
import com.hp.hpl.jena.rdf.model.ModelFactory
import com.hp.hpl.jena.rdf.model.Property
import com.hp.hpl.jena.rdf.model.RDFNode
import com.hp.hpl.jena.rdf.model.Resource
import com.hp.hpl.jena.rdf.model.Selector
import com.hp.hpl.jena.rdf.model.SimpleSelector
import com.hp.hpl.jena.rdf.model.Statement
import com.hp.hpl.jena.rdf.model.StmtIterator
import java.io._
import play.api.Play.current
import scala.collection.JavaConversions._
import scala.collection.mutable.HashMap
import scala.collection.mutable.MutableList
import scala.util.Sorting

/**
 * A facade around the RDF triplestore
 */
object Triplestore {

    /**
     * The Apache Jena RDF model.
     * @see <http://jena.apache.org/documentation/javadoc/jena/com/hp/hpl/jena/rdf/model/Model.html>
     */
    val model: Model = ModelFactory.createDefaultModel()

    val languages = Seq("de", "", null, "en", "fr", "it", "es", "jp")

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

    // TODO: handle the case when the parameter is not defined.
    val rdf_file_name = current.configuration.getString("rdfWeb.rdfFile").getOrElse("")

    val rdf_file_input_stream = new FileInputStream(rdf_file_name)

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
                objects += new RdfObject(subject_short_uri(o), subject_label(o))
        }

        val objects_array = objects.toArray
        Sorting.quickSort(objects_array)(RdfObjectOrderingByLabel)
        objects_array
    }

    /**
     *
     */
    def subject_description(rdf_subject: RDFNode): String = {

        if (rdf_subject.isLiteral())
            return rdf_subject.asLiteral().toString();

        val properties = Seq(SCHEMA_DESCRIPTION,
            SKOS_NOTE)

        val resource: Resource = rdf_subject.asResource()

        for (property <- properties) {

            // Get an iterator over the RDF statements where the current RDF
            // subject has the current RDF label property.
            val rdf_statement_iterator: StmtIterator = resource
                .listProperties(property)

            val description_per_language: HashMap[String, String] = new HashMap()

            for (rdf_statement <- rdf_statement_iterator) {

                // Get the RDF statement's RDF object.
                val rdf_object: RDFNode = rdf_statement.getObject()

                // The RDF object is an RDF literal.
                if (rdf_object.isLiteral()) {

                    // Add the literal per language.
                    val literal: Literal = rdf_object.asLiteral()

                    description_per_language.put(literal.getLanguage(),
                        literal.getString())
                }
            }

            // Iterate over the preferred languages.
            for (language <- languages) {

                val maybe_description: Option[String] = description_per_language.get(language)

                for (description <- maybe_description) {
                    return description;
                }
            }
        }

        null
    }

    /**
     *
     */
    def subject_details(subject_id: String): RdfSubjectDetails = {

        // Try to select the RDF subject from the given ID.
        val resource: Resource = model.getResource(model.expandPrefix(subject_id))

        // TODO: handle `resource` null case

        // Get the RDF subject label and description.
        val label: String = subject_label(resource)
        val description: String = subject_description(resource)

        // Collect the outgoing triples.
        val outgoing_triples: MutableList[RdfTriple] = new MutableList

        // Select all outgoing RDF statements for the RDF subject.
        val outgoing_selector: Selector = new SimpleSelector(resource, null, null.asInstanceOf[Object])

        val outgoing_statement_iterator: StmtIterator = model.listStatements(outgoing_selector)

        // Iterate over the outgoing RDF statements.
        for (outgoing_statement <- outgoing_statement_iterator) {
            val rdf_predicate: Property = outgoing_statement.getPredicate()
            val rdf_object: RDFNode = outgoing_statement.getObject()

            val predicate_id: String = subject_short_uri(rdf_predicate)

            val object_id: String =
                if (rdf_object.isLiteral()) { "" }
                else { subject_short_uri(rdf_object) }

            val object_label: String = subject_label(rdf_object)

            val object_label_language: String =
                if (rdf_object.isLiteral()) { rdf_object.asLiteral().getLanguage() }
                else { "" }

            outgoing_triples += new RdfTriple(subject_id, predicate_id, object_id, object_label, object_label_language)
        }

        // TODO: create incoming triples
        val incoming_triples: Seq[RdfTriple] = Seq()

        new RdfSubjectDetails(subject_id, label, description, outgoing_triples.toList, incoming_triples)
    }

    /**
     *
     */
    def subject_label(rdf_subject: RDFNode): String = {

        if (rdf_subject.isLiteral())
            return rdf_subject.asLiteral().getString();

        val properties = Seq(SCHEMA_NAME,
            SCHEMA_ALTERNATE_NAME, SKOS_PREFLABEL,
            SKOS_ALTLABEL)

        val resource: Resource = rdf_subject.asResource()

        // Iterate over all possible label RDF predicates.
        for (property <- properties) {

            // Get an iterator over the RDF statements where the current RDF
            // subject has the current RDF description property.
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

        // No subject label was found: use an empty string.
        ""
    }

    /**
     *
     */
    def subject_short_uri(rdf_subject: RDFNode): String = {
        val resource: Resource = rdf_subject.asResource()
        val model: Model = rdf_subject.getModel()

        model.shortForm(resource.getURI());
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
