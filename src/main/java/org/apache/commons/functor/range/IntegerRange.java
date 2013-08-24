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
     * Left limit.
     */
    private final Endpoint<Integer> leftEndpoint;

    /**
     * Right limit.
     */
    private final Endpoint<Integer> rightEndpoint;

    /**
     * Increment step.
     */
    private final int step;

    /**
     * Current value.
     */
    private int currentValue;

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
     * @param from start
     * @param to end
     */
    public IntegerRange(Number from, Number to) {
        this(from.intValue(), to.intValue());
    }

    /**
     * Create a new IntegerRange.
     *
     * @param from start
     * @param to end
     * @param step increment
     */
    public IntegerRange(Number from, Number to, Number step) {
        this(from.intValue(), to.intValue(), step.intValue());
    }

    /**
     * Create a new IntegerRange.
     *
     * @param from start
     * @param to end
     */
    public IntegerRange(int from, int to) {
        this(from, to, DEFAULT_STEP.evaluate(from, to).intValue());
    }

    /**
     * Create a new IntegerRange.
     *
     * @param from start
     * @param to end
     * @param step increment
     */
    public IntegerRange(int from, int to, int step) {
        this(from, DEFAULT_LEFT_BOUND_TYPE, to, DEFAULT_RIGHT_BOUND_TYPE, step);
    }

    /**
     * Create a new IntegerRange.
     *
     * @param from start
     * @param to end
     */
    public IntegerRange(Endpoint<Integer> from, Endpoint<Integer> to) {
        this(from.getValue(), from.getBoundType(), to.getValue(), to.getBoundType(),
                DEFAULT_STEP.evaluate(from.getValue(), to.getValue()));
    }

    /**
     * Create a new IntegerRange.
     *
     * @param from start
     * @param to end
     * @param step increment
     */
    public IntegerRange(Endpoint<Integer> from, Endpoint<Integer> to, int step) {
        this(from.getValue(), from.getBoundType(), to.getValue(), to.getBoundType(), step);
    }

    /**
     * Create a new IntegerRange.
     *
     * @param from start
     * @param leftBoundType type of left bound
     * @param to end
     * @param rightBoundType type of right bound
     */
    public IntegerRange(int from, BoundType leftBoundType, int to,
                        BoundType rightBoundType) {
        this(from, leftBoundType, to, rightBoundType, DEFAULT_STEP.evaluate(from, to));
    }

    /**
     * Create a new IntegerRange.
     *
     * @param from start
     * @param leftBoundType type of left bound
     * @param to end
     * @param rightBoundType type of right bound
     * @param step increment
     */
    public IntegerRange(int from, BoundType leftBoundType, int to,
                        BoundType rightBoundType, int step) {
        this.leftEndpoint = Validate
            .notNull(new Endpoint<Integer>(from, leftBoundType),
                     "Left Endpoint argument must not be null");
        this.rightEndpoint = Validate
            .notNull(new Endpoint<Integer>(to, rightBoundType),
                     "Right Endpoint argument must not be null");
        this.step = step;
        if (from != to && Integer.signum(step) != Integer.signum(to - from)) {
            throw new IllegalArgumentException("Will never reach " + to
                                               + " from " + from
                                               + " using step " + step);
        }
        if (this.leftEndpoint.getBoundType() == BoundType.CLOSED) {
            this.currentValue = this.leftEndpoint.getValue();
        } else {
            this.currentValue = this.leftEndpoint.getValue() + this.step;
        }
    }

    // range methods
    // ---------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    public Endpoint<Integer> getLeftEndpoint() {
        return this.leftEndpoint;
    }

    /**
     * {@inheritDoc}
     */
    public Endpoint<Integer> getRightEndpoint() {
        return this.rightEndpoint;
    }

    /**
     * {@inheritDoc}
     */
    public Integer getStep() {
        return this.step;
    }

    // iterable, iterator methods
    // ---------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    public boolean hasNext() {
        final int to = this.rightEndpoint.getValue();
        if (step < 0) {
            if (this.rightEndpoint.getBoundType() == BoundType.CLOSED) {
                return this.currentValue >= to;
            } else {
                return this.currentValue > to;
            }
        } else {
            if (this.rightEndpoint.getBoundType() == BoundType.CLOSED) {
                return this.currentValue <= to;
            } else {
                return this.currentValue < to;
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    public Integer next() {
        final int step = this.getStep();
        final int r = this.currentValue;
        this.currentValue += step;
        return r;
    }

    /**
     * {@inheritDoc}
     */
    public void remove() {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    public Iterator<Integer> iterator() {
        return this;
    }

    // object methods
    // ---------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "IntegerRange<" + this.leftEndpoint.toLeftString()
                + ", " + this.rightEndpoint.toRightString()
                + ", " + this.step + ">";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof IntegerRange)) {
            return false;
        }
        IntegerRange that = (IntegerRange) obj;
        return this.leftEndpoint.equals(that.leftEndpoint)
                && this.rightEndpoint.equals(that.rightEndpoint)
                && this.step == that.step;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = "IntegerRange".hashCode();
        hash <<= 2;
        hash ^= this.leftEndpoint.getValue();
        hash <<= 2;
        hash ^= this.rightEndpoint.getValue();
        hash <<= 2;
        hash ^= this.step;
        return hash;
    }

}
