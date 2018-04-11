# LK Kotlin

[ ![Download](https://api.bintray.com/packages/lightningkite/com.lightningkite.kotlin/utils/images/download.svg) ](https://bintray.com/lightningkite/com.lightningkite.kotlin/utils/_latestVersion)

Lightning Kite's project to make Kotlin even more awesome for speedy development

## What is this?

This project contains a number of small, minimalist libraries for using Kotlin more effectively.

Examples of usage are in each library as far as possible in the tests.  The tests that are examples will be in the `examples` subpackage, and the other tests will be in the `tests` subpackage.

Each piece of the library is built to be read - in other words, you should be able to look at the source of anything in it and understand it pretty much immediately.  If not, please make a GitHub issue for it so that it can be better written or at least better documented.

This includes the following packages:

- [utils](utils/README.md) - Various useful extension functions.  Includes things for manipulating ByteArrays, lambdas, and collections, as well as extensions functions for doing math the Kotlin way and simple email validation.
- [jvm-utils](jvm-utils/README.md) - Various useful extensions depending on the JVM.  Includes things like stream manipulation, asynchronous execution, date reading, files, and randomness.
- [jackson](jackson/README.md) - Adds some DSL for the Jackson JSON library, as well as extension functions for using Jackson on various objects.
- [okhttp](okhttp/README.md) - Extensions for the OkHttp networking library that convert calls to lambdas and allow you to manipulate them conveniently.
- [okhttp-jackson](okhttp-jackson/README.md) - Adds Jackson functionality to the OkHttp extensions.
- [lifecycle](lifecycle/README.md) - Defines the interface and concept of general lifecycles, with a start and stop callback.  Useful for attaching UI to observable things.
- [observable-property](observable-property/README.md) - Defines an *extremely* lightweight observable property for use with databinding.
- [observable-property-jvm](observable-property-jvm/README.md) - Extensions for observable properties that are dependent on the JVM.
- [observable-property-lifecycle](observable-property-lifecycle/README.md) - Extensions for listening to observable properties only during the active part of a lifecycle.
- [observable-list](observable-list/README.md) - Observable lists, which you can observe changes on very conveniently.  Also has functionality for applying live transformations on them, such as sorting and grouping.
- [observable-list-lifecycle](observable-list-lifecycle/README.md) - Extensions for listening to observable lists only during the active part of a lifecycle.

Seriously, check out those example unit tests.  They show you exactly how to use the functionality provided.


## Gradle Inclusion

Bintray: [ ![Download](https://api.bintray.com/packages/lightningkite/com.lightningkite.kotlin/utils/images/download.svg) ](https://bintray.com/lightningkite/com.lightningkite.kotlin/utils/_latestVersion)

Add the repository:

```
repositories {
    maven {
        url "https://dl.bintray.com/lightningkite/com.lightningkite.kotlin"
    }
}
```

Include the desired libraries:

```
dependencies {
    implementation "com.lightningkite.kotlin:utils:[version]"

    implementation "com.lightningkite.kotlin:jvm-utils:[version]"

    implementation "com.lightningkite.kotlin:jackson:[version]"
    implementation "com.fasterxml.jackson.core:jackson-databind:2.9.+"
    implementation "com.fasterxml.jackson.module:jackson-module-kotlin:2.9.+"

    implementation "com.lightningkite.kotlin:okhttp:[version]"
    implementation 'com.squareup.okhttp3:okhttp:3.9.0'

    implementation "com.lightningkite.kotlin:okhttp-jackson:[version]"

    implementation "com.lightningkite.kotlin:lifecycle:[version]"

    implementation "com.lightningkite.kotlin:observable-property:[version]"

    implementation "com.lightningkite.kotlin:observable-property-jvm:[version]"

    implementation "com.lightningkite.kotlin:observable-property-lifecycle:[version]"

    implementation "com.lightningkite.kotlin:observable-list:[version]"

    implementation "com.lightningkite.kotlin:observable-list-lifecycle:[version]"

    implementation 'com.esotericsoftware:reflectasm:1.11.3'
    implementation "com.lightningkite.kotlin:reflect:[version]"
}
```

# TODO Notes

- Get better code coverage on the observable lists
