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

import java.io.Serializable;

import org.apache.commons.functor.Function;
import org.apache.commons.functor.Predicate;
import org.apache.commons.lang3.Validate;

/**
 * A {@link Function Function}
 * similiar to Java's "ternary"
 * or "conditional" operator (<code>&#x3F; &#x3A;</code>).
 * Given a {@link Predicate predicate}
 * <i>p</i> and {@link Function functions}
 * <i>f</i> and <i>g</i>, {@link #evaluate evaluates}
 * to
 * <code>p.test() ? f.evaluate() : g.evaluate()</code>.
 * <p>
 * Note that although this class implements
 * {@link Serializable}, a given instance will
 * only be truly <code>Serializable</code> if all the
 * underlying functors are.  Attempts to serialize
 * an instance whose delegates are not all
 * <code>Serializable</code> will result in an exception.
 * </p>
 * @param <T> the returned value type.
 * @version $Revision$ $Date$
 */
public final class ConditionalFunction<T> implements Function<T>, Serializable {
    /**
     * serialVersionUID declaration.
     */
    private static final long serialVersionUID = 4214871352184887792L;

    /** Base hash integer used to shift hash. */
    private static final int HASH_SHIFT = 4;
    // attributes
    // ------------------------------------------------------------------------
    /**
     * the condition to be evaluated.
     */
    private final Predicate ifPred;
    /**
     * the function executed if the condition is satisfied.
     */
    private final Function<? extends T> thenFunc;
    /**
     * the function executed if the condition is not satisfied.
     */
    private final Function<? extends T> elseFunc;

    // constructor
    // ------------------------------------------------------------------------
    /**
     * Create a new ConditionalFunction.
     * @param ifPred if
     * @param thenFunc then
     * @param elseFunc else
     */
    public ConditionalFunction(Predicate ifPred, Function<? extends T> thenFunc, Function<? extends T> elseFunc) {
        this.ifPred = Validate.notNull(ifPred, "Predicate argument was null");
        this.thenFunc = Validate.notNull(thenFunc, "'then' Function argument was null");
        this.elseFunc = Validate.notNull(elseFunc, "'else' Function argument was null");
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
    public boolean equals(Object that) {
        return that == this || (that instanceof ConditionalFunction<?> && equals((ConditionalFunction<?>) that));
    }

    /**
     * Learn whether another ConditionalFunction is equal to this.
     * @param that ConditionalFunction to test
     * @return boolean
     */
    public boolean equals(ConditionalFunction<?> that) {
        return null != that
                && ifPred.equals(that.ifPred)
                && thenFunc.equals(that.thenFunc)
                && elseFunc.equals(that.elseFunc);
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
