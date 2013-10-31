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

import org.apache.commons.functor.NullaryPredicate;

/**
 * {@link #test Tests} <code>true</code> iff
 * none of its children test <code>false</code>.
 * Note that by this definition, the "and" of
 * an empty collection of predicates tests <code>true</code>.
 * @version $Revision: 1345136 $ $Date: 2012-06-01 09:47:06 -0300 (Fri, 01 Jun 2012) $
 */
public final class NullaryAnd extends BaseNullaryPredicateList {

    // constructor
    // ------------------------------------------------------------------------

    /**
     * Create a new NullaryAnd.
     */
    public NullaryAnd() {
        super();
    }

    /**
     * Create a new NullaryAnd instance.
     *
     * @param predicates the predicates to add
     */
    public NullaryAnd(Iterable<NullaryPredicate> predicates) {
        super(predicates);
    }

    /**
     * Create a new NullaryAnd instance.
     *
     * @param predicates the predicates to add
     */
    public NullaryAnd(NullaryPredicate... predicates) {
        super(predicates);
    }

    // modifiers
    // ------------------------------------------------------------------------
    /**
     * Add a Predicate.
     * @param p Predicate to add
     * @return this
     */
    public NullaryAnd and(NullaryPredicate p) {
        super.addNullaryPredicate(p);
        return this;
    }

    // predicate interface
    // ------------------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    public boolean test() {
        for (NullaryPredicate p : getNullaryPredicateList()) {
            if (!p.test()) {
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
        if (!(obj instanceof NullaryAnd)) {
            return false;
        }
        NullaryAnd that = (NullaryAnd) obj;
        return getNullaryPredicateListEquals(that);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return "And".hashCode() ^ getNullaryPredicateListHashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "And<" + getNullaryPredicateListToString() + ">";
    }

}
