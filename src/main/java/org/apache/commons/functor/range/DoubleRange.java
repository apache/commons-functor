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
 * @version $Revision: $ $Date: $
 */
public class DoubleRange extends NumericRange<Double> implements Iterable<Double>, Iterator<Double> {

    // attributes
    // ---------------------------------------------------------------
    /**
     * Left limit.
     */
    private final Endpoint<Double> leftEndpoint;

    /**
     * Right limit.
     */
    private final Endpoint<Double> rightEndpoint;

    /**
     * Increment step.
     */
    private final double step;

    /**
     * Current value.
     */
    private double currentValue;

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
     */
    public DoubleRange(Endpoint<Double> from, Endpoint<Double> to) {
        this(from.getValue(), from.getBoundType(), to.getValue(), to.getBoundType(),
                DEFAULT_STEP.evaluate(from.getValue(), to.getValue()));
    }

    /**
     * Create a new DoubleRange.
     *
     * @param from start
     * @param leftBoundType type of left bound
     * @param to end
     * @param rightBoundType type of right bound
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
     */
    public DoubleRange(Endpoint<Double> from, Endpoint<Double> to, double step) {
        this(from.getValue(), from.getBoundType(), to.getValue(), to.getBoundType(), step);
    }

    /**
     * Create a new DoubleRange.
     *
     * @param from start
     * @param leftBoundType type of left bound
     * @param to end
     * @param rightBoundType type of right bound
     * @param step increment
     */
    public DoubleRange(double from, BoundType leftBoundType, double to,
                       BoundType rightBoundType, double step) {
        this.leftEndpoint = Validate
            .notNull(new Endpoint<Double>(from, leftBoundType),
                     "Left Endpoint argument must not be null");
        this.rightEndpoint = Validate
            .notNull(new Endpoint<Double>(to, rightBoundType),
                     "Right Endpoint argument must not be null");
        this.step = step;
        if (from != to && Math.signum(step) != Math.signum(to - from)) {
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
    public Endpoint<Double> getLeftEndpoint() {
        return this.leftEndpoint;
    }

    /**
     * {@inheritDoc}
     */
    public Endpoint<Double> getRightEndpoint() {
        return this.rightEndpoint;
    }

    /**
     * {@inheritDoc}
     */
    public Double getStep() {
        return this.step;
    }
 // iterable, iterator methods
    // ---------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    public boolean hasNext() {
        final double to = this.rightEndpoint.getValue();
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
    public Double next() {
        final double step = this.getStep();
        final double r = this.currentValue;
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
    public Iterator<Double> iterator() {
        return this;
    }

    // object methods
    // ---------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "DoubleRange<" + this.leftEndpoint.toLeftString() + ", "
                + this.rightEndpoint.toRightString() + ", " + this.step + ">";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof DoubleRange)) {
            return false;
        }
        DoubleRange that = (DoubleRange) obj;
        return this.leftEndpoint.equals(that.leftEndpoint)
                && this.rightEndpoint.equals(that.rightEndpoint)
                && this.step == that.step;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = "DoubleRange".hashCode();
        hash <<= 2;
        hash ^= this.leftEndpoint.getValue().hashCode();
        hash <<= 2;
        hash ^= this.rightEndpoint.getValue().hashCode();
        hash <<= 2;
        hash ^= Double.valueOf(this.step).hashCode();
        return hash;
    }

}
