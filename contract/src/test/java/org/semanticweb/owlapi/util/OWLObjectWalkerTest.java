package org.semanticweb.owlapi.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.semanticweb.owlapi.util.StructureWalker.AnnotationWalkingControl.DONT_WALK_ANNOTATIONS;
import static org.semanticweb.owlapi.util.StructureWalker.AnnotationWalkingControl.WALK_ANNOTATIONS;
import static org.semanticweb.owlapi.util.StructureWalker.AnnotationWalkingControl.WALK_ONTOLOGY_ANNOTATIONS_ONLY;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.model.AddOntologyAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * Created by ses on 8/15/15.
 */
class OWLObjectWalkerTest extends TestBase {

    private OWLAnnotation world;
    private OWLAnnotation cruelWorld;
    private OWLAnnotation goodbye;
    private OWLAnnotation hello;

    private static void checkWalkWithFlags(OWLOntology o,
        StructureWalker.AnnotationWalkingControl walkFlag, List<OWLAnnotation> expected) {
        final List<OWLAnnotation> visitedAnnotations = new ArrayList<>();

        OWLObjectVisitor visitor = new OWLObjectVisitorAdapter() {
            @Override
            public void visit(OWLAnnotation node) {
                visitedAnnotations.add(node);
            }
        };
        Set<? extends OWLObject> ontologySet = Collections.singleton(o);
        OWLObjectWalker<? extends OWLObject> walker;
        if (walkFlag == WALK_ONTOLOGY_ANNOTATIONS_ONLY) {
            walker = new OWLObjectWalker<>(ontologySet);
        } else {
            walker = new OWLObjectWalker<>(ontologySet, walkFlag);
        }
        walker.walkStructure(visitor);
        assertEquals(expected, visitedAnnotations);
    }

    @BeforeEach
    void setUp() {
        cruelWorld = df.getOWLAnnotation(AP, df.getOWLLiteral("cruel world"));
        goodbye = df.getOWLAnnotation(AP, df.getOWLLiteral("goodbye"), singleton(cruelWorld));
        world = df.getOWLAnnotation(AP, df.getOWLLiteral("world"));
        hello = df.getOWLAnnotation(AP, df.getOWLLiteral("hello"), singleton(world));
    }

    @Test
    void testWalkAnnotations() {
        OWLOntology o = getOwlOntology();
        List<OWLAnnotation> emptyAnnotationList = Collections.emptyList();
        checkWalkWithFlags(o, DONT_WALK_ANNOTATIONS, emptyAnnotationList);
        checkWalkWithFlags(o, WALK_ONTOLOGY_ANNOTATIONS_ONLY, Arrays.asList(hello));
        checkWalkWithFlags(o, WALK_ANNOTATIONS, Arrays.asList(hello, world, goodbye, cruelWorld));
    }

    @Nonnull
    private OWLOntology getOwlOntology() {
        OWLOntology o = create("foo");
        m.applyChange(new AddOntologyAnnotation(o, hello));
        addAxiom(o, df.getOWLDeclarationAxiom(AP, singleton(goodbye)));
        return o;
    }
}
