package org.semanticweb.owlapi.api.test.syntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.apitest.TestFiles;
import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.ManchesterSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.OWLXMLDocumentFormat;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.formats.TurtleDocumentFormat;
import org.semanticweb.owlapi.io.AnonymousIndividualProperties;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLOntology;

class OutputSyntaxSortTestCase extends TestBase {

    static Collection<Object[]> getData() {
        return Arrays.<Object[]>asList(new Object[] {new ManchesterSyntaxDocumentFormat()},
            new Object[] {new FunctionalSyntaxDocumentFormat()},
            new Object[] {new TurtleDocumentFormat()}, new Object[] {new RDFXMLDocumentFormat()},
            new Object[] {new OWLXMLDocumentFormat()});
    }

    @ParameterizedTest
    @MethodSource("getData")
    void shouldOutputAllInSameOrder(OWLDocumentFormat format) {
        AnonymousIndividualProperties.setRemapAllAnonymousIndividualsIds(false);
        try {
            List<OWLOntology> ontologies = new ArrayList<>();
            List<String> set = new ArrayList<>();
            for (String s : TestFiles.inputSorting) {
                OWLOntology o = loadOntologyFromString(new StringDocumentSource(s,
                    IRI.generateDocumentIRI(), new FunctionalSyntaxDocumentFormat(), null));
                set.add(saveOntology(o, format).toString());
                ontologies.add(o);
            }
            for (int index = 0; index < ontologies.size() - 1; index++) {
                equal(ontologies.get(index), ontologies.get(index + 1));
            }
            for (int index = 0; index < set.size() - 1; index++) {
                assertEquals(set.get(index), set.get(index + 1));
            }
        } finally {
            AnonymousIndividualProperties.resetToDefault();
        }
    }
}
