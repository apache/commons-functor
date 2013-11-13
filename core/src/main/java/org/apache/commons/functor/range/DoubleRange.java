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
 * A generator for a range of doubles.
 *
 * @since 1.0
 * @version $Revision$ $Date$
 */
public class DoubleRange extends NumericRange<Double> {

    /**
     * Calculate default step.
     */
    public static final BinaryFunction<Double, Double, Double> DEFAULT_STEP =
            new BinaryFunction<Double, Double, Double>() {

        public Double evaluate(Double left, Double right) {
            return left > right ? -1.0d : 1.0d;
        }
    };

    // constructors
    // ---------------------------------------------------------------
    /**
     * Create a new DoubleRange.
     *
     * @param from start
     * @param to end
     */
    public DoubleRange(Number from, Number to) {
        this(from.doubleValue(), to.doubleValue());
    }

    /**
     * Create a new DoubleRange.
     *
     * @param from start
     * @param to end
     * @param step increment
     */
    public DoubleRange(Number from, Number to, Number step) {
        this(from.doubleValue(), to.doubleValue(), step.doubleValue());
    }

    /**
     * Create a new DoubleRange.
     *
     * @param from start
     * @param to end
     */
    public DoubleRange(double from, double to) {
        this(from, to, DEFAULT_STEP.evaluate(from, to).intValue());
    }

    /**
     * Create a new DoubleRange.
     *
     * @param from start
     * @param to end
     * @param step increment
     */
    public DoubleRange(double from, double to, double step) {
        this(from, DEFAULT_LEFT_BOUND_TYPE, to, DEFAULT_RIGHT_BOUND_TYPE, step);
    }

    /**
     * Create a new DoubleRange.
     *
     * @param from start
     * @param to end
     * @throws NullPointerException if either {@link Endpoint} is {@code null}
     */
    public DoubleRange(Endpoint<Double> from, Endpoint<Double> to) {
        this(from, to, DEFAULT_STEP.evaluate(from.getValue(), to.getValue()));
    }

    /**
     * Create a new DoubleRange.
     *
     * @param from start
     * @param leftBoundType type of left bound
     * @param to end
     * @param rightBoundType type of right bound
     * @throws NullPointerException if either bound type is {@code null}
     */
    public DoubleRange(double from, BoundType leftBoundType, double to,
                        BoundType rightBoundType) {
        this(from, leftBoundType, to, rightBoundType, DEFAULT_STEP.evaluate(from, to));
    }

    /**
     * Create a new DoubleRange.
     *
     * @param from start
     * @param to end
     * @param step increment
     * @throws NullPointerException if either {@link Endpoint} is {@code null}
     */
    public DoubleRange(Endpoint<Double> from, Endpoint<Double> to, double step) {
        super(from, to, Double.valueOf(step), new BinaryFunction<Double, Double, Double>() {

            public Double evaluate(Double left, Double right) {
                return Double.valueOf(left.doubleValue() + right.doubleValue());
            }
        });

        final double f = from.getValue();
        final double t = to.getValue();

        Validate.isTrue(f == t || Math.signum(step) == Math.signum(t - f),
            "Will never reach '%s' from '%s' using step %s", t, f, step);
    }

    /**
     * Create a new DoubleRange.
     *
     * @param from start
     * @param leftBoundType type of left bound
     * @param to end
     * @param rightBoundType type of right bound
     * @param step increment
     * @throws NullPointerException if either bound type is {@code null}
     */
    public DoubleRange(double from, BoundType leftBoundType, double to,
                       BoundType rightBoundType, double step) {
        this(new Endpoint<Double>(from, leftBoundType), new Endpoint<Double>(to, rightBoundType), step);
    }

    /**
     * {@inheritDoc}
     */
    protected Iterator<Double> createIterator() {
        return new Iterator<Double>() {
            private double currentValue;

            {
                currentValue = leftEndpoint.getValue();

                if (leftEndpoint.getBoundType() == BoundType.OPEN) {
                    this.currentValue += step;
                }
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }

            public Double next() {
                final double step = getStep();
                final double r = currentValue;
                currentValue += step;
                return Double.valueOf(r);
            }

            public boolean hasNext() {
                final int cmp = Double.compare(currentValue, rightEndpoint.getValue());

                if (cmp == 0) {
                    return rightEndpoint.getBoundType() == BoundType.CLOSED;
                }
                if (step > 0d) {
                    return cmp < 0;
                }
                return cmp > 0;
            }
        };
    }

}
