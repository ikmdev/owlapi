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
package org.semanticweb.owlapi.model;

import java.util.Set;

import javax.annotation.Nonnull;

/**
 * Ontology methods related to it being a collection of axioms. This interface differs from
 * OWLAxiomCollection because its methods take no arguments for imports closure, and are equivalent
 * to calling the corresponding method from OWLAxiomCollection with argument Imports.EXCLUDED.
 * 
 * @author ignazio
 * @since 4.0.0
 */
public interface OWLAxiomCollectionNoArgs
    extends HasAxioms, HasLogicalAxioms, HasAxiomsByType, HasContainsAxiom {

    /**
     * @return The number of axioms in this ontology.
     */
    int getAxiomCount();

    /**
     * Gets the number of logical axioms in this collection.
     * 
     * @return The number of axioms in this collection.
     */
    int getLogicalAxiomCount();

    /**
     * Gets the axiom count of a specific type of axiom.
     * 
     * @param axiomType The type of axiom to count
     * @param <T>       axiom type class
     * @return The number of the specified types of axioms in this collection
     */
    <T extends OWLAxiom> int getAxiomCount(@Nonnull AxiomType<T> axiomType);

    /**
     * Determines if this ontology contains the specified axiom.
     * 
     * @param axiom The axiom to search.
     * @return {@code true} if the ontology contains the specified axiom.
     */
    boolean containsAxiomIgnoreAnnotations(@Nonnull OWLAxiom axiom);

    /**
     * Gets the set of axioms contained in this collection that have the same "logical structure" as
     * the specified axiom; i.e., all axioms that equal the specified axiom, when ignoring
     * annotations. Optionally the imports closure is included.
     * 
     * @param axiom The axiom that the returned axioms must equal, ignoring annotations.
     * @return The set of axioms such that for any two axioms, {@code axiomA} and {@code axiomB} in
     *         the set, {@code axiomA.getAxiomWithoutAnnotations()} is equal to
     *         {@code axiomB.getAxiomWithoutAnnotations()}. The specified axiom will be contained in
     *         the set.
     */
    @Nonnull
    Set<OWLAxiom> getAxiomsIgnoreAnnotations(@Nonnull OWLAxiom axiom);

    /**
     * Gets the axioms where the specified {@link OWLPrimitive} appears in the signature of the
     * axiom.<br>
     * Note that currently signatures contain {@link OWLEntity} only. This method accepts
     * OWLPrimitive so that also anonymous individuals, literals, IRIs and annotation values can be
     * passed in, although they are not included in the axioms' signatures.
     * 
     * @param owlEntity The entity that should be directly referred to by all axioms in the results
     *                  set.
     * @return All axioms referencing the entity. The set is a copy of the data.
     */
    @Nonnull
    Set<OWLAxiom> getReferencingAxioms(@Nonnull OWLPrimitive owlEntity);

    // Axioms that form part of a description of a named entity
    /**
     * Gets the axioms that form the definition/description of a class.
     * 
     * @param cls The class whose describing axioms are to be retrieved.
     * @return A set of class axioms that describe the class. This set includes
     *         <ul>
     *         <li>Subclass axioms where the subclass is equal to the specified class</li>
     *         <li>Equivalent class axioms where the specified class is an operand in the equivalent
     *         class axiom</li>
     *         <li>Disjoint class axioms where the specified class is an operand in the disjoint
     *         class axiom</li>
     *         <li>Disjoint union axioms, where the specified class is the named class that is
     *         equivalent to the disjoint union</li>
     *         </ul>
     *         The returned set is a copy of the data.
     * @deprecated use
     *             {@link OWLAxiomCollection#getAxioms(OWLClass, org.semanticweb.owlapi.model.parameters.Imports)}
     *             instead
     */
    @Nonnull
    @Deprecated
    Set<OWLClassAxiom> getAxioms(@Nonnull OWLClass cls);

    /**
     * Gets the axioms that form the definition/description of an object property.
     * 
     * @param property The property whose defining axioms are to be retrieved.
     * @return A set of object property axioms that includes
     *         <ul>
     *         <li>Sub-property axioms where the sub property is the specified property</li>
     *         <li>Equivalent property axioms where the axiom contains the specified property</li>
     *         <li>Equivalent property axioms that contain the inverse of the specified
     *         property</li>
     *         <li>Disjoint property axioms that contain the specified property</li>
     *         <li>Domain axioms that specify a domain of the specified property</li>
     *         <li>Range axioms that specify a range of the specified property</li>
     *         <li>Any property characteristic axiom (i.e. Functional, Symmetric, Reflexive etc.)
     *         whose subject is the specified property</li>
     *         <li>Inverse properties axioms that contain the specified property</li>
     *         </ul>
     *         The set that is returned is a copy of the data.
     * @deprecated use
     *             {@link OWLAxiomCollection#getAxioms(OWLObjectPropertyExpression, org.semanticweb.owlapi.model.parameters.Imports)}
     *             instead
     */
    @Nonnull
    @Deprecated
    Set<OWLObjectPropertyAxiom> getAxioms(@Nonnull OWLObjectPropertyExpression property);

    /**
     * Gets the axioms that form the definition/description of a data property.
     * 
     * @param property The property whose defining axioms are to be retrieved.
     * @return A set of data property axioms that includes
     *         <ul>
     *         <li>Sub-property axioms where the sub property is the specified property</li>
     *         <li>Equivalent property axioms where the axiom contains the specified property</li>
     *         <li>Disjoint property axioms that contain the specified property</li>
     *         <li>Domain axioms that specify a domain of the specified property</li>
     *         <li>Range axioms that specify a range of the specified property</li>
     *         <li>Any property characteristic axiom (i.e. Functional, Symmetric, Reflexive etc.)
     *         whose subject is the specified property</li>
     *         </ul>
     *         The set is a copy of the data.
     * @deprecated use
     *             {@link OWLAxiomCollection#getAxioms(OWLDataProperty, org.semanticweb.owlapi.model.parameters.Imports)}
     *             instead
     */
    @Nonnull
    @Deprecated
    Set<OWLDataPropertyAxiom> getAxioms(@Nonnull OWLDataProperty property);

    /**
     * Gets the axioms that form the definition/description of an individual.
     * 
     * @param individual The individual whose defining axioms are to be retrieved.
     * @return A set of individual axioms that includes
     *         <ul>
     *         <li>Individual type assertions that assert the type of the specified individual</li>
     *         <li>Same individuals axioms that contain the specified individual</li>
     *         <li>Different individuals axioms that contain the specified individual</li>
     *         <li>Object property assertion axioms whose subject is the specified individual</li>
     *         <li>Data property assertion axioms whose subject is the specified individual</li>
     *         <li>Negative object property assertion axioms whose subject is the specified
     *         individual</li>
     *         <li>Negative data property assertion axioms whose subject is the specified
     *         individual</li>
     *         </ul>
     *         The set is a copy of the data.
     * @deprecated use
     *             {@link OWLAxiomCollection#getAxioms(OWLIndividual, org.semanticweb.owlapi.model.parameters.Imports)}
     *             instead
     */
    @Nonnull
    @Deprecated
    Set<OWLIndividualAxiom> getAxioms(@Nonnull OWLIndividual individual);

    /**
     * Gets the axioms that form the definition/description of an annotation property.
     * 
     * @param property The property whose definition axioms are to be retrieved
     * @return A set of axioms that includes
     *         <ul>
     *         <li>Annotation subpropertyOf axioms where the specified property is the sub
     *         property</li>
     *         <li>Annotation property domain axioms that specify a domain for the specified
     *         property</li>
     *         <li>Annotation property range axioms that specify a range for the specified
     *         property</li>
     *         </ul>
     *         The set is a copy of the data.
     * @deprecated use
     *             {@link OWLAxiomCollection#getAxioms(OWLAnnotationProperty, org.semanticweb.owlapi.model.parameters.Imports)}
     *             instead
     */
    @Nonnull
    @Deprecated
    Set<OWLAnnotationAxiom> getAxioms(@Nonnull OWLAnnotationProperty property);

    /**
     * Gets the datatype definition axioms for the specified datatype.
     * 
     * @param datatype The datatype
     * @return The set of datatype definition axioms for the specified datatype. The set is a copy
     *         of the data.
     * @deprecated use
     *             {@link OWLAxiomCollection#getAxioms(OWLDatatype, org.semanticweb.owlapi.model.parameters.Imports)}
     *             instead
     */
    @Nonnull
    @Deprecated
    Set<OWLDatatypeDefinitionAxiom> getAxioms(@Nonnull OWLDatatype datatype);
}
