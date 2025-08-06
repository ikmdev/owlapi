# OWLAPI

This is a fork of the [version 4 branch](https://github.com/owlcs/owlapi/tree/version4) of the [OWL API](https://github.com/owlcs/owlapi) at version 4.5.25

Primary changes:

* Implement modules (JPMS)
* Remove some modules, retaining core API and parsers
* Replace guava with java.util and Eclipse collections

* Requires Java 24.
* Requires Maven 3.9.11
* Requires Git

To build on Unix/Linux/OSX: `./mvnw clean install`

On Windows: `./mvnw.cmd clean install`

Available at [Maven Central](https://central.sonatype.com/namespace/dev.ikm.owlapi)

### Team Ownership - Product Owner

Data Team - Eric Mays (External) <emays@mays-systems.com>

## Issues and Contributions
Technical and non-technical issues can be reported to the [Issue Tracker](https://github.com/ikmdev/owlapi/issues).

Contributions can be submitted via pull requests. Please check the [contribution guide](doc/how-to-contribute.md) for more details.
