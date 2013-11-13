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
 * A range of longs.
 *
 * @since 1.0
 * @version $Revision: 1385335 $ $Date: 2012-09-16 15:08:31 -0300 (Sun, 16 Sep 2012) $
 */
public final class LongRange extends NumericRange<Long> {
    // attributes
    //---------------------------------------------------------------

    /**
     * Calculate default step.
     */
    public static final BinaryFunction<Long, Long, Long> DEFAULT_STEP = new BinaryFunction<Long, Long, Long>() {

        public Long evaluate(Long left, Long right) {
            return left > right ? -1L : 1L;
        }
    };

    // constructors
    // ---------------------------------------------------------------
    /**
     * Create a new LongRange.
     *
     * @param from start
     * @param to end
     */
    public LongRange(Number from, Number to) {
        this(from.longValue(), to.longValue());
    }

    /**
     * Create a new LongRange.
     *
     * @param from start
     * @param to end
     * @param step increment
     */
    public LongRange(Number from, Number to, Number step) {
        this(from.longValue(), to.longValue(), step.longValue());
    }

    /**
     * Create a new LongRange.
     *
     * @param from start
     * @param to end
     */
    public LongRange(long from, long to) {
        this(from, to, DEFAULT_STEP.evaluate(from, to).longValue());
    }

    /**
     * Create a new LongRange.
     *
     * @param from start
     * @param to end
     * @param step increment
     */
    public LongRange(long from, long to, long step) {
        this(from, DEFAULT_LEFT_BOUND_TYPE, to, DEFAULT_RIGHT_BOUND_TYPE, step);
    }

    /**
     * Create a new LongRange.
     *
     * @param from start
     * @param to end
     * @throws NullPointerException if either {@link Endpoint} is {@code null}
     */
    public LongRange(Endpoint<Long> from, Endpoint<Long> to) {
        this(from, to, DEFAULT_STEP.evaluate(from.getValue(), to.getValue()));
    }

    /**
     * Create a new LongRange.
     *
     * @param from start
     * @param to end
     * @param step increment
     * @throws NullPointerException if either {@link Endpoint} is {@code null}
     */
    public LongRange(Endpoint<Long> from, Endpoint<Long> to, int step) {
        this(from, to, (long) step);
    }

    /**
     * Create a new LongRange.
     *
     * @param from start
     * @param leftBoundType type of left bound
     * @param to end
     * @param rightBoundType type of right bound
     * @throws NullPointerException if either {@link BoundType} is {@code null}
     */
    public LongRange(long from, BoundType leftBoundType, long to, BoundType rightBoundType) {
        this(from, leftBoundType, to, rightBoundType, DEFAULT_STEP.evaluate(from, to));
    }

    /**
     * Create a new LongRange.
     *
     * @param from start
     * @param to end
     * @param step increment
     * @throws NullPointerException if either {@link Endpoint} is {@code null}
     */
    public LongRange(Endpoint<Long> from, Endpoint<Long> to, long step) {
        super(from, to, Long.valueOf(step), new BinaryFunction<Long, Long, Long>() {

            public Long evaluate(Long left, Long right) {
                return Long.valueOf(left.longValue() + right.longValue());
            }
        });

        final long f = from.getValue();
        final long t = to.getValue();

        Validate.isTrue(f == t || Long.signum(step) == Long.signum(t - f),
            "Will never reach '%s' from '%s' using step %s", t, f, step);
    }

    /**
     * Create a new LongRange.
     *
     * @param from start
     * @param leftBoundType type of left bound
     * @param to end
     * @param rightBoundType type of right bound
     * @param step increment
     * @throws NullPointerException if either {@link BoundType} is {@code null}
     */
    public LongRange(long from, BoundType leftBoundType, long to,
                     BoundType rightBoundType, long step) {
        this(new Endpoint<Long>(from, leftBoundType), new Endpoint<Long>(to, rightBoundType), step);
    }

    // iterable
    // ---------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    protected Iterator<Long> createIterator() {
        return new Iterator<Long>() {
            private long currentValue;

            {
                currentValue = leftEndpoint.getValue();

                if (leftEndpoint.getBoundType() == BoundType.OPEN) {
                    this.currentValue += step;
                }
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }

            public Long next() {
                final long step = getStep();
                final long r = currentValue;
                currentValue += step;
                return Long.valueOf(r);
            }

            public boolean hasNext() {
                final int cmp = Long.valueOf(currentValue).compareTo(rightEndpoint.getValue());

                if (cmp == 0) {
                    return rightEndpoint.getBoundType() == BoundType.CLOSED;
                }
                if (step > 0L) {
                    return cmp < 0;
                }
                return cmp > 0;
            }
        };
    }

}
