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

import javax.annotation.Nonnull;

/**
 * Provides a base for visitorEx adapters. The adapter can be set up to return a default value.
 * 
 * @author Matthew Horridge, The University Of Manchester, Information Management Group
 * @since 4.0.0
 * @param <O> visitor return type
 * @param <I> type accepting the visitor
 */
public class OWLBaseVisitorExAdapter<O, I> {

    @Nonnull
    private final O defaultReturnValue;

    /**
     * Gets the default return value for this visitor. By default, the default is {@code null}, but
     * a fixed value (independent of the specified {@code OWLObject} {@code object}) can be
     * specified in the constructor
     * {@link org.semanticweb.owlapi.util.OWLBaseVisitorExAdapter#OWLBaseVisitorExAdapter(Object)}
     * 
     * @param object The object that was visited.
     * @return The default return value
     */
    @Nonnull
    protected O doDefault(@SuppressWarnings("unused") @Nonnull I object) {
        return defaultReturnValue;
    }

    /**
     * @param object object to visit
     * @return default value
     * @deprecated use {@link #doDefault(Object)} instead
     */
    @Deprecated
    @Nonnull
    protected O getDefaultReturnValue(@Nonnull I object) {
        return doDefault(object);
    }

    /**
     * @param defaultReturnValue default return value
     */
    public OWLBaseVisitorExAdapter(@Nonnull O defaultReturnValue) {
        this.defaultReturnValue = defaultReturnValue;
    }
}
