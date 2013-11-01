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

import org.apache.commons.functor.BinaryPredicate;
import org.apache.commons.functor.NullaryPredicate;
import org.apache.commons.lang3.Validate;

/**
 * Adapts a
 * {@link BinaryPredicate BinaryPredicate}
 * to the
 * {@link org.apache.commons.functor.NullaryPredicate NullaryPredicate} interface
 * using a constant left-side argument.
 *
 * @version $Revision: 1365377 $ $Date: 2012-07-24 21:59:23 -0300 (Tue, 24 Jul 2012) $
 */
public final class FullyBoundNullaryPredicate implements NullaryPredicate {

    /** The {@link BinaryPredicate BinaryPredicate} I'm wrapping. */
    private final BinaryPredicate<Object, Object> predicate;
    /** The left parameter to pass to {@code predicate}. */
    private final Object left;
    /** The right parameter to pass to {@code predicate}. */
    private final Object right;

    /**
     * Create a new FullyBoundNullaryPredicate instance.
     * @param <L> left type
     * @param <R> right type
     * @param predicate the predicate to adapt
     * @param left the left argument to use
     * @param right the right argument to use
     */
    @SuppressWarnings("unchecked")
    public <L, R> FullyBoundNullaryPredicate(BinaryPredicate<? super L, ? super R> predicate, L left, R right) {
        this.predicate =
            (BinaryPredicate<Object, Object>) Validate.notNull(predicate,
                "BinaryPredicate argument was null");
        this.left = left;
        this.right = right;
    }

    /**
     * {@inheritDoc}
     */
    public boolean test() {
        return predicate.test(left, right);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof FullyBoundNullaryPredicate)) {
            return false;
        }
        FullyBoundNullaryPredicate that = (FullyBoundNullaryPredicate) obj;
        return predicate.equals(that.predicate)
                && (null == left ? null == that.left : left.equals(that.left))
                && (null == right ? null == that.right : right.equals(that.right));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = "FullyBoundNullaryPredicate".hashCode();
        hash <<= 2;
        hash ^= predicate.hashCode();
        hash <<= 2;
        if (null != left) {
            hash ^= left.hashCode();
        }
        hash <<= 2;
        if (null != right) {
            hash ^= right.hashCode();
        }
        return hash;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "FullyBoundNullaryPredicate<" + predicate + "(" + left + ", " + right + ")>";
    }

    /**
     * Adapt a BinaryPredicate to the NullaryPredicate interface.
     * @param predicate to adapt
     * @param <L> left type
     * @param <R> right type
     * @param left L argument to always send as the left operand to the wrapped function
     * @param right R argument to always send as the right operand to the wrapped function
     * @return FullyBoundNullaryPredicate
     */
    public static <L, R> FullyBoundNullaryPredicate bind(
            BinaryPredicate<? super L, ? super R> predicate, L left, R right) {
        return null == predicate ? null : new FullyBoundNullaryPredicate(predicate, left, right);
    }
}
