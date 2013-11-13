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

import java.util.Iterator;

import org.apache.commons.functor.BinaryFunction;
import org.apache.commons.lang3.Validate;

/**
 * A generator for a range of float.
 *
 * @since 1.0
 * @version $Revision$ $Date$
 */
public class FloatRange extends NumericRange<Float> {

    // attributes
    // ---------------------------------------------------------------

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
     * @param to end
     * @throws NullPointerException if either {@link Endpoint} is {@code null}
     */
    public FloatRange(Endpoint<Float> from, Endpoint<Float> to) {
        this(from, to, DEFAULT_STEP.evaluate(from.getValue(), to.getValue()));
    }

    /**
     * Create a new FloatRange.
     *
     * @param from start
     * @param to end
     * @param step increment
     * @throws NullPointerException if either {@link Endpoint} is {@code null}
     */
    public FloatRange(Endpoint<Float> from, Endpoint<Float> to, float step) {
        super(from, to, Float.valueOf(step), new BinaryFunction<Float, Float, Float>() {

            public Float evaluate(Float left, Float right) {
                return Float.valueOf(left.floatValue() + right.floatValue());
            }
        });
        final float f = from.getValue();
        final float t = to.getValue();

        Validate.isTrue(f == t || Math.signum(step) == Math.signum(t - f),
            "Will never reach '%s' from '%s' using step %s", t, f, step);
    }

    /**
     * Create a new FloatRange.
     *
     * @param from start
     * @param leftBoundType type of left bound
     * @param to end
     * @param rightBoundType type of right bound
     * @throws NullPointerException if either bound type is {@code null}
     */
    public FloatRange(float from, BoundType leftBoundType, float to,
                        BoundType rightBoundType) {
        this(from, leftBoundType, to, rightBoundType, DEFAULT_STEP.evaluate(from, to));
    }

    /**
     * Create a new FloatRange.
     *
     * @param from start
     * @param leftBoundType type of left bound
     * @param to end
     * @param rightBoundType type of right bound
     * @param step increment
     * @throws NullPointerException if either bound type is {@code null}
     */
    public FloatRange(float from, BoundType leftBoundType, float to,
                      BoundType rightBoundType, float step) {
        this(new Endpoint<Float>(from, leftBoundType), new Endpoint<Float>(to, rightBoundType), step);
    }

    /**
     * {@inheritDoc}
     */
    protected Iterator<Float> createIterator() {
        return new Iterator<Float>() {
            private float currentValue;

            {
                currentValue = leftEndpoint.getValue();

                if (leftEndpoint.getBoundType() == BoundType.OPEN) {
                    this.currentValue += step;
                }
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }

            public Float next() {
                final float step = getStep();
                final float r = currentValue;
                currentValue += step;
                return Float.valueOf(r);
            }

            public boolean hasNext() {
                final int cmp = Float.compare(currentValue, rightEndpoint.getValue());

                if (cmp == 0) {
                    return rightEndpoint.getBoundType() == BoundType.CLOSED;
                }
                if (step > 0f) {
                    return cmp < 0;
                }
                return cmp > 0;
            }
        };
    }

}
