package lk.kotlin.utils.math

import kotlin.math.*

/**
 * Various math functions.
 * Created by jivie on 9/28/15.
 */

/**
 * Degrees from this angle (in degrees) to another angle (in degrees).
 */
infix fun Float.degreesTo(to: Float): Float {
    return ((to - this + 180 + 360).rem(360f)) - 180
}

/**
 * Radians from this angle (in radians) to another angle (in radians).
 */
infix fun Float.radiansTo(to: Float): Float {
    return (((to - this + PI * 3).rem(PI * 2)) - PI).toFloat()
}

/**
 * Degrees from this angle (in degrees) to another angle (in degrees).
 */
infix fun Double.degreesTo(to: Double): Double {
    return ((to - this + 180 + 360).rem(360.0)) - 180
}

/**
 * Radians from this angle (in radians) to another angle (in radians).
 */
infix fun Double.radiansTo(to: Double): Double {
    return ((to - this + PI * 3).rem(PI * 2)) - PI
}