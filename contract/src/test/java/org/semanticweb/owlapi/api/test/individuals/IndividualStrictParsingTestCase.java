package org.semanticweb.owlapi.api.test.individuals;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.ManchesterSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.NQuadsDocumentFormat;
import org.semanticweb.owlapi.formats.NTriplesDocumentFormat;
import org.semanticweb.owlapi.formats.OWLXMLDocumentFormat;
import org.semanticweb.owlapi.formats.RDFJsonDocumentFormat;
import org.semanticweb.owlapi.formats.RDFJsonLDDocumentFormat;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.formats.RioRDFXMLDocumentFormat;
import org.semanticweb.owlapi.formats.RioTurtleDocumentFormat;
import org.semanticweb.owlapi.formats.TrigDocumentFormat;
import org.semanticweb.owlapi.formats.TurtleDocumentFormat;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;

class IndividualStrictParsingTestCase extends TestBase {

    @Test
    void should() {
        OWLOntology o = createAnon();

        OWLObjectProperty p =
            df.getOWLObjectProperty(IRI.create("http://purl.obolibrary.org/obo/BFO_0000051"));
        OWLClass cl = df.getOWLClass(IRI.create("http://purl.obolibrary.org/obo/ENVO_00000191"));
        OWLNamedIndividual individual =
            df.getOWLNamedIndividual(IRI.create("https://www.wikidata.org/wiki/Q2306597"));

        m.addAxioms(o,
            new HashSet<>(Arrays.asList(df.getOWLDeclarationAxiom(p), df.getOWLDeclarationAxiom(cl),
                df.getOWLDeclarationAxiom(individual),
                df.getOWLClassAssertionAxiom(df.getOWLObjectSomeValuesFrom(p, cl), individual))));
        OWLOntologyLoaderConfiguration conf = new OWLOntologyLoaderConfiguration().setStrict(true);
        roundTrip(o, new RDFXMLDocumentFormat(), conf);
        roundTrip(o, new RioRDFXMLDocumentFormat(), conf);
        roundTrip(o, new RDFJsonDocumentFormat(), conf);
        roundTrip(o, new OWLXMLDocumentFormat(), conf);
        roundTrip(o, new FunctionalSyntaxDocumentFormat(), conf);
        roundTrip(o, new TurtleDocumentFormat(), conf);
        roundTrip(o, new RioTurtleDocumentFormat(), conf);
        roundTrip(o, new ManchesterSyntaxDocumentFormat(), conf);
        roundTrip(o, new TrigDocumentFormat(), conf);
        roundTrip(o, new RDFJsonLDDocumentFormat(), conf);
        roundTrip(o, new NTriplesDocumentFormat(), conf);
        roundTrip(o, new NQuadsDocumentFormat(), conf);
    }
}
