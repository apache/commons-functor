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

import org.apache.commons.functor.Function;
import org.apache.commons.functor.Predicate;
import org.apache.commons.lang3.Validate;

/**
 * Adapts a
 * {@link Predicate Predicate}
 * to the
 * {@link Function Function} interface.
 *
 * @param <A> the argument type.
 * @version $Revision$ $Date$
 */
public final class PredicateFunction<A> implements Function<A, Boolean> {
    /** The {@link Predicate Predicate} I'm wrapping. */
    private final Predicate<? super A> predicate;

    /**
     * Create a new PredicateFunction.
     * @param predicate to adapt
     */
    public PredicateFunction(Predicate<? super A> predicate) {
        this.predicate = Validate.notNull(predicate, "Predicate argument was null");
    }

    /**
     * {@inheritDoc}
     * Returns <code>Boolean.TRUE</code> (<code>Boolean.FALSE</code>)
     * when the {@link Predicate#test test} method of my underlying
     * predicate returns <code>true</code> (<code>false</code>).
     *
     * @return a non-<code>null</code> <code>Boolean</code> instance
     */
    public Boolean evaluate(A obj) {
        return Boolean.valueOf(predicate.test(obj));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof PredicateFunction<?>)) {
            return false;
        }
        PredicateFunction<?> that = (PredicateFunction<?>) obj;
        return this.predicate.equals(that.predicate);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = "PredicateFunction".hashCode();
        hash ^= predicate.hashCode();
        return hash;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "PredicateFunction<" + predicate + ">";
    }

    /**
     * Adapt a Predicate to the Function interface.
     * @param <A> the argument type.
     * @param predicate to adapt
     * @return PredicateFunction
     */
    public static <A> PredicateFunction<A> adapt(Predicate<? super A> predicate) {
        return null == predicate ? null : new PredicateFunction<A>(predicate);
    }

}
