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

import org.apache.commons.functor.BinaryFunction;
import org.apache.commons.functor.BinaryPredicate;
import org.apache.commons.lang3.Validate;

/**
 * A {@link BinaryFunction BinaryFunction}
 * similiar to Java's "ternary"
 * or "conditional" operator (<code>&#x3F; &#x3A;</code>).
 * Given a {@link BinaryPredicate predicate}
 * <i>p</i> and {@link BinaryFunction functions}
 * <i>f</i> and <i>g</i>, {@link #evaluate evaluates}
 * to
 * <code>p.test(x,y) ? f.evaluate(x,y) : g.evaluate(x,y)</code>.
 * @param <L> the left argument type.
 * @param <R> the right argument type.
 * @param <T> the output function returned value type.
 * @version $Revision$ $Date$
 */
public final class ConditionalBinaryFunction<L, R, T> implements BinaryFunction<L, R, T> {

    /** Base hash integer used to shift hash. */
    private static final int HASH_SHIFT = 4;
    // attributes
    // ------------------------------------------------------------------------
    /**
     * the condition to be evaluated.
     */
    private final BinaryPredicate<? super L, ? super R> ifPred;
    /**
     * the function executed if the condition is satisfied.
     */
    private final BinaryFunction<? super L, ? super R, ? extends T> thenFunc;
    /**
     * the function executed if the condition is not satisfied.
     */
    private final BinaryFunction<? super L, ? super R, ? extends T> elseFunc;

    // constructor
    // ------------------------------------------------------------------------
    /**
     * Create a new ConditionalBinaryFunction.
     * @param ifPred if
     * @param thenFunc then
     * @param elseFunc else
     */
    public ConditionalBinaryFunction(BinaryPredicate<? super L, ? super R> ifPred,
            BinaryFunction<? super L, ? super R, ? extends T> thenFunc,
            BinaryFunction<? super L, ? super R, ? extends T> elseFunc) {
        this.ifPred = Validate.notNull(ifPred, "BinaryPredicate argument was null");
        this.thenFunc = Validate.notNull(thenFunc, "'then' BinaryFunction argument was null");
        this.elseFunc = Validate.notNull(elseFunc, "'else' BinaryFunction argument was null");
    }

    // predicate interface
    // ------------------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    public T evaluate(L left, R right) {
        if (ifPred.test(left, right)) {
            return thenFunc.evaluate(left, right);
        } else {
            return elseFunc.evaluate(left, right);
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
        if (!(obj instanceof ConditionalBinaryFunction<?, ?, ?>)) {
            return false;
        }
        ConditionalBinaryFunction<?, ?, ?> that = (ConditionalBinaryFunction<?, ?, ?>) obj;
        return this.ifPred.equals(that.ifPred)
                && this.thenFunc.equals(that.thenFunc)
                && this.elseFunc.equals(that.elseFunc);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = "ConditionalBinaryFunction".hashCode();
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
        return "ConditionalBinaryFunction<" + ifPred + "?" + thenFunc + ":" + elseFunc + ">";
    }

}
