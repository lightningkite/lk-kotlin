# LK Kotlin

Lightning Kite's project to make Kotlin even more awesome for speedy development

## What is this?

This project contains a number of small, minimalist libraries for using Kotlin more effectively.

Examples of usage are in each library as far as possible in the tests.  The tests that are examples will be in the `examples` subpackage, and the other tests will be in the `tests` subpackage.

Each piece of the library is built to be read - in other words, you should be able to look at the source of anything in it and understand it pretty much immediately.  If not, please make a GitHub issue for it so that it can be better written or at least better documented.

This includes the following packages:

- [lk-kotlin-utils](lk-kotlin-utils/README.md) - Various useful extension functions
- [lk-kotlin-jvm-utils](lk-kotlin-jvm-utils/README.md) - Various useful extensions depending on the JVM
- [lk-kotlin-jackson](lk-kotlin-jackson/README.md) - Adds some DSL for the Jackson JSON library, as well as extension functions for using Jackson on various objects.
- [lk-kotlin-okhttp](lk-kotlin-okhttp/README.md) - Extensions for the OkHttp networking library that convert calls to lambdas and allow you to manipulate them conveniently.
- [lk-kotlin-okhttp-jackson](lk-kotlin-okhttp-jackson/README.md) - Adds Jackson functionality to the OkHttp extensions.
- [lk-kotlin-lifecycle](lk-kotlin-lifecycle/README.md) - Defines the interface and concept of general lifecycles, with a start and stop callback.  Useful for attaching UI to observable things.
- [lk-kotlin-observable-property](lk-kotlin-observable-property/README.md) - Defines an *extremely* lightweight observable property for use with databinding.
- [lk-kotlin-observable-property-jvm](lk-kotlin-observable-property-jvm/README.md) - Extensions for observable properties that are dependent on the JVM.
- [lk-kotlin-observable-property-lifecycle](lk-kotlin-observable-property-lifecycle/README.md) - Extensions for listening to observable properties only during the active part of a lifecycle.
- [lk-kotlin-observable-list](lk-kotlin-observable-list/README.md) - Observable lists, which you can observe changes on very conveniently.  Also has functionality for applying live transformations on them, such as sorting and grouping.
- [lk-kotlin-observable-list-lifecycle](lk-kotlin-observable-list-lifecycle/README.md) - Extensions for listening to observable lists only during the active part of a lifecycle.

Seriously, check out those example unit tests.  They show you exactly how to use the functionality provided.



# TODO Notes

- Get better code coverage on the observable lists