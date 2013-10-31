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

import org.apache.commons.functor.NullaryPredicate;
import org.apache.commons.lang3.Validate;

/**
 * A {@link NullaryPredicate NullaryPredicate}
 * similiar to Java's "ternary"
 * or "conditional" operator (<code>&#x3F; &#x3A;</code>).
 * Given three {@link NullaryPredicate predicates}
 * <i>p</i>, <i>q</i>, <i>r</i>,
 * {@link #test tests}
 * to
 * <code>p.test() ? q.test() : r.test()</code>.
 * @version $Revision: 1365329 $ $Date: 2012-07-24 19:34:23 -0300 (Tue, 24 Jul 2012) $
 */
public final class ConditionalNullaryPredicate implements NullaryPredicate {

    /** Base hash integer used to shift hash. */
    private static final int HASH_SHIFT = 4;
    // attributes
    // ------------------------------------------------------------------------
    /**
     * the condition to be evaluated.
     */
    private final NullaryPredicate ifPred;
    /**
     * the predicate executed if the condition is satisfied.
     */
    private final NullaryPredicate thenPred;
    /**
     * the predicate executed if the condition is not satisfied.
     */
    private final NullaryPredicate elsePred;

    // constructor
    // ------------------------------------------------------------------------
    /**
     * Create a new ConditionalNullaryPredicate.
     * @param ifPred if
     * @param thenPred then
     * @param elsePred else
     */
    public ConditionalNullaryPredicate(NullaryPredicate ifPred, NullaryPredicate thenPred, NullaryPredicate elsePred) {
        this.ifPred = Validate.notNull(ifPred, "'if' NullaryPredicate argument was null");
        this.thenPred = Validate.notNull(thenPred, "'then' NullaryPredicate argument was null");
        this.elsePred = Validate.notNull(elsePred, "'else' NullaryPredicate argument was null");
    }

    // predicate interface
    // ------------------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    public boolean test() {
        return ifPred.test() ? thenPred.test() : elsePred.test();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ConditionalNullaryPredicate)) {
            return false;
        }
        ConditionalNullaryPredicate that = (ConditionalNullaryPredicate) obj;
        return this.ifPred.equals(that.ifPred)
                && this.thenPred.equals(that.thenPred)
                && this.elsePred.equals(that.elsePred);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = "ConditionalNullaryPredicate".hashCode();
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
        return "ConditionalNullaryPredicate<" + ifPred + "?" + thenPred + ":" + elsePred + ">";
    }

}
