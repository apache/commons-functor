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

import org.apache.commons.functor.Function;
import org.apache.commons.functor.Predicate;
import org.apache.commons.lang3.Validate;

/**
 * A {@link Function Function}
 * similiar to Java's "ternary"
 * or "conditional" operator (<code>&#x3F; &#x3A;</code>).
 * Given a {@link Predicate predicate}
 * <i>p</i> and {@link Function functions}
 * <i>f</i> and <i>g</i>, {@link #evaluate evalautes}
 * to
 * <code>p.test(x) ? f.evaluate(x) : g.evaluate(x)</code>.
 * @param <A> the argument type.
 * @param <T> the returned value type.
 * @version $Revision$ $Date$
 */
public final class ConditionalFunction<A, T> implements Function<A, T> {

    /** Base hash integer used to shift hash. */
    private static final int HASH_SHIFT = 4;
    // attributes
    // ------------------------------------------------------------------------
    /**
     * the condition to be evaluated.
     */
    private final Predicate<? super A> ifPred;
    /**
     * the function executed if the condition is satisfied.
     */
    private final Function<? super A, ? extends T> thenFunc;
    /**
     * the function executed if the condition is not satisfied.
     */
    private final Function<? super A, ? extends T> elseFunc;

    // constructor
    // ------------------------------------------------------------------------
    /**
     * Create a new ConditionalFunction.
     * @param ifPred if
     * @param thenFunc then
     * @param elseFunc else
     */
    public ConditionalFunction(Predicate<? super A> ifPred, Function<? super A, ? extends T> thenFunc,
        Function<? super A, ? extends T> elseFunc) {
        this.ifPred = Validate.notNull(ifPred, "Predicate argument was null");
        this.thenFunc = Validate.notNull(thenFunc, "'then' Function argument was null");
        this.elseFunc = Validate.notNull(elseFunc, "'else' Function argument was null");
    }

    // predicate interface
    // ------------------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    public T evaluate(A obj) {
        if (ifPred.test(obj)) {
            return thenFunc.evaluate(obj);
        } else {
            return elseFunc.evaluate(obj);
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
        if (!(obj instanceof ConditionalFunction<?, ?>)) {
            return false;
        }
        ConditionalFunction<?, ?> that = (ConditionalFunction<?, ?>) obj;
        return this.ifPred.equals(that.ifPred)
                && this.thenFunc.equals(that.thenFunc)
                && this.elseFunc.equals(that.elseFunc);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = "ConditionalFunction".hashCode();
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
        return "ConditionalFunction<" + ifPred + "?" + thenFunc + ":" + elseFunc + ">";
    }

}
