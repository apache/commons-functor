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

import java.util.List;

import org.apache.commons.functor.Function;

/**
 * Aggregator function to be used with subclasses of
 * {@link org.apache.commons.functor.aggregator.AbstractListBackedAggregator}
 * which sums up all the numbers in the list.
 */
public final class IntegerSumAggregatorFunction implements Function<List<Integer>, Integer> {
    /**
     * Does the actual adding and returns the result. Please note that caller is
     * responsible for synchronizing access to the list.
     *
     * @param data
     *            List to traverse and sum
     * @return arithmetic sum of all the data in the list or null if the list is
     *         empty.
     */
    public Integer evaluate(List<Integer> data) {
        if (data == null || data.size() == 0) {
            return null;
        }
        int sum = 0;
        for (Integer d : data) {
            sum += d;
        }
        return sum;
    }

    @Override
    public String toString() {
        return IntegerSumAggregatorFunction.class.getName();
    }
}
