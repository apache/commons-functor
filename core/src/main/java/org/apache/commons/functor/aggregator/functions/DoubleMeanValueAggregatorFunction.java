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
 * which computes the arithmetic mean of all the numbers in the list.
 */
public final class DoubleMeanValueAggregatorFunction implements Function<List<Double>, Double> {
    /**
     * Does the actual computation and returns the result. Please note that
     * caller is responsible for synchronizing access to the list.
     *
     * @param data
     *            List to traverse and sum
     * @return arithmetic mean (average) of all the data in the list or null if
     *         the list is empty.
     */
    public Double evaluate(List<Double> data) {
        if (data == null || data.size() == 0) {
            return null;
        }
        double mean = 0.0;
        int n = data.size();
        for (Double d : data) {
            mean += d.doubleValue();
        }
        mean /= n;

        return mean;
    }

    @Override
    public String toString() {
        return DoubleMeanValueAggregatorFunction.class.getName();
    }
}
