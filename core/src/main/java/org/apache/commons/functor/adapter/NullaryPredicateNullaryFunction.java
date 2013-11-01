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

import org.apache.commons.functor.NullaryFunction;
import org.apache.commons.functor.NullaryPredicate;
import org.apache.commons.lang3.Validate;

/**
 * Adapts a
 * {@link NullaryPredicate Predicate}
 * to the
 * {@link NullaryFunction NullaryFunction} interface.
 *
 * @version $Revision: 1365377 $ $Date: 2012-07-24 21:59:23 -0300 (Tue, 24 Jul 2012) $
 */
public final class NullaryPredicateNullaryFunction implements NullaryFunction<Boolean> {
    /** The {@link NullaryPredicate NullaryPredicate} I'm wrapping. */
    private final NullaryPredicate predicate;

    /**
     * Create a new NullaryPredicateNullaryFunction.
     * @param predicate to adapt
     */
    public NullaryPredicateNullaryFunction(NullaryPredicate predicate) {
        this.predicate = Validate.notNull(predicate, "NullaryPredicate argument was null");
    }

    /**
     * {@inheritDoc}
     * Returns <code>Boolean.TRUE</code> (<code>Boolean.FALSE</code>)
     * when the {@link NullaryPredicate#test test} method of my underlying
     * predicate returns <code>true</code> (<code>false</code>).
     *
     * @return a non-<code>null</code> <code>Boolean</code> instance
     */
    public Boolean evaluate() {
        return Boolean.valueOf(predicate.test());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof NullaryPredicateNullaryFunction)) {
            return false;
        }
        NullaryPredicateNullaryFunction that = (NullaryPredicateNullaryFunction) obj;
        return this.predicate.equals(that.predicate);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = "NullaryPredicateNullaryFunction".hashCode();
        hash ^= predicate.hashCode();
        return hash;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "NullaryPredicateNullaryFunction<" + predicate + ">";
    }

    /**
     * Adapt a NullaryPredicate to the NullaryFunction interface.
     * @param predicate to adapt
     * @return NullaryPredicateNullaryFunction
     */
    public static NullaryPredicateNullaryFunction adapt(NullaryPredicate predicate) {
        return null == predicate ? null : new NullaryPredicateNullaryFunction(predicate);
    }

}
