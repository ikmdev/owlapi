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

import java.util.Optional;

import javax.annotation.Nonnull;

/**
 * A marker interface for annotation values, which can either be an IRI (URI), Literal or Anonymous
 * Individual, with visitor methods.
 * 
 * @see org.semanticweb.owlapi.model.IRI
 * @see org.semanticweb.owlapi.model.OWLLiteral
 * @see org.semanticweb.owlapi.model.OWLAnonymousIndividual
 * @author Matthew Horridge, The University of Manchester, Information Management Group
 * @since 3.0.0
 */
public interface OWLAnnotationValue extends OWLAnnotationObject, OWLPrimitive {

    /**
     * @param visitor visitor to accept
     */
    void accept(@Nonnull OWLAnnotationValueVisitor visitor);

    /**
     * @param visitor visitor to accept
     * @param <O> visitor return type
     * @return visitor value
     */
    @Nonnull
    <O> O accept(@Nonnull OWLAnnotationValueVisitorEx<O> visitor);

    /**
     * @return if the value is a literal, return an optional containing it. Return Optional.absent
     *         otherwise.
     */
    @Nonnull
    Optional<OWLLiteral> asLiteral();

    /**
     * @return if the value is an IRI, return an optional containing it. Return Optional.absent
     *         otherwise.
     */
    @Nonnull
    Optional<IRI> asIRI();

    /**
     * @return if the value is an anonymous, return an optional containing it. Return
     *         Optional.absent otherwise.
     */
    @Nonnull
    Optional<OWLAnonymousIndividual> asAnonymousIndividual();

    /**
     * @return true if the annotation value is a literal
     */
    default boolean isLiteral() {
        return false;
    }
}
