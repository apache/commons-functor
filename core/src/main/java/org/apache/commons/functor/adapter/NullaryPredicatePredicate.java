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

import org.apache.commons.functor.NullaryPredicate;
import org.apache.commons.functor.Predicate;
import org.apache.commons.lang3.Validate;

/**
 * Adapts a
 * {@link NullaryPredicate NullaryPredicate}
 * to the
 * {@link Predicate Predicate} interface
 * by ignoring the given argument.
 *
 * @param <A> the argument type.
 */
public final class NullaryPredicatePredicate<A> implements Predicate<A> {
    /** The {@link NullaryPredicate NullaryPredicate} I'm wrapping. */
    private final NullaryPredicate predicate;

    /**
     * Create a new NullaryPredicatePredicate.
     * @param predicate to adapt
     */
    public NullaryPredicatePredicate(NullaryPredicate predicate) {
        this.predicate = Validate.notNull(predicate, "NullaryPredicate argument was null");
    }

    /**
     * {@inheritDoc}
     */
    public boolean test(Object obj) {
        return predicate.test();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof NullaryPredicatePredicate<?>)) {
            return false;
        }
        NullaryPredicatePredicate<?> that = (NullaryPredicatePredicate<?>) obj;
        return this.predicate.equals(that.predicate);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = "NullaryPredicatePredicate".hashCode();
        hash ^= predicate.hashCode();
        return hash;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "NullaryPredicatePredicate<" + predicate + ">";
    }

    /**
     * Adapt a NullaryPredicate to the Predicate interface.
     * @param <A> the argument type.
     * @param predicate to adapt
     * @return NullaryPredicatePredicate<A>
     */
    public static <A> NullaryPredicatePredicate<A> adapt(NullaryPredicate predicate) {
        return null == predicate ? null : new NullaryPredicatePredicate<A>(predicate);
    }

}
