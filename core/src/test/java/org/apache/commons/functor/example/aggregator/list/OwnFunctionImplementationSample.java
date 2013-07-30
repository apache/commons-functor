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
package org.apache.commons.functor.example.aggregator.list;

import java.util.List;

import org.apache.commons.functor.Function;
import org.apache.commons.functor.aggregator.ArrayListBackedAggregator;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Shows how to implement own aggregation function with a List-backed
 * aggregator. In this example we want to monitor whether a certain value
 * appears in the list or not.
 */
public class OwnFunctionImplementationSample {
    @Test
    public void findValue() throws Exception {
        // we're looking for 10
        ArrayListBackedAggregator<Integer> agg = new ArrayListBackedAggregator<Integer>(new OwnFunction(10));
        int eval = agg.evaluate();
        assertEquals(eval, -1);

        agg.add( 1 );
        eval = agg.evaluate();
        assertEquals(eval, -1);

        agg.add( 2 );
        eval = agg.evaluate();
        assertEquals(eval, -1);

        agg.add( 10 );
        eval = agg.evaluate();
        assertEquals(eval, 2);
        //function finds FIRST occurence!
        agg.add( 10 );
        eval = agg.evaluate();
        assertEquals(eval, 2);
        agg.add( 10 );
        eval = agg.evaluate();
        assertEquals(eval, 2);
        agg.add( 10 );
        eval = agg.evaluate();
        assertEquals(eval, 2);
    }

    /**
     * This function returns the index of the first occurrence in the list of
     * the given value.
     */
    static class OwnFunction implements Function<List<Integer>, Integer> {
        /** Value to find in the list. */
        private int valueToFind;

        public OwnFunction(int valueToFind) {
            this.valueToFind = valueToFind;
        }

        /**
         * Search in the list and find the first index of the given value.
         *
         * @param lst
         *            List to search in
         * @return index of {@link #valueToFind} if found or -1 if value not
         *         present in the list
         */
        public Integer evaluate(List<Integer> lst) {
            if (lst == null || lst.size() == 0) {
                return -1;
            }
            for (int i = 0; i < lst.size(); i++) {
                if (lst.get(i).intValue() == valueToFind) {
                    return i;
                }
            }
            return -1;
        }
    }
}
