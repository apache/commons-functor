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

import org.apache.commons.functor.BinaryPredicate;

/**
 * {@link #test Tests} <code>true</code> iff
 * at least one of its children test <code>true</code>.
 * Note that by this definition, the "or" of
 * an empty collection of predicates tests <code>false</code>.
 * @param <L> the left argument type.
 * @param <R> the right argument type.
 * @version $Revision$ $Date$
 */
public final class BinaryOr<L, R> extends BaseBinaryPredicateList<L, R> {

    // constructor
    // ------------------------------------------------------------------------
    /**
     * Create a new BinaryOr.
     */
    public BinaryOr() {
        super();
    }

    /**
     * Create a new BinaryOr instance.
     *
     * @param predicates the predicates to add
     */
    public BinaryOr(BinaryPredicate<? super L, ? super R>... predicates) {
        super(predicates);
    }

    /**
     * Create a new BinaryOr instance.
     *
     * @param predicates the predicates to add
     */
    public BinaryOr(Iterable<BinaryPredicate<? super L, ? super R>> predicates) {
        super(predicates);
    }

    // modifiers
    // ------------------------------------------------------------------------
    /**
     * Fluently add a BinaryPredicate.
     * @param p BinaryPredicate to add
     * @return this
     */
    public BinaryOr<L, R> or(BinaryPredicate<? super L, ? super R> p) {
        super.addBinaryPredicate(p);
        return this;
    }

    // predicate interface
    // ------------------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    public boolean test(L a, R b) {
        for (BinaryPredicate<? super L, ? super R> p : getBinaryPredicateList()) {
            if (p.test(a, b)) {
                return true;
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof BinaryOr<?, ?>)) {
            return false;
        }
        BinaryOr<?, ?> that = (BinaryOr<?, ?>) obj;
        return getBinaryPredicateListEquals(that);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return "BinaryOr".hashCode() ^ getBinaryPredicateListHashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "BinaryOr<" + getBinaryPredicateListToString() + ">";
    }

}
