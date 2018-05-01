package lk.kotlin.utils.lambda

import kotlin.collections.*

/**
 *
 * Created by joseph on 11/14/16.
 */

/**
 * Appends more functionality to a lambda.
 */
infix fun <A, B> (() -> A).then(next: (A) -> B): () -> B {
    return { this.invoke().let(next) }
}