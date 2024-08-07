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
package org.semanticweb.owlapi.util;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.RemoveAxiom;

/**
 * Performs a bulk conversion/translation of entity URIs. This utility class can be used to replace
 * entity names with IDs for example. The entity converter is supplied with a set of ontologies and
 * a conversion strategy. All of the entities that are referenced in the specified ontologies will
 * have their URIs converted according the specified conversion strategy.
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.2.0
 */
public class OWLEntityURIConverter {

    @Nonnull
    private final OWLOntologyManager manager;
    // The ontologies that reference the
    // entities whose names will be converted
    @Nonnull
    private final Collection<OWLOntology> ontologies;
    @Nonnull
    private final Map<OWLEntity, IRI> replacementMap = new HashMap<>();
    private Set<OWLEntity> processedEntities;
    @Nonnull
    private final OWLEntityURIConverterStrategy strategy;

    /**
     * Creates a converter that will convert the URIs of entities in the specified ontologies using
     * the specified conversion strategy.
     * 
     * @param manager    The manager which managers the specified ontologies.
     * @param ontologies The ontologies whose entity IRIs will be converted
     * @param strategy   The conversion strategy to be used.
     */
    public OWLEntityURIConverter(@Nonnull OWLOntologyManager manager,
        @Nonnull Set<OWLOntology> ontologies, @Nonnull OWLEntityURIConverterStrategy strategy) {
        this.manager = checkNotNull(manager, "manager cannot be null");
        this.ontologies = new ArrayList<>(checkNotNull(ontologies, "ontologies cannot be null"));
        this.strategy = checkNotNull(strategy, "strategy cannot be null");
    }

    /**
     * Gets the changes required to perform the conversion.
     * 
     * @return A list of ontology changes that should be applied in order to convert the URI of
     *         entities in the specified ontologies.
     */
    public List<OWLOntologyChange> getChanges() {
        replacementMap.clear();
        processedEntities = new HashSet<>();
        List<OWLOntologyChange> changes = new ArrayList<>();
        for (OWLOntology ont : ontologies) {
            for (OWLClass cls : ont.getClassesInSignature()) {
                if (!cls.isOWLThing() && !cls.isOWLNothing()) {
                    processEntity(cls);
                }
            }
            for (OWLObjectProperty prop : ont.getObjectPropertiesInSignature()) {
                assert prop != null;
                processEntity(prop);
            }
            for (OWLDataProperty prop : ont.getDataPropertiesInSignature()) {
                assert prop != null;
                processEntity(prop);
            }
            for (OWLNamedIndividual ind : ont.getIndividualsInSignature()) {
                assert ind != null;
                processEntity(ind);
            }
            for (OWLAnnotationProperty prop : ont.getAnnotationPropertiesInSignature()) {
                assert prop != null;
                processEntity(prop);
            }
            for (OWLDatatype d : ont.getDatatypesInSignature()) {
                assert d != null;
                processEntity(d);
            }
        }
        OWLObjectDuplicator dup = new OWLObjectDuplicator(replacementMap,
            manager.getOWLDataFactory(), Collections.emptyMap(),
            new RemappingIndividualProvider(manager.getOWLDataFactory(), false));
        for (OWLOntology ont : ontologies) {
            assert ont != null;
            for (OWLAxiom ax : ont.getAxioms()) {
                assert ax != null;
                OWLAxiom dupAx = dup.duplicateObject(ax);
                if (!dupAx.equals(ax)) {
                    changes.add(new RemoveAxiom(ont, ax));
                    changes.add(new AddAxiom(ont, dupAx));
                }
            }
        }
        return changes;
    }

    private void processEntity(@Nonnull OWLEntity ent) {
        if (processedEntities.contains(ent)) {
            return;
        }
        // Add label?
        IRI rep = getTinyIRI(ent);
        replacementMap.put(ent, rep);
        processedEntities.add(ent);
    }

    private IRI getTinyIRI(@Nonnull OWLEntity ent) {
        return strategy.getConvertedIRI(ent);
    }
}
