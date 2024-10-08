open module org.semanticweb.owlapi.parsers {

	requires java.xml;
	
	requires dev.ikm.jpms.google.common;
	requires dev.ikm.jpms.javax.annotation;
	requires dev.ikm.jpms.javax.inject;
	
	requires org.slf4j;

	requires transitive org.semanticweb.owlapi;

//	exports org.semanticweb.owlapi.functional;
	exports org.semanticweb.owlapi.functional.parser;
	exports org.semanticweb.owlapi.functional.renderer;

}