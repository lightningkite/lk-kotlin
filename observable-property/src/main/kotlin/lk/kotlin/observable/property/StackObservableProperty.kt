package lk.kotlin.observable.property


/**
 * An observable property where you can push and pop states.
 * Notifies listeners of the current state.
 * Created by joseph on 1/19/18.
 */
class StackObservableProperty<T>() : MutableObservableProperty<T> {

    private val internalStack = ArrayList<T>()
    private val listeners = ArrayList<(T) -> Unit>()
    val stack: List<T> get() = internalStack

    /**
     * Creates a [StackObservableProperty] with a starting state.
     */
    constructor(firstItem: T) : this() {
        internalStack.add(firstItem)
    }

    /**
     * Creates a [StackObservableProperty] with a starting state.
     */
    constructor(firstItemGenerator: (StackObservableProperty<T>) -> T) : this() {
        internalStack.add(firstItemGenerator(this))
    }

    /**
     * An exception indicating that there are no states available.
     */
    class NoStatesLeftException : IllegalStateException()

    /**
     * The top value on the stack.
     */
    override var value: T
        get() {
            if (stack.isEmpty()) throw NoStatesLeftException()
            return internalStack.last()
        }
        set(value) {
            if (internalStack.isEmpty()) internalStack.add(value)
            else internalStack[internalStack.lastIndex] = value
            listeners.forEach { it.invoke(value) }
        }

    override fun add(element: (T) -> Unit): Boolean = listeners.add(element)
    override fun remove(element: (T) -> Unit): Boolean = listeners.remove(element)

    /**
     * Adds a state to the bottom of the stack silently.
     */
    fun prepend(element: T) {
        internalStack.add(0, element)
    }

    /**
     * Pushes a new state onto the stack and notifies the listeners.
     */
    fun push(element: T) {
        internalStack.add(element)
        listeners.forEach { it.invoke(element) }
    }

    /**
     * Swaps the top state in the stack for another and notifies the listeners.
     */
    fun swap(element: T) {
        internalStack[internalStack.lastIndex] = element
        listeners.forEach { it.invoke(element) }
    }

    /**
     * Pops a state off the stack and notifies the listeners.
     */
    fun pop():Boolean {
        return if (stack.size > 1) {
            internalStack.removeAt(internalStack.lastIndex)
            val previous = internalStack.last()
            listeners.forEach { it.invoke(previous) }
            true
        } else false
    }

    /**
     * Pops a state off the stack and notifies the listeners.
     * If there are no states that can be popped off, the function returns false.
     */
    @Deprecated("Use plain pop instead.", ReplaceWith("pop()"))
    fun popOrFalse(): Boolean = pop()

    /**
     * Pops all of the states off the stack except for the bottom one and notifies the listeners.
     */
    fun root() {
        val element = internalStack.first()
        internalStack.clear()
        internalStack.add(element)
        listeners.forEach { it.invoke(element) }
    }

    /**
     * Pops states off the stack until a predicate is satisfied and notifies the listeners.
     */
    fun back(predicate: (T) -> Boolean) {
        val index = internalStack.indexOfLast(predicate)
        while (index + 1 < internalStack.size) {
            internalStack.removeAt(index + 1)
        }
        listeners.forEach { it.invoke(internalStack.last()) }
    }

    /**
     * Clears the stack and starts over with a new element and notifies the listeners.
     */
    fun reset(element: T) {
        internalStack.clear()
        internalStack.add(element)
        listeners.forEach { it.invoke(value) }
    }

    private fun internalPopTo(from: T): Boolean {
        val index = internalStack.indexOf(from)
        if(index == -1) return false
        while (index + 1 < internalStack.size) {
            internalStack.removeAt(index + 1)
        }
        return true
    }

    /**
     * Pushes a state onto the stack after [from].
     * If [from] is not on the stack, then this will do nothing.
     * If [from] is not the last item on the stack, items will be popped off until it is.
     */
    fun pushFrom(from: T, to:T) {
        if(internalPopTo(from)){
            internalStack.add(to)
            listeners.forEach { it.invoke(value) }
        }
    }

    /**
     * Pops the [from] state off the stack.
     * If [from] is not on the stack, then this will do nothing and return false.
     * If [from] is the last item on the stack, then this will do nothing and return false.
     * If [from] is not the last item on the stack, items will be popped off until it is.
     */
    fun popFrom(from: T): Boolean{
        if(internalPopTo(from)){
            if(internalStack.size <= 1) return false
            internalStack.removeAt(internalStack.lastIndex)
            listeners.forEach { it.invoke(value) }
            return true
        }
        return false
    }

    /**
     * Gives an observable property that also has the size of the stack in a pair.
     */
    fun withSize(): ObservablePropertyMapped<T, Pair<T, Int>> = this.transform { it to this.stack.size }

    /**
     * Gives an observable property that has the whole stack rather than just the top value.
     */
    fun asList(): ObservablePropertyMapped<T, List<T>> = this.transform { this.stack }
}