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

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.functor.BinaryPredicate;
import org.apache.commons.functor.NullaryPredicate;
import org.apache.commons.functor.Predicate;
import org.apache.commons.lang3.Validate;

/**
 * A predicate that returns <code>true</code>
 * the first <i>n</i> times it is invoked, and
 * <code>false</code> thereafter.
 *
 * @since 1.0
 * @version $Revision$ $Date$
 */
public final class Limit implements NullaryPredicate, Predicate<Object>, BinaryPredicate<Object, Object> {
    // instance variables
    //---------------------------------------------------------------
    /**
     * The max number of times the predicate can be invoked.
     */
    private final int max;
    /**
     * The current number of times the predicate has been invoked.
     */
    private final AtomicInteger state = new AtomicInteger();

    /**
     * Create a new Limit.
     * @param count limit
     */
    public Limit(int count) {
        Validate.isTrue(count >= 0, "Argument must be a non-negative integer.");
        this.max = count;
    }

    /**
     * {@inheritDoc}
     */
    public boolean test() {
        // stop incrementing when we've hit max, so we don't loop around
        if (state.get() < max) {
            state.incrementAndGet();
            return true;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    public boolean test(Object obj) {
        return test();
    }

    /**
     * {@inheritDoc}
     */
    public boolean test(Object a, Object b) {
        return test();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Limit)) {
            return false;
        }
        Limit other = (Limit) obj;
        return other.max == max;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = "Limit".hashCode();
        result <<= 2;
        result ^= max;
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Limit<" + max + ">";
    }

    //default == equals/hashCode due to statefulness

    // static methods
    // ------------------------------------------------------------------------

    /**
     * Get a Limit instance for the specified value.
     * @param count limit
     * @return Limit
     */
    public static Limit of(int count) {
        return new Limit(count);
    }

}
