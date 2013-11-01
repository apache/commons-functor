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

import org.apache.commons.functor.BinaryPredicate;
import org.apache.commons.functor.Predicate;
import org.apache.commons.lang3.Validate;

/**
 * Adapts a
 * {@link Predicate Predicate}
 * to the
 * {@link BinaryPredicate BinaryPredicate} interface
 * by ignoring the first binary argument.
 *
 * @param <L> the left argument type.
 * @param <R> the right argument type.
 * @version $Revision$ $Date$
 */
public final class IgnoreLeftPredicate<L, R> implements BinaryPredicate<L, R> {
    /** The {@link Predicate Predicate} I'm wrapping. */
    private final Predicate<? super R> predicate;

    /**
     * Create a new IgnoreLeftPredicate.
     * @param predicate the right predicate
     */
    public IgnoreLeftPredicate(Predicate<? super R> predicate) {
        this.predicate = Validate.notNull(predicate, "Predicate argument was null");
    }

    /**
     * {@inheritDoc}
     */
    public boolean test(L left, R right) {
        return predicate.test(right);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof IgnoreLeftPredicate<?, ?>)) {
            return false;
        }
        IgnoreLeftPredicate<?, ?> that = (IgnoreLeftPredicate<?, ?>) obj;
        return this.predicate.equals(that.predicate);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = "IgnoreLeftPredicate".hashCode();
        hash ^= predicate.hashCode();
        return hash;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "IgnoreLeftPredicate<" + predicate + ">";
    }

    /**
     * Adapt a Predicate to an IgnoreLeftPredicate.
     * @param <L> left type
     * @param <R> right type
     * @param predicate to adapt
     * @return IgnoreLeftPredicate<L, R>
     */
    public static <L, R> IgnoreLeftPredicate<L, R> adapt(Predicate<? super R> predicate) {
        return null == predicate ? null : new IgnoreLeftPredicate<L, R>(predicate);
    }

}
