# LK Kotlin

Lightning Kite's project to make Kotlin even more awesome for speedy development

## What is this?

This project contains a number of small, minimalist libraries for using Kotlin more effectively.

Examples of usage are in each library as far as possible in the tests.  The tests that are examples will be in the `examples` subpackage, and the other tests will be in the `tests` subpackage.

Each piece of the library is built to be read - in other words, you should be able to look at the source of anything in it and understand it pretty much immediately.  If not, please make a GitHub issue for it so that it can be better written or at least better documented.

This includes the following packages:

- `lk-kotlin-utils` - Various useful extension functions
- `lk-kotlin-jvm-utils` - Various useful extensions depending on the JVM
- `lk-kotlin-jackson` - Adds some DSL for the Jackson JSON library, as well as extension functions for using Jackson on various objects.
- `lk-kotlin-okhttp` - Extensions for the OkHttp networking library that convert calls to lambdas and allow you to manipulate them conveniently.
- `lk-kotlin-okhttp-jackson` - Adds Jackson functionality to the OkHttp extensions.
- `lk-kotlin-lifecycle` - Defines the interface and concept of general lifecycles, with a start and stop callback.  Useful for attaching UI to observable things.
- `lk-kotlin-observable-property` - Defines an *extremely* lightweight observable property for use with databinding.
- `lk-kotlin-observable-property-jvm` - Extensions for observable properties that are dependent on the JVM.
- `lk-kotlin-observable-property-lifecycle` - Extensions for listening to observable properties only during the active part of a lifecycle.
- `lk-kotlin-observable-list` - Observable lists, which you can observe changes on very conveniently.  Also has functionality for applying live transformations on them, such as sorting and grouping.
- `lk-kotlin-observable-list-lifecycle` - Extensions for listening to observable lists only during the active part of a lifecycle.

#### lk-kotlin-utils

Various extensions that had no good classifications.  May be broken out in the future.

- bytes
    - ByteArray.ext.kt - Convert a byte array to a hex string and back
    - IntBitArray.kt - Contains a class and functions for using an integer as an array of bits for flags.
    - LongBitArray.kt - Contains a class and functions for using a long integer as an array of bits for flags.
- collection
    - Cache - Used for computing values from a key lazily, caching the results
    - CollectionWriteOnlyMapping - Maps a collection for write operations live - in other words, this class acts as a wrapper around another collection, such that you can use the instance to write to the collection as if it were of a different type.
    - Iterator.ext.kt - Mapping functions for iterators, mutable and list iterators included.
    - List.sorted.ext.kt - Various extension functions for adding elements to lists while keeping the elements sorted.
    - MappingList - Class/extension functions for mapping lists to and from various types.
    - MappingMutableList - Class/extension functions for mapping lists to and from various types.
    - MappingMutableSet - Class/extension functions for mapping sets to and from various types.
    - ObserveEmptyArrayList - A list that calls functions upon changing from empty to not empty and vice versa.
- lambda
    - Various extensions for manipulating lambdas.
    - `then` can be used to append functionality to a lambda with another lambda.
    - `invokeAll` can be used on a list of lambdas to invoke them all with the given values.  Extremely handy for using mutable collections of lambdas as events.
- math
    - Adds `degreesTo` and `radiansTo` to `Float` and `Double` to calculate angle differences.
    - Adds functionality to `Float` and `Double` to call all of their respective `Math` functions using a more Kotlin-ish format. `3.0 pow 4.0` for example, or `10.0.log()`.
- text
    - `String.isEmail()`
- casts - You can cast using dot-function syntax. `test.cast<ToType>()` and `test.castOrNull<ToType>()`

#### lk-kotlin-jvm-utils
- async
    - Async - a thread pool to use for background operations
    - Executor.ext.kt - extensions to run lambdas on Executors.
- date
    - Date.ext.kt - Extension functions for converting `Date` to `Calendar` and then reading the fields from `Calendar`.
- files - adds `File.child("sub")
- lambda
    - You can add `.cooldown(timeoutMs)` to the end of a lambda to ensure it runs a maximum of once per `timeoutMs` milliseconds.
    - Parallel - You can run multiple lambdas in parallel.
- random
    - Adds `.random()` to ranges to get random numbers.
    - List.ext.kt - adds a random() extension function to lists, allowing you to pull a randomly select item out of the list.
- stream
    - Adds functions for `InputStream` that pull all of the data out of the stream and put it into a file, string, or byte array.



# TODO Notes

- Get better code coverage on the observable lists