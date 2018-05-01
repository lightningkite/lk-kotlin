



package lk.kotlin.jvm.utils.lambda

private class CooldownLambda(val time: Long, val inner: () -> Unit) : () -> Unit {
    var lastTime = 0L
    override fun invoke() {
        val now = System.currentTimeMillis()
        if (now - lastTime > time) {
            inner()
            lastTime = now
        }
    }
}

/**
 * Adds a cooldown to a lambda, such that if it happened within the last [milliseconds] milliseconds, then the lambda will not be run.
 */
fun (() -> Unit).cooldown(milliseconds: Long): () -> Unit = CooldownLambda(milliseconds, this)