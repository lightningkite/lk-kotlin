


package lk.kotlin.jvm.utils.random

import java.util.*

/**
 * Various extensions functions for lists.
 * Created by jivie on 4/26/16.
 */

inline fun <E> List<E>.random(random: Random = DefaultRandom): E {
    return this[random.nextInt(size)]
}