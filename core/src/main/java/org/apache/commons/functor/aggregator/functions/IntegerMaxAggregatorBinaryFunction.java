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
package org.apache.commons.functor.aggregator.functions;

import org.apache.commons.functor.BinaryFunction;

/**
 * Aggregation function to be used with subclasses of
 * {@link org.apache.commons.functor.aggregator.AbstractNoStoreAggregator} which
 * finds the maximum of 2 ints.
 */
public class IntegerMaxAggregatorBinaryFunction implements BinaryFunction<Integer, Integer, Integer> {
    /**
     * Computes the maximum of the 2 given numbers and returns the result.
     *
     * @param left
     *            first number to compare. If <code>null</code>, then
     *            <code>right</code> will be returned.
     * @param right
     *            second number to add. If <code>null</code>, then
     *            <code>left</code> will be returned.
     * @return max of the 2 integers as described above
     */
    public Integer evaluate(Integer left, Integer right) {
        if (left == null) {
            return right;
        }
        if (right == null) {
            return left;
        }
        if (left.intValue() < right.intValue()) {
            return right;
        }
        return left;
    }

    @Override
    public String toString() {
        return IntegerMaxAggregatorBinaryFunction.class.getName();
    }
}
