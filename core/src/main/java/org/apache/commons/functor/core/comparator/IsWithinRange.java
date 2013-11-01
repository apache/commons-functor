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
package org.apache.commons.functor.core.comparator;

import org.apache.commons.functor.Predicate;
import org.apache.commons.lang3.Validate;

/**
 * A {@link Predicate} that tests whether a {@link Comparable} object is
 * within a range. The range is defined in the constructor.
 *
 * @since 1.0
 * @param <A> the predicate argument type.
 * @version $Revision$ $Date$
 */
public class IsWithinRange<A extends Comparable<A>> implements Predicate<A> {

    /** Hashcode of the name of this Predicate. */
    private static final int NAME_HASH_CODE = "IsWithinRange".hashCode();

    /** Base Hashcode. */
    private static final int BASE_HASH_CODE = 29;

    /***************************************************
     *  Instance variables
     ***************************************************/

    /** The minimum value of the range. */
    private final A min;
    /** The maximum value of the range. */
    private final A max;

    /***************************************************
     *  Constructors
     ***************************************************/

    /**
     * Create a new IsWithinRange by passing in the range that will
     * be used in the {@link #test}.
     * @param min Comparable
     * @param max Comparable
     */
    public IsWithinRange(A min, A max) {
        Validate.notNull(min, "min must not be null");
        Validate.notNull(max, "max must not be null");
        if (min.compareTo(max) > 0) {
            throw new IllegalArgumentException("min must be <= max");
        }
        this.min = min;
        this.max = max;
    }

    /***************************************************
     *  Instance methods
     ***************************************************/

    /**
     * {@inheritDoc}
     * Test if the passed in object is within the specified range.
     */
    public final boolean test(A o) {
        return o.compareTo(min) >= 0 && o.compareTo(max) <= 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IsWithinRange<?>)) {
            return false;
        }
        final IsWithinRange<?> isWithinRange = (IsWithinRange<?>) o;
        return max.equals(isWithinRange.max) && min.equals(isWithinRange.min);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return BASE_HASH_CODE * min.hashCode() + max.hashCode() + NAME_HASH_CODE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "IsWithinRange(" + min + ", " + max + ")";
    }

    /**
     * Obtain an IsWithinRange instance.
     * @param <A> the argument type.
     * @param min the min value
     * @param max the max value
     * @return IsWithinRange<A>
     */
    public static <A extends Comparable<A>> IsWithinRange<A> instance(A min, A max) {
        return new IsWithinRange<A>(min, max);
    }
}
