/* This file is part of the OWL API.
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * Copyright 2014, The University of Manchester
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License. */
package org.semanticweb.owlapi.api.test.dataproperties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DisjointClasses;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ObjectUnionOf;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.apitest.TestFiles;
import org.semanticweb.owlapi.formats.ManchesterSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.PrefixDocumentFormat;
import org.semanticweb.owlapi.model.OWLOntology;

class DisjointClassesRoundTripTestCase extends TestBase {

    static final String NS = "http://ns.owl";

    @Test
    void shouldParse() {
        OWLOntology ontology = buildOntology();
        OWLOntology roundtripped = loadOntologyFromString(TestFiles.parseDisjointClasses,
            new ManchesterSyntaxDocumentFormat());
        equal(ontology, roundtripped);
    }

    @Test
    void shouldRoundTrip() {
        OWLOntology ontology = buildOntology();
        PrefixDocumentFormat format = new ManchesterSyntaxDocumentFormat();
        format.setPrefix("piz", NS + '#');
        OWLOntology roundtripped = roundTrip(ontology, format);
        assertEquals(ontology.getLogicalAxioms(), roundtripped.getLogicalAxioms());
    }

    private OWLOntology buildOntology() {
        OWLOntology ontology = create(iri(NS, ""));
        ontology.getOWLOntologyManager().addAxiom(ontology,
            DisjointClasses(ObjectUnionOf(C, D), ObjectUnionOf(C, E), ObjectUnionOf(C, F)));
        return ontology;
    }
}
