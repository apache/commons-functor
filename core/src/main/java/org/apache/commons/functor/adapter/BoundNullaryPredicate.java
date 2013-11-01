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
 * {@link Predicate Predicate}
 * to the
 * {@link NullaryPredicate NullaryPredicate} interface
 * using a constant unary argument.
 *
 * @version $Revision: 1365377 $ $Date: 2012-07-24 21:59:23 -0300 (Tue, 24 Jul 2012) $
 */
public final class BoundNullaryPredicate implements NullaryPredicate {
    /** The {@link Predicate Predicate} I'm wrapping. */
    private final Predicate<Object> predicate;
    /** The parameter to pass to {@code predicate}. */
    private final Object param;

    /**
     * Create a new BoundNullaryPredicate instance.
     * @param <A> input type
     * @param predicate the predicate to adapt
     * @param arg the constant argument to use
     */
    @SuppressWarnings("unchecked")
    public <A> BoundNullaryPredicate(Predicate<? super A> predicate, A arg) {
        this.predicate =
            (Predicate<Object>) Validate.notNull(predicate,
                "Predicate argument was null");
        this.param = arg;
    }

    /**
     * {@inheritDoc}
     */
    public boolean test() {
        return predicate.test(param);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof BoundNullaryPredicate)) {
            return false;
        }
        BoundNullaryPredicate that = (BoundNullaryPredicate) obj;
        return predicate.equals(that.predicate)
                && (null == param ? null == that.param : param.equals(that.param));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = "BoundNullaryPredicate".hashCode();
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
        return "BoundNullaryPredicate<" + predicate + "(" + param + ")>";
    }

    /**
     * Adapt the given, possibly-<code>null</code>,
     * {@link Predicate Predicate} to the
     * {@link NullaryPredicate NullaryPredicate} interface by binding
     * the specified <code>Object</code> as a constant
     * argument.
     * When the given <code>Predicate</code> is <code>null</code>,
     * returns <code>null</code>.
     *
     * @param <A> input type
     * @param predicate the possibly-<code>null</code>
     *        {@link Predicate Predicate} to adapt
     * @param arg the object to bind as a constant argument
     * @return a <code>BoundNullaryPredicate</code> wrapping the given
     *         {@link Predicate Predicate}, or <code>null</code>
     *         if the given <code>Predicate</code> is <code>null</code>
     */
    public static <A> BoundNullaryPredicate bind(Predicate<? super A> predicate, A arg) {
        return null == predicate ? null : new BoundNullaryPredicate(predicate, arg);
    }

}
