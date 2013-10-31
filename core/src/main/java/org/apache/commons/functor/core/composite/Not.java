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
import org.apache.commons.lang3.Validate;

/**
 * {@link #test Tests} to the logical inverse
 * of some other predicate.
 * @param <A> the argument type.
 * @version $Revision$ $Date$
 */
public final class Not<A> implements Predicate<A> {
    // attributes
    // ------------------------------------------------------------------------
    /**
     * The adapted predicate.
     */
    private final Predicate<? super A> predicate;

    // constructor
    // ------------------------------------------------------------------------
    /**
     * Create a new Not.
     * @param predicate Predicate to negate
     */
    public Not(Predicate<? super A> predicate) {
        this.predicate = Validate.notNull(predicate, "Predicate argument was null");
    }

    // predicate interface
    // ------------------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    public boolean test(A obj) {
        return !(predicate.test(obj));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Not<?>)) {
            return false;
        }
        Not<?> that = (Not<?>) obj;
        return this.predicate.equals(that.predicate);
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
     * Invert a Predicate.
     * @param <A> the argument type.
     * @param pred Predicate to invert
     * @return Predicate<A>
     */
    public static <A> Predicate<A> not(Predicate<? super A> pred) {
        return null == pred ? null : new Not<A>(pred);
    }

}
