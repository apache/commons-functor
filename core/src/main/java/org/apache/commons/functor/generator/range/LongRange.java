/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
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
 * A range of longs.
 *
 * @since 1.0
 * @version $Revision$ $Date$
 */
public final class LongRange extends NumericRange<Long> {
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
     * @param leftBoundType type of left bound
     * @param to end
     * @param rightBoundType type of right bound
     * @param step increment
     */
    public LongRange(long from, BoundType leftBoundType, long to, BoundType rightBoundType, long step) {
        this(new Endpoint<Long>(Long.valueOf(from), leftBoundType),
            new Endpoint<Long>(Long.valueOf(to), rightBoundType), Long.valueOf(step));
    }

    /**
     * Create a new LongRange.
     *
     * @param from start
     * @param to end
     * @param step increment
     */
    public LongRange(Endpoint<Long> from, Endpoint<Long> to, Long step) {
        super(from, to, step);
    }

    // methods
    //---------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    public void run(UnaryProcedure<? super Long> proc) {
        final long step = this.getStep();
        final boolean includeLeftValue = this.getLeftEndpoint()
            .getBoundType() == BoundType.CLOSED;
        final boolean includeRightValue = this.getRightEndpoint()
            .getBoundType() == BoundType.CLOSED;
        final long leftValue = this.getLeftEndpoint().getValue();
        final long rightValue = this.getRightEndpoint().getValue();
        if (step < 0) {
            final long from = includeLeftValue ? leftValue : leftValue + step;
            if (includeRightValue) {
                for (long i = from; i >= rightValue; i += step) {
                    proc.run(i);
                }
            } else {
                for (long i = from; i > rightValue; i += step) {
                    proc.run(i);
                }
            }
        } else {
            final long from = includeLeftValue ? leftValue : leftValue + step;
            if (includeRightValue) {
                for (long i = from; i <= rightValue; i += step) {
                    proc.run(i);
                }
            } else {
                for (long i = from; i < rightValue; i += step) {
                    proc.run(i);
                }
            }
        }
    }

}
