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
package org.semanticweb.owlapi.api.test.ontology;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.AnnotationAssertion;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Literal;

import javax.annotation.Nonnull;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLOntology;

class MissingDeclarationRoundTripTestCase extends TestBase {

    @Test
    void shouldFindOneAxiom() {
        OWLOntology ontology = createOntology(AP);
        assertTrue(ontology.containsAnnotationPropertyInSignature(AP.getIRI()));
        assertEquals(1, ontology.getAxiomCount());
        RDFXMLDocumentFormat format = new RDFXMLDocumentFormat();
        format.setAddMissingTypes(false);
        ontology = loadOntologyStrict(saveOntology(ontology, format), format);
        assertFalse(ontology.containsAnnotationPropertyInSignature(AP.getIRI()));
        assertEquals(0, ontology.getAxiomCount());
    }

    @Nonnull
    private OWLOntology createOntology(@Nonnull OWLAnnotationProperty p) {
        OWLOntology o = createAnon();
        o.getOWLOntologyManager().addAxiom(o, AnnotationAssertion(p, A.getIRI(), Literal("Hello")));
        return o;
    }
}
