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
 * A range of integers.
 *
 * @since 1.0
 * @version $Revision$ $Date$
 */
public class IntegerRange extends NumericRange<Integer> {

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
    public IntegerRange(int from, BoundType leftBoundType, int to, BoundType rightBoundType, int step) {
        this(new Endpoint<Integer>(Integer.valueOf(from), leftBoundType), new Endpoint<Integer>(Integer.valueOf(to),
            rightBoundType), Integer.valueOf(step));
    }

    /**
     * Create a new IntegerRange.
     *
     * @param from start
     * @param to end
     * @param step increment
     */
    public IntegerRange(Endpoint<Integer> from, Endpoint<Integer> to, Integer step) {
        super(from, to, step);
   }

    // methods
    // ---------------------------------------------------------------

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

}
