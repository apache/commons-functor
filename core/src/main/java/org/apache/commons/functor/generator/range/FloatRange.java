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

import org.apache.commons.functor.BinaryFunction;
import org.apache.commons.functor.UnaryProcedure;

/**
 * A generator for a range of float.
 *
 * @since 1.0
 * @version $Revision$ $Date$
 */
public class FloatRange extends NumericRange<Float> {

    /**
     * Calculate default step.
     */
    public static final BinaryFunction<Float, Float, Float> DEFAULT_STEP = new BinaryFunction<Float, Float, Float>() {

        public Float evaluate(Float left, Float right) {
            return left > right ? -1.0f : 1.0f;
        }
    };

    // constructors
    // ---------------------------------------------------------------
    /**
     * Create a new FloatRange.
     *
     * @param from start
     * @param to end
     */
    public FloatRange(Number from, Number to) {
        this(from.floatValue(), to.floatValue());
    }

    /**
     * Create a new FloatRange.
     *
     * @param from start
     * @param to end
     * @param step increment
     */
    public FloatRange(Number from, Number to, Number step) {
        this(from.floatValue(), to.floatValue(), step.floatValue());
    }

    /**
     * Create a new FloatRange.
     *
     * @param from start
     * @param to end
     */
    public FloatRange(float from, float to) {
        this(from, to, DEFAULT_STEP.evaluate(from, to).floatValue());
    }

    /**
     * Create a new FloatRange.
     *
     * @param from start
     * @param to end
     * @param step increment
     */
    public FloatRange(float from, float to, float step) {
        this(from, DEFAULT_LEFT_BOUND_TYPE, to, DEFAULT_RIGHT_BOUND_TYPE, step);
    }

    /**
     * Create a new FloatRange.
     *
     * @param from start
     * @param leftBoundType type of left bound
     * @param to end
     * @param rightBoundType type of right bound
     * @param step increment
     */
    public FloatRange(float from, BoundType leftBoundType, float to, BoundType rightBoundType, float step) {
        this(new Endpoint<Float>(Float.valueOf(from), leftBoundType), new Endpoint<Float>(Float.valueOf(to),
            rightBoundType), Float.valueOf(step));
    }

    /**
     * Create a new FloatRange.
     *
     * @param from start
     * @param to end
     * @param step increment
     */
    public FloatRange(Endpoint<Float> from, Endpoint<Float> to, Float step) {
        super(from, to, step);
    }

    // methods
    // ---------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    public void run(UnaryProcedure<? super Float> proc) {
        final float step = this.getStep();
        final boolean includeLeftValue = this.getLeftEndpoint()
            .getBoundType() == BoundType.CLOSED;
        final boolean includeRightValue = this.getRightEndpoint()
            .getBoundType() == BoundType.CLOSED;
        final float leftValue = this.getLeftEndpoint().getValue();
        final float rightValue = this.getRightEndpoint().getValue();
        if (step < 0) {
            final float from = includeLeftValue ? leftValue : leftValue + step;
            if (includeRightValue) {
                for (float i = from; i >= rightValue; i += step) {
                    proc.run(i);
                }
            } else {
                for (float i = from; i > rightValue; i += step) {
                    proc.run(i);
                }
            }
        } else {
            final float from = includeLeftValue ? leftValue : leftValue + step;
            if (includeRightValue) {
                for (float i = from; i <= rightValue; i += step) {
                    proc.run(i);
                }
            } else {
                for (float i = from; i < rightValue; i += step) {
                    proc.run(i);
                }
            }
        }
    }

}
