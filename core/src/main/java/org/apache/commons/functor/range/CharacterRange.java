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
 * A generator for a range of characters.
 *
 * @since 1.0
 * @version $Revision$ $Date$
 */
public final class CharacterRange extends AbstractRange<Character, Integer> {

    /**
     * Calculate default step.
     */
    public static final BinaryFunction<Character, Character, Integer> DEFAULT_STEP =
        new BinaryFunction<Character, Character, Integer>() {

            public Integer evaluate(Character left, Character right) {
                return left > right ? -1 : 1;
            }
        };

    // constructors
    // ---------------------------------------------------------------
    /**
     * Create a new CharacterRange.
     *
     * @param from start
     * @param to end
     */
    public CharacterRange(char from, char to) {
        this(from, to, DEFAULT_STEP.evaluate(from, to).intValue());
    }

    /**
     * Create a new CharacterRange.
     *
     * @param from start
     * @param to end
     * @param step increment
     */
    public CharacterRange(char from, char to, int step) {
        this(from, BoundType.CLOSED, to, BoundType.CLOSED, step);
    }

    /**
     * Create a new CharacterRange.
     *
     * @param from start
     * @param to end
     * @throws NullPointerException if either {@link Endpoint} is {@code null}
     */
    public CharacterRange(Endpoint<Character> from, Endpoint<Character> to) {
        this(from, to, DEFAULT_STEP.evaluate(from.getValue(), to.getValue()));
    }

    /**
     * Create a new CharacterRange.
     *
     * @param from start
     * @param to end
     * @param step increment
     * @throws NullPointerException if either {@link Endpoint} is {@code null}
     */
    public CharacterRange(Endpoint<Character> from, Endpoint<Character> to, int step) {
        super(from, to, Integer.valueOf(step), new BinaryFunction<Character, Integer, Character>() {

            public Character evaluate(Character left, Integer right) {
                return Character.valueOf((char) (left.charValue() + right.intValue()));
            }
        });
        final char f = from.getValue();
        final char t = to.getValue();

        Validate.isTrue(f == t || Integer.signum(step) == Integer.signum(t - f),
            "Will never reach '%s' from '%s' using step %s", t, f, step);
    }

    /**
     * Create a new CharacterRange.
     *
     * @param from start
     * @param leftBoundType type of left bound
     * @param to end
     * @param rightBoundType type of right bound
     * @throws NullPointerException if either bound type is {@code null}
     */
    public CharacterRange(char from, BoundType leftBoundType, char to, BoundType rightBoundType) {
        this(from, leftBoundType, to, rightBoundType, DEFAULT_STEP.evaluate(from, to));
    }

    /**
     * Create a new CharacterRange.
     *
     * @param from start
     * @param leftBoundType type of left bound
     * @param to end
     * @param rightBoundType type of right bound
     * @param step increment
     * @throws NullPointerException if either bound type is {@code null}
     */
    public CharacterRange(char from, BoundType leftBoundType, char to, BoundType rightBoundType, int step) {
        this(new Endpoint<Character>(from, leftBoundType), new Endpoint<Character>(to, rightBoundType), step);
    }

    // range methods
    // ---------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    public boolean contains(Character obj) {
        if (obj == null) {
            return Boolean.FALSE;
        }
        char leftValue = this.getLeftEndpoint().getValue().charValue();
        char rightValue = this.getRightEndpoint().getValue().charValue();
        boolean includeLeft = this.getLeftEndpoint().getBoundType() == BoundType.CLOSED;
        boolean includeRight = this.getRightEndpoint().getBoundType() == BoundType.CLOSED;
        int step = this.getStep().intValue();
        int value = (int) obj.charValue();

        int firstValue = 0;
        int lastValue = 0;

        if (step < 0.0) {
            firstValue = includeLeft ? leftValue : leftValue + step;
            lastValue = includeRight ? rightValue : rightValue + 1;
            if (value > firstValue || value < lastValue) {
                return Boolean.FALSE;
            }
        } else {
            firstValue = includeLeft ? leftValue : leftValue + step;
            lastValue = includeRight ? rightValue : rightValue - 1;
            if (value < firstValue || value > lastValue) {
                return Boolean.FALSE;
            }
        }
        return ((double) (value - firstValue) / step + 1) % 1.0 == 0.0;
    }

    /**
     * {@inheritDoc}
     */
    protected Iterator<Character> createIterator() {
        return new Iterator<Character>() {
            private char currentValue;

            {
                currentValue = leftEndpoint.getValue();

                if (leftEndpoint.getBoundType() == BoundType.OPEN) {
                    this.currentValue += step;
                }
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }

            public Character next() {
                final int step = getStep();
                final char r = currentValue;
                currentValue += step;
                return Character.valueOf(r);
            }

            public boolean hasNext() {
                final int cmp = Character.valueOf(currentValue).compareTo(rightEndpoint.getValue());

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
