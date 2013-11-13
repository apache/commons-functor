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
 * A range of integers.
 *
 * @since 1.0
 * @version $Revision: 1385335 $ $Date: 2012-09-16 15:08:31 -0300 (Sun, 16 Sep 2012) $
 */
public class IntegerRange extends NumericRange<Integer> {

    // attributes
    // ---------------------------------------------------------------

    /**
     * Calculate default step.
     */
    public static final BinaryFunction<Integer, Integer, Integer> DEFAULT_STEP =
        new BinaryFunction<Integer, Integer, Integer>() {

            public Integer evaluate(Integer left, Integer right) {
                return left > right ? -1 : 1;
            }
        };

    // constructors
    // ---------------------------------------------------------------
    /**
     * Create a new IntegerRange.
     *
     * @param from
     *            start
     * @param to
     *            end
     */
    public IntegerRange(Number from, Number to) {
        this(from.intValue(), to.intValue());
    }

    /**
     * Create a new IntegerRange.
     *
     * @param from
     *            start
     * @param to
     *            end
     * @param step
     *            increment
     */
    public IntegerRange(Number from, Number to, Number step) {
        this(from.intValue(), to.intValue(), step.intValue());
    }

    /**
     * Create a new IntegerRange.
     *
     * @param from
     *            start
     * @param to
     *            end
     */
    public IntegerRange(int from, int to) {
        this(from, to, DEFAULT_STEP.evaluate(from, to).intValue());
    }

    /**
     * Create a new IntegerRange.
     *
     * @param from
     *            start
     * @param to
     *            end
     * @param step
     *            increment
     */
    public IntegerRange(int from, int to, int step) {
        this(from, DEFAULT_LEFT_BOUND_TYPE, to, DEFAULT_RIGHT_BOUND_TYPE, step);
    }

    /**
     * Create a new IntegerRange.
     *
     * @param from
     *            start
     * @param to
     *            end
     * @throws NullPointerException
     *             if either {@link Endpoint} is {@code null}
     */
    public IntegerRange(Endpoint<Integer> from, Endpoint<Integer> to) {
        this(from, to, DEFAULT_STEP.evaluate(from.getValue(), to.getValue()));
    }

    /**
     * Create a new IntegerRange.
     *
     * @param from
     *            start
     * @param leftBoundType
     *            type of left bound
     * @param to
     *            end
     * @param rightBoundType
     *            type of right bound
     * @throws NullPointerException
     *             if either {@link BoundType} is {@code null}
     */
    public IntegerRange(int from, BoundType leftBoundType, int to, BoundType rightBoundType) {
        this(from, leftBoundType, to, rightBoundType, DEFAULT_STEP.evaluate(from, to));
    }

    /**
     * Create a new IntegerRange.
     *
     * @param from
     *            start
     * @param to
     *            end
     * @param step
     *            increment
     * @throws NullPointerException
     *             if either {@link Endpoint} is {@code null}
     */
    public IntegerRange(Endpoint<Integer> from, Endpoint<Integer> to, int step) {
        super(from, to, Integer.valueOf(step), new BinaryFunction<Integer, Integer, Integer>() {

            public Integer evaluate(Integer left, Integer right) {
                return Integer.valueOf(left.intValue() + right.intValue());
            }
        });

        final int f = from.getValue();
        final int t = to.getValue();

        Validate.isTrue(f == t || Integer.signum(step) == Integer.signum(t - f),
            "Will never reach '%s' from '%s' using step %s", t, f, step);
    }

    /**
     * Create a new IntegerRange.
     *
     * @param from
     *            start
     * @param leftBoundType
     *            type of left bound
     * @param to
     *            end
     * @param rightBoundType
     *            type of right bound
     * @param step
     *            increment
     * @throws NullPointerException
     *             if either {@link BoundType} is {@code null}
     */
    public IntegerRange(int from, BoundType leftBoundType, int to, BoundType rightBoundType, int step) {
        this(new Endpoint<Integer>(from, leftBoundType), new Endpoint<Integer>(to, rightBoundType), step);
    }

    /**
     * {@inheritDoc}
     */
    protected Iterator<Integer> createIterator() {
        return new Iterator<Integer>() {
            private int currentValue;

            {
                currentValue = leftEndpoint.getValue();

                if (leftEndpoint.getBoundType() == BoundType.OPEN) {
                    this.currentValue += step;
                }
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }

            public Integer next() {
                final int step = getStep();
                final int r = currentValue;
                currentValue += step;
                return Integer.valueOf(r);
            }

            public boolean hasNext() {
                final int cmp = Integer.valueOf(currentValue).compareTo(rightEndpoint.getValue());

                if (cmp == 0) {
                    return rightEndpoint.getBoundType() == BoundType.CLOSED;
                }
                if (step > 0) {
                    return cmp < 0;
                }
                return cmp > 0;
            }
        };
    }

}
