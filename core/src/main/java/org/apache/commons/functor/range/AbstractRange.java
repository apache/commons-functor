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
import java.util.Collections;
import java.util.Iterator;

import org.apache.commons.functor.BinaryFunction;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;

/**
 * Abstract {@link Range}.
 *
 * @param <T> type of element
 * @param <S> type of step
 */
public abstract class AbstractRange<T extends Comparable<T>, S> implements Range<T, S> {

    /**
     * Left limit.
     */
    protected final Endpoint<T> leftEndpoint;

    /**
     * Right limit.
     */
    protected final Endpoint<T> rightEndpoint;

    /**
     * Increment step.
     */
    protected final S step;

    /**
     * Function to implement the taking of a step.
     */
    private final BinaryFunction<T, S, T> nextValue;

    /**
     * Create a new {@link AbstractRange}.
     *
     * @param leftEndpoint left endpoint
     * @param rightEndpoint right endpoint
     * @param step increment step
     * @param nextValue function to implement the taking of a step
     */
    protected AbstractRange(Endpoint<T> leftEndpoint, Endpoint<T> rightEndpoint, S step,
            BinaryFunction<T, S, T> nextValue) {
        super();
        this.leftEndpoint = Validate.notNull(leftEndpoint, "Left Endpoint argument must not be null");
        this.rightEndpoint = Validate.notNull(rightEndpoint, "Right Endpoint argument must not be null");
        this.step = Validate.notNull(step, "step must not be null");
        this.nextValue = Validate.notNull(nextValue, "next value function");
    }

    /**
     * {@inheritDoc}
     */
    public Endpoint<T> getLeftEndpoint() {
        return leftEndpoint;
    }

    /**
     * {@inheritDoc}
     */
    public Endpoint<T> getRightEndpoint() {
        return rightEndpoint;
    }

    /**
     * {@inheritDoc}
     */
    public S getStep() {
        return step;
    }

    /**
     * {@inheritDoc}
     */
    public boolean containsAll(Collection<T> coll) {
        if (coll == null || coll.isEmpty()) {
            return false;
        }
        for (T t : coll) {
            if (!contains(t)) {
                return false;
            }
        }
        return true;
    }

    // iterable
    // ---------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    public final Iterator<T> iterator() {
        // empty range -> empty iterator:
        if (isEmpty()) {
            return Collections.<T> emptySet().iterator();
        }
        // not empty and same values -> single-value range:
        if (ObjectUtils.equals(leftEndpoint.getValue(), rightEndpoint.getValue())) {
            return Collections.singleton(leftEndpoint.getValue()).iterator();
        }
        return createIterator();
    }

    /**
     * Create a non-empty iterator.
     *
     * @return Iterator
     */
    protected abstract Iterator<T> createIterator();

    // object methods
    // ---------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        final String pattern = "%s<%s, %s, %s>";
        return String.format(pattern, getClass().getSimpleName(), leftEndpoint.toLeftString(),
            rightEndpoint.toRightString(), step);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof AbstractRange<?, ?>)) {
            return false;
        }
        final Range<?, ?> that = (Range<?, ?>) obj;
        return new EqualsBuilder().append(getLeftEndpoint(), that.getLeftEndpoint())
            .append(getRightEndpoint(), that.getRightEndpoint()).append(getStep(), that.getStep()).build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = getClass().getName().hashCode();
        hash <<= 2;
        hash ^= this.leftEndpoint.hashCode();
        hash <<= 2;
        hash ^= this.rightEndpoint.hashCode();
        hash <<= 2;
        hash ^= this.step.hashCode();
        return hash;
    }

    /**
     * {@inheritDoc}
     */
    public final boolean isEmpty() {
        final T leftValue = leftEndpoint.getValue();
        final T rightValue = rightEndpoint.getValue();

        final int cmp = ObjectUtils.compare(leftValue, rightValue);
        final boolean closedLeft = leftEndpoint.getBoundType() == BoundType.CLOSED;
        final boolean closedRight = rightEndpoint.getBoundType() == BoundType.CLOSED;

        if (cmp == 0 && !(closedLeft && closedRight)) {
            return true;
        }

        final T firstValue = closedLeft ? leftValue : nextValue.evaluate(leftValue, step);

        final int cmpFirst = ObjectUtils.compare(firstValue, rightValue);

        if (cmpFirst == 0) {
            return !closedRight;
        }

        final boolean ascending = cmp < 0;
        return ascending ? cmpFirst > 0 : cmpFirst < 0;
    }

}
