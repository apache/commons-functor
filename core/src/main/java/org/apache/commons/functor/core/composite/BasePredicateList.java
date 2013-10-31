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

import org.apache.commons.functor.Predicate;

/**
 * Abstract base class for {@link Predicate Predicates}
 * composed of a list of {@link Predicate Predicates}.
 * @param <A> the predicate argument type.
 * @version $Revision$ $Date$
 */
abstract class BasePredicateList<A> implements Predicate<A> {

    // attributes
    // ------------------------------------------------------------------------
    /**
     * A list where storing the adapted predicates.
     */
    private final List<Predicate<? super A>> list = new ArrayList<Predicate<? super A>>();

    // constructor
    // ------------------------------------------------------------------------
    /**
     * Create a new BasePredicateList.
     */
    protected BasePredicateList() {
        super();
    }

    /**
     * Create a new BasePredicateList instance.
     *
     * @param predicates to add
     */
    protected BasePredicateList(Predicate<? super A>... predicates) {
        this();
        if (predicates != null) {
            for (Predicate<? super A> p : predicates) {
                addPredicate(p);
            }
        }
    }

    /**
     * Create a new BasePredicateList instance.
     *
     * @param predicates to add
     */
    protected BasePredicateList(Iterable<Predicate<? super A>> predicates) {
        this();
        if (predicates != null) {
            for (Predicate<? super A> p : predicates) {
                addPredicate(p);
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
     * Add a Predicate to the list.
     * @param p Predicate to add
     */
    protected void addPredicate(Predicate<? super A> p) {
        if (p != null) {
            list.add(p);
        }
    }

    // protected
    // ------------------------------------------------------------------------

    /**
     * Get the "live" list of contained {@link Predicate}s.
     * @return List
     */
    protected List<Predicate<? super A>> getPredicateList() {
        return list;
    }

    /**
     * Learn whether another BasePredicateList has content equal to this.
     * @param that the BasePredicateList to test
     * @return boolean
     */
    protected boolean getPredicateListEquals(BasePredicateList<?> that) {
        return (null != that && this.list.equals(that.list));
    }

    /**
     * Get a hashCode for the list.
     * @return int
     */
    protected int getPredicateListHashCode() {
        return list.hashCode();
    }

    /**
     * Get a toString for the list.
     * @return String
     */
    protected String getPredicateListToString() {
        return String.valueOf(list);
    }

}
