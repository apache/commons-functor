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
 * which finds the maximum number in a list. It does this by traversing the list
 * (once) -- so the complexity of this will be <i>O(n)</i>.
 */
public class IntegerMaxAggregatorFunction implements Function<List<Integer>, Integer> {
    /**
     * Does the actual traversal of the list and finds the maximum value then
     * returns the result. Please note that caller is responsible for
     * synchronizing access to the list.
     *
     * @param data
     *            List to traverse and find max
     * @return max number in the list or null if the list is empty.
     */
    public Integer evaluate(List<Integer> data) {
        if (data == null || data.size() == 0) {
            return null;
        }
        Integer max = null;
        for (Integer d : data) {
            if (max == null) {
                max = d;
            } else {
                if (max.intValue() < d.intValue()) {
                    max = d;
                }
            }
        }
        return max;
    }

    @Override
    public String toString() {
        return IntegerMaxAggregatorFunction.class.getName();
    }
}
