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

import org.apache.commons.functor.Predicate;
import org.apache.commons.lang3.Validate;

/**
 * A {@link Predicate Predicate}
 * similiar to Java's "ternary"
 * or "conditional" operator (<code>&#x3F; &#x3A;</code>).
 * Given three {@link Predicate predicate}
 * <i>p</i>, <i>q</i>, <i>r</i>,
 * {@link #test tests}
 * to
 * <code>p.test(x) ? q.test(x) : r.test(x)</code>.
 * @param <A> the predicate argument type.
 * @version $Revision$ $Date$
 */
public final class ConditionalPredicate<A> implements Predicate<A> {

    /** Base hash integer used to shift hash. */
    private static final int HASH_SHIFT = 4;
    // attributes
    // ------------------------------------------------------------------------
    /**
     * the condition to be evaluated.
     */
    private final Predicate<? super A> ifPred;
    /**
     * the predicate executed if the condition is satisfied.
     */
    private final Predicate<? super A> thenPred;
    /**
     * the predicate executed if the condition is not satisfied.
     */
    private final Predicate<? super A> elsePred;

    // constructor
    // ------------------------------------------------------------------------
    /**
     * Create a new ConditionalPredicate.
     * @param ifPred if
     * @param thenPred then
     * @param elsePred else
     */
    public ConditionalPredicate(Predicate<? super A> ifPred, Predicate<? super A> thenPred,
            Predicate<? super A> elsePred) {
        this.ifPred = Validate.notNull(ifPred, "'if' Predicate argument was null");
        this.thenPred = Validate.notNull(thenPred, "'then' Predicate argument was null");
        this.elsePred = Validate.notNull(elsePred, "'else' Predicate argument was null");
    }

    // predicate interface
    // ------------------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    public boolean test(A obj) {
        return ifPred.test(obj) ? thenPred.test(obj) : elsePred.test(obj);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ConditionalPredicate<?>)) {
            return false;
        }
        ConditionalPredicate<?> that = (ConditionalPredicate<?>) obj;
        return this.ifPred.equals(that.ifPred)
                && this.thenPred.equals(that.thenPred)
                && this.elsePred.equals(that.elsePred);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = "ConditionalPredicate".hashCode();
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
        return "ConditionalPredicate<" + ifPred + "?" + thenPred + ":" + elsePred + ">";
    }

}
