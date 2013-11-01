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
import org.apache.commons.lang3.Validate;

/**
 * Adapts a {@link Comparator Comparator} to the
 * {@link BinaryFunction} interface.
 *
 * @param <T> the binary function input types
 * @version $Revision$ $Date$
 */
public final class ComparatorFunction<T> implements BinaryFunction<T, T, Integer> {

    /**
     * Basic ComparatorFunction instance.
     */
    public static final ComparatorFunction<Comparable<?>> INSTANCE = ComparatorFunction.<Comparable<?>>instance();

    /**
     * The comparator to wrap.
     */
    private final Comparator<? super T> comparator;

    /**
     * Create a new ComparatorFunction.
     * @param comparator to wrap
     */
    public ComparatorFunction(Comparator<? super T> comparator) {
        this.comparator = Validate.notNull(comparator, "Comparator argument must not be null");
    }

    /**
     * {@inheritDoc}
     */
    public Integer evaluate(T left, T right) {
        return Integer.valueOf(comparator.compare(left, right));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ComparatorFunction<?>)) {
            return false;
        }
        ComparatorFunction<?> that = (ComparatorFunction<?>) obj;
        return this.comparator.equals(that.comparator);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return "ComparatorFunction".hashCode() ^ comparator.hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "ComparatorFunction<" + comparator + ">";
    }

    /**
     * Get a basic ComparatorFunction instance.
     *
     * @param <T> the binary function input types
     * @return ComparatorFunction
     */
    @SuppressWarnings("unchecked")
    public static <T> ComparatorFunction<T> instance() {
        return new ComparatorFunction<T>((Comparator<? super T>) ComparableComparator.INSTANCE);
    }
}
