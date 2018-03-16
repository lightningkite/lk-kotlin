@file:JvmName("LkKotlinJvmUtils")
@file:JvmMultifileClass

package lk.kotlin.jvm.utils.random

import java.util.*


/**
 * Returns a random integer within the range.
 */
fun IntRange.random(random: Random = DefaultRandom): Int {
    return random.nextInt(this.endInclusive + 1 - this.start) + this.start
}

/**
 * Returns a random float within the range.
 */
fun ClosedRange<Float>.random(random: Random = DefaultRandom): Float {
    return random.nextFloat().times(this.endInclusive - start).plus(start)
}

/**
 * Returns a random double within the range.
 */
fun ClosedRange<Double>.random(random: Random = DefaultRandom): Double {
    return random.nextDouble().times(this.endInclusive - start).plus(start)
}