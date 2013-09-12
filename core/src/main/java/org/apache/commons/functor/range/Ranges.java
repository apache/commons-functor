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

/**
 * Range factory.
 *
 * @since 1.0
 * @version $Revision$ $Date$
 */
public final class Ranges {

    /**
     * Hidden constructor as this only is a helper class with static methods.
     */
    private Ranges() {
    }

    // Integer ranges
    /**
     * Create a new IntegerRange.
     *
     * @param from start
     * @param to end
     * @return IntegerRange
     */
    public static IntegerRange integerRange(Number from, Number to) {
        return new IntegerRange(from, to);
    }

    /**
     * Create a new IntegerRange.
     *
     * @param from start
     * @param to end
     * @param step increment
     * @return IntegerRange
     */
    public static IntegerRange integerRange(Number from, Number to, Number step) {
        return new IntegerRange(from, to, step);
    }

    /**
     * Create a new IntegerRange.
     *
     * @param from start
     * @param to end
     * @return IntegerRange
     */
    public static IntegerRange integerRange(int from, int to) {
        return new IntegerRange(from, to);
    }

    /**
     * Create a new IntegerRange.
     *
     * @param from start
     * @param to end
     * @param step increment
     * @return IntegerRange
     */
    public static IntegerRange integerRange(int from, int to, int step) {
        return new IntegerRange(from, to, step);
    }

    /**
     * Create a new IntegerRange.
     *
     * @param from start
     * @param leftBoundType type of left bound
     * @param to end
     * @param rightBoundType type of right bound
     * @return IntegerRange
     */
    public static IntegerRange integerRange(int from, BoundType leftBoundType,
                                            int to, BoundType rightBoundType) {
        return new IntegerRange(from, leftBoundType, to, rightBoundType);
    }

    /**
     * Create a new IntegerRange.
     *
     * @param from start
     * @param leftBoundType type of left bound
     * @param to end
     * @param rightBoundType type of right bound
     * @param step increment
     * @return IntegerRange
     */
    public static IntegerRange integerRange(int from, BoundType leftBoundType,
                                            int to, BoundType rightBoundType,
                                            int step) {
        return new IntegerRange(from, leftBoundType, to, rightBoundType, step);
    }

    /**
     * Create a new IntegerRange.
     *
     * @param leftEndpoint start
     * @param rightEndpoint end
     * @param step increment
     * @return IntegerRange
     */
    public static IntegerRange integerRange(Endpoint<Integer> leftEndpoint,
                                                Endpoint<Integer> rightEndpoint,
                                                int step) {
        return new IntegerRange(leftEndpoint, rightEndpoint, step);
    }

    /**
     * Create a new LongRange.
     *
     * @param from start
     * @param to end
     * @return LongRange
     */
    public static LongRange longRange(Number from, Number to) {
        return new LongRange(from, to);
    }

    /**
     * Create a new IntegerRange.
     *
     * @param from start
     * @param to end
     * @param step increment
     * @return LongRange
     */
    public static LongRange longRange(Number from, Number to, Number step) {
        return new LongRange(from, to, step);
    }

    // Long ranges
    /**
     * Create a new LongRange.
     *
     * @param from start
     * @param to end
     * @return LongRange
     */
    public static LongRange longRange(long from, long to) {
        return new LongRange(from, to);
    }

    /**
     * Create a new LongRange.
     *
     * @param from start
     * @param to end
     * @param step increment
     * @return LongRange
     */
    public static LongRange longRange(long from, long to, long step) {
        return new LongRange(from, to, step);
    }

    /**
     * Create a new LongRange.
     *
     * @param from start
     * @param leftBoundType type of left bound
     * @param to end
     * @param rightBoundType type of right bound
     * @return LongRange
     */
    public static LongRange longRange(long from, BoundType leftBoundType,
                                      long to, BoundType rightBoundType) {
        return new LongRange(from, leftBoundType, to, rightBoundType);
    }

    /**
     * Create a new LongRange.
     *
     * @param from start
     * @param leftBoundType type of left bound
     * @param to end
     * @param rightBoundType type of right bound
     * @param step increment
     * @return LongRange
     */
    public static LongRange longRange(long from, BoundType leftBoundType,
                                      long to, BoundType rightBoundType,
                                      long step) {
        return new LongRange(from, leftBoundType, to, rightBoundType, step);
    }

    /**
     * Create a new LongRange.
     *
     * @param leftEndpoint start
     * @param rightEndpoint end
     * @param step increment
     * @return LongRange
     */
    public static LongRange longRange(Endpoint<Long> leftEndpoint,
                                                Endpoint<Long> rightEndpoint,
                                                long step) {
        return new LongRange(leftEndpoint, rightEndpoint, step);
    }

    // Float ranges
    /**
     * Create a new FloatRange.
     *
     * @param from start
     * @param to end
     * @return FloatRange
     */
    public static FloatRange floatRange(Number from, Number to) {
        return new FloatRange(from, to);
    }

    /**
     * Create a new FloatRange.
     *
     * @param from start
     * @param to end
     * @param step increment
     * @return FloatRange
     */
    public static FloatRange floatRange(Number from, Number to, Number step) {
        return new FloatRange(from, to, step);
    }

    /**
     * Create a new FloatRange.
     *
     * @param from start
     * @param to end
     * @return FloatRange
     */
    public static FloatRange floatRange(float from, float to) {
        return new FloatRange(from, to);
    }

    /**
     * Create a new FloatRange.
     *
     * @param from start
     * @param to end
     * @param step increment
     * @return FloatRange
     */
    public static FloatRange floatRange(float from, float to, float step) {
        return new FloatRange(from, to, step);
    }

    /**
     * Create a new FloatRange.
     *
     * @param from start
     * @param leftBoundType type of left bound
     * @param to end
     * @param rightBoundType type of right bound
     * @return FloatRange
     */
    public static FloatRange floatRange(float from, BoundType leftBoundType,
                                        float to, BoundType rightBoundType) {
        return new FloatRange(from, leftBoundType, to, rightBoundType);
    }

    /**
     * Create a new FloatRange.
     *
     * @param from start
     * @param leftBoundType type of left bound
     * @param to end
     * @param rightBoundType type of right bound
     * @param step increment
     * @return FloatRange
     */
    public static FloatRange floatRange(float from, BoundType leftBoundType,
                                        float to, BoundType rightBoundType,
                                        float step) {
        return new FloatRange(from, leftBoundType, to, rightBoundType, step);
    }

    /**
     * Create a new FloatRange.
     *
     * @param leftEndpoint start
     * @param rightEndpoint end
     * @param step increment
     * @return FloatRange
     */
    public static FloatRange floatRange(Endpoint<Float> leftEndpoint,
                                                Endpoint<Float> rightEndpoint,
                                                float step) {
        return new FloatRange(leftEndpoint, rightEndpoint, step);
    }

    // Double ranges
    /**
     * Create a new DoubleRange.
     *
     * @param from start
     * @param to end
     * @return DoubleRange
     */
    public static DoubleRange doubleRange(Number from, Number to) {
        return new DoubleRange(from, to);
    }

    /**
     * Create a new DoubleRange.
     *
     * @param from start
     * @param to end
     * @param step increment
     * @return DoubleRange
     */
    public static DoubleRange doubleRange(Number from, Number to, Number step) {
        return new DoubleRange(from, to, step);
    }

    /**
     * Create a new DoubleRange.
     *
     * @param from start
     * @param to end
     * @return DoubleRange
     */
    public static DoubleRange doubleRange(double from, double to) {
        return new DoubleRange(from, to);
    }

    /**
     * Create a new DoubleRange.
     *
     * @param from start
     * @param to end
     * @param step increment
     * @return DoubleRange
     */
    public static DoubleRange doubleRange(double from, double to, double step) {
        return new DoubleRange(from, to, step);
    }

    /**
     * Create a new DoubleRange.
     *
     * @param from start
     * @param leftBoundType type of left bound
     * @param to end
     * @param rightBoundType type of right bound
     * @param step increment
     * @return DoubleRange
     */
    public static DoubleRange doubleRange(double from, BoundType leftBoundType,
                                          double to, BoundType rightBoundType,
                                          double step) {
        return new DoubleRange(from, leftBoundType, to, rightBoundType, step);
    }

    /**
     * Create a new DoubleRange.
     *
     * @param from start
     * @param leftBoundType type of left bound
     * @param to end
     * @param rightBoundType type of right bound
     * @return DoubleRange
     */
    public static DoubleRange doubleRange(double from, BoundType leftBoundType,
                                          double to, BoundType rightBoundType) {
        return new DoubleRange(from, leftBoundType, to, rightBoundType);
    }

    /**
     * Create a new DoubleRange.
     *
     * @param leftEndpoint start
     * @param rightEndpoint end
     * @param step increment
     * @return DoubleRange
     */
    public static DoubleRange doubleRange(Endpoint<Double> leftEndpoint,
                                                Endpoint<Double> rightEndpoint,
                                                double step) {
        return new DoubleRange(leftEndpoint, rightEndpoint, step);
    }

    // Character ranges
    /**
     * Create a new CharacterRange.
     *
     * @param from start
     * @param to end
     * @return CharacterRange
     */
    public static CharacterRange characterRange(char from, char to) {
        return new CharacterRange(from, to);
    }

    /**
     * Create a new CharacterRange.
     *
     * @param from start
     * @param to end
     * @param step increment
     * @return CharacterRange
     */
    public static CharacterRange characterRange(char from, char to, int step) {
        return new CharacterRange(from, to, step);
    }

    /**
     * Create a new CharacterRange.
     *
     * @param from start
     * @param leftBoundType type of left bound
     * @param to end
     * @param rightBoundType type of right bound
     * @return CharacterRange
     */
    public static CharacterRange characterRange(char from,
                                                BoundType leftBoundType,
                                                char to,
                                                BoundType rightBoundType) {
        return new CharacterRange(from, leftBoundType, to, rightBoundType);
    }

    /**
     * Create a new CharacterRange.
     *
     * @param from start
     * @param leftBoundType type of left bound
     * @param to end
     * @param rightBoundType type of right bound
     * @param step increment
     * @return CharacterRange
     */
    public static CharacterRange characterRange(char from,
                                                BoundType leftBoundType,
                                                char to,
                                                BoundType rightBoundType,
                                                int step) {
        return new CharacterRange(from, leftBoundType, to, rightBoundType, step);
    }

    /**
     * Create a new CharacterRange.
     *
     * @param leftEndpoint start
     * @param rightEndpoint end
     * @param step increment
     * @return CharacterRange
     */
    public static CharacterRange characterRange(Endpoint<Character> leftEndpoint,
                                                Endpoint<Character> rightEndpoint,
                                                int step) {
        return new CharacterRange(leftEndpoint, rightEndpoint, step);
    }
}
