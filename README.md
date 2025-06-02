# Axon Framework 5 - Getting Started

This repository is my personal implementation of the Axon Framework 5, Getting Started, as
described [here](https://docs.axoniq.io/axon-framework-5-getting-started/). If you are looking for the
AxonIQ-implementation of the Getting Started, please check [this repository](https://github.com/AxonIQ/university-demo/)
instead.

This Getting Started for the next major installment of Axon Framework follows the "Axon University" domain.
This domain consists out of "Students" and "Courses" (for now).

It is implemented following
a ["Vertical Slice Architecture"](https://www.baeldung.com/java-vertical-slice-architecture), for which Axon Framework
has improved support compared to previous major releases. A large part of this support, is the introduction of the
Dynamic Consistency Boundary (DCB for short) support of the Event Store. This approach means we'll have the following
packages present in our application:

1. The application package, called `university` in this example.
2. A bounded-context package, called `faculty` in this example.
3. An `events` package per bounded-context, containing that context's events. For now, there is a single `events`
   package in the `faculty` package.
4. A `write` package per bounded-context, containing packages **per** write-slice. Read and automation slices are not
   implemented **yet**.

Note that although a Vertical-slice-style is chosen for this sample, that you are still able to follow the "old"
aggregate approach!

Other choices that are taken by this project are:

1. Axon Framework 5 (of course)
2. Java as the language, JDK21 to be exact - the new baseline version of Axon Framework 5
3. Maven for dependency management
4. JUnit for testing
5. Annotation-based handlers and state containers
6. No Spring support **yet!** - the next milestone of Axon Framework 5 will provide Spring support
