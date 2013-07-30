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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.functor.Function;
import org.apache.commons.functor.aggregator.AbstractListBackedAggregator;
import org.apache.commons.functor.aggregator.functions.IntegerSumAggregatorFunction;
import org.junit.Test;

/**
 * Class which shows how to provide a custom List implementation with an
 * instance of {@link AbstractListBackedAggregator}. This particular instance
 * uses a LinkedList.
 */
public class OwnListImplementationSample {
    @Test
    public void sampleCreateOwnList() {
        CustomListAggregator<Integer> agg = new CustomListAggregator<Integer>(new IntegerSumAggregatorFunction());
        assertNull(agg.evaluate());
        agg.add(2);
        agg.add(3);
        int eval = agg.evaluate();
        assertEquals(eval, 5);
        assertEquals(agg.getDataSize(), 2); // 2 items added
    }

    /**
     * List-backed aggregator which uses a LinkedList.
     *
     * @param <T>
     *            type of parameter stored.
     */
    static class CustomListAggregator<T> extends AbstractListBackedAggregator<T> {
        public CustomListAggregator(Function<List<T>, T> aggregationFunction) {
            super(aggregationFunction);
        }

        public CustomListAggregator(Function<List<T>, T> aggregationFunction, long interval) {
            super(aggregationFunction, interval);
        }

        public CustomListAggregator(Function<List<T>, T> aggregationFunction, long interval,
                boolean useSharedTimer) {
            super(aggregationFunction, interval, useSharedTimer);
        }

        @Override
        protected List<T> createList() {
            return new LinkedList<T>();
        }
    }
}
