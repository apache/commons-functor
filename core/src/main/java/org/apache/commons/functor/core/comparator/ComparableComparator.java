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

/**
 * A {@link Comparator Comparator} that compares {@link Comparable Comparable}
 * objects.
 * <p>
 * This class was created based on commons-collection's ComparableComparator.
 *
 * @param <E> the comparable type
 * @version $Revision$ $Date$
 */
final class ComparableComparator<E extends Comparable<? super E>> implements Comparator<E> {

    /** Singleton. */
    @SuppressWarnings("rawtypes")
    public static final ComparableComparator<?> INSTANCE = new ComparableComparator();

    /**
     * Create a new ComparableComparator.
     */
    public ComparableComparator() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    public int compare(E o1, E o2) {
        return o1.compareTo(o2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        return (obj instanceof ComparableComparator);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "ComparableComparator";
    }

    /**
     * Get a ComparableComparator instance.
     * @param <E> the comparable type
     * @return ComparableComparator
     */
    @SuppressWarnings("unchecked")
    public static <E extends Comparable<? super E>> ComparableComparator<E> instance() {
        return (ComparableComparator<E>) INSTANCE;
    }

}
