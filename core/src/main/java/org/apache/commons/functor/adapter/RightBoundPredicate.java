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
 * {@link BinaryPredicate BinaryPredicate}
 * to the
 * {@link Predicate Predicate} interface
 * using a constant left-side argument.
 *
 * @param <A> the argument type.
 * @version $Revision$ $Date$
 */
public final class RightBoundPredicate<A> implements Predicate<A> {
    /** The {@link BinaryPredicate BinaryPredicate} I'm wrapping. */
    private final BinaryPredicate<? super A, Object> predicate;
    /** The parameter to pass to {@code predicate}. */
    private final Object param;

    /**
     * Create a new RightBoundPredicate.
     * @param <R> bound arg type
     * @param predicate the predicate to adapt
     * @param arg the constant argument to use
     */
    @SuppressWarnings("unchecked")
    public <R> RightBoundPredicate(BinaryPredicate<? super A, ? super R> predicate, R arg) {
        this.predicate =
            (BinaryPredicate<? super A, Object>) Validate.notNull(predicate,
                "BinaryPredicate argument was null");
        this.param = arg;
    }

    /**
     * {@inheritDoc}
     */
    public boolean test(A obj) {
        return predicate.test(obj, param);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof RightBoundPredicate<?>)) {
            return false;
        }
        RightBoundPredicate<?> that = (RightBoundPredicate<?>) obj;
        return this.predicate.equals(that.predicate)
                && (null == this.param ? null == that.param : this.param.equals(that.param));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = "RightBoundPredicate".hashCode();
        hash <<= 2;
        hash ^= predicate.hashCode();
        if (null != param) {
            hash <<= 2;
            hash ^= param.hashCode();
        }
        return hash;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "RightBoundPredicate<" + predicate + "(?," + param + ")>";
    }

    /**
     * Adapt a BinaryPredicate as a Predicate.
     * @param <L> the left argument type.
     * @param <R> the right argument type.
     * @param predicate to adapt
     * @param arg right side
     * @return RightBoundPredicate
     */
    public static <L, R> RightBoundPredicate<L> bind(BinaryPredicate<? super L, ? super R> predicate, R arg) {
        return null == predicate ? null : new RightBoundPredicate<L>(predicate, arg);
    }

}
