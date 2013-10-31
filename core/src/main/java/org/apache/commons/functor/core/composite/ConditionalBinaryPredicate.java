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
 * A {@link BinaryPredicate BinaryPredicate}
 * similiar to Java's "ternary"
 * or "conditional" operator (<code>&#x3F; &#x3A;</code>).
 * Given three {@link BinaryPredicate predicates}
 * <i>p</i>, <i>q</i>, <i>r</i>,
 * {@link #test tests}
 * to
 * <code>p.test(x,y) ? q.test(x,y) : r.test(x,y)</code>.
 * @param <L> the left argument type.
 * @param <R> the right argument type.
 * @version $Revision$ $Date$
 */
public final class ConditionalBinaryPredicate<L, R> implements BinaryPredicate<L, R> {

    /** Base hash integer used to shift hash. */
    private static final int HASH_SHIFT = 4;
    // attributes
    // ------------------------------------------------------------------------
    /**
     * the condition to be evaluated.
     */
    private final BinaryPredicate<? super L, ? super R> ifPred;
    /**
     * the predicate executed if the condition is satisfied.
     */
    private final BinaryPredicate<? super L, ? super R> thenPred;
    /**
     * the predicate executed if the condition is not satisfied.
     */
    private final BinaryPredicate<? super L, ? super R> elsePred;

    // constructor
    // ------------------------------------------------------------------------
    /**
     * Create a new ConditionalBinaryPredicate.
     * @param ifPred if
     * @param thenPred then
     * @param elsePred else
     */
    public ConditionalBinaryPredicate(BinaryPredicate<? super L, ? super R> ifPred,
            BinaryPredicate<? super L, ? super R> thenPred, BinaryPredicate<? super L, ? super R> elsePred) {
        this.ifPred = Validate.notNull(ifPred, "'if' BinaryPredicate argument was null");
        this.thenPred = Validate.notNull(thenPred, "'then' BinaryPredicate argument was null");
        this.elsePred = Validate.notNull(elsePred, "'else' BinaryPredicate argument was null");
    }

    // predicate interface
    // ------------------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    public boolean test(L left, R right) {
        return ifPred.test(left, right) ? thenPred.test(left, right) : elsePred.test(left, right);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ConditionalBinaryPredicate<?, ?>)) {
            return false;
        }
        ConditionalBinaryPredicate<?, ?> that = (ConditionalBinaryPredicate<?, ?>) obj;
        return this.ifPred.equals(that.ifPred)
                && this.thenPred.equals(that.thenPred)
                && this.elsePred.equals(that.elsePred);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = "ConditionalBinaryPredicate".hashCode();
        hash <<= HASH_SHIFT;
        hash ^= ifPred.hashCode();
        hash <<= HASH_SHIFT;
        hash ^= thenPred.hashCode();
        hash <<= HASH_SHIFT;
        hash ^= elsePred.hashCode();
        return hash;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "ConditionalBinaryPredicate<" + ifPred + "?" + thenPred + ":" + elsePred + ">";
    }

}
