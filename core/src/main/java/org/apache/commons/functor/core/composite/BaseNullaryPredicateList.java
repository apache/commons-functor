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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.functor.NullaryPredicate;

/**
 * Abstract base class for {@link NullaryPredicate NullaryPredicates}
 * composed of a list of {@link NullaryPredicate NullaryPredicates}.
 * @version $Revision: 1365329 $ $Date: 2012-07-24 19:34:23 -0300 (Tue, 24 Jul 2012) $
 */
abstract class BaseNullaryPredicateList implements NullaryPredicate {
    // attributes
    // ------------------------------------------------------------------------
    /**
     * A list where storing the adapted predicates.
     */
    private final List<NullaryPredicate> list = new ArrayList<NullaryPredicate>();

    // constructor
    // ------------------------------------------------------------------------
    /**
     * Create a new BaseNullaryPredicateList instance.
     */
    protected BaseNullaryPredicateList() {
        super();
    }

    /**
     * Create a new BaseNullaryPredicateList instance.
     *
     * @param predicates to add
     */
    protected BaseNullaryPredicateList(NullaryPredicate... predicates) {
        this();
        if (predicates != null) {
            for (NullaryPredicate p : predicates) {
                addNullaryPredicate(p);
            }
        }
    }

    /**
     * Create a new BaseNullaryPredicateList instance.
     *
     * @param predicates to add
     */
    protected BaseNullaryPredicateList(Iterable<NullaryPredicate> predicates) {
        this();
        if (predicates != null) {
            for (NullaryPredicate p : predicates) {
                addNullaryPredicate(p);
            }
        }
    }

    // abstract
    // ------------------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    @Override
    public abstract boolean equals(Object that);

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract int hashCode();

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract String toString();

    // modifiers
    // ------------------------------------------------------------------------
    /**
     * Add a NullaryPredicate to the list.
     * @param p NullaryPredicate to add
     */
    protected void addNullaryPredicate(NullaryPredicate p) {
        if (p != null) {
            list.add(p);
        }
    }

    // protected
    // ------------------------------------------------------------------------

    /**
     * Get the "live" list of {@link NullaryPredicate}s.
     * @return List<NullaryPredicate>
     */
    protected List<NullaryPredicate> getNullaryPredicateList() {
        return list;
    }

    /**
     * Learn whether the list of another BaseNullaryPredicateList is equal to my list.
     * @param that BaseNullaryPredicateList to test
     * @return boolean
     */
    protected boolean getNullaryPredicateListEquals(BaseNullaryPredicateList that) {
        return (null != that && this.list.equals(that.list));
    }

    /**
     * Get a hashCode for my list.
     * @return int
     */
    protected int getNullaryPredicateListHashCode() {
        return list.hashCode();
    }

    /**
     * Get a toString for my list.
     * @return String
     */
    protected String getNullaryPredicateListToString() {
        return String.valueOf(list);
    }

}
