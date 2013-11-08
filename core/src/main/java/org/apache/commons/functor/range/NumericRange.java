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
 * A base class for numeric ranges. The elements within this range must be a
 * <b>Number</b> and <b>Comparable</b>.
 *
 * @param <T> the type of numbers and step that are both a number and comparable
 * @see org.apache.commons.functor.range.IntegerRange
 * @see org.apache.commons.functor.range.LongRange
 * @see org.apache.commons.functor.range.FloatRange
 * @see org.apache.commons.functor.range.DoubleRange
 * @see org.apache.commons.functor.range.CharacterRange
 * @since 0.1
 * @version $Revision$ $Date$
 */
public abstract class NumericRange<T extends Number & Comparable<?>> implements Range<T, T> {

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
        if (col == null || col.isEmpty()) {
            return false;
        }
        for (T t : col) {
            if (!contains(t)) {
                return false;
            }
        }
        return true;
    }

}
