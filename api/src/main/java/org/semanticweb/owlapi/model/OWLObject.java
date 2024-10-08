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

import java.io.Serializable;
import java.util.Set;

import javax.annotation.Nonnull;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.0.0
 */
public interface OWLObject extends Comparable<OWLObject>, Serializable, HasSignature,
    HasContainsEntityInSignature, HasAnonymousIndividuals, HasClassesInSignature,
    HasObjectPropertiesInSignature, HasDataPropertiesInSignature, HasIndividualsInSignature,
    HasDatatypesInSignature, HasAnnotationPropertiesInSignature, IsAnonymous {
    /**
     * @return true if this object is an IRI.
     */
    default boolean isIRI() {
        return false;
    }

    /**
     * @return true if this object is an individual.
     */
    default boolean isIndividual() {
        return false;
    }


    /**
     * Gets all of the nested (includes top level) class expressions (anonymous and named) that are
     * used in this object. For an ontology, this method visits the logical axioms, so entities that
     * are only declared (and possibly annotated) but do not appear in any logical axiom are not
     * returned as part of the set.
     * 
     * @return A set of {@link org.semanticweb.owlapi.model.OWLClassExpression}s that represent the
     *         nested class expressions used in this object.
     */
    @Nonnull
    Set<OWLClassExpression> getNestedClassExpressions();

    /**
     * Accepts a visitor
     * 
     * @param visitor The visitor
     */
    void accept(@Nonnull OWLObjectVisitor visitor);

    /**
     * Accepts a visitor
     * 
     * @param visitor The visitor
     * @param <O> visitor return type
     * @return visitor value
     */
    @Nonnull
    <O> O accept(@Nonnull OWLObjectVisitorEx<O> visitor);

    /**
     * Determines if this object is either, owl:Thing (the top class), owl:topObjectProperty (the
     * top object property) , owl:topDataProperty (the top data property) or rdfs:Literal (the top
     * datatype).
     * 
     * @return {@code true} if this object corresponds to one of the above entities.
     */
    boolean isTopEntity();

    /**
     * Determines if this object is either, owl:Nothing (the bottom class), owl:bottomObjectProperty
     * (the bottom object property) , owl:bottomDataProperty (the bottom data property).
     * 
     * @return {@code true} if this object corresponds to one of the above entities.
     */
    boolean isBottomEntity();

    @Nonnull
    @Override
    String toString();
}
