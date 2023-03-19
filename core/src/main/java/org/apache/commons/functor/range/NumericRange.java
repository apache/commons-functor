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

import org.apache.commons.functor.BinaryFunction;

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
 */
public abstract class NumericRange<T extends Number & Comparable<T>> extends AbstractRange<T, T> {

    /**
     * Construct a new {@link NumericRange}.
     * @param leftEndpoint left endpoint
     * @param rightEndpoint right endpoint
     * @param step increment step
     * @param nextValue function to implement the taking of a step
     */
    protected NumericRange(Endpoint<T> leftEndpoint, Endpoint<T> rightEndpoint, T step,
            BinaryFunction<T, T, T> nextValue) {
        super(leftEndpoint, rightEndpoint, step, nextValue);
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

}
