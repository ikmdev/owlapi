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
package org.semanticweb.owlapi.owlxml.renderer;

import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.ABBREVIATED_IRI_ATTRIBUTE;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.ABBREVIATED_IRI_ELEMENT;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.ANNOTATION_URI;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.CARDINALITY_ATTRIBUTE;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.DATATYPE_FACET;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.DATATYPE_IRI;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.IRI_ATTRIBUTE;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.IRI_ELEMENT;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.NAME_ATTRIBUTE;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.NODE_ID;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.ONTOLOGY;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.PREFIX;

import java.io.IOException;
import java.io.Writer;
import java.net.URI;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.semanticweb.owlapi.io.OWLRendererException;
import org.semanticweb.owlapi.io.OWLRendererIOException;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.NodeID;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.rdf.rdfxml.renderer.XMLWriter;
import org.semanticweb.owlapi.rdf.rdfxml.renderer.XMLWriterFactory;
import org.semanticweb.owlapi.rdf.rdfxml.renderer.XMLWriterNamespaceManager;
import org.semanticweb.owlapi.util.StringLengthComparator;
import org.semanticweb.owlapi.util.VersionInfo;
import org.semanticweb.owlapi.vocab.Namespaces;
import org.semanticweb.owlapi.vocab.OWLFacet;
import org.semanticweb.owlapi.vocab.OWLXMLVocabulary;

/**
 * Writes OWL/XML. In an OWL/XML documents written by this writer, the base is always the ontology
 * URI, and the default namespace is always the OWL namespace (http://www.w3.org/2002/07/owl#).
 * Unlike RDF/XML, entity URIs aren't abbreviated using the XML namespace mechanism, instead they
 * are encoded using 'prefix' elements.
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.0.0
 */
public class OWLXMLWriter {

    @Nonnull
    private static final String LANG_IRI = "xml:lang";
    @Nonnull
    private static final IRI VERSION_IRI = IRI.create(Namespaces.OWL.getPrefixIRI(), "versionIRI");
    @Nonnull
    private static final IRI ONTOLOGY_IRI =
        IRI.create(Namespaces.OWL.getPrefixIRI(), "ontologyIRI");
    private final XMLWriter writer;
    @Nonnull
    private final Map<String, String> iriPrefixMap = new TreeMap<>(new StringLengthComparator());

    /**
     * @param writer writer
     * @param ontology ontology
     */
    public OWLXMLWriter(@Nonnull Writer writer, @Nullable OWLOntology ontology) {
        XMLWriterNamespaceManager nsm = new XMLWriterNamespaceManager(Namespaces.OWL.toString());
        nsm.setPrefix("xsd", Namespaces.XSD.toString());
        nsm.setPrefix("rdf", Namespaces.RDF.toString());
        nsm.setPrefix("rdfs", Namespaces.RDFS.toString());
        nsm.setPrefix("xml", Namespaces.XML.toString());
        String base = Namespaces.OWL.toString();
        if (ontology != null && !ontology.isAnonymous()) {
            base = ontology.getOntologyID().getOntologyIRI().get().toString();
        }
        this.writer = XMLWriterFactory.createXMLWriter(writer, nsm, base);
    }

    /**
     * @return iri to prefix map
     */
    public Map<String, String> getIRIPrefixMap() {
        return iriPrefixMap;
    }

    /**
     * @return namespace manager
     */
    public XMLWriterNamespaceManager getNamespaceManager() {
        return writer.getNamespacePrefixes();
    }

    /**
     * A convenience method to write a prefix.
     * 
     * @param prefixName The name of the prefix (e.g. owl: is the prefix name for the OWL prefix)
     * @param iri The prefix iri
     * @throws IOException io error
     */
    public void writePrefix(String prefixName, String iri) throws IOException {
        writer.writeStartElement(PREFIX.getIRI());
        if (prefixName.endsWith(":")) {
            String attName = prefixName.substring(0, prefixName.length() - 1);
            writer.writeAttribute(NAME_ATTRIBUTE.getIRI(), attName);
        } else {
            writer.writeAttribute(NAME_ATTRIBUTE.getIRI(), prefixName);
        }
        writer.writeAttribute(IRI_ATTRIBUTE.getIRI(), iri);
        writer.writeEndElement();
        iriPrefixMap.put(iri, prefixName);
    }

    /**
     * Gets an IRI attribute value for a full IRI. If the IRI has a prefix that coincides with a
     * written prefix then the compact IRI will be returned, otherwise the full IRI will be
     * returned.
     * 
     * @param iri The IRI
     * @return Either the compact version of the IRI or the full IRI.
     */
    public String getIRIString(IRI iri) {
        String prefixName = iriPrefixMap.get(iri.getNamespace());
        if (prefixName == null) {
            return iri.toString();
        }
        return iri.prefixedBy(prefixName);
    }

    /**
     * @param ontology ontology
     * @throws OWLRendererException renderer error
     */
    public void startDocument(OWLOntology ontology) throws OWLRendererException {
        try {
            writer.startDocument(ONTOLOGY.getIRI());
            if (!ontology.isAnonymous()) {
                writer.writeAttribute(ONTOLOGY_IRI,
                    ontology.getOntologyID().getOntologyIRI().get().toString());
                if (ontology.getOntologyID().getVersionIRI().isPresent()) {
                    writer.writeAttribute(VERSION_IRI,
                        ontology.getOntologyID().getVersionIRI().get().toString());
                }
            }
        } catch (IOException e) {
            throw new OWLRendererIOException(e);
        }
    }

    /**
     * 
     */
    public void endDocument() {
        try {
            writer.endDocument();
            writer.writeComment(VersionInfo.getVersionInfo().getGeneratedByMessage());
        } catch (IOException e) {
            throw new OWLRuntimeException(e);
        }
    }

    /**
     * @param name name
     */
    public void writeStartElement(OWLXMLVocabulary name) {
        try {
            writer.writeStartElement(name.getIRI());
        } catch (IOException e) {
            throw new OWLRuntimeException(e);
        }
    }

    /** write end element */
    public void writeEndElement() {
        try {
            writer.writeEndElement();
        } catch (IOException e) {
            throw new OWLRuntimeException(e);
        }
    }

    /**
     * Writes a datatype attributed (used on Literal elements). The full datatype IRI is written out
     * 
     * @param datatype The datatype
     */
    public void writeDatatypeAttribute(OWLDatatype datatype) {
        try {
            writer.writeAttribute(DATATYPE_IRI.getIRI(), datatype.getIRI().toString());
        } catch (IOException e) {
            throw new OWLRuntimeException(e);
        }
    }

    /**
     * @param nodeID nodeID
     */
    public void writeNodeIDAttribute(NodeID nodeID) {
        try {
            writer.writeAttribute(NODE_ID.getIRI(), nodeID.getID());
        } catch (IOException e) {
            throw new OWLRuntimeException(e);
        }
    }

    /**
     * @param iri iri
     */
    public void writeIRIAttribute(IRI iri) {
        try {
            IRI attName = IRI_ATTRIBUTE.getIRI();
            String value = iri.toString();
            if (value.startsWith(writer.getXMLBase())) {
                writer.writeAttribute(attName, iriMinusBase(value));
            } else {
                String val = getIRIString(iri);
                if (!val.equals(iri.toString())) {
                    writer.writeAttribute(ABBREVIATED_IRI_ATTRIBUTE.getIRI(), val);
                } else {
                    writer.writeAttribute(attName, val);
                }
            }
        } catch (IOException e) {
            throw new OWLRuntimeException(e);
        }
    }

    /**
     * Writes an IRI element for a given IRI.
     * 
     * @param iri The IRI to be written as an element. If the IRI can be abbreviated then an
     *        AbbreviatedIRI element will be written
     */
    public void writeIRIElement(IRI iri) {
        try {
            String iriString = iri.toString();
            if (iriString.startsWith(writer.getXMLBase())) {
                writeStartElement(IRI_ELEMENT);
                writeTextContent(iriMinusBase(iriString));
                writeEndElement();
            } else {
                String val = getIRIString(iri);
                if (!val.equals(iriString)) {
                    writeStartElement(ABBREVIATED_IRI_ELEMENT);
                    writer.writeTextContent(val);
                    writeEndElement();
                } else {
                    writeStartElement(IRI_ELEMENT);
                    writer.writeTextContent(val);
                    writeEndElement();
                }
            }
        } catch (IOException e) {
            throw new OWLRuntimeException(e);
        }
    }

    @Nonnull
    String iriMinusBase(String iriString) {
        return iriString.substring(writer.getXMLBase().length(), iriString.length());
    }

    /**
     * @param lang language tag
     */
    public void writeLangAttribute(@Nonnull String lang) {
        try {
            writer.writeAttribute(LANG_IRI, lang);
        } catch (IOException e) {
            throw new OWLRuntimeException(e);
        }
    }

    /**
     * @param cardinality cardinality
     */
    public void writeCardinalityAttribute(int cardinality) {
        try {
            writer.writeAttribute(CARDINALITY_ATTRIBUTE.getIRI(), Integer.toString(cardinality));
        } catch (IOException e) {
            throw new OWLRuntimeException(e);
        }
    }

    /**
     * @param text text
     */
    public void writeTextContent(@Nonnull String text) {
        try {
            writer.writeTextContent(text);
        } catch (IOException e) {
            throw new OWLRuntimeException(e);
        }
    }

    /**
     * @param facet facet
     */
    public void writeFacetAttribute(OWLFacet facet) {
        try {
            writer.writeAttribute(DATATYPE_FACET.getIRI(), facet.getIRI().toString());
        } catch (IOException e) {
            throw new OWLRuntimeException(e);
        }
    }

    /**
     * @param uri URI
     */
    public void writeAnnotationURIAttribute(URI uri) {
        try {
            writer.writeAttribute(ANNOTATION_URI.getIRI(), uri.toString());
        } catch (IOException e) {
            throw new OWLRuntimeException(e);
        }
    }
}
