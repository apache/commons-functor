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

import org.apache.commons.functor.Predicate;

/**
 * {@link #test Tests} <code>true</code> iff
 * none of its children test <code>false</code>.
 * Note that by this definition, the "and" of
 * an empty collection of predicates tests <code>true</code>.
 * @param <A> the predicate argument type.
 * @version $Revision$ $Date$
 */
public final class And<A> extends BasePredicateList<A> {

    // constructor
    // ------------------------------------------------------------------------
    /**
     * Create a new And.
     */
    public And() {
        super();
    }

    /**
     * Create a new And instance.
     *
     * @param predicates the predicates to put in and.
     */
    public And(Iterable<Predicate<? super A>> predicates) {
        super(predicates);
    }

    /**
     * Create a new And instance.
     *
     * @param predicates the predicates to put in and.
     */
    public And(Predicate<? super A>... predicates) {
        super(predicates);
    }

    // modifiers
    // ------------------------------------------------------------------------
    /**
     * Fluently add a Predicate.
     * @param p Predicate to add
     * @return this
     */
    public And<A> and(Predicate<? super A> p) {
        super.addPredicate(p);
        return this;
    }

    // predicate interface
    // ------------------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    public boolean test(A obj) {
        for (Predicate<? super A> p : getPredicateList()) {
            if (!p.test(obj)) {
                return false;
            }
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof And<?>)) {
            return false;
        }
        And<?> that = (And<?>) obj;
        return getPredicateListEquals(that);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return "And".hashCode() ^ getPredicateListHashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "And<" + getPredicateListToString() + ">";
    }

}
