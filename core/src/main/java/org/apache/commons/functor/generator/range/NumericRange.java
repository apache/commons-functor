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

package org.apache.commons.functor.generator.range;

import java.util.Collection;

import org.apache.commons.functor.generator.loop.LoopGenerator;
import org.apache.commons.lang3.Validate;

/**
 * A base class for numeric ranges. The elements within this range must be a
 * <b>Number</b> and <b>Comparable</b>.
 *
 * @param <T> the type of numbers and step that are both a number and comparable
 * @see org.apache.commons.functor.generator.range.IntegerRange
 * @see org.apache.commons.functor.generator.range.LongRange
 * @see org.apache.commons.functor.generator.range.FloatRange
 * @see org.apache.commons.functor.generator.range.DoubleRange
 * @see org.apache.commons.functor.generator.range.CharacterRange
 * @since 0.1
 * @version $Revision$ $Date$
 */
public abstract class NumericRange<T extends Number & Comparable<T>> extends LoopGenerator<T> implements Range<T, T> {
    // attributes
    // ---------------------------------------------------------------
    /**
     * Left limit.
     */
    protected final Endpoint<T> leftEndpoint;

    /**
     * Right limit.
     */
    protected final Endpoint<T> rightEndpoint;
    
    protected final T step;

    /**
     * Create a new NumericRange instance.
     * @param from left endpoint
     * @param to right endpoint
     * @param step
     */
    protected NumericRange(Endpoint<T> from, Endpoint<T> to, T step) {
        this.leftEndpoint = Validate.notNull(from, "Left Endpoint argument must not be null");
        this.rightEndpoint = Validate.notNull(to, "Right Endpoint argument must not be null");
        this.step = Validate.notNull(step, "step argument must not be null");
        final int cmp = to.getValue().compareTo(from.getValue());
        if (cmp != 0 && Double.valueOf(Math.signum(step.doubleValue())).intValue() != cmp) {
            throw new IllegalArgumentException(String.format("Will never reach %s from %s using step %s", to, from,
                step));
        }
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
    public T getStep() {
        return step;
    }

    /**
     * {@inheritDoc}
     */
    public boolean isEmpty() {
        double leftValue = this.getLeftEndpoint().getValue().doubleValue();
        double rightValue = this.getRightEndpoint().getValue().doubleValue();
        boolean closedLeft = this.getLeftEndpoint().getBoundType() == BoundType.CLOSED;
        boolean closedRight = this.getRightEndpoint().getBoundType() == BoundType.CLOSED;
        if (!closedLeft && !closedRight
             && this.getLeftEndpoint().equals(this.getRightEndpoint())) {
            return true;
        }
        double step = this.getStep().doubleValue();
        if (step > 0.0) {
            double firstValue = closedLeft ? leftValue : leftValue + step;
            return closedRight ? firstValue > rightValue
                              : firstValue >= rightValue;
        } else {
            double firstValue = closedLeft ? leftValue : leftValue + step;
            return closedRight ? firstValue < rightValue
                              : firstValue <= rightValue;
        }
    }

    /**
     * {@inheritDoc}
     */
    public boolean contains(T obj) {
        if (obj == null) {
            return false;
        }
        double leftValue = this.getLeftEndpoint().getValue().doubleValue();
        double rightValue = this.getRightEndpoint().getValue().doubleValue();
        boolean includeLeft = this.getLeftEndpoint().getBoundType() == BoundType.CLOSED;
        boolean includeRight = this.getRightEndpoint().getBoundType() == BoundType.CLOSED;
        double step = this.getStep().doubleValue();
        double value = obj.doubleValue();

        double firstValue = 0;
        double lastValue = 0;

        if (step < 0.0) {
            firstValue = includeLeft ? leftValue : leftValue + step;
            lastValue = includeRight ? rightValue : Math.nextUp(rightValue);
            if (value > firstValue || value < lastValue) {
                return false;
            }
        } else {
            firstValue = includeLeft ? leftValue : leftValue + step;
            lastValue = includeRight ? rightValue : rightValue
                                                    - (rightValue - Math
                                                        .nextUp(rightValue));
            if (value < firstValue || value > lastValue) {
                return false;
            }
        }
        return ((value - firstValue) / step + 1) % 1.0 == 0.0;
    }

    /**
     * {@inheritDoc}
     */
    public boolean containsAll(Collection<T> col) {
        if (col == null || col.size() == 0) {
            return false;
        }
        for (T t : col) {
            if (!this.contains(t)) {
                return false;
            }
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format("%s<%s, %s, %s>", getClass().getSimpleName(), this.leftEndpoint.toLeftString(),
            rightEndpoint.toRightString(), this.step);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof NumericRange<?>)) {
            return false;
        }
        NumericRange<?> that = (NumericRange<?>) obj;
        return this.leftEndpoint.equals(that.leftEndpoint) && this.rightEndpoint.equals(that.rightEndpoint)
            && this.step.equals(that.step);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = getClass().getSimpleName().hashCode();
        hash <<= 2;
        hash ^= this.leftEndpoint.getValue().hashCode();
        hash <<= 2;
        hash ^= this.rightEndpoint.getValue().hashCode();
        hash <<= 2;
        hash ^= this.step.hashCode();
        return hash;
    }
}
