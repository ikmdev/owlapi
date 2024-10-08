package org.semanticweb.owlapi.api.test.syntax.manchester;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.expression.OWLEntityChecker;
import org.semanticweb.owlapi.expression.ShortFormEntityChecker;
import org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntaxClassExpressionParser;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.BidirectionalShortFormProviderAdapter;
import org.semanticweb.owlapi.util.SimpleShortFormProvider;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

class ManchesterSyntaxParserTest extends TestBase {

    public static Collection<Object[]> data() {
        // can't use df at this point - it has not been initialised yet
        OWLDataFactory datafactory = OWLManager.getOWLDataFactory();
        OWLDataProperty hasAge =
            datafactory.getOWLDataProperty(iri("http://example.org/", "hasAge"));
        return Arrays.asList(
        //@formatter:off
            new Object[] { "hasAge exactly 1 xsd:int",  datafactory.getOWLDataExactCardinality(1, hasAge, OWL2Datatype.XSD_INT.getDatatype(datafactory)) },
            new Object[] { "hasAge exactly 1",          datafactory.getOWLDataExactCardinality(1, hasAge)},
            new Object[] { "hasAge min 1 xsd:int",      datafactory.getOWLDataMinCardinality(1, hasAge, OWL2Datatype.XSD_INT.getDatatype(datafactory))},
            new Object[] { "hasAge min 1",              datafactory.getOWLDataMinCardinality(1, hasAge)},
            new Object[] { "hasAge max 1 xsd:int",      datafactory.getOWLDataMaxCardinality(1, hasAge, OWL2Datatype.XSD_INT.getDatatype(datafactory))},
            new Object[] { "hasAge max 1",              datafactory.getOWLDataMaxCardinality(1, hasAge)});
            //@formatter:on
    }

    @ParameterizedTest
    @MethodSource("data")
    void testParseDataCardinalityExpression(String input, Object expected) {
        OWLDataProperty hasAge = df.getOWLDataProperty(iri("http://example.org/", "hasAge"));
        OWLOntology ont = createAnon();
        m.addAxiom(ont, df.getOWLDeclarationAxiom(hasAge));
        ManchesterOWLSyntaxClassExpressionParser parser =
            new ManchesterOWLSyntaxClassExpressionParser(df, checker(m));
        assertEquals(expected, parser.parse(input));
    }

    protected OWLEntityChecker checker(OWLOntologyManager manager) {
        BidirectionalShortFormProviderAdapter adapter = new BidirectionalShortFormProviderAdapter(
            manager.getOntologies(), new SimpleShortFormProvider());
        return new ShortFormEntityChecker(adapter);
    }
}
