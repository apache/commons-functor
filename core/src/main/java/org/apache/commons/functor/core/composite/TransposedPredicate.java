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
package org.apache.commons.functor.core.composite;

import org.apache.commons.functor.BinaryPredicate;
import org.apache.commons.lang3.Validate;

/**
 * Transposes (swaps) the arguments to some other
 * {@link BinaryPredicate predicate}.
 * For example, given a predicate <i>p</i>
 * and the ordered pair of arguments <i>a</i>,
 * <i>b</i>.
 * {@link #test tests}
 * <code>p.test(b,a)</code>.
 * @param <L> the left argument type.
 * @param <R> the right argument type.
 * @version $Revision$ $Date$
 */
public class TransposedPredicate<L, R> implements BinaryPredicate<L, R> {
    // attributes
    // ------------------------------------------------------------------------
    /**
     * The adapted predicate.
     */
    private final BinaryPredicate<? super R, ? super L> predicate;

    // constructor
    // ------------------------------------------------------------------------
    /**
     * Create a new TransposedPredicate.
     * @param predicate the BinaryPredicate to transpose
     */
    public TransposedPredicate(BinaryPredicate<? super R, ? super L> predicate) {
        this.predicate = Validate.notNull(predicate, "BinaryPredicate argument must not be null");
    }

    // functor interface
    // ------------------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    public final boolean test(L left, R right) {
        return predicate.test(right, left);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof TransposedPredicate<?, ?>)) {
            return false;
        }
        TransposedPredicate<?, ?> that = (TransposedPredicate<?, ?>) obj;
        return this.predicate.equals(that.predicate);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = "TransposedPredicate".hashCode();
        hash ^= predicate.hashCode();
        return hash;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "TransposedPredicate<" + predicate + ">";
    }

    // static
    // ------------------------------------------------------------------------
    /**
     * Return the transposition of <code>p</code>.
     *
     * @param <L> the left argument type.
     * @param <R> the right argument type.
     * @param p BinaryPredicate to transpose
     * @return TransposedPredicate
     */
    public static <L, R> TransposedPredicate<R, L> transpose(BinaryPredicate<? super L, ? super R> p) {
        return null == p ? null : new TransposedPredicate<R, L>(p);
    }

}
