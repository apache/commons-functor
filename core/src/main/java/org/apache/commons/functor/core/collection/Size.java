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

import org.apache.commons.functor.Function;
import org.apache.commons.lang3.Validate;

/**
 * Returns the size of the specified Collection, or the length
 * of the specified array or String.
 *
 * @param <A> the function argument type.
 * @version $Revision$ $Date$
 */
public final class Size<A> implements Function<A, Integer> {

    /**
     * A static {@code Size} instance reference.
     */
    private static final Size<Object> INSTANCE = new Size<Object>();

    // constructor
    // ------------------------------------------------------------------------
    /**
     * Create a new Size.
     */
    public Size() { }

    /**
     * {@inheritDoc}
     */
    public Integer evaluate(Object obj) {
        Validate.notNull(obj, "Argument must not be null");
        if (obj instanceof Collection<?>) {
            return evaluate((Collection<?>) obj);
        }
        if (obj instanceof String) {
            return evaluate((String) obj);
        }
        if (obj.getClass().isArray()) {
            return evaluateArray(obj);
        }
        throw new IllegalArgumentException("Expected Collection, String or Array, found " + obj);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object that) {
        return that instanceof Size<?>;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return "Size".hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Size()";
    }

    /**
     * Get a Size instance.
     * @return Size
     */
    public static Size<Object> instance() {
        return INSTANCE;
    }

    /**
     * Evaluate a Collection.
     * @param col to evaluate
     * @return Integer
     */
    private Integer evaluate(Collection<?> col) {
        return Integer.valueOf(col.size());
    }

    /**
     * Evaluate a String.
     * @param str to evaluate
     * @return Integer
     */
    private Integer evaluate(String str) {
        return Integer.valueOf(str.length());
    }

    /**
     * Evaluate an array.
     * @param array to evaluate
     * @return Integer
     */
    private Integer evaluateArray(Object array) {
        return Integer.valueOf(Array.getLength(array));
    }

}
