# Axon Framework 5 - Getting Started

This repository is my personal implementation of the Axon Framework 5, Getting Started, as
described [here](https://docs.axoniq.io/axon-framework-5-getting-started/). If you are looking for the
AxonIQ-implementation of the Getting Started, please check [this repository](https://github.com/AxonIQ/university-demo/)
instead.

This Getting Started for the next major installment of Axon Framework follows the "Axon University" domain.
This domain consists out of "Students" and "Courses" (for now).

It is implemented following
a ["Vertical Slice Architecture"](https://www.baeldung.com/java-vertical-slice-architecture), for which Axon Framework
has improved support compared to previous major releases.
Note that although this style is chosen for **this** sample, that you are still able to follow the "old" aggregate
approach!

Other choices that are taken by this project are:

1. Java as the language, JDK21 to be exact - the new baseline version of Axon Framework 5
2. Maven for dependency management
3. JUnit for testing
4. Annotation-based handlers and state containers
5. No Spring support **yet!** - the next milestone of Axon Framework 5 will provide Spring support
