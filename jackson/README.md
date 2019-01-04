# Module jackson

[Return to Index](../)

## Package description

Adds some DSL for the Jackson JSON library, as well as extension functions for using Jackson on various objects.

## Gradle Inclusion

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
    implementation "com.lightningkite.kotlin:jackson:[version]"
    implementation "com.fasterxml.jackson.core:jackson-databind:2.9.6"
}
```

## Resources

[Examples](https://github.com/lightningkite/lk-kotlin/tree/master/jackson/src/test/kotlin/lk/kotlin/jackson/example)