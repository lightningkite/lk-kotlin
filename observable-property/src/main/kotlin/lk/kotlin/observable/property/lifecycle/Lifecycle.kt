package lk.kotlin.observable.property.lifecycle

import lk.kotlin.observable.property.ObservableProperty

/**
 * Represents the lifecycle of something, `true` meaning that the thing is active and `false`
 * meaning that the thing is inactive.
 */
typealias Lifecycle = ObservableProperty<Boolean>