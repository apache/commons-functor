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
import org.apache.commons.lang3.Validate;

/**
 * A range of longs.
 *
 * @since 1.0
 * @version $Revision$ $Date$
 */
public final class LongRange extends NumericRange<Long> {
    // attributes
    //---------------------------------------------------------------

    /**
     * Left limit.
     */
    private final Endpoint<Long> leftEndpoint;

    /**
     * Right limit.
     */
    private final Endpoint<Long> rightEndpoint;

    /**
     * Increment step.
     */
    private final long step;

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
    public LongRange(long from, BoundType leftBoundType, long to,
                     BoundType rightBoundType, long step) {
        this.leftEndpoint = Validate
            .notNull(new Endpoint<Long>(from, leftBoundType),
                     "Left Endpoint argument must not be null");
        this.rightEndpoint = Validate
            .notNull(new Endpoint<Long>(to, rightBoundType),
                     "Right Endpoint argument must not be null");
        this.step = step;
        if (from != to && Long.signum(step) != Long.signum(to - from)) {
            throw new IllegalArgumentException("Will never reach " + to
                                               + " from " + from
                                               + " using step " + step);
        }
    }

    /**
     * Create a new LongRange.
     *
     * @param from start
     * @param to end
     * @param step increment
     */
    public LongRange(Endpoint<Long> from, Endpoint<Long> to, long step) {
        this.leftEndpoint = Validate
            .notNull(from, "Left Endpoint argument must not be null");
        this.rightEndpoint = Validate
            .notNull(to, "Right Endpoint argument must not be null");
        this.step = step;
        if (from.equals(to) == Boolean.FALSE
            && Long.signum(step) != Long.signum(to.getValue() - from.getValue())) {
            throw new IllegalArgumentException("Will never reach " + to
                                               + " from " + from
                                               + " using step " + step);
        }
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

    /**
     * {@inheritDoc}
     */
    public Endpoint<Long> getLeftEndpoint() {
        return this.leftEndpoint;
    }

    /**
     * {@inheritDoc}
     */
    public Endpoint<Long> getRightEndpoint() {
        return this.rightEndpoint;
    }

    /**
     * {@inheritDoc}
     */
    public Long getStep() {
        return this.step;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "LongRange<" + this.leftEndpoint.toLeftString() + ", "
                + this.rightEndpoint.toRightString() + ", " + step + ">";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof LongRange)) {
            return false;
        }
        LongRange that = (LongRange) obj;
        return this.leftEndpoint.equals(that.leftEndpoint)
                && this.rightEndpoint.equals(that.rightEndpoint)
                && this.step == that.step;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = "LongRange".hashCode();
        hash <<= 2;
        hash ^= this.leftEndpoint.getValue();
        hash <<= 2;
        hash ^= this.rightEndpoint.getValue();
        hash <<= 2;
        hash ^= this.step;
        return hash;
    }

}
