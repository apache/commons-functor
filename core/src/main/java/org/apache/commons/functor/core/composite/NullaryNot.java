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

import java.io.Serializable;

import org.apache.commons.functor.NullaryPredicate;
import org.apache.commons.lang3.Validate;

/**
 * {@link #test Tests} to the logical inverse
 * of some other predicate.
 * <p>
 * Note that although this class implements
 * {@link Serializable}, a given instance will
 * only be truly <code>Serializable</code> if the
 * underlying functor is.  Attempts to serialize
 * an instance whose delegate is not
 * <code>Serializable</code> will result in an exception.
 * </p>
 * @version $Revision: 1365329 $ $Date: 2012-07-24 19:34:23 -0300 (Tue, 24 Jul 2012) $
 */
public final class NullaryNot implements NullaryPredicate, Serializable {

    /**
     * serialVersionUID declaration.
     */
    private static final long serialVersionUID = 8268713706856765874L;
    // attributes
    // ------------------------------------------------------------------------
    /**
     * The adapted predicate has to be negated.
     */
    private final NullaryPredicate predicate;

    // constructor
    // ------------------------------------------------------------------------
    /**
     * Create a new NullaryNot.
     * @param predicate NullaryPredicate to negate
     */
    public NullaryNot(NullaryPredicate predicate) {
        this.predicate = Validate.notNull(predicate, "NullaryPredicate argument was null");
    }

    // predicate interface
    // ------------------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    public boolean test() {
        return !(predicate.test());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object that) {
        return that == this || (that instanceof NullaryNot && equals((NullaryNot) that));
    }

    /**
     * Learn whether another NullaryNot is equal to this.
     * @param that the NullaryNot to test
     * @return boolean
     */
    public boolean equals(NullaryNot that) {
        return null != that && predicate.equals(that.predicate);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = "Not".hashCode();
        hash ^= predicate.hashCode();
        return hash;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Not<" + predicate + ">";
    }

    // static
    // ------------------------------------------------------------------------
    /**
     * Get a NullaryNot instance for <code>that</code>.
     * @param that Predicate to negate
     * @return NullaryNot
     */
    public static NullaryPredicate not(NullaryPredicate that) {
        return null == that ? null : new NullaryNot(that);
    }
}
