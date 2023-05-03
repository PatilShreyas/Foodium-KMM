/**
 * Copyright 2023 Shreyas Patil
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package utils.navigation.impl

/**
 * The stack data structure
 */
class Stack<E : Any>(initialItems: Collection<E> = emptyList()) {
    private val queue = ArrayDeque<E>(initialItems)

    /**
     * Items in this stack
     */
    val items: List<E> get() = queue.toList()

    /**
     * Top element of this stack.
     */
    val top: E? get() = queue.lastOrNull()

    /**
     * Size of this stack.
     */
    val size: Int get() = queue.size

    /**
     * Pushes [element] onto the stack.
     */
    fun push(element: E) {
        queue.addLast(element)
    }

    /**
     * Pops element out of the stack and returns popped element.
     */
    fun pop(): E {
        return queue.removeLast()
    }
}
