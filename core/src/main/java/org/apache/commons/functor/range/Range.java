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
package org.apache.commons.functor.range;

import java.util.Collection;

/**
 * Represent an interval of elements that varies from the <b>left limit</b>
 * to the <b>right limit</b>. Each limit in this range is an {@link Endpoint
 * Endpoint}. The left and the right limits can be <b>inclusive</b>
 * (<b>bounded</b>, <b>closed</b>) or <b>exclusive</b> (<b>unbounded</b>,
 * <b>open</b>).
 * <p>
 * The difference between each element within this range is called <b>step</b>.
 * The step can be positive or negative, displaying whether the range elements
 * are ascending or descending.
 *
 * @param <T> the type of elements held by this range.
 * @param <S> the type of the step of this range.
 * @see org.apache.commons.functor.range.Endpoint
 * @since 1.0
 */
public interface Range<T extends Comparable<?>, S> extends Iterable<T> {

    /**
     * Default left bound type.
     */
    BoundType DEFAULT_LEFT_BOUND_TYPE = BoundType.CLOSED;

    /**
     * Default right bound type.
     */
    BoundType DEFAULT_RIGHT_BOUND_TYPE = BoundType.OPEN;

    /**
     * Get the left limit of this range.
     *
     * @return Endpoint
     */
    Endpoint<T> getLeftEndpoint();

    /**
     * Get the right limit of this range.
     *
     * @return Endpoint
     */
    Endpoint<T> getRightEndpoint();

    /**
     * Get the step between elements of this range.
     *
     * @return Number
     */
    S getStep();

    /**
     * Returns <code>true</code> if this range is empty.
     *
     * @return <code>true</code> if this range is empty
     */
    boolean isEmpty();

    /**
     * Returns <code>true</code> if this range contains the specified element.
     *
     * @param obj element whose presence is being tested in this range
     * @return <code>true</code> if this range contains the specified element
     */
    boolean contains(T obj);

    /**
     * Returns <code>true</code> is this range contains all of the elements in
     * the specified collection.
     *
     * @param col collection to be checked for the containment in this range
     * @return <code>true</code> if this range contains all of the elements in
     * the specified collection
     */
    boolean containsAll(Collection<T> col);
}
