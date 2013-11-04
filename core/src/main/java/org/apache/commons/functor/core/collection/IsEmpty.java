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
package org.apache.commons.functor.core.collection;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.functor.Predicate;
import org.apache.commons.lang3.Validate;

/**
 * A {@link Predicate} that checks to see if the specified object is empty.
 *
 * @param <A> the predicate argument type.
 * @version $Revision$ $Date$
 */
public final class IsEmpty<A> implements Predicate<A> {

    // class variables
    // ------------------------------------------------------------------------

    /**
     * Basic IsEmpty instance.
     */
    public static final IsEmpty<Object> INSTANCE = new IsEmpty<Object>();

    // constructor
    // ------------------------------------------------------------------------
    /**
     * Create a new IsEmpty.
     */
    public IsEmpty() {
    }

    // instance methods
    // ------------------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    public boolean test(A obj) {
        Validate.notNull(obj, "Argument must not be null");
        if (obj instanceof Collection<?>) {
            return testCollection((Collection<?>) obj);
        }
        if (obj instanceof Map<?, ?>) {
            return testMap((Map<?, ?>) obj);
        }
        if (obj instanceof String) {
            return testString((String) obj);
        }
        if (obj.getClass().isArray()) {
            return testArray(obj);
        }
        throw new IllegalArgumentException("Expected Collection, Map, String or Array, found " + obj.getClass());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object that) {
        return that instanceof IsEmpty<?>;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return "IsEmpty".hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "IsEmpty()";
    }

    /**
     * Test a collection.
     * @param col to test
     * @return boolean
     */
    private boolean testCollection(Collection<?> col) {
        return col.isEmpty();
    }

    /**
     * Test a map.
     * @param map to test
     * @return boolean
     */
    private boolean testMap(Map<?, ?> map) {
        return map.isEmpty();
    }

    /**
     * Test a string.
     * @param str to test
     * @return boolean
     */
    private boolean testString(String str) {
        return 0 == str.length();
    }

    /**
     * Test an array.
     * @param array to test
     * @return boolean
     */
    private boolean testArray(Object array) {
        return 0 == Array.getLength(array);
    }

    // static
    // ------------------------------------------------------------------------
    /**
     * Get an IsEmpty instance.
     *
     * @param <A> the predicate argument type.
     * @return IsEmpty
     */
    public static <A> IsEmpty<A> instance() {
        return new IsEmpty<A>();
    }

}
