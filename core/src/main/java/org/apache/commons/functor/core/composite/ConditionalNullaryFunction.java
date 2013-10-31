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

import org.apache.commons.functor.NullaryFunction;
import org.apache.commons.functor.NullaryPredicate;
import org.apache.commons.lang3.Validate;

/**
 * A {@link NullaryFunction NullaryFunction}
 * similiar to Java's "ternary"
 * or "conditional" operator (<code>&#x3F; &#x3A;</code>).
 * Given a {@link NullaryPredicate predicate}
 * <i>p</i> and {@link NullaryFunction functions}
 * <i>f</i> and <i>g</i>, {@link #evaluate evaluates}
 * to
 * <code>p.test() ? f.evaluate() : g.evaluate()</code>.
 * @param <T> the returned value type.
 * @version $Revision: 1365329 $ $Date: 2012-07-24 19:34:23 -0300 (Tue, 24 Jul 2012) $
 */
public final class ConditionalNullaryFunction<T> implements NullaryFunction<T> {

    /** Base hash integer used to shift hash. */
    private static final int HASH_SHIFT = 4;
    // attributes
    // ------------------------------------------------------------------------
    /**
     * the condition to be evaluated.
     */
    private final NullaryPredicate ifPred;
    /**
     * the function executed if the condition is satisfied.
     */
    private final NullaryFunction<? extends T> thenFunc;
    /**
     * the function executed if the condition is not satisfied.
     */
    private final NullaryFunction<? extends T> elseFunc;

    // constructor
    // ------------------------------------------------------------------------
    /**
     * Create a new ConditionalNullaryFunction.
     * @param ifPred if
     * @param thenFunc then
     * @param elseFunc else
     */
    public ConditionalNullaryFunction(NullaryPredicate ifPred, NullaryFunction<? extends T> thenFunc,
            NullaryFunction<? extends T> elseFunc) {
        this.ifPred = Validate.notNull(ifPred, "NullaryPredicate argument was null");
        this.thenFunc = Validate.notNull(thenFunc, "'then' NullaryFunction argument was null");
        this.elseFunc = Validate.notNull(elseFunc, "'else' NullaryFunction argument was null");
    }

    // predicate interface
    // ------------------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    public T evaluate() {
        if (ifPred.test()) {
            return thenFunc.evaluate();
        } else {
            return elseFunc.evaluate();
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
        if (!(obj instanceof ConditionalNullaryFunction<?>)) {
            return false;
        }
        ConditionalNullaryFunction<?> that = (ConditionalNullaryFunction<?>) obj;
        return this.ifPred.equals(that.ifPred)
                && this.thenFunc.equals(that.thenFunc)
                && this.elseFunc.equals(that.elseFunc);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = "ConditionalNullaryFunction".hashCode();
        hash <<= HASH_SHIFT;
        hash ^= ifPred.hashCode();
        hash <<= HASH_SHIFT;
        hash ^= thenFunc.hashCode();
        hash <<= HASH_SHIFT;
        hash ^= elseFunc.hashCode();
        return hash;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "ConditionalNullaryFunction<" + ifPred + "?" + thenFunc + ":" + elseFunc + ">";
    }

}
