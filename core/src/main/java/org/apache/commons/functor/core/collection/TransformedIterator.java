/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.functor.core.collection;

import java.util.Iterator;

import org.apache.commons.functor.Function;
import org.apache.commons.lang3.Validate;

/**
 * Iterator that transforms another Iterator by applying a Function to each returned element.
 *
 * @param <E> the function argument type
 * @param <T> the iterator elements type
 * @version $Revision$ $Date$
 */
public final class TransformedIterator<E, T> implements Iterator<T> {

    // attributes
    // ------------------------------------------------------------------------

    /**
     * The function to apply to each iterator element.
     */
    private final Function<? super E, ? extends T> function;
    /**
     * The wrapped iterator.
     */
    private final Iterator<? extends E> iterator;

    // constructor
    // ------------------------------------------------------------------------
    /**
     * Create a new TransformedIterator.
     * @param iterator Iterator to decorate
     * @param function to apply
     */
    public TransformedIterator(Iterator<? extends E> iterator, Function<? super E, ? extends T> function) {
        this.function = Validate.notNull(function, "filtering Function argument was null");
        this.iterator = Validate.notNull(iterator, "Iterator argument was null");
    }

    // iterator methods
    // ------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     * @see java.util.Iterator#hasNext()
     */
    public boolean hasNext() {
        return iterator.hasNext();
    }

    /**
     * {@inheritDoc}
     * @see java.util.Iterator#next()
     */
    public T next() {
        return function.evaluate(iterator.next());
    }

    /**
     * {@inheritDoc}
     * @see java.util.Iterator#remove()
     */
    public void remove() {
        iterator.remove();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof TransformedIterator<?, ?>)) {
            return false;
        }
        TransformedIterator<?, ?> that = (TransformedIterator<?, ?>) obj;
        return function.equals(that.function) && iterator.equals(that.iterator);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = "TransformedIterator".hashCode();
        hash <<= 2;
        hash ^= function.hashCode();
        hash <<= 2;
        hash ^= iterator.hashCode();
        return hash;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "TransformedIterator<" + iterator + "," + function + ">";
    }

    // class methods
    // ------------------------------------------------------------------------
    /**
     * Get a Transformed Iterator instance.
     *
     * @param <E> the function argument type
     * @param <T> the iterator elements type
     * @param iter to decorate, if null result is null
     * @param func transforming function, cannot be null
     * @return Iterator<T>
     */
    public static <E, T> Iterator<T> transform(Iterator<? extends E> iter, Function<? super E, ? extends T> func) {
        if (null == iter) {
            return null;
        }
        return new TransformedIterator<E, T>(iter, func);
    }

    /**
     * Get an Iterator instance that may be transformed.
     *
     * @param <E> the iterator elements type
     * @param iter to decorate, if null result is null
     * @param func transforming function, if null result is iter
     * @return Iterator<?>
     */
    public static <E> Iterator<?> maybeTransform(Iterator<? extends E> iter, Function<? super E, ?> func) {
        return null == func ? (null == iter ? null : iter) : new TransformedIterator<E, Object>(iter, func);
    }

}
