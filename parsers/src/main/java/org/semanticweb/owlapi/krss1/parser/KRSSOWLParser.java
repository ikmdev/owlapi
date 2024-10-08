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
package org.semanticweb.owlapi.krss1.parser;

import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Optional;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.formats.KRSSDocumentFormat;
import org.semanticweb.owlapi.formats.KRSSDocumentFormatFactory;
import org.semanticweb.owlapi.io.AbstractOWLParser;
import org.semanticweb.owlapi.io.OWLOntologyDocumentSource;
import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLDocumentFormatFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.0.0
 */
public class KRSSOWLParser extends AbstractOWLParser {

    private static final long serialVersionUID = 40000L;

    @Nonnull
    @Override
    public String getName() {
        return "KRSSOWLParser";
    }

    @Override
    public OWLDocumentFormatFactory getSupportedFormat() {
        return new KRSSDocumentFormatFactory();
    }

    @Override
    public OWLDocumentFormat parse(OWLOntologyDocumentSource documentSource, OWLOntology ontology,
        OWLOntologyLoaderConfiguration configuration) throws IOException {
        try {
            KRSSParser parser;
            if (documentSource.isReaderAvailable()) {
                parser = new KRSSParser(documentSource.getReader());
            } else if (documentSource.isInputStreamAvailable()) {
                parser = new KRSSParser(documentSource.getInputStream());
            } else {
                InputStream is = null;
                try {
                    if (documentSource.getDocumentIRI().getNamespace().startsWith("jar:")) {
                        if (documentSource.getDocumentIRI().getNamespace().startsWith("jar:!")) {
                            String name = documentSource.getDocumentIRI().toString().substring(5);
                            if (!name.startsWith("/")) {
                                name = "/" + name;
                            }
                            is = getClass().getResourceAsStream(name);
                        } else {
                            try {
                                is = ((JarURLConnection) new URL(
                                    documentSource.getDocumentIRI().toString()).openConnection())
                                        .getInputStream();
                            } catch (IOException e) {
                                throw new OWLParserException(e);
                            }
                        }
                    } else {
                        Optional<String> headers = documentSource.getAcceptHeaders();
                        if (headers.isPresent()) {
                            is = getInputStream(documentSource.getDocumentIRI(), configuration,
                                headers.get());
                        } else {
                            is = getInputStream(documentSource.getDocumentIRI(), configuration,
                                DEFAULT_REQUEST);
                        }
                    }
                    parser = new KRSSParser(is);
                } finally {
                    if (is != null) {
                        is.close();
                    }
                }
            }
            parser.setOntology(ontology, ontology.getOWLOntologyManager().getOWLDataFactory());
            parser.parse();
            return new KRSSDocumentFormat();
        } catch (ParseException e) {
            throw new KRSSOWLParserException(e);
        }
    }
}
