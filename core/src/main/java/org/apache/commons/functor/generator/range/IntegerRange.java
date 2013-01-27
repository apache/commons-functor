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
import org.apache.commons.lang3.Validate;

/**
 * A range of integers.
 *
 * @since 1.0
 * @version $Revision$ $Date$
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
    }

    /**
     * Create a new IntegerRange.
     *
     * @param from start
     * @param to end
     * @param step increment
     */
    public IntegerRange(Endpoint<Integer> from, Endpoint<Integer> to, int step) {
        this.leftEndpoint = Validate
            .notNull(from, "Left Endpoint argument must not be null");
        this.rightEndpoint = Validate
            .notNull(to, "Right Endpoint argument must not be null");
        this.step = step;
        if (from != to
            && Integer.signum(step) != Integer.signum(to.getValue()
                                                   - from.getValue())) {
            throw new IllegalArgumentException("Will never reach " + to
                                               + " from " + from
                                               + " using step " + step);
        }
    }

    // methods
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

    /**
     * {@inheritDoc}
     */
    public void run(UnaryProcedure<? super Integer> proc) {
        final int step = this.getStep();
        final boolean includeLeftValue = this.getLeftEndpoint()
            .getBoundType() == BoundType.CLOSED;
        final boolean includeRightValue = this.getRightEndpoint()
            .getBoundType() == BoundType.CLOSED;
        final int leftValue = this.getLeftEndpoint().getValue();
        final int rightValue = this.getRightEndpoint().getValue();
        if (step < 0) {
            final int from = includeLeftValue ? leftValue : leftValue + step;
            if (includeRightValue) {
                for (int i = from; i >= rightValue; i += step) {
                    proc.run(i);
                }
            } else {
                for (int i = from; i > rightValue; i += step) {
                    proc.run(i);
                }
            }
        } else {
            final int from = includeLeftValue ? leftValue : leftValue + step;
            if (includeRightValue) {
                for (int i = from; i <= rightValue; i += step) {
                    proc.run(i);
                }
            } else {
                for (int i = from; i < rightValue; i += step) {
                    proc.run(i);
                }
            }
        }
    }

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
