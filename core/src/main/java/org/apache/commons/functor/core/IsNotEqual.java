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
package org.apache.commons.functor.core;

import org.apache.commons.functor.BinaryPredicate;
import org.apache.commons.functor.Predicate;
import org.apache.commons.functor.adapter.RightBoundPredicate;

/**
 * {@link #test Tests}
 * <code>true</code> iff its arguments are
 * not {@link Object#equals equal} or both
 * <code>null</code>.
 * <p>
 * This relation is symmetric but irreflexive
 * and not transitive.
 * </p>
 * @param <L> the left argument type.
 * @param <R> the right argument type.
 */
public final class IsNotEqual<L, R> implements BinaryPredicate<L, R> {
    // static attributes
    // ------------------------------------------------------------------------
    /**
     * Basic IsNotEqual<Object, Object> instance.
     */
    public static final IsNotEqual<Object, Object> INSTANCE = IsNotEqual.<Object, Object>instance();

    // constructor
    // ------------------------------------------------------------------------
    /**
     * Create a new IsNotEqual.
     */
    public IsNotEqual() {
    }

    // predicate interface
    // ------------------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    public boolean test(L left, R right) {
        return (null == left ? null != right : !left.equals(right));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object that) {
        return that instanceof IsNotEqual<?, ?>;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return "IsNotEqual".hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "IsNotEqual";
    }

    // static methods
  // ------------------------------------------------------------------------

    /**
     * Get an IsNotEqual instance.
     * @param <L> the left argument type.
     * @param <R> the right argument type.
     * @return IsNotEqual<L, R>
     */
    public static <L, R> IsNotEqual<L, R> instance() {
        return new IsNotEqual<L, R>();
    }

    /**
     * Get an IsNotEqual Predicate.
     * @param <L> the left argument type.
     * @param <R> the right argument type.
     * @param object bound comparison object
     * @return Predicate<L>
     */
    public static <L, R> Predicate<L> to(R object) {
        return new RightBoundPredicate<L>(new IsNotEqual<L, R>(), object);
    }
}
