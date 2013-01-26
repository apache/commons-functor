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

import org.apache.commons.functor.BinaryFunction;

/**
 * An implementation of an aggregator which doesn't store the data series but
 * instead it processes the data on the fly, as it arrives in
 * {@link #add(Object)} and stores the result after each addition. It processes
 * the data by using a {@link BinaryFunction} which takes the result of the
 * previous {@link #add(Object)} and the data passed in and returns a new result
 * which gets stored (for using again in the next call to {@link #add(Object)}.
 * The call to {@link #evaluate()} simply returns this stored value at any
 * point. This has a lower memory footprint compared to
 * {@link AbstractListBackedAggregator} however it only allows for simpler
 * processing on the data received.
 *
 * @param <T>
 *            Type of object stored.
 */
public abstract class AbstractNoStoreAggregator<T> extends AbstractTimedAggregator<T> {
    /**
     * Function used to aggregate the data on the fly in {@link #add(Object)}.
     *
     * @see #add(Object)
     * @see #AbstractNoStoreAggregator(BinaryFunction)
     */
    private BinaryFunction<T, T, T> aggregationFunction;

    /**
     * Stores the result of the last {@link #add(Object)} operation.
     *
     * @see #add(Object)
     */
    private T                       result;

    /**
     * Similar to {@link #AbstractNoStoreAggregator(BinaryFunction, long)
     * AbstractNoStoreAggregator(aggregationFunction,0L)}.
     *
     * @param aggregationFunction
     *            Aggregation function to use in {@link #add(Object)}. Throws
     *            <code>NullPointerException</code> if this is <code>null</code>
     * @see #add(Object)
     * @see #aggregationFunction
     */
    public AbstractNoStoreAggregator(BinaryFunction<T, T, T> aggregationFunction) {
        this(aggregationFunction, 0L);
    }

    /**
     * Similar to
     * {@link #AbstractNoStoreAggregator(BinaryFunction, long,boolean)
     * AbstractNoStoreAggregator(aggregationFunction,0L,false)}.
     *
     * @param aggregationFunction
     *            Aggregation function to use in {@link #add(Object)}. Throws
     *            <code>NullPointerException</code> if this is <code>null</code>
     * @param interval
     *            interval in miliseconds to reset this aggregator
     * @see #add(Object)
     * @see #aggregationFunction
     */
    public AbstractNoStoreAggregator(BinaryFunction<T, T, T> aggregationFunction, long interval) {
        this(aggregationFunction, interval, false);
    }

    /**
     * Constructs an aggregator which will use the given function, reset itself
     * at the given interval and will use a shared timer on own private timer.
     * Simply prepares an aggregator which will use the given aggregation
     * function each time {@link #add(Object)} is called. Also it initializes
     * {@link #result} with the value returned by {@link #initialValue()}, thus
     * allowing subclasses to have a custom way of specifying the start value.
     *
     * @param aggregationFunction
     *            Aggregation function to use in {@link #add(Object)}. Throws
     *            <code>NullPointerException</code> if this is <code>null</code>
     * @param interval
     *            interval in miliseconds to reset this aggregator
     * @param useSharedTimer
     *            if set to true, it will use a shared timer, as per
     *            {@link AbstractTimedAggregator#AbstractTimedAggregator(long, boolean)}
     *            ; otherwise if it's false it will use its own timer instance
     * @see AbstractTimedAggregator#AbstractTimedAggregator(long, boolean)
     */
    public AbstractNoStoreAggregator(BinaryFunction<T, T, T> aggregationFunction, long interval,
            boolean useSharedTimer) {
        super(interval, useSharedTimer);
        this.aggregationFunction = aggregationFunction;
        result = initialValue();
    }

    /**
     * Receives data to be aggregated/processed on the fly. This implementation
     * simply calls {@link #aggregationFunction} and stores the result.
     *
     * @param data
     *            Data to aggregate
     */
    @Override
    protected final void doAdd(T data) {
        result = aggregationFunction.evaluate(result, data);
    }

    /**
     * Returns the value already computed and stored in {@link #result}.
     *
     * @return Current (aggregated) value stored in {@link #result}
     * @see Aggregator#evaluate()
     */
    @Override
    protected final T doEvaluate() {
        return result;
    }

    /**
     * Resets the {@link #result} member to the {@link #initialValue()}.
     *
     * @see #initialValue()
     */
    @Override
    protected final void doReset() {
        result = initialValue();
    }

    /**
     * Allows subclasses to define the "initial" value. This value will be
     * stored in {@link #result} when an instance of this class is created or
     * when {@link #reset()} is called.
     *
     * @return Initial value to be used in {@link #result}.
     */
    protected abstract T initialValue();

    /**
     * Getter for {@link #aggregationFunction}.
     *
     * @return Current value of the member.
     */
    final BinaryFunction<T, T, T> getAggregationFunction() {
        return aggregationFunction;
    }

    /**
     * Getter for {@link #result}. Provided for test purposes only.
     *
     * @return Current value of the aggregated data.
     */
    final T getResult() {
        return result;
    }

    /**
     * Setter for {@link #result}. Provided for test purposes only.
     *
     * @param result
     *            New value to store in {@link #result}
     */
    final void setResult(T result) {
        this.result = result;
    }

    /**
     * This aggregator doesn't store any data, so the data series size is always
     * 0 (zero).
     *
     * @return 0
     */
    @Override
    protected int retrieveDataSize() {
        return 0;
    }

    @Override
    public String toString() {
        return AbstractNoStoreAggregator.class.getName();
    }
}
