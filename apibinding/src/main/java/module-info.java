open module org.semanticweb.owlapi.apibinding {

	requires dev.ikm.jpms.javax.annotation;

	requires transitive org.semanticweb.owlapi;
	requires transitive org.semanticweb.owlapi.impl;

	exports org.semanticweb.owlapi.apibinding;

	provides org.semanticweb.owlapi.model.OWLOntologyManagerFactory with org.semanticweb.owlapi.apibinding.OWLManager;

}