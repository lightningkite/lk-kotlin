# LK Kotlin

Lightning Kite's project to make Kotlin even more awesome for speedy development

## What is this?

This project contains a number of small, minimalist libraries for using Kotlin more effectively.

Examples of usage are in each library as far as possible in the tests.  The tests that are examples will be in the `examples` subpackage, and the other tests will be in the `tests` subpackage.

Each piece of the library is built to be read - in other words, you should be able to look at the source of anything in it and understand it pretty much immediately.  If not, please make a GitHub issue for it so that it can be better written or at least better documented.

This includes the following packages:

- [utils](utils/index.html) - Various useful extension functions
- [jvm-utils](jvm-utils/index.html) - Various useful extensions depending on the JVM
- [jackson](jackson/index.html) - Adds some DSL for the Jackson JSON library, as well as extension functions for using Jackson on various objects.
- [okhttp](okhttp/index.html) - Extensions for the OkHttp networking library that convert calls to lambdas and allow you to manipulate them conveniently.
- [okhttp-jackson](okhttp-jackson/index.html) - Adds Jackson functionality to the OkHttp extensions.
- [lifecycle](lifecycle/index.html) - Defines the interface and concept of general lifecycles, with a start and stop callback.  Useful for attaching UI to observable things.
- [observable-property](observable-property/index.html) - Defines an *extremely* lightweight observable property for use with databinding.
- [observable-property-jvm](observable-property-jvm/index.html) - Extensions for observable properties that are dependent on the JVM.
- [observable-property-lifecycle](observable-property-lifecycle/index.html) - Extensions for listening to observable properties only during the active part of a lifecycle.
- [observable-list](observable-list/index.html) - Observable lists, which you can observe changes on very conveniently.  Also has functionality for applying live transformations on them, such as sorting and grouping.
- [observable-list-lifecycle](observable-list-lifecycle/index.html) - Extensions for listening to observable lists only during the active part of a lifecycle.

Seriously, check out those example unit tests.  They show you exactly how to use the functionality provided.



# TODO Notes

- Get better code coverage on the observable lists