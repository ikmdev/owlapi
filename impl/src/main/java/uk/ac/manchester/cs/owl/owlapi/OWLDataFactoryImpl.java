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
package uk.ac.manchester.cs.owl.owlapi;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNegative;
import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.semanticweb.owlapi.model.EntityType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.NodeID;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationSubject;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLClassProvider;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataComplementOf;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataIntersectionOf;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataOneOf;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLDataUnionOf;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi.model.OWLDatatypeRestriction;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLFacetRestriction;
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectHasSelf;
import org.semanticweb.owlapi.model.OWLObjectHasValue;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectInverseOf;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.semanticweb.owlapi.model.OWLPropertyExpression;
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubAnnotationPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.model.SWRLBuiltInAtom;
import org.semanticweb.owlapi.model.SWRLClassAtom;
import org.semanticweb.owlapi.model.SWRLDArgument;
import org.semanticweb.owlapi.model.SWRLDataPropertyAtom;
import org.semanticweb.owlapi.model.SWRLDataRangeAtom;
import org.semanticweb.owlapi.model.SWRLDifferentIndividualsAtom;
import org.semanticweb.owlapi.model.SWRLIArgument;
import org.semanticweb.owlapi.model.SWRLIndividualArgument;
import org.semanticweb.owlapi.model.SWRLLiteralArgument;
import org.semanticweb.owlapi.model.SWRLObjectPropertyAtom;
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.model.SWRLSameIndividualAtom;
import org.semanticweb.owlapi.model.SWRLVariable;
import org.semanticweb.owlapi.util.CollectionFactory;
import org.semanticweb.owlapi.util.VersionInfo;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.semanticweb.owlapi.vocab.OWLFacet;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.0.0
 */
@Singleton
public class OWLDataFactoryImpl implements OWLDataFactory, Serializable, OWLClassProvider {

    private static final String CLASS_EXPRESSION_CANNOT_BE_NULL = "classExpression cannot be null";
    private static final String ARG1_CANNOT_BE_NULL = "arg1 cannot be null";
    private static final String CLASS_EXPRESSIONS = "classExpressions";
    private static final String OPERANDS2 = "operands";
    private static final String INDIVIDUALS2 = "individuals";
    private static final String DATA_RANGE_CANNOT_BE_NULL = "dataRange cannot be null";
    private static final String PREFIX_MANAGER_CANNOT_BE_NULL = "prefixManager cannot be null";
    private static final String DATA_RANGES = "dataRanges";
    private static final String CARDINALITY_CANNOT_BE_NEGATIVE = "cardinality cannot be negative";
    private static final String SUPER_PROPERTY_CANNOT_BE_NULL = "superProperty cannot be null";
    private static final String PROPERTY_CANNOT_BE_NULL = "property cannot be null";
    private static final String ARG0_CANNOT_BE_NULL = "arg0 cannot be null";
    private static final String PROPERTIES2 = "properties";
    private static final String SUBJECT_CANNOT_BE_NULL = "subject cannot be null";
    private static final String INDIVIDUAL_CANNOT_BE_NULL = "individual cannot be null";
    private static final String VALUE_CANNOT_BE_NULL = "value cannot be null";
    private static final String OBJECT_CANNOT_BE_NULL = "object cannot be null";
    private static final String IRI_CANNOT_BE_NULL = "iri cannot be null";
    private static final String DATATYPE_CANNOT_BE_NULL = "datatype cannot be null";
    private static final long serialVersionUID = 40000L;
    // Distinguished Entities
    //@formatter:off 
    @Nonnull private static final OWLClass               OWL_THING                    = new OWLClassImpl(              OWLRDFVocabulary.OWL_THING.getIRI());
    @Nonnull private static final OWLClass               OWL_NOTHING                  = new OWLClassImpl(              OWLRDFVocabulary.OWL_NOTHING.getIRI());
    @Nonnull private static final OWLAnnotationProperty  RDFS_LABEL                   = new OWLAnnotationPropertyImpl( OWLRDFVocabulary.RDFS_LABEL.getIRI());
    @Nonnull private static final OWLAnnotationProperty  RDFS_COMMENT                 = new OWLAnnotationPropertyImpl( OWLRDFVocabulary.RDFS_COMMENT.getIRI());
    @Nonnull private static final OWLAnnotationProperty  RDFS_SEE_ALSO                = new OWLAnnotationPropertyImpl( OWLRDFVocabulary.RDFS_SEE_ALSO.getIRI());
    @Nonnull private static final OWLAnnotationProperty  RDFS_IS_DEFINED_BY           = new OWLAnnotationPropertyImpl( OWLRDFVocabulary.RDFS_IS_DEFINED_BY.getIRI());
    @Nonnull private static final OWLAnnotationProperty  OWL_BACKWARD_COMPATIBLE_WITH = new OWLAnnotationPropertyImpl( OWLRDFVocabulary.OWL_BACKWARD_COMPATIBLE_WITH.getIRI());
    @Nonnull private static final OWLAnnotationProperty  OWL_INCOMPATIBLE_WITH        = new OWLAnnotationPropertyImpl( OWLRDFVocabulary.OWL_INCOMPATIBLE_WITH.getIRI());
    @Nonnull private static final OWLAnnotationProperty  OWL_VERSION_INFO             = new OWLAnnotationPropertyImpl( OWLRDFVocabulary.OWL_VERSION_INFO.getIRI());
    @Nonnull private static final OWLAnnotationProperty  OWL_DEPRECATED               = new OWLAnnotationPropertyImpl( OWLRDFVocabulary.OWL_DEPRECATED.getIRI());
    @Nonnull private static final OWLObjectProperty      OWL_TOP_OBJECT_PROPERTY      = new OWLObjectPropertyImpl(     OWLRDFVocabulary.OWL_TOP_OBJECT_PROPERTY.getIRI());
    @Nonnull private static final OWLObjectProperty      OWL_BOTTOM_OBJECT_PROPERTY   = new OWLObjectPropertyImpl(     OWLRDFVocabulary.OWL_BOTTOM_OBJECT_PROPERTY.getIRI());
    @Nonnull private static final OWLDataProperty        OWL_TOP_DATA_PROPERTY        = new OWLDataPropertyImpl(       OWLRDFVocabulary.OWL_TOP_DATA_PROPERTY.getIRI());
    @Nonnull private static final OWLDataProperty        OWL_BOTTOM_DATA_PROPERTY     = new OWLDataPropertyImpl(       OWLRDFVocabulary.OWL_BOTTOM_DATA_PROPERTY.getIRI());
    //@formatter:on
    private final OWLDataFactoryInternals dataFactoryInternals =
        new OWLDataFactoryInternalsImpl(false);
    private OWLOntologyLoaderConfiguration config;

    /**
     * Constructor for configuration injection.
     */
    @Inject
    public OWLDataFactoryImpl() {
        this(new OWLOntologyLoaderConfiguration());
    }

    /**
     * @param config configuration
     */
    public OWLDataFactoryImpl(OWLOntologyLoaderConfiguration config) {
        this.config = config;
    }

    @Override
    public void purge() {
        dataFactoryInternals.purge();
    }

    private static void checkAnnotations(@Nonnull Set<? extends OWLAnnotation> o) {
        checkNull(o, "annotations cannot be null", true);
    }

    private static void checkNull(@Nonnull Collection<?> o, String name, boolean emptyAllowed) {
        checkNotNull(o, name + " cannot be null");
        if (!emptyAllowed && o.isEmpty()) {
            throw new IllegalArgumentException(name + " cannot be empty");
        }
    }

    private static void checkNull(@Nonnull Object[] o, String name, boolean emptyAllowed) {
        checkNotNull(o, name + " cannot be null");
        if (!emptyAllowed && o.length == 0) {
            throw new IllegalArgumentException(name + " cannot be empty");
        }
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public <E extends OWLEntity> E getOWLEntity(@Nonnull EntityType<E> entityType, IRI iri) {
        checkNotNull(entityType, "entityType cannot be null");
        checkNotNull(iri, IRI_CANNOT_BE_NULL);
        E ret = null;
        if (entityType.equals(EntityType.CLASS)) {
            ret = (E) getOWLClass(iri);
        } else if (entityType.equals(EntityType.OBJECT_PROPERTY)) {
            ret = (E) getOWLObjectProperty(iri);
        } else if (entityType.equals(EntityType.DATA_PROPERTY)) {
            ret = (E) getOWLDataProperty(iri);
        } else if (entityType.equals(EntityType.ANNOTATION_PROPERTY)) {
            ret = (E) getOWLAnnotationProperty(iri);
        } else if (entityType.equals(EntityType.NAMED_INDIVIDUAL)) {
            ret = (E) getOWLNamedIndividual(iri);
        } else if (entityType.equals(EntityType.DATATYPE)) {
            ret = (E) getOWLDatatype(iri);
        }
        if (ret != null) {
            return ret;
        }
        throw new OWLRuntimeException(
            "Entity type not recognized: " + entityType + " for iri " + iri);
    }

    @Override
    public OWLClass getOWLClass(IRI iri) {
        checkNotNull(iri, IRI_CANNOT_BE_NULL);
        return dataFactoryInternals.getOWLClass(iri);
    }

    @Override
    public OWLClass getOWLClass(String abbreviatedIRI, @Nonnull PrefixManager prefixManager) {
        checkNotNull(abbreviatedIRI, IRI_CANNOT_BE_NULL);
        checkNotNull(prefixManager, PREFIX_MANAGER_CANNOT_BE_NULL);
        return getOWLClass(prefixManager.getIRI(abbreviatedIRI));
    }

    @Override
    public OWLAnnotationProperty getOWLAnnotationProperty(String abbreviatedIRI,
        @Nonnull PrefixManager prefixManager) {
        checkNotNull(abbreviatedIRI, "abbreviatedIRI cannot be null");
        checkNotNull(prefixManager, PREFIX_MANAGER_CANNOT_BE_NULL);
        return getOWLAnnotationProperty(prefixManager.getIRI(abbreviatedIRI));
    }

    @Nonnull
    @Override
    public OWLAnnotationProperty getRDFSLabel() {
        return RDFS_LABEL;
    }

    @Nonnull
    @Override
    public OWLAnnotationProperty getRDFSComment() {
        return RDFS_COMMENT;
    }

    @Nonnull
    @Override
    public OWLAnnotationProperty getRDFSSeeAlso() {
        return RDFS_SEE_ALSO;
    }

    @Nonnull
    @Override
    public OWLAnnotationProperty getRDFSIsDefinedBy() {
        return RDFS_IS_DEFINED_BY;
    }

    @Nonnull
    @Override
    public OWLAnnotationProperty getOWLVersionInfo() {
        return OWL_VERSION_INFO;
    }

    @Nonnull
    @Override
    public OWLAnnotationProperty getOWLBackwardCompatibleWith() {
        return OWL_BACKWARD_COMPATIBLE_WITH;
    }

    @Nonnull
    @Override
    public OWLAnnotationProperty getOWLIncompatibleWith() {
        return OWL_INCOMPATIBLE_WITH;
    }

    @Nonnull
    @Override
    public OWLAnnotationProperty getOWLDeprecated() {
        return OWL_DEPRECATED;
    }

    @Override
    public OWLDatatype getOWLDatatype(String abbreviatedIRI, @Nonnull PrefixManager prefixManager) {
        checkNotNull(abbreviatedIRI, "abbreviatedIRI cannot be null");
        checkNotNull(prefixManager, PREFIX_MANAGER_CANNOT_BE_NULL);
        return dataFactoryInternals.getOWLDatatype(prefixManager.getIRI(abbreviatedIRI));
    }

    @Nonnull
    @Override
    public OWLClass getOWLThing() {
        return OWL_THING;
    }

    @Nonnull
    @Override
    public OWLClass getOWLNothing() {
        return OWL_NOTHING;
    }

    @Nonnull
    @Override
    public OWLDataProperty getOWLBottomDataProperty() {
        return OWL_BOTTOM_DATA_PROPERTY;
    }

    @Nonnull
    @Override
    public OWLObjectProperty getOWLBottomObjectProperty() {
        return OWL_BOTTOM_OBJECT_PROPERTY;
    }

    @Nonnull
    @Override
    public OWLDataProperty getOWLTopDataProperty() {
        return OWL_TOP_DATA_PROPERTY;
    }

    @Nonnull
    @Override
    public OWLObjectProperty getOWLTopObjectProperty() {
        return OWL_TOP_OBJECT_PROPERTY;
    }

    @Override
    public OWLObjectProperty getOWLObjectProperty(IRI iri) {
        checkNotNull(iri, IRI_CANNOT_BE_NULL);
        return dataFactoryInternals.getOWLObjectProperty(iri);
    }

    @Override
    public OWLDataProperty getOWLDataProperty(IRI iri) {
        checkNotNull(iri, IRI_CANNOT_BE_NULL);
        return dataFactoryInternals.getOWLDataProperty(iri);
    }

    @Override
    public OWLNamedIndividual getOWLNamedIndividual(IRI iri) {
        checkNotNull(iri, IRI_CANNOT_BE_NULL);
        return dataFactoryInternals.getOWLNamedIndividual(iri);
    }

    @Override
    public OWLDataProperty getOWLDataProperty(String abbreviatedIRI,
        @Nonnull PrefixManager prefixManager) {
        checkNotNull(abbreviatedIRI, "curi canno be null");
        checkNotNull(prefixManager, PREFIX_MANAGER_CANNOT_BE_NULL);
        return getOWLDataProperty(prefixManager.getIRI(abbreviatedIRI));
    }

    @Override
    public OWLNamedIndividual getOWLNamedIndividual(String abbreviatedIRI,
        @Nonnull PrefixManager prefixManager) {
        checkNotNull(abbreviatedIRI, "curi canno be null");
        checkNotNull(prefixManager, PREFIX_MANAGER_CANNOT_BE_NULL);
        return getOWLNamedIndividual(prefixManager.getIRI(abbreviatedIRI));
    }

    @Override
    public OWLObjectProperty getOWLObjectProperty(String abbreviatedIRI,
        @Nonnull PrefixManager prefixManager) {
        checkNotNull(abbreviatedIRI, "curi canno be null");
        checkNotNull(prefixManager, PREFIX_MANAGER_CANNOT_BE_NULL);
        return getOWLObjectProperty(prefixManager.getIRI(abbreviatedIRI));
    }

    @Nonnull
    @Override
    public OWLAnonymousIndividual getOWLAnonymousIndividual(String nodeId) {
        checkNotNull(nodeId, "id cannot be null");
        return new OWLAnonymousIndividualImpl(NodeID.getNodeID(nodeId));
    }

    @Nonnull
    @Override
    public OWLAnonymousIndividual getOWLAnonymousIndividual() {
        return new OWLAnonymousIndividualImpl(NodeID.getNodeID(null));
    }

    @Override
    public OWLDatatype getOWLDatatype(IRI iri) {
        checkNotNull(iri, IRI_CANNOT_BE_NULL);
        return dataFactoryInternals.getOWLDatatype(iri);
    }

    @Override
    public OWLLiteral getOWLLiteral(String lexicalValue, @Nonnull OWL2Datatype datatype) {
        checkNotNull(lexicalValue, "lexicalValue cannot be null");
        checkNotNull(datatype, DATATYPE_CANNOT_BE_NULL);
        return getOWLLiteral(lexicalValue, getOWLDatatype(datatype.getIRI()));
    }

    @Override
    public OWLLiteral getOWLLiteral(boolean value) {
        return dataFactoryInternals.getOWLLiteral(value);
    }

    @Nonnull
    @Override
    public OWLDataOneOf getOWLDataOneOf(@Nonnull Set<? extends OWLLiteral> values) {
        checkNull(values, "values", true);
        return new OWLDataOneOfImpl(values);
    }

    @Override
    public OWLDataOneOf getOWLDataOneOf(OWLLiteral... values) {
        checkNull(values, "values", true);
        return getOWLDataOneOf(CollectionFactory.createSet(values));
    }

    @Nonnull
    @Override
    public OWLDataComplementOf getOWLDataComplementOf(OWLDataRange dataRange) {
        checkNotNull(dataRange, DATA_RANGE_CANNOT_BE_NULL);
        return new OWLDataComplementOfImpl(dataRange);
    }

    @Override
    public OWLDataIntersectionOf getOWLDataIntersectionOf(OWLDataRange... dataRanges) {
        checkNull(dataRanges, DATA_RANGES, true);
        return getOWLDataIntersectionOf(CollectionFactory.createSet(dataRanges));
    }

    @Nonnull
    @Override
    public OWLDataIntersectionOf getOWLDataIntersectionOf(
        @Nonnull Set<? extends OWLDataRange> dataRanges) {
        checkNull(dataRanges, DATA_RANGES, true);
        return new OWLDataIntersectionOfImpl(dataRanges);
    }

    @Override
    public OWLDataUnionOf getOWLDataUnionOf(OWLDataRange... dataRanges) {
        checkNull(dataRanges, DATA_RANGES, true);
        return getOWLDataUnionOf(CollectionFactory.createSet(dataRanges));
    }

    @Nonnull
    @Override
    public OWLDataUnionOf getOWLDataUnionOf(@Nonnull Set<? extends OWLDataRange> dataRanges) {
        checkNull(dataRanges, DATA_RANGES, true);
        return new OWLDataUnionOfImpl(dataRanges);
    }

    @Nonnull
    @Override
    public OWLDatatypeRestriction getOWLDatatypeRestriction(OWLDatatype dataType,
        @Nonnull Set<OWLFacetRestriction> facetRestrictions) {
        checkNotNull(dataType, DATATYPE_CANNOT_BE_NULL);
        checkNull(facetRestrictions, "facets", true);
        return new OWLDatatypeRestrictionImpl(dataType, facetRestrictions);
    }

    @Nonnull
    @Override
    public OWLDatatypeRestriction getOWLDatatypeRestriction(OWLDatatype dataType, OWLFacet facet,
        OWLLiteral typedLiteral) {
        checkNotNull(dataType, DATATYPE_CANNOT_BE_NULL);
        checkNotNull(facet, "facet cannot be null");
        checkNotNull(typedLiteral, "typedConstant cannot be null");
        return new OWLDatatypeRestrictionImpl(dataType,
            CollectionFactory.createSet(getOWLFacetRestriction(facet, typedLiteral)));
    }

    @Override
    public OWLDatatypeRestriction getOWLDatatypeRestriction(OWLDatatype dataType,
        OWLFacetRestriction... facetRestrictions) {
        checkNull(facetRestrictions, "facetRestrictions", true);
        return getOWLDatatypeRestriction(dataType, CollectionFactory.createSet(facetRestrictions));
    }

    @Override
    public OWLDatatypeRestriction getOWLDatatypeMinInclusiveRestriction(int minInclusive) {
        return getOWLDatatypeRestriction(getIntegerOWLDatatype(), OWLFacet.MIN_INCLUSIVE,
            getOWLLiteral(minInclusive));
    }

    @Override
    public OWLDatatypeRestriction getOWLDatatypeMaxInclusiveRestriction(int maxInclusive) {
        return getOWLDatatypeRestriction(getIntegerOWLDatatype(), OWLFacet.MAX_INCLUSIVE,
            getOWLLiteral(maxInclusive));
    }

    @Override
    public OWLDatatypeRestriction getOWLDatatypeMinMaxInclusiveRestriction(int minInclusive,
        int maxInclusive) {
        return getOWLDatatypeRestriction(getIntegerOWLDatatype(),
            getOWLFacetRestriction(OWLFacet.MIN_INCLUSIVE, getOWLLiteral(minInclusive)),
            getOWLFacetRestriction(OWLFacet.MAX_INCLUSIVE, maxInclusive));
    }

    @Override
    public OWLDatatypeRestriction getOWLDatatypeMinExclusiveRestriction(int minExclusive) {
        return getOWLDatatypeRestriction(getIntegerOWLDatatype(), OWLFacet.MIN_EXCLUSIVE,
            getOWLLiteral(minExclusive));
    }

    @Override
    public OWLDatatypeRestriction getOWLDatatypeMaxExclusiveRestriction(int maxExclusive) {
        return getOWLDatatypeRestriction(getIntegerOWLDatatype(), OWLFacet.MAX_EXCLUSIVE,
            getOWLLiteral(maxExclusive));
    }

    @Override
    public OWLDatatypeRestriction getOWLDatatypeMinMaxExclusiveRestriction(int minExclusive,
        int maxExclusive) {
        return getOWLDatatypeRestriction(getIntegerOWLDatatype(),
            getOWLFacetRestriction(OWLFacet.MIN_EXCLUSIVE, getOWLLiteral(minExclusive)),
            getOWLFacetRestriction(OWLFacet.MAX_EXCLUSIVE, maxExclusive));
    }

    @Override
    public OWLDatatypeRestriction getOWLDatatypeMinInclusiveRestriction(double minInclusive) {
        return getOWLDatatypeRestriction(getDoubleOWLDatatype(), OWLFacet.MIN_INCLUSIVE,
            getOWLLiteral(minInclusive));
    }

    @Override
    public OWLDatatypeRestriction getOWLDatatypeMaxInclusiveRestriction(double maxInclusive) {
        return getOWLDatatypeRestriction(getDoubleOWLDatatype(), OWLFacet.MAX_INCLUSIVE,
            getOWLLiteral(maxInclusive));
    }

    @Override
    public OWLDatatypeRestriction getOWLDatatypeMinMaxInclusiveRestriction(double minInclusive,
        double maxInclusive) {
        return getOWLDatatypeRestriction(getDoubleOWLDatatype(),
            getOWLFacetRestriction(OWLFacet.MIN_INCLUSIVE, getOWLLiteral(minInclusive)),
            getOWLFacetRestriction(OWLFacet.MAX_INCLUSIVE, maxInclusive));
    }

    @Override
    public OWLDatatypeRestriction getOWLDatatypeMinExclusiveRestriction(double minExclusive) {
        return getOWLDatatypeRestriction(getDoubleOWLDatatype(), OWLFacet.MIN_EXCLUSIVE,
            getOWLLiteral(minExclusive));
    }

    @Override
    public OWLDatatypeRestriction getOWLDatatypeMaxExclusiveRestriction(double maxExclusive) {
        return getOWLDatatypeRestriction(getDoubleOWLDatatype(), OWLFacet.MAX_EXCLUSIVE,
            getOWLLiteral(maxExclusive));
    }

    @Override
    public OWLDatatypeRestriction getOWLDatatypeMinMaxExclusiveRestriction(double minExclusive,
        double maxExclusive) {
        return getOWLDatatypeRestriction(getDoubleOWLDatatype(),
            getOWLFacetRestriction(OWLFacet.MIN_EXCLUSIVE, getOWLLiteral(minExclusive)),
            getOWLFacetRestriction(OWLFacet.MAX_EXCLUSIVE, maxExclusive));
    }

    @Nonnull
    @Override
    public OWLFacetRestriction getOWLFacetRestriction(OWLFacet facet, int facetValue) {
        return getOWLFacetRestriction(facet, getOWLLiteral(facetValue));
    }

    @Nonnull
    @Override
    public OWLFacetRestriction getOWLFacetRestriction(OWLFacet facet, double facetValue) {
        return getOWLFacetRestriction(facet, getOWLLiteral(facetValue));
    }

    @Nonnull
    @Override
    public OWLFacetRestriction getOWLFacetRestriction(OWLFacet facet, float facetValue) {
        return getOWLFacetRestriction(facet, getOWLLiteral(facetValue));
    }

    @Nonnull
    @Override
    public OWLFacetRestriction getOWLFacetRestriction(OWLFacet facet, OWLLiteral facetValue) {
        checkNotNull(facet, "facet cannot be null");
        checkNotNull(facetValue, "facetValue cannot be null");
        return new OWLFacetRestrictionImpl(facet, facetValue);
    }

    @Nonnull
    @Override
    public OWLObjectIntersectionOf getOWLObjectIntersectionOf(
        @Nonnull Set<? extends OWLClassExpression> operands) {
        checkNull(operands, OPERANDS2, true);
        return new OWLObjectIntersectionOfImpl(operands);
    }

    @Override
    public OWLObjectIntersectionOf getOWLObjectIntersectionOf(OWLClassExpression... operands) {
        checkNull(operands, OPERANDS2, true);
        return getOWLObjectIntersectionOf(CollectionFactory.createSet(operands));
    }

    @Nonnull
    @Override
    public OWLDataAllValuesFrom getOWLDataAllValuesFrom(OWLDataPropertyExpression property,
        OWLDataRange dataRange) {
        checkNotNull(dataRange, DATA_RANGE_CANNOT_BE_NULL);
        checkNotNull(property, PROPERTY_CANNOT_BE_NULL);
        return new OWLDataAllValuesFromImpl(property, dataRange);
    }

    @Nonnull
    @Override
    public OWLDataExactCardinality getOWLDataExactCardinality(int cardinality,
        OWLDataPropertyExpression property) {
        checkNotNegative(cardinality, CARDINALITY_CANNOT_BE_NEGATIVE);
        checkNotNull(property, PROPERTY_CANNOT_BE_NULL);
        return new OWLDataExactCardinalityImpl(property, cardinality, getTopDatatype());
    }

    @Nonnull
    @Override
    public OWLDataExactCardinality getOWLDataExactCardinality(int cardinality,
        OWLDataPropertyExpression property, OWLDataRange dataRange) {
        checkNotNull(dataRange, DATA_RANGE_CANNOT_BE_NULL);
        checkNotNull(property, PROPERTY_CANNOT_BE_NULL);
        checkNotNegative(cardinality, CARDINALITY_CANNOT_BE_NEGATIVE);
        return new OWLDataExactCardinalityImpl(property, cardinality, dataRange);
    }

    @Nonnull
    @Override
    public OWLDataMaxCardinality getOWLDataMaxCardinality(int cardinality,
        OWLDataPropertyExpression property) {
        checkNotNegative(cardinality, CARDINALITY_CANNOT_BE_NEGATIVE);
        checkNotNull(property, PROPERTY_CANNOT_BE_NULL);
        return new OWLDataMaxCardinalityImpl(property, cardinality, getTopDatatype());
    }

    @Nonnull
    @Override
    public OWLDataMaxCardinality getOWLDataMaxCardinality(int cardinality,
        OWLDataPropertyExpression property, OWLDataRange dataRange) {
        checkNotNegative(cardinality, CARDINALITY_CANNOT_BE_NEGATIVE);
        checkNotNull(property, PROPERTY_CANNOT_BE_NULL);
        checkNotNull(dataRange, DATA_RANGE_CANNOT_BE_NULL);
        return new OWLDataMaxCardinalityImpl(property, cardinality, dataRange);
    }

    @Nonnull
    @Override
    public OWLDataMinCardinality getOWLDataMinCardinality(int cardinality,
        OWLDataPropertyExpression property) {
        checkNotNegative(cardinality, CARDINALITY_CANNOT_BE_NEGATIVE);
        checkNotNull(property, PROPERTY_CANNOT_BE_NULL);
        return new OWLDataMinCardinalityImpl(property, cardinality, getTopDatatype());
    }

    @Nonnull
    @Override
    public OWLDataMinCardinality getOWLDataMinCardinality(int cardinality,
        OWLDataPropertyExpression property, OWLDataRange dataRange) {
        checkNotNull(dataRange, DATA_RANGE_CANNOT_BE_NULL);
        checkNotNegative(cardinality, CARDINALITY_CANNOT_BE_NEGATIVE);
        checkNotNull(property, PROPERTY_CANNOT_BE_NULL);
        return new OWLDataMinCardinalityImpl(property, cardinality, dataRange);
    }

    @Nonnull
    @Override
    public OWLDataSomeValuesFrom getOWLDataSomeValuesFrom(OWLDataPropertyExpression property,
        OWLDataRange dataRange) {
        checkNotNull(dataRange, DATA_RANGE_CANNOT_BE_NULL);
        checkNotNull(property, PROPERTY_CANNOT_BE_NULL);
        return new OWLDataSomeValuesFromImpl(property, dataRange);
    }

    @Nonnull
    @Override
    public OWLDataHasValue getOWLDataHasValue(OWLDataPropertyExpression property,
        OWLLiteral value) {
        checkNotNull(property, PROPERTY_CANNOT_BE_NULL);
        checkNotNull(value, VALUE_CANNOT_BE_NULL);
        return new OWLDataHasValueImpl(property, value);
    }

    @Nonnull
    @Override
    public OWLObjectComplementOf getOWLObjectComplementOf(OWLClassExpression operand) {
        checkNotNull(operand, "operand");
        return new OWLObjectComplementOfImpl(operand);
    }

    @Nonnull
    @Override
    public OWLObjectAllValuesFrom getOWLObjectAllValuesFrom(OWLObjectPropertyExpression property,
        OWLClassExpression classExpression) {
        checkNotNull(classExpression, CLASS_EXPRESSION_CANNOT_BE_NULL);
        checkNotNull(property, PROPERTY_CANNOT_BE_NULL);
        return new OWLObjectAllValuesFromImpl(property, classExpression);
    }

    @Nonnull
    @Override
    public OWLObjectOneOf getOWLObjectOneOf(@Nonnull Set<? extends OWLIndividual> values) {
        checkNull(values, "values", true);
        return new OWLObjectOneOfImpl(values);
    }

    @Override
    public OWLObjectOneOf getOWLObjectOneOf(OWLIndividual... individuals) {
        checkNull(individuals, INDIVIDUALS2, true);
        return getOWLObjectOneOf(CollectionFactory.createSet(individuals));
    }

    @Nonnull
    @Override
    public OWLObjectExactCardinality getOWLObjectExactCardinality(int cardinality,
        OWLObjectPropertyExpression property) {
        checkNotNegative(cardinality, CARDINALITY_CANNOT_BE_NEGATIVE);
        checkNotNull(property, PROPERTY_CANNOT_BE_NULL);
        return new OWLObjectExactCardinalityImpl(property, cardinality, OWL_THING);
    }

    @Nonnull
    @Override
    public OWLObjectExactCardinality getOWLObjectExactCardinality(int cardinality,
        OWLObjectPropertyExpression property, OWLClassExpression classExpression) {
        checkNotNull(classExpression, CLASS_EXPRESSION_CANNOT_BE_NULL);
        checkNotNull(property, PROPERTY_CANNOT_BE_NULL);
        checkNotNegative(cardinality, CARDINALITY_CANNOT_BE_NEGATIVE);
        return new OWLObjectExactCardinalityImpl(property, cardinality, classExpression);
    }

    @Nonnull
    @Override
    public OWLObjectMinCardinality getOWLObjectMinCardinality(int cardinality,
        OWLObjectPropertyExpression property) {
        checkNotNegative(cardinality, CARDINALITY_CANNOT_BE_NEGATIVE);
        checkNotNull(property, PROPERTY_CANNOT_BE_NULL);
        return new OWLObjectMinCardinalityImpl(property, cardinality, OWL_THING);
    }

    @Nonnull
    @Override
    public OWLObjectMinCardinality getOWLObjectMinCardinality(int cardinality,
        OWLObjectPropertyExpression property, OWLClassExpression classExpression) {
        checkNotNull(classExpression, CLASS_EXPRESSION_CANNOT_BE_NULL);
        checkNotNull(property, PROPERTY_CANNOT_BE_NULL);
        checkNotNegative(cardinality, CARDINALITY_CANNOT_BE_NEGATIVE);
        return new OWLObjectMinCardinalityImpl(property, cardinality, classExpression);
    }

    @Nonnull
    @Override
    public OWLObjectMaxCardinality getOWLObjectMaxCardinality(int cardinality,
        OWLObjectPropertyExpression property) {
        checkNotNegative(cardinality, CARDINALITY_CANNOT_BE_NEGATIVE);
        checkNotNull(property, PROPERTY_CANNOT_BE_NULL);
        return new OWLObjectMaxCardinalityImpl(property, cardinality, OWL_THING);
    }

    @Nonnull
    @Override
    public OWLObjectMaxCardinality getOWLObjectMaxCardinality(int cardinality,
        OWLObjectPropertyExpression property, OWLClassExpression classExpression) {
        checkNotNegative(cardinality, CARDINALITY_CANNOT_BE_NEGATIVE);
        checkNotNull(classExpression, CLASS_EXPRESSION_CANNOT_BE_NULL);
        checkNotNull(property, PROPERTY_CANNOT_BE_NULL);
        return new OWLObjectMaxCardinalityImpl(property, cardinality, classExpression);
    }

    @Nonnull
    @Override
    public OWLObjectHasSelf getOWLObjectHasSelf(OWLObjectPropertyExpression property) {
        checkNotNull(property, PROPERTY_CANNOT_BE_NULL);
        return new OWLObjectHasSelfImpl(property);
    }

    @Nonnull
    @Override
    public OWLObjectSomeValuesFrom getOWLObjectSomeValuesFrom(OWLObjectPropertyExpression property,
        OWLClassExpression classExpression) {
        checkNotNull(classExpression, CLASS_EXPRESSION_CANNOT_BE_NULL);
        checkNotNull(property, PROPERTY_CANNOT_BE_NULL);
        return new OWLObjectSomeValuesFromImpl(property, classExpression);
    }

    @Nonnull
    @Override
    public OWLObjectHasValue getOWLObjectHasValue(OWLObjectPropertyExpression property,
        OWLIndividual individual) {
        checkNotNull(property, PROPERTY_CANNOT_BE_NULL);
        checkNotNull(individual, INDIVIDUAL_CANNOT_BE_NULL);
        return new OWLObjectHasValueImpl(property, individual);
    }

    @Nonnull
    @Override
    public OWLObjectUnionOf getOWLObjectUnionOf(
        @Nonnull Set<? extends OWLClassExpression> operands) {
        checkNull(operands, OPERANDS2, true);
        return new OWLObjectUnionOfImpl(operands);
    }

    @Override
    public OWLObjectUnionOf getOWLObjectUnionOf(OWLClassExpression... operands) {
        checkNull(operands, OPERANDS2, true);
        return getOWLObjectUnionOf(CollectionFactory.createSet(operands));
    }

    @Nonnull
    @Override
    public OWLAsymmetricObjectPropertyAxiom getOWLAsymmetricObjectPropertyAxiom(
        OWLObjectPropertyExpression propertyExpression,
        @Nonnull Set<? extends OWLAnnotation> annotations) {
        checkNotNull(propertyExpression, PROPERTY_CANNOT_BE_NULL);
        checkAnnotations(annotations);
        return new OWLAsymmetricObjectPropertyAxiomImpl(propertyExpression, annotations);
    }

    @Nonnull
    @Override
    public OWLAsymmetricObjectPropertyAxiom getOWLAsymmetricObjectPropertyAxiom(
        OWLObjectPropertyExpression propertyExpression) {
        return getOWLAsymmetricObjectPropertyAxiom(propertyExpression, EMPTY_ANNOTATIONS_SET);
    }

    @Nonnull
    @Override
    public OWLDataPropertyDomainAxiom getOWLDataPropertyDomainAxiom(
        OWLDataPropertyExpression property, OWLClassExpression domain,
        @Nonnull Set<? extends OWLAnnotation> annotations) {
        checkNotNull(property, PROPERTY_CANNOT_BE_NULL);
        checkNotNull(domain, "domain cannot be null");
        checkAnnotations(annotations);
        return new OWLDataPropertyDomainAxiomImpl(property, domain, annotations);
    }

    @Nonnull
    @Override
    public OWLDataPropertyDomainAxiom getOWLDataPropertyDomainAxiom(
        OWLDataPropertyExpression property, OWLClassExpression domain) {
        return getOWLDataPropertyDomainAxiom(property, domain, EMPTY_ANNOTATIONS_SET);
    }

    @Nonnull
    @Override
    public OWLDataPropertyRangeAxiom getOWLDataPropertyRangeAxiom(
        OWLDataPropertyExpression property, OWLDataRange owlDataRange,
        @Nonnull Set<? extends OWLAnnotation> annotations) {
        checkNotNull(property, PROPERTY_CANNOT_BE_NULL);
        checkNotNull(owlDataRange, "owlDataRange cannot be null");
        checkAnnotations(annotations);
        return new OWLDataPropertyRangeAxiomImpl(property, owlDataRange, annotations);
    }

    @Nonnull
    @Override
    public OWLDataPropertyRangeAxiom getOWLDataPropertyRangeAxiom(
        OWLDataPropertyExpression property, OWLDataRange owlDataRange) {
        return getOWLDataPropertyRangeAxiom(property, owlDataRange, EMPTY_ANNOTATIONS_SET);
    }

    @Nonnull
    @Override
    public OWLSubDataPropertyOfAxiom getOWLSubDataPropertyOfAxiom(
        OWLDataPropertyExpression subProperty, OWLDataPropertyExpression superProperty,
        @Nonnull Set<? extends OWLAnnotation> annotations) {
        checkNotNull(subProperty, "subProperty cannot be null");
        checkNotNull(superProperty, SUPER_PROPERTY_CANNOT_BE_NULL);
        checkAnnotations(annotations);
        return new OWLSubDataPropertyOfAxiomImpl(subProperty, superProperty, annotations);
    }

    @Nonnull
    @Override
    public OWLSubDataPropertyOfAxiom getOWLSubDataPropertyOfAxiom(
        OWLDataPropertyExpression subProperty, OWLDataPropertyExpression superProperty) {
        return getOWLSubDataPropertyOfAxiom(subProperty, superProperty, EMPTY_ANNOTATIONS_SET);
    }

    @Nonnull
    @Override
    public OWLDeclarationAxiom getOWLDeclarationAxiom(OWLEntity owlEntity) {
        return getOWLDeclarationAxiom(owlEntity, EMPTY_ANNOTATIONS_SET);
    }

    @Nonnull
    @Override
    public OWLDeclarationAxiom getOWLDeclarationAxiom(OWLEntity owlEntity,
        @Nonnull Set<? extends OWLAnnotation> annotations) {
        checkNotNull(owlEntity, "owlEntity cannot be null");
        checkAnnotations(annotations);
        return new OWLDeclarationAxiomImpl(owlEntity, annotations);
    }

    @Nonnull
    @Override
    public OWLDifferentIndividualsAxiom getOWLDifferentIndividualsAxiom(
        @Nonnull Set<? extends OWLIndividual> individuals,
        @Nonnull Set<? extends OWLAnnotation> annotations) {
        checkNull(individuals, INDIVIDUALS2, true);
        checkAnnotations(annotations);
        return new OWLDifferentIndividualsAxiomImpl(individuals, annotations);
    }

    @Override
    public OWLDifferentIndividualsAxiom getOWLDifferentIndividualsAxiom(
        OWLIndividual... individuals) {
        checkNull(individuals, INDIVIDUALS2, true);
        return getOWLDifferentIndividualsAxiom(CollectionFactory.createSet(individuals));
    }

    @Nonnull
    @Override
    public OWLDifferentIndividualsAxiom getOWLDifferentIndividualsAxiom(
        @Nonnull Set<? extends OWLIndividual> individuals) {
        return getOWLDifferentIndividualsAxiom(individuals, EMPTY_ANNOTATIONS_SET);
    }

    @Nonnull
    @Override
    public OWLDisjointClassesAxiom getOWLDisjointClassesAxiom(
        @Nonnull Set<? extends OWLClassExpression> classExpressions,
        @Nonnull Set<? extends OWLAnnotation> annotations) {
        checkNull(classExpressions, CLASS_EXPRESSIONS, true);
        checkAnnotations(annotations);
        // Hack to handle the case where classExpressions has only a single
        // member which will usually be the result of :x owl:disjointWith :x .
        if (classExpressions.size() == 1 && !config.shouldAllowDuplicatesInConstructSets()) {
            OWLClassExpression classExpression = classExpressions.iterator().next();
            if (classExpression.isOWLThing()) {
                throw new OWLRuntimeException(
                    "DisjointClasses(owl:Thing) cannot be created. It is not a syntactically valid OWL 2 axiom. If the intent is to declare owl:Thing as disjoint with itself and therefore empty, it cannot be created as a DisjointClasses axiom. Please rewrite it as SubClassOf(owl:Thing, owl:Nothing).");
            }
            if (classExpression.isOWLNothing()) {
                throw new OWLRuntimeException(
                    "DisjointClasses(owl:Nothing) cannot be created. It is not a syntactically valid OWL 2 axiom. If the intent is to declare owl:Nothing as disjoint with itself and therefore empty, it cannot be created as a DisjointClasses axiom, and it is also redundant as owl:Nothing is always empty. Please rewrite it as SubClassOf(owl:Nothing, owl:Nothing) or remove the axiom.");
            }
            Set<OWLClassExpression> modifiedClassExpressions = new HashSet<>(2);
            OWLClass addedClass = classExpression.isOWLThing() ? OWL_NOTHING : OWL_THING;
            modifiedClassExpressions.add(addedClass);
            modifiedClassExpressions.add(classExpression);
            return new OWLDisjointClassesAxiomImpl(modifiedClassExpressions,
                makeSingletonDisjoinClassWarningAnnotation(annotations, classExpression,
                    addedClass));
        }
        return new OWLDisjointClassesAxiomImpl(classExpressions, annotations);
    }

    @Nonnull
    protected Set<? extends OWLAnnotation> makeSingletonDisjoinClassWarningAnnotation(
        Set<? extends OWLAnnotation> annotations, OWLClassExpression classExpression,
        OWLClassExpression addedClass) {
        Set<OWLAnnotation> modifiedAnnotations = new HashSet<>(annotations.size() + 1);
        modifiedAnnotations.addAll(annotations);
        String provenanceComment =
            String.format("%s on %s", VersionInfo.getVersionInfo().getGeneratedByMessage(),
                new SimpleDateFormat().format(new Date()));
        OWLAnnotationImpl provenanceAnnotation = new OWLAnnotationImpl(RDFS_COMMENT,
            getOWLLiteral(provenanceComment), EMPTY_ANNOTATIONS_SET);
        Set<? extends OWLAnnotation> metaAnnotations = Collections.singleton(provenanceAnnotation);
        String changeComment =
            String.format("DisjointClasses(%s) replaced by DisjointClasses(%s %s)", classExpression,
                classExpression, addedClass);
        modifiedAnnotations.add(
            new OWLAnnotationImpl(RDFS_COMMENT, getOWLLiteral(changeComment), metaAnnotations));
        return modifiedAnnotations;
    }

    @Nonnull
    @Override
    public OWLDisjointClassesAxiom getOWLDisjointClassesAxiom(
        @Nonnull Set<? extends OWLClassExpression> classExpressions) {
        return getOWLDisjointClassesAxiom(classExpressions, EMPTY_ANNOTATIONS_SET);
    }

    @Nonnull
    @Override
    public OWLDisjointClassesAxiom getOWLDisjointClassesAxiom(
        OWLClassExpression... classExpressions) {
        checkNull(classExpressions, CLASS_EXPRESSIONS, true);
        Set<OWLClassExpression> clses = new HashSet<>();
        clses.addAll(Arrays.asList(classExpressions));
        return getOWLDisjointClassesAxiom(clses);
    }

    @Nonnull
    @Override
    public OWLDisjointDataPropertiesAxiom getOWLDisjointDataPropertiesAxiom(
        @Nonnull Set<? extends OWLDataPropertyExpression> properties,
        @Nonnull Set<? extends OWLAnnotation> annotations) {
        checkNull(properties, PROPERTIES2, true);
        checkAnnotations(annotations);
        return new OWLDisjointDataPropertiesAxiomImpl(properties, annotations);
    }

    @Nonnull
    @Override
    public OWLDisjointDataPropertiesAxiom getOWLDisjointDataPropertiesAxiom(
        @Nonnull Set<? extends OWLDataPropertyExpression> properties) {
        return getOWLDisjointDataPropertiesAxiom(properties, EMPTY_ANNOTATIONS_SET);
    }

    @Override
    public OWLDisjointDataPropertiesAxiom getOWLDisjointDataPropertiesAxiom(
        OWLDataPropertyExpression... dataProperties) {
        checkNull(dataProperties, PROPERTIES2, true);
        return getOWLDisjointDataPropertiesAxiom(CollectionFactory.createSet(dataProperties));
    }

    @Override
    public OWLDisjointObjectPropertiesAxiom getOWLDisjointObjectPropertiesAxiom(
        OWLObjectPropertyExpression... properties) {
        checkNull(properties, PROPERTIES2, true);
        return getOWLDisjointObjectPropertiesAxiom(CollectionFactory.createSet(properties));
    }

    @Nonnull
    @Override
    public OWLDisjointObjectPropertiesAxiom getOWLDisjointObjectPropertiesAxiom(
        @Nonnull Set<? extends OWLObjectPropertyExpression> properties) {
        return getOWLDisjointObjectPropertiesAxiom(properties, EMPTY_ANNOTATIONS_SET);
    }

    @Nonnull
    @Override
    public OWLDisjointObjectPropertiesAxiom getOWLDisjointObjectPropertiesAxiom(
        @Nonnull Set<? extends OWLObjectPropertyExpression> properties,
        @Nonnull Set<? extends OWLAnnotation> annotations) {
        checkNull(properties, PROPERTIES2, true);
        checkAnnotations(annotations);
        return new OWLDisjointObjectPropertiesAxiomImpl(properties, annotations);
    }

    @Nonnull
    @Override
    public OWLEquivalentClassesAxiom getOWLEquivalentClassesAxiom(
        @Nonnull Set<? extends OWLClassExpression> classExpressions,
        @Nonnull Set<? extends OWLAnnotation> annotations) {
        checkNull(classExpressions, CLASS_EXPRESSIONS, true);
        checkAnnotations(annotations);
        return new OWLEquivalentClassesAxiomImpl(classExpressions, annotations);
    }

    @Override
    public OWLEquivalentClassesAxiom getOWLEquivalentClassesAxiom(OWLClassExpression clsA,
        OWLClassExpression clsB) {
        checkNotNull(clsA, "clsA cannot be null");
        checkNotNull(clsB, "clsB cannot be null");
        return getOWLEquivalentClassesAxiom(clsA, clsB, EMPTY_ANNOTATIONS_SET);
    }

    @Override
    public OWLEquivalentClassesAxiom getOWLEquivalentClassesAxiom(OWLClassExpression clsA,
        OWLClassExpression clsB, Set<? extends OWLAnnotation> annotations) {
        checkNotNull(clsA, "clsA cannot be null");
        checkNotNull(clsB, "clsB cannot be null");
        return getOWLEquivalentClassesAxiom(CollectionFactory.createSet(clsA, clsB), annotations);
    }

    @Nonnull
    @Override
    public OWLEquivalentClassesAxiom getOWLEquivalentClassesAxiom(
        OWLClassExpression... classExpressions) {
        checkNull(classExpressions, CLASS_EXPRESSIONS, true);
        Set<OWLClassExpression> clses = new HashSet<>();
        clses.addAll(Arrays.asList(classExpressions));
        return getOWLEquivalentClassesAxiom(clses);
    }

    @Nonnull
    @Override
    public OWLEquivalentClassesAxiom getOWLEquivalentClassesAxiom(
        @Nonnull Set<? extends OWLClassExpression> classExpressions) {
        return getOWLEquivalentClassesAxiom(classExpressions, EMPTY_ANNOTATIONS_SET);
    }

    @Nonnull
    @Override
    public OWLEquivalentDataPropertiesAxiom getOWLEquivalentDataPropertiesAxiom(
        @Nonnull Set<? extends OWLDataPropertyExpression> properties,
        @Nonnull Set<? extends OWLAnnotation> annotations) {
        checkNull(properties, PROPERTIES2, true);
        checkAnnotations(annotations);
        return new OWLEquivalentDataPropertiesAxiomImpl(properties, annotations);
    }

    @Nonnull
    @Override
    public OWLEquivalentDataPropertiesAxiom getOWLEquivalentDataPropertiesAxiom(
        @Nonnull Set<? extends OWLDataPropertyExpression> properties) {
        return getOWLEquivalentDataPropertiesAxiom(properties, EMPTY_ANNOTATIONS_SET);
    }

    @Override
    public OWLEquivalentDataPropertiesAxiom getOWLEquivalentDataPropertiesAxiom(
        OWLDataPropertyExpression propertyA, OWLDataPropertyExpression propertyB) {
        return getOWLEquivalentDataPropertiesAxiom(propertyA, propertyB, EMPTY_ANNOTATIONS_SET);
    }

    @Override
    public OWLEquivalentDataPropertiesAxiom getOWLEquivalentDataPropertiesAxiom(
        OWLDataPropertyExpression propertyA, OWLDataPropertyExpression propertyB,
        Set<? extends OWLAnnotation> annotations) {
        checkNotNull(propertyA, "propertyA cannot be null");
        checkNotNull(propertyB, "propertyB cannot be null");
        return getOWLEquivalentDataPropertiesAxiom(
            CollectionFactory.createSet(propertyA, propertyB), annotations);
    }

    @Override
    public OWLEquivalentDataPropertiesAxiom getOWLEquivalentDataPropertiesAxiom(
        OWLDataPropertyExpression... properties) {
        checkNull(properties, PROPERTIES2, true);
        return getOWLEquivalentDataPropertiesAxiom(CollectionFactory.createSet(properties));
    }

    @Override
    public OWLEquivalentObjectPropertiesAxiom getOWLEquivalentObjectPropertiesAxiom(
        OWLObjectPropertyExpression... properties) {
        checkNull(properties, PROPERTIES2, true);
        return getOWLEquivalentObjectPropertiesAxiom(CollectionFactory.createSet(properties));
    }

    @Nonnull
    @Override
    public OWLEquivalentObjectPropertiesAxiom getOWLEquivalentObjectPropertiesAxiom(
        @Nonnull Set<? extends OWLObjectPropertyExpression> properties) {
        return getOWLEquivalentObjectPropertiesAxiom(properties, EMPTY_ANNOTATIONS_SET);
    }

    @Override
    public OWLEquivalentObjectPropertiesAxiom getOWLEquivalentObjectPropertiesAxiom(
        OWLObjectPropertyExpression propertyA, OWLObjectPropertyExpression propertyB) {
        checkNotNull(propertyA, "propertyA cannot be null");
        checkNotNull(propertyB, "propertyB cannot be null");
        return getOWLEquivalentObjectPropertiesAxiom(propertyA, propertyB, EMPTY_ANNOTATIONS_SET);
    }

    @Override
    public OWLEquivalentObjectPropertiesAxiom getOWLEquivalentObjectPropertiesAxiom(
        OWLObjectPropertyExpression propertyA, OWLObjectPropertyExpression propertyB,
        Set<? extends OWLAnnotation> annotations) {
        checkNotNull(propertyA, "propertyA cannot be null");
        checkNotNull(propertyB, "propertyB cannot be null");
        return getOWLEquivalentObjectPropertiesAxiom(
            CollectionFactory.createSet(propertyA, propertyB), annotations);
    }

    @Nonnull
    @Override
    public OWLFunctionalDataPropertyAxiom getOWLFunctionalDataPropertyAxiom(
        OWLDataPropertyExpression property, @Nonnull Set<? extends OWLAnnotation> annotations) {
        checkNotNull(property, PROPERTY_CANNOT_BE_NULL);
        checkAnnotations(annotations);
        return new OWLFunctionalDataPropertyAxiomImpl(property, annotations);
    }

    @Nonnull
    @Override
    public OWLFunctionalDataPropertyAxiom getOWLFunctionalDataPropertyAxiom(
        OWLDataPropertyExpression property) {
        return getOWLFunctionalDataPropertyAxiom(property, EMPTY_ANNOTATIONS_SET);
    }

    @Nonnull
    @Override
    public OWLFunctionalObjectPropertyAxiom getOWLFunctionalObjectPropertyAxiom(
        OWLObjectPropertyExpression property, @Nonnull Set<? extends OWLAnnotation> annotations) {
        checkNotNull(property, PROPERTY_CANNOT_BE_NULL);
        checkAnnotations(annotations);
        return new OWLFunctionalObjectPropertyAxiomImpl(property, annotations);
    }

    @Nonnull
    @Override
    public OWLFunctionalObjectPropertyAxiom getOWLFunctionalObjectPropertyAxiom(
        OWLObjectPropertyExpression property) {
        return getOWLFunctionalObjectPropertyAxiom(property, EMPTY_ANNOTATIONS_SET);
    }

    @Nonnull
    @Override
    public OWLImportsDeclaration getOWLImportsDeclaration(IRI importedOntologyIRI) {
        checkNotNull(importedOntologyIRI, "importedOntologyIRI cannot be null");
        return new OWLImportsDeclarationImpl(importedOntologyIRI);
    }

    @Nonnull
    @Override
    public OWLDataPropertyAssertionAxiom getOWLDataPropertyAssertionAxiom(
        OWLDataPropertyExpression property, OWLIndividual subject, OWLLiteral object,
        @Nonnull Set<? extends OWLAnnotation> annotations) {
        checkNotNull(property, PROPERTY_CANNOT_BE_NULL);
        checkNotNull(object, OBJECT_CANNOT_BE_NULL);
        checkNotNull(subject, SUBJECT_CANNOT_BE_NULL);
        checkAnnotations(annotations);
        return new OWLDataPropertyAssertionAxiomImpl(subject, property, object, annotations);
    }

    @Nonnull
    @Override
    public OWLDataPropertyAssertionAxiom getOWLDataPropertyAssertionAxiom(
        OWLDataPropertyExpression property, OWLIndividual subject, OWLLiteral object) {
        return getOWLDataPropertyAssertionAxiom(property, subject, object, EMPTY_ANNOTATIONS_SET);
    }

    @Nonnull
    @Override
    public OWLDataPropertyAssertionAxiom getOWLDataPropertyAssertionAxiom(
        OWLDataPropertyExpression property, OWLIndividual subject, int value) {
        return getOWLDataPropertyAssertionAxiom(property, subject, getOWLLiteral(value),
            EMPTY_ANNOTATIONS_SET);
    }

    @Nonnull
    @Override
    public OWLDataPropertyAssertionAxiom getOWLDataPropertyAssertionAxiom(
        OWLDataPropertyExpression property, OWLIndividual subject, double value) {
        return getOWLDataPropertyAssertionAxiom(property, subject, getOWLLiteral(value),
            EMPTY_ANNOTATIONS_SET);
    }

    @Nonnull
    @Override
    public OWLDataPropertyAssertionAxiom getOWLDataPropertyAssertionAxiom(
        OWLDataPropertyExpression property, OWLIndividual subject, float value) {
        return getOWLDataPropertyAssertionAxiom(property, subject, getOWLLiteral(value),
            EMPTY_ANNOTATIONS_SET);
    }

    @Nonnull
    @Override
    public OWLDataPropertyAssertionAxiom getOWLDataPropertyAssertionAxiom(
        OWLDataPropertyExpression property, OWLIndividual subject, boolean value) {
        return getOWLDataPropertyAssertionAxiom(property, subject, getOWLLiteral(value),
            EMPTY_ANNOTATIONS_SET);
    }

    @Nonnull
    @Override
    public OWLDataPropertyAssertionAxiom getOWLDataPropertyAssertionAxiom(
        OWLDataPropertyExpression property, OWLIndividual subject, String value) {
        return getOWLDataPropertyAssertionAxiom(property, subject, getOWLLiteral(value),
            EMPTY_ANNOTATIONS_SET);
    }

    @Nonnull
    @Override
    public OWLNegativeDataPropertyAssertionAxiom getOWLNegativeDataPropertyAssertionAxiom(
        OWLDataPropertyExpression property, OWLIndividual subject, OWLLiteral object) {
        return getOWLNegativeDataPropertyAssertionAxiom(property, subject, object,
            EMPTY_ANNOTATIONS_SET);
    }

    @Nonnull
    @Override
    public OWLNegativeDataPropertyAssertionAxiom getOWLNegativeDataPropertyAssertionAxiom(
        OWLDataPropertyExpression property, OWLIndividual subject, OWLLiteral object,
        @Nonnull Set<? extends OWLAnnotation> annotations) {
        checkNotNull(property, PROPERTY_CANNOT_BE_NULL);
        checkNotNull(object, OBJECT_CANNOT_BE_NULL);
        checkNotNull(subject, SUBJECT_CANNOT_BE_NULL);
        checkAnnotations(annotations);
        return new OWLNegativeDataPropertyAssertionAxiomImpl(subject, property, object,
            annotations);
    }

    @Nonnull
    @Override
    public OWLNegativeObjectPropertyAssertionAxiom getOWLNegativeObjectPropertyAssertionAxiom(
        OWLObjectPropertyExpression property, OWLIndividual subject, OWLIndividual object) {
        return getOWLNegativeObjectPropertyAssertionAxiom(property, subject, object,
            EMPTY_ANNOTATIONS_SET);
    }

    @Nonnull
    @Override
    public OWLNegativeObjectPropertyAssertionAxiom getOWLNegativeObjectPropertyAssertionAxiom(
        OWLObjectPropertyExpression property, OWLIndividual subject, OWLIndividual object,
        @Nonnull Set<? extends OWLAnnotation> annotations) {
        checkNotNull(property, PROPERTY_CANNOT_BE_NULL);
        checkNotNull(object, OBJECT_CANNOT_BE_NULL);
        checkNotNull(subject, SUBJECT_CANNOT_BE_NULL);
        checkAnnotations(annotations);
        return new OWLNegativeObjectPropertyAssertionAxiomImpl(subject, property, object,
            annotations);
    }

    @Nonnull
    @Override
    public OWLObjectPropertyAssertionAxiom getOWLObjectPropertyAssertionAxiom(
        OWLObjectPropertyExpression property, OWLIndividual individual, OWLIndividual object) {
        return getOWLObjectPropertyAssertionAxiom(property, individual, object,
            EMPTY_ANNOTATIONS_SET);
    }

    @Nonnull
    @Override
    public OWLClassAssertionAxiom getOWLClassAssertionAxiom(OWLClassExpression classExpression,
        OWLIndividual individual) {
        return getOWLClassAssertionAxiom(classExpression, individual, EMPTY_ANNOTATIONS_SET);
    }

    @Nonnull
    @Override
    public OWLClassAssertionAxiom getOWLClassAssertionAxiom(OWLClassExpression classExpression,
        OWLIndividual individual, @Nonnull Set<? extends OWLAnnotation> annotations) {
        checkNotNull(classExpression, CLASS_EXPRESSION_CANNOT_BE_NULL);
        checkNotNull(individual, INDIVIDUAL_CANNOT_BE_NULL);
        checkAnnotations(annotations);
        return new OWLClassAssertionAxiomImpl(individual, classExpression, annotations);
    }

    @Nonnull
    @Override
    public OWLInverseFunctionalObjectPropertyAxiom getOWLInverseFunctionalObjectPropertyAxiom(
        OWLObjectPropertyExpression property) {
        return getOWLInverseFunctionalObjectPropertyAxiom(property, EMPTY_ANNOTATIONS_SET);
    }

    @Nonnull
    @Override
    public OWLInverseFunctionalObjectPropertyAxiom getOWLInverseFunctionalObjectPropertyAxiom(
        OWLObjectPropertyExpression property, @Nonnull Set<? extends OWLAnnotation> annotations) {
        checkNotNull(property, PROPERTY_CANNOT_BE_NULL);
        checkAnnotations(annotations);
        return new OWLInverseFunctionalObjectPropertyAxiomImpl(property, annotations);
    }

    @Nonnull
    @Override
    public OWLIrreflexiveObjectPropertyAxiom getOWLIrreflexiveObjectPropertyAxiom(
        OWLObjectPropertyExpression property, @Nonnull Set<? extends OWLAnnotation> annotations) {
        checkNotNull(property, PROPERTY_CANNOT_BE_NULL);
        checkAnnotations(annotations);
        return new OWLIrreflexiveObjectPropertyAxiomImpl(property, annotations);
    }

    @Nonnull
    @Override
    public OWLReflexiveObjectPropertyAxiom getOWLReflexiveObjectPropertyAxiom(
        OWLObjectPropertyExpression property) {
        return getOWLReflexiveObjectPropertyAxiom(property, EMPTY_ANNOTATIONS_SET);
    }

    @Nonnull
    @Override
    public OWLIrreflexiveObjectPropertyAxiom getOWLIrreflexiveObjectPropertyAxiom(
        OWLObjectPropertyExpression property) {
        return getOWLIrreflexiveObjectPropertyAxiom(property, EMPTY_ANNOTATIONS_SET);
    }

    @Nonnull
    @Override
    public OWLObjectPropertyDomainAxiom getOWLObjectPropertyDomainAxiom(
        OWLObjectPropertyExpression property, OWLClassExpression classExpression,
        @Nonnull Set<? extends OWLAnnotation> annotations) {
        checkNotNull(property, PROPERTY_CANNOT_BE_NULL);
        checkNotNull(classExpression, CLASS_EXPRESSION_CANNOT_BE_NULL);
        checkAnnotations(annotations);
        return new OWLObjectPropertyDomainAxiomImpl(property, classExpression, annotations);
    }

    @Nonnull
    @Override
    public OWLObjectPropertyDomainAxiom getOWLObjectPropertyDomainAxiom(
        OWLObjectPropertyExpression property, OWLClassExpression classExpression) {
        return getOWLObjectPropertyDomainAxiom(property, classExpression, EMPTY_ANNOTATIONS_SET);
    }

    @Nonnull
    @Override
    public OWLObjectPropertyRangeAxiom getOWLObjectPropertyRangeAxiom(
        OWLObjectPropertyExpression property, OWLClassExpression range,
        @Nonnull Set<? extends OWLAnnotation> annotations) {
        checkNotNull(property, PROPERTY_CANNOT_BE_NULL);
        checkNotNull(range, "range cannot be null");
        checkAnnotations(annotations);
        return new OWLObjectPropertyRangeAxiomImpl(property, range, annotations);
    }

    @Nonnull
    @Override
    public OWLObjectPropertyRangeAxiom getOWLObjectPropertyRangeAxiom(
        OWLObjectPropertyExpression property, OWLClassExpression range) {
        return getOWLObjectPropertyRangeAxiom(property, range, EMPTY_ANNOTATIONS_SET);
    }

    @Nonnull
    @Override
    public OWLSubObjectPropertyOfAxiom getOWLSubObjectPropertyOfAxiom(
        OWLObjectPropertyExpression subProperty, OWLObjectPropertyExpression superProperty,
        @Nonnull Set<? extends OWLAnnotation> annotations) {
        checkNotNull(subProperty, "subProperty cannot be null");
        checkNotNull(superProperty, SUPER_PROPERTY_CANNOT_BE_NULL);
        checkAnnotations(annotations);
        return new OWLSubObjectPropertyOfAxiomImpl(subProperty, superProperty, annotations);
    }

    @Nonnull
    @Override
    public OWLSubObjectPropertyOfAxiom getOWLSubObjectPropertyOfAxiom(
        OWLObjectPropertyExpression subProperty, OWLObjectPropertyExpression superProperty) {
        return getOWLSubObjectPropertyOfAxiom(subProperty, superProperty, EMPTY_ANNOTATIONS_SET);
    }

    @Nonnull
    @Override
    public OWLReflexiveObjectPropertyAxiom getOWLReflexiveObjectPropertyAxiom(
        OWLObjectPropertyExpression property, @Nonnull Set<? extends OWLAnnotation> annotations) {
        checkNotNull(property, PROPERTY_CANNOT_BE_NULL);
        checkAnnotations(annotations);
        return new OWLReflexiveObjectPropertyAxiomImpl(property, annotations);
    }

    @Nonnull
    @Override
    public OWLSameIndividualAxiom getOWLSameIndividualAxiom(
        @Nonnull Set<? extends OWLIndividual> individuals,
        @Nonnull Set<? extends OWLAnnotation> annotations) {
        checkNull(individuals, INDIVIDUALS2, true);
        checkAnnotations(annotations);
        return new OWLSameIndividualAxiomImpl(individuals, annotations);
    }

    @Nonnull
    @Override
    public OWLSameIndividualAxiom getOWLSameIndividualAxiom(OWLIndividual... individual) {
        checkNull(individual, INDIVIDUALS2, true);
        Set<OWLIndividual> inds = new HashSet<>();
        inds.addAll(Arrays.asList(individual));
        return getOWLSameIndividualAxiom(inds);
    }

    @Nonnull
    @Override
    public OWLSameIndividualAxiom getOWLSameIndividualAxiom(
        @Nonnull Set<? extends OWLIndividual> individuals) {
        return getOWLSameIndividualAxiom(individuals, EMPTY_ANNOTATIONS_SET);
    }

    @Nonnull
    @Override
    public OWLSubClassOfAxiom getOWLSubClassOfAxiom(OWLClassExpression subClass,
        OWLClassExpression superClass, @Nonnull Set<? extends OWLAnnotation> annotations) {
        checkNotNull(subClass, "subclass cannot be null");
        checkNotNull(superClass, "superclass cannot be null");
        checkAnnotations(annotations);
        return new OWLSubClassOfAxiomImpl(subClass, superClass, annotations);
    }

    @Nonnull
    @Override
    public OWLSubClassOfAxiom getOWLSubClassOfAxiom(OWLClassExpression subClass,
        OWLClassExpression superClass) {
        return getOWLSubClassOfAxiom(subClass, superClass, EMPTY_ANNOTATIONS_SET);
    }

    @Nonnull
    @Override
    public OWLSymmetricObjectPropertyAxiom getOWLSymmetricObjectPropertyAxiom(
        OWLObjectPropertyExpression property, @Nonnull Set<? extends OWLAnnotation> annotations) {
        checkNotNull(property, PROPERTY_CANNOT_BE_NULL);
        checkAnnotations(annotations);
        return new OWLSymmetricObjectPropertyAxiomImpl(property, annotations);
    }

    @Nonnull
    @Override
    public OWLSymmetricObjectPropertyAxiom getOWLSymmetricObjectPropertyAxiom(
        OWLObjectPropertyExpression property) {
        return getOWLSymmetricObjectPropertyAxiom(property, EMPTY_ANNOTATIONS_SET);
    }

    @Nonnull
    @Override
    public OWLTransitiveObjectPropertyAxiom getOWLTransitiveObjectPropertyAxiom(
        OWLObjectPropertyExpression property, @Nonnull Set<? extends OWLAnnotation> annotations) {
        checkNotNull(property, PROPERTY_CANNOT_BE_NULL);
        checkAnnotations(annotations);
        return new OWLTransitiveObjectPropertyAxiomImpl(property, annotations);
    }

    @Nonnull
    @Override
    public OWLTransitiveObjectPropertyAxiom getOWLTransitiveObjectPropertyAxiom(
        OWLObjectPropertyExpression property) {
        return getOWLTransitiveObjectPropertyAxiom(property, EMPTY_ANNOTATIONS_SET);
    }

    @Nonnull
    @Override
    public OWLObjectInverseOf getOWLObjectInverseOf(OWLObjectPropertyExpression property) {
        checkNotNull(property, PROPERTY_CANNOT_BE_NULL);
        if (!(property instanceof OWLObjectProperty)) {
            throw new IllegalArgumentException(
                "ObjectInverseOf can only be applied to Object Properties");
        }
        return new OWLObjectInverseOfImpl(property);
    }

    @Nonnull
    @Override
    public OWLInverseObjectPropertiesAxiom getOWLInverseObjectPropertiesAxiom(
        OWLObjectPropertyExpression forwardProperty, OWLObjectPropertyExpression inverseProperty,
        @Nonnull Set<? extends OWLAnnotation> annotations) {
        checkNotNull(forwardProperty, "forwardProperty cannot be null");
        checkNotNull(inverseProperty, "inverseProperty cannot be null");
        checkAnnotations(annotations);
        return new OWLInverseObjectPropertiesAxiomImpl(forwardProperty, inverseProperty,
            annotations);
    }

    @Nonnull
    @Override
    public OWLInverseObjectPropertiesAxiom getOWLInverseObjectPropertiesAxiom(
        OWLObjectPropertyExpression forwardProperty, OWLObjectPropertyExpression inverseProperty) {
        return getOWLInverseObjectPropertiesAxiom(forwardProperty, inverseProperty,
            EMPTY_ANNOTATIONS_SET);
    }

    @Nonnull
    @Override
    public OWLSubPropertyChainOfAxiom getOWLSubPropertyChainOfAxiom(
        @Nonnull List<? extends OWLObjectPropertyExpression> chain,
        OWLObjectPropertyExpression superProperty,
        @Nonnull Set<? extends OWLAnnotation> annotations) {
        checkNotNull(superProperty, SUPER_PROPERTY_CANNOT_BE_NULL);
        checkNull(chain, "chain", true);
        checkAnnotations(annotations);
        return new OWLSubPropertyChainAxiomImpl(chain, superProperty, annotations);
    }

    @Nonnull
    @Override
    public OWLSubPropertyChainOfAxiom getOWLSubPropertyChainOfAxiom(
        @Nonnull List<? extends OWLObjectPropertyExpression> chain,
        OWLObjectPropertyExpression superProperty) {
        return getOWLSubPropertyChainOfAxiom(chain, superProperty, EMPTY_ANNOTATIONS_SET);
    }

    @Nonnull
    @Override
    public OWLHasKeyAxiom getOWLHasKeyAxiom(OWLClassExpression ce,
        @Nonnull Set<? extends OWLPropertyExpression> objectProperties,
        @Nonnull Set<? extends OWLAnnotation> annotations) {
        checkNotNull(ce, CLASS_EXPRESSION_CANNOT_BE_NULL);
        checkNull(objectProperties, PROPERTIES2, true);
        checkAnnotations(annotations);
        return new OWLHasKeyAxiomImpl(ce, objectProperties, annotations);
    }

    @Nonnull
    @Override
    public OWLHasKeyAxiom getOWLHasKeyAxiom(OWLClassExpression ce,
        @Nonnull Set<? extends OWLPropertyExpression> properties) {
        return getOWLHasKeyAxiom(ce, properties, EMPTY_ANNOTATIONS_SET);
    }

    @Override
    public OWLHasKeyAxiom getOWLHasKeyAxiom(OWLClassExpression ce,
        OWLPropertyExpression... properties) {
        checkNotNull(ce, CLASS_EXPRESSION_CANNOT_BE_NULL);
        checkNull(properties, PROPERTIES2, true);
        return getOWLHasKeyAxiom(ce, CollectionFactory.createSet(properties));
    }

    @Nonnull
    @Override
    public OWLDisjointUnionAxiom getOWLDisjointUnionAxiom(OWLClass owlClass,
        @Nonnull Set<? extends OWLClassExpression> classExpressions,
        @Nonnull Set<? extends OWLAnnotation> annotations) {
        checkNotNull(owlClass, "owlClass cannot be null");
        checkNull(classExpressions, CLASS_EXPRESSIONS, true);
        checkAnnotations(annotations);
        return new OWLDisjointUnionAxiomImpl(owlClass, classExpressions, annotations);
    }

    @Nonnull
    @Override
    public OWLDisjointUnionAxiom getOWLDisjointUnionAxiom(OWLClass owlClass,
        @Nonnull Set<? extends OWLClassExpression> classExpressions) {
        return getOWLDisjointUnionAxiom(owlClass, classExpressions, EMPTY_ANNOTATIONS_SET);
    }

    @Nonnull
    @Override
    public OWLEquivalentObjectPropertiesAxiom getOWLEquivalentObjectPropertiesAxiom(
        @Nonnull Set<? extends OWLObjectPropertyExpression> properties,
        @Nonnull Set<? extends OWLAnnotation> annotations) {
        checkNull(properties, PROPERTIES2, true);
        checkAnnotations(annotations);
        return new OWLEquivalentObjectPropertiesAxiomImpl(properties, annotations);
    }

    @Nonnull
    @Override
    public OWLObjectPropertyAssertionAxiom getOWLObjectPropertyAssertionAxiom(
        OWLObjectPropertyExpression property, OWLIndividual individual, OWLIndividual object,
        @Nonnull Set<? extends OWLAnnotation> annotations) {
        checkNotNull(property, PROPERTY_CANNOT_BE_NULL);
        checkNotNull(individual, INDIVIDUAL_CANNOT_BE_NULL);
        checkNotNull(object, OBJECT_CANNOT_BE_NULL);
        checkAnnotations(annotations);
        return new OWLObjectPropertyAssertionAxiomImpl(individual, property, object, annotations);
    }

    @Nonnull
    @Override
    public OWLSubAnnotationPropertyOfAxiom getOWLSubAnnotationPropertyOfAxiom(
        OWLAnnotationProperty sub, OWLAnnotationProperty sup) {
        return getOWLSubAnnotationPropertyOfAxiom(sub, sup, EMPTY_ANNOTATIONS_SET);
    }

    @Nonnull
    @Override
    public OWLSubAnnotationPropertyOfAxiom getOWLSubAnnotationPropertyOfAxiom(
        OWLAnnotationProperty sub, OWLAnnotationProperty sup,
        @Nonnull Set<? extends OWLAnnotation> annotations) {
        checkNotNull(sub, "subProperty cannot be null");
        checkNotNull(sup, SUPER_PROPERTY_CANNOT_BE_NULL);
        checkAnnotations(annotations);
        return new OWLSubAnnotationPropertyOfAxiomImpl(sub, sup, annotations);
    }

    // Annotations
    @Override
    public OWLAnnotationProperty getOWLAnnotationProperty(IRI iri) {
        checkNotNull(iri, IRI_CANNOT_BE_NULL);
        return dataFactoryInternals.getOWLAnnotationProperty(iri);
    }

    @Nonnull
    @Override
    public OWLAnnotation getOWLAnnotation(OWLAnnotationProperty property,
        OWLAnnotationValue value) {
        return getOWLAnnotation(property, value, EMPTY_ANNOTATIONS_SET);
    }

    @Nonnull
    @Override
    public OWLAnnotation getOWLAnnotation(OWLAnnotationProperty property, OWLAnnotationValue value,
        @Nonnull Set<? extends OWLAnnotation> annotations) {
        checkNotNull(property, PROPERTY_CANNOT_BE_NULL);
        checkNotNull(value, VALUE_CANNOT_BE_NULL);
        checkAnnotations(annotations);
        return dataFactoryInternals.getOWLAnnotation(property, value, annotations);
    }

    @Nonnull
    @Override
    public OWLAnnotationAssertionAxiom getOWLAnnotationAssertionAxiom(OWLAnnotationSubject subject,
        @Nonnull OWLAnnotation annotation) {
        checkNotNull(annotation, "annotation cannot be null");
        return getOWLAnnotationAssertionAxiom(annotation.getProperty(), subject,
            annotation.getValue(), annotation.getAnnotations());
    }

    @Nonnull
    @Override
    public OWLAnnotationAssertionAxiom getOWLAnnotationAssertionAxiom(OWLAnnotationSubject subject,
        @Nonnull OWLAnnotation annotation, @Nonnull Set<? extends OWLAnnotation> annotations) {
        checkNotNull(annotation, "annotation cannot be null");
        return getOWLAnnotationAssertionAxiom(annotation.getProperty(), subject,
            annotation.getValue(), annotations);
    }

    @Nonnull
    @Override
    public OWLAnnotationAssertionAxiom getOWLAnnotationAssertionAxiom(
        OWLAnnotationProperty property, OWLAnnotationSubject subject, OWLAnnotationValue value) {
        return getOWLAnnotationAssertionAxiom(property, subject, value, EMPTY_ANNOTATIONS_SET);
    }

    @Nonnull
    @Override
    public OWLAnnotationAssertionAxiom getOWLAnnotationAssertionAxiom(
        OWLAnnotationProperty property, OWLAnnotationSubject subject, OWLAnnotationValue value,
        @Nonnull Set<? extends OWLAnnotation> annotations) {
        checkNotNull(subject, SUBJECT_CANNOT_BE_NULL);
        checkNotNull(property, PROPERTY_CANNOT_BE_NULL);
        checkNotNull(value, VALUE_CANNOT_BE_NULL);
        checkAnnotations(annotations);
        return new OWLAnnotationAssertionAxiomImpl(subject, property, value, annotations);
    }

    @Override
    public OWLAnnotationAssertionAxiom getDeprecatedOWLAnnotationAssertionAxiom(IRI subject) {
        checkNotNull(subject, SUBJECT_CANNOT_BE_NULL);
        return getOWLAnnotationAssertionAxiom(getOWLDeprecated(), subject, getOWLLiteral(true));
    }

    @Nonnull
    @Override
    public OWLAnnotationPropertyDomainAxiom getOWLAnnotationPropertyDomainAxiom(
        OWLAnnotationProperty prop, IRI domain, @Nonnull Set<? extends OWLAnnotation> annotations) {
        checkNotNull(prop, PROPERTY_CANNOT_BE_NULL);
        checkNotNull(domain, "domain cannot be null");
        checkAnnotations(annotations);
        return new OWLAnnotationPropertyDomainAxiomImpl(prop, domain, annotations);
    }

    @Nonnull
    @Override
    public OWLAnnotationPropertyDomainAxiom getOWLAnnotationPropertyDomainAxiom(
        OWLAnnotationProperty prop, IRI domain) {
        return getOWLAnnotationPropertyDomainAxiom(prop, domain, EMPTY_ANNOTATIONS_SET);
    }

    @Nonnull
    @Override
    public OWLAnnotationPropertyRangeAxiom getOWLAnnotationPropertyRangeAxiom(
        OWLAnnotationProperty prop, IRI range, @Nonnull Set<? extends OWLAnnotation> annotations) {
        checkNotNull(prop, PROPERTY_CANNOT_BE_NULL);
        checkNotNull(range, "range cannot be null");
        checkAnnotations(annotations);
        return new OWLAnnotationPropertyRangeAxiomImpl(prop, range, annotations);
    }

    @Nonnull
    @Override
    public OWLAnnotationPropertyRangeAxiom getOWLAnnotationPropertyRangeAxiom(
        OWLAnnotationProperty prop, IRI range) {
        return getOWLAnnotationPropertyRangeAxiom(prop, range, EMPTY_ANNOTATIONS_SET);
    }

    // SWRL
    @Nonnull
    @Override
    public SWRLRule getSWRLRule(@Nonnull Set<? extends SWRLAtom> body,
        @Nonnull Set<? extends SWRLAtom> head, @Nonnull Set<OWLAnnotation> annotations) {
        checkNull(body, "body", true);
        checkNull(head, "head", true);
        checkAnnotations(annotations);
        return new SWRLRuleImpl(body, head, annotations);
    }

    @Nonnull
    @Override
    public SWRLRule getSWRLRule(@Nonnull Set<? extends SWRLAtom> body,
        @Nonnull Set<? extends SWRLAtom> head) {
        checkNull(body, "antecedent", true);
        checkNull(head, "consequent", true);
        return new SWRLRuleImpl(body, head);
    }

    @Nonnull
    @Override
    public SWRLClassAtom getSWRLClassAtom(OWLClassExpression predicate, SWRLIArgument arg) {
        checkNotNull(predicate, "predicate cannot be null");
        checkNotNull(arg, "arg cannot be null");
        return new SWRLClassAtomImpl(predicate, arg);
    }

    @Nonnull
    @Override
    public SWRLDataRangeAtom getSWRLDataRangeAtom(OWLDataRange predicate, SWRLDArgument arg) {
        checkNotNull(predicate, "predicate cannot be null");
        checkNotNull(arg, "arg cannot be null");
        return new SWRLDataRangeAtomImpl(predicate, arg);
    }

    @Nonnull
    @Override
    public SWRLObjectPropertyAtom getSWRLObjectPropertyAtom(OWLObjectPropertyExpression property,
        SWRLIArgument arg0, SWRLIArgument arg1) {
        checkNotNull(property, PROPERTY_CANNOT_BE_NULL);
        checkNotNull(arg0, ARG0_CANNOT_BE_NULL);
        checkNotNull(arg1, ARG1_CANNOT_BE_NULL);
        return new SWRLObjectPropertyAtomImpl(property, arg0, arg1);
    }

    @Nonnull
    @Override
    public SWRLDataPropertyAtom getSWRLDataPropertyAtom(OWLDataPropertyExpression property,
        SWRLIArgument arg0, SWRLDArgument arg1) {
        checkNotNull(property, PROPERTY_CANNOT_BE_NULL);
        checkNotNull(arg0, ARG0_CANNOT_BE_NULL);
        checkNotNull(arg1, ARG1_CANNOT_BE_NULL);
        return new SWRLDataPropertyAtomImpl(property, arg0, arg1);
    }

    @Nonnull
    @Override
    public SWRLBuiltInAtom getSWRLBuiltInAtom(IRI builtInIRI, List<SWRLDArgument> args) {
        checkNotNull(builtInIRI, "builtInIRI cannot be null");
        checkNotNull(args, "args cannot be null");
        return new SWRLBuiltInAtomImpl(builtInIRI, args);
    }

    @Nonnull
    @Override
    public SWRLVariable getSWRLVariable(IRI var) {
        checkNotNull(var, "var cannot be null");
        return new SWRLVariableImpl(var);
    }

    @Nonnull
    @Override
    public SWRLIndividualArgument getSWRLIndividualArgument(OWLIndividual individual) {
        checkNotNull(individual, INDIVIDUAL_CANNOT_BE_NULL);
        return new SWRLIndividualArgumentImpl(individual);
    }

    @Nonnull
    @Override
    public SWRLLiteralArgument getSWRLLiteralArgument(OWLLiteral literal) {
        checkNotNull(literal, "literal");
        return new SWRLLiteralArgumentImpl(literal);
    }

    @Nonnull
    @Override
    public SWRLDifferentIndividualsAtom getSWRLDifferentIndividualsAtom(SWRLIArgument arg0,
        SWRLIArgument arg1) {
        checkNotNull(arg0, ARG0_CANNOT_BE_NULL);
        checkNotNull(arg1, ARG1_CANNOT_BE_NULL);
        return new SWRLDifferentIndividualsAtomImpl(
            getOWLObjectProperty(OWLRDFVocabulary.OWL_DIFFERENT_FROM.getIRI()), arg0, arg1);
    }

    @Nonnull
    @Override
    public SWRLSameIndividualAtom getSWRLSameIndividualAtom(SWRLIArgument arg0,
        SWRLIArgument arg1) {
        checkNotNull(arg0, ARG0_CANNOT_BE_NULL);
        checkNotNull(arg1, ARG1_CANNOT_BE_NULL);
        return new SWRLSameIndividualAtomImpl(
            getOWLObjectProperty(OWLRDFVocabulary.OWL_SAME_AS.getIRI()), arg0, arg1);
    }

    @Nonnull
    private static final Set<OWLAnnotation> EMPTY_ANNOTATIONS_SET = CollectionFactory.emptySet();

    @Nonnull
    @Override
    public OWLDatatypeDefinitionAxiom getOWLDatatypeDefinitionAxiom(OWLDatatype datatype,
        OWLDataRange dataRange) {
        checkNotNull(datatype, DATATYPE_CANNOT_BE_NULL);
        checkNotNull(dataRange, DATA_RANGE_CANNOT_BE_NULL);
        return getOWLDatatypeDefinitionAxiom(datatype, dataRange, EMPTY_ANNOTATIONS_SET);
    }

    @Nonnull
    @Override
    public OWLDatatypeDefinitionAxiom getOWLDatatypeDefinitionAxiom(OWLDatatype datatype,
        OWLDataRange dataRange, @Nonnull Set<? extends OWLAnnotation> annotations) {
        checkNotNull(datatype, DATATYPE_CANNOT_BE_NULL);
        checkNotNull(dataRange, DATA_RANGE_CANNOT_BE_NULL);
        checkAnnotations(annotations);
        return new OWLDatatypeDefinitionAxiomImpl(datatype, dataRange, annotations);
    }

    @Override
    public OWLLiteral getOWLLiteral(String lexicalValue, OWLDatatype datatype) {
        checkNotNull(lexicalValue, "lexicalValue cannot be null");
        checkNotNull(datatype, DATATYPE_CANNOT_BE_NULL);
        return dataFactoryInternals.getOWLLiteral(lexicalValue, datatype);
    }

    @Override
    public OWLLiteral getOWLLiteral(int value) {
        return dataFactoryInternals.getOWLLiteral(value);
    }

    @Override
    public OWLLiteral getOWLLiteral(double value) {
        return dataFactoryInternals.getOWLLiteral(value);
    }

    @Override
    public OWLLiteral getOWLLiteral(float value) {
        return dataFactoryInternals.getOWLLiteral(value);
    }

    @Override
    public OWLLiteral getOWLLiteral(String value) {
        checkNotNull(value, VALUE_CANNOT_BE_NULL);
        return dataFactoryInternals.getOWLLiteral(value);
    }

    @Override
    public OWLLiteral getOWLLiteral(String literal, String lang) {
        checkNotNull(literal, "literal cannot be null");
        return dataFactoryInternals.getOWLLiteral(literal, lang);
    }

    @Override
    public OWLDatatype getBooleanOWLDatatype() {
        return dataFactoryInternals.getBooleanOWLDatatype();
    }

    @Override
    public OWLDatatype getDoubleOWLDatatype() {
        return dataFactoryInternals.getDoubleOWLDatatype();
    }

    @Override
    public OWLDatatype getFloatOWLDatatype() {
        return dataFactoryInternals.getFloatOWLDatatype();
    }

    @Override
    public OWLDatatype getIntegerOWLDatatype() {
        return dataFactoryInternals.getIntegerOWLDatatype();
    }

    @Override
    public OWLDatatype getTopDatatype() {
        return dataFactoryInternals.getTopDatatype();
    }

    @Override
    public OWLDatatype getRDFPlainLiteral() {
        return dataFactoryInternals.getRDFPlainLiteral();
    }
}
