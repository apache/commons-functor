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
package org.apache.commons.functor.adapter;

import org.apache.commons.functor.BinaryFunction;
import org.apache.commons.functor.BinaryPredicate;

/**
 * Adapts a
 * {@link BinaryPredicate BinaryPredicate}
 * to the
 * {@link BinaryFunction BinaryFunction} interface.
 *
 * @param <L> the left argument type.
 * @param <R> the right argument type.
 * @version $Revision$ $Date$
 */
public final class BinaryPredicateBinaryFunction<L, R> implements BinaryFunction<L, R, Boolean> {
    /** The {@link BinaryPredicate BinaryPredicate} I'm wrapping. */
    private final BinaryPredicate<? super L, ? super R> predicate;

    /**
     * Create a new BinaryPredicateBinaryFunction.
     * @param predicate to adapt
     */
    public BinaryPredicateBinaryFunction(BinaryPredicate<? super L, ? super R> predicate) {
        this.predicate = predicate;
    }

    /**
     * {@inheritDoc}
     * Returns <code>Boolean.TRUE</code> (<code>Boolean.FALSE</code>)
     * when the {@link BinaryPredicate#test test} method of my underlying
     * predicate returns <code>true</code> (<code>false</code>).
     *
     * @return a non-<code>null</code> <code>Boolean</code> instance
     */
    public Boolean evaluate(L left, R right) {
        return predicate.test(left, right) ? Boolean.TRUE : Boolean.FALSE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof BinaryPredicateBinaryFunction<?, ?>)) {
            return false;
        }
        BinaryPredicateBinaryFunction<?, ?> that = (BinaryPredicateBinaryFunction<?, ?>) obj;
        return this.predicate.equals(that.predicate);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = "BinaryPredicateBinaryFunction".hashCode();
        hash ^= predicate.hashCode();
        return hash;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "BinaryPredicateBinaryFunction<" + predicate + ">";
    }

    /**
     * Adapt the given, possibly-<code>null</code>,
     * {@link BinaryPredicate BinaryPredicate} to the
     * {@link BinaryFunction BinaryFunction} interface.
     * When the given <code>BinaryPredicate</code> is <code>null</code>,
     * returns <code>null</code>.
     *
     * @param <L> left type
     * @param <R> right type
     * @param predicate the possibly-<code>null</code>
     *        {@link BinaryPredicate BinaryPredicate} to adapt
     * @return a <code>BinaryPredicateBinaryFunction</code> wrapping the given
     *         {@link BinaryPredicate BinaryPredicate}, or <code>null</code>
     *         if the given <code>BinaryPredicate</code> is <code>null</code>
     */
    public static <L, R> BinaryPredicateBinaryFunction<L, R> adapt(BinaryPredicate<? super L, ? super R> predicate) {
        return null == predicate ? null : new BinaryPredicateBinaryFunction<L, R>(predicate);
    }

}
