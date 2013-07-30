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
package org.apache.commons.functor.aggregator;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.functor.Function;

/**
 * Implementation of an aggregator which stores the data series in an
 * <code>ArrayList</code>.
 *
 * @param <T>
 *            Type of object stored in the data series.
 */
public class ArrayListBackedAggregator<T> extends AbstractListBackedAggregator<T> {
    /**
     * Similar to {@link #ArrayListBackedAggregator(Function, long)
     * ArrayListBackedAggregator(aggregationFunction, 0L)}.
     *
     * @param aggregationFunction
     *            Aggregation function to use in {@link #evaluate()}. Throws
     *            <code>NullPointerException</code> if this is <code>null</code>
     */
    public ArrayListBackedAggregator(Function<List<T>, T> aggregationFunction) {
        this(aggregationFunction, 0L);
    }

    /**
     * Similar to
     * {@link #ArrayListBackedAggregator(Function, long, boolean)
     * ArrayListBackedAggregator(aggregationFunction,interval,false)}.
     *
     * @param aggregationFunction
     *            Aggregation function to use in {@link #evaluate()}. Throws
     *            <code>NullPointerException</code> if this is <code>null</code>
     * @param interval
     *            interval in miliseconds to reset this aggregator
     */
    public ArrayListBackedAggregator(Function<List<T>, T> aggregationFunction, long interval) {
        this(aggregationFunction, interval, false);
    }

    /**
     * Initializes an aggregator with the given function, interval and decides
     * whether to use the shared timer or own timer.
     *
     * @param aggregationFunction
     *            Aggregation function to use in {@link #evaluate()}. Throws
     *            <code>NullPointerException</code> if this is <code>null</code>
     * @param interval
     *            interval in miliseconds to reset this aggregator
     * @param useSharedTimer
     *            if set to true, it shares a timer across instances as per
     *            {@link AbstractTimedAggregator#AbstractTimedAggregator(long,boolean)}
     *            , otherwise this instance will use its private timer
     */
    public ArrayListBackedAggregator(Function<List<T>, T> aggregationFunction, long interval,
            boolean useSharedTimer) {
        super(aggregationFunction, interval, useSharedTimer);
    }

    /**
     * Creates an instance of <code>ArrayList</code> and returns it.
     *
     * @return newly created <code>ArrayList</code> (with default initial
     *         capacity as per JDK).
     */
    @Override
    protected List<T> createList() {
        return new ArrayList<T>();
    }

    @Override
    public String toString() {
        return ArrayListBackedAggregator.class.getName();
    }
}
