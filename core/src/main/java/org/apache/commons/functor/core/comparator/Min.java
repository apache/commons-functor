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

import java.util.Comparator;

import org.apache.commons.functor.BinaryFunction;
import org.apache.commons.functor.Function;
import org.apache.commons.functor.adapter.RightBoundFunction;
import org.apache.commons.lang3.Validate;

/**
 * Adapts a {@link Comparator Comparator} to the
 * {@link BinaryFunction} interface.
 *
 * @param <T> the binary function arguments and return types.
 * @version $Revision$ $Date$
 */
public final class Min<T> implements BinaryFunction<T, T, T> {

    /**
     * Basic Min instance.
     */
    public static final Min<Comparable<?>> INSTANCE = Min.<Comparable<?>>instance();

    /**
     * The wrapped comparator.
     */
    private final Comparator<T> comparator;

    /**
     * Create a new Min.
     */
    @SuppressWarnings("unchecked")
    public Min() {
        this((Comparator<T>) ComparableComparator.INSTANCE);
    }

    /**
     * Create a new Min.
     * @param comparator to use
     */
    public Min(Comparator<T> comparator) {
        this.comparator = Validate.notNull(comparator, "Comparator argument must not be null");
    }

    /**
     * {@inheritDoc}
     */
    public T evaluate(T left, T right) {
        return (comparator.compare(left, right) <= 0) ? left : right;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Min<?>)) {
            return false;
        }
        Min<?> that = (Min<?>) obj;
        return this.comparator.equals(that.comparator);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return "Min".hashCode() ^ comparator.hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Min<" + comparator + ">";
    }

    /**
     * Get a basic Min instance.
     *
     * @param <T> the binary function arguments and return types.
     * @return Min
     */
    public static <T extends Comparable<?>> Min<T> instance() {
        return new Min<T>();
    }

    /**
     * Get a Min Function.
     *
     * @param <T> the binary function arguments and return types.
     * @param right the right side argument of the Min function
     * @return Function<T, T>
     */
    public static <T extends Comparable<?>> Function<T, T> instance(T right) {
        return RightBoundFunction.bind(new Min<T>(), right);
    }

}
