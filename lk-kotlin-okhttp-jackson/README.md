# Module lk-kotlin-okhttp-jackson

[Return to Index](../)

## Package description

Adds Jackson functionality to the OkHttp extensions.

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
    implementation "com.fasterxml.jackson.core:jackson-databind:2.9.+"
    implementation "com.fasterxml.jackson.module:jackson-module-kotlin:2.9.+"
    implementation 'com.squareup.okhttp3:okhttp:3.9.0'
    implementation "com.lightningkite.kotlin:lk-kotlin-jackson:[version]"
    implementation "com.lightningkite.kotlin:lk-kotlin-okhttp:[version]"
    implementation "com.lightningkite.kotlin:lk-kotlin-okhttp-jackson:[version]"
}
```

## Resources

[Examples](https://github.com/lightningkite/lk-kotlin/tree/master/lk-kotlin-okhttp-jackson/src/test/kotlin/lk/kotlin/okhttp/jackson/example)