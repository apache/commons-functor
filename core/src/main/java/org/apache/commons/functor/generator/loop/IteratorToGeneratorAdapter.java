/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.commons.functor.generator.loop;

import java.util.Iterator;

import org.apache.commons.functor.Procedure;
import org.apache.commons.lang3.Validate;

/**
 * Adapts an {@link Iterator} to the {@link LoopGenerator} interface.
 *
 * @param <E> the type of elements held in this generator.
 * @since 1.0
 * @version $Revision: 1508677 $ $Date: 2013-07-30 19:48:02 -0300 (Tue, 30 Jul 2013) $
 */
public final class IteratorToGeneratorAdapter<E> extends LoopGenerator<E> {
    /**
     * Helper iterator; enforces that iterators from equal owners are considered equal.
     * 
     * @param <E> the type of elements in this iterator.
     */
    private static class EqualityIterator<E> implements Iterator<E> {
        /**
         * Iterable that owns this iterator.
         */
        final Iterable<? extends E> owner;

        /**
         * Wrapped iterator.
         */
        final Iterator<? extends E> wrapped;

        /**
         * Create a new EqualityIterator.
         * @param owner iterable that owns this iterator
         */
        EqualityIterator(Iterable<? extends E> owner) {
            super();
            this.owner = Validate.notNull(owner);
            this.wrapped = owner.iterator();
        }

        /**
         * {@inheritDoc}
         */
        public boolean hasNext() {
            return wrapped.hasNext();
        }

        /**
         * {@inheritDoc}
         */
        public E next() {
            return wrapped.next();
        }

        /**
         * {@inheritDoc}
         */
        public void remove() {
            wrapped.remove();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof EqualityIterator)) {
                return false;
            }
            return ((EqualityIterator<?>) obj).owner.equals(owner);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int hashCode() {
            int hash = "IteratorToGeneratorAdapter$EqualityIterator".hashCode();
            hash <<= 2;
            hash ^= owner.hashCode();
            return hash;
        }
    }

    // instance variables
    //-----------------------------------------------------

    /**
     * The adapted iterator.
     */
    private final Iterator<? extends E> iter;

    // constructors
    //-----------------------------------------------------
    /**
     * Create a new IteratorToGeneratorAdapter.
     * @param iter Iterator to adapt
     */
    public IteratorToGeneratorAdapter(Iterator<? extends E> iter) {
        this.iter = Validate.notNull(iter, "Iterator argument was null");
    }

    // instance methods
    //-----------------------------------------------------
    /**
     * {@inheritDoc}
     */
    public void run(Procedure<? super E> proc) {
        while (iter.hasNext()) {
            proc.run(iter.next());
            if (isStopped()) {
                break;
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof IteratorToGeneratorAdapter<?>)) {
            return false;
        }
        IteratorToGeneratorAdapter<?> that = (IteratorToGeneratorAdapter<?>) obj;
        return this.iter.equals(that.iter);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = "IteratorToGeneratorAdapater".hashCode();
        hash <<= 2;
        hash ^= iter.hashCode();
        return hash;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "IteratorToGeneratorAdapter<" + iter + ">";
    }

    // static methods
    //-----------------------------------------------------
    /**
     * Adapt an Iterator to the Generator interface.
     *
     * @param <E> the type of elements held in this generator.
     * @param iter to adapt
     * @return IteratorToGeneratorAdapter
     */
    public static <E> IteratorToGeneratorAdapter<E> adapt(Iterator<? extends E> iter) {
        return null == iter ? null : new IteratorToGeneratorAdapter<E>(iter);
    }

    /**
     * Adapt an Iterable to the Generator interface.
     *
     * @param <E> the type of elements held in this generator.
     * @param iterable to adapt
     * @return IteratorToGeneratorAdapter
     */
    public static <E> IteratorToGeneratorAdapter<E> adapt(Iterable<? extends E> iterable) {
        return null == iterable ? null : new IteratorToGeneratorAdapter<E>(new EqualityIterator<E>(iterable));
    }
}
