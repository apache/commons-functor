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

import java.util.List;

import org.apache.commons.functor.Function;
import org.apache.commons.lang3.Validate;

/**
 * An aggregator which stores the data series in a List. Every call to
 * {@link #add(Object)} will add an item in the array and there is no limit to
 * how much data we can store. It is down to subclasses to decide which types of
 * List implementation they need to used -- and the abstract factory
 * {@link #createList()} is provided for this.
 * <p>This implementation also allows for various "aggregations" of the list to be
 * used by providing a {@link Function Function<List<T>, T>} in the
 * constructor.</p>
 * <p>
 * <b>Thread safety</b> : Note that due to the fact that
 * {@link AbstractTimedAggregator} provides a threadsafe environment for access
 * to data, the <code>List</code> implementation can be unsynchronized.
 * </p>
 *
 * @param <T>
 *            Type of object stored.
 * @see AbstractTimedAggregator
 */
public abstract class AbstractListBackedAggregator<T> extends AbstractTimedAggregator<T> {
    /**
     * Stores the data series we ought to aggregate/evaluate. This list can only
     * be modified via {@link #reset()} and {@link #add(Object)} and will be
     * traversed during {@link #evaluate()}.
     */
    private List<T>                   series;

    /**
     * Used to actually aggregate the data when {@link #evaluate()} is called.
     * This is set in {@link #AbstractListBackedAggregator() the constructor}.
     */
    private Function<List<T>, T> aggregationFunction;

    /**
     * Default constructor. Similar to
     * {@link #AbstractListBackedAggregator(Function, long)
     * AbstractListBackedAggregator(aggregationFunction,0L}.
     *
     * @param aggregationFunction
     *            Aggregation function to use in {@link #evaluate()}. Throws
     *            <code>NullPointerException</code> if this is <code>null</code>
     */
    public AbstractListBackedAggregator(Function<List<T>, T> aggregationFunction) {
        this(aggregationFunction, 0L);
    }

    /**
     * Similar to
     * {@link #AbstractListBackedAggregator(Function, long, boolean)
     * AbstractListBackedAggregator(aggregationFunction,interval,false}.
     *
     * @param aggregationFunction
     *            Aggregation function to use in {@link #evaluate()}. Throws
     *            <code>NullPointerException</code> if this is <code>null</code>
     * @param interval
     *            interval in miliseconds to reset this aggregator
     */
    public AbstractListBackedAggregator(Function<List<T>, T> aggregationFunction, long interval) {
        this(aggregationFunction, interval, false);
    }

    /**
     * Constructs an aggregator which will use the given function, reset itself
     * at the given interval and will use a shared timer on own private timer.
     *
     * @param aggregationFunction
     *            Aggregation function to use in {@link #evaluate()}. Throws
     *            <code>NullPointerException</code> if this is <code>null</code>
     * @param interval
     *            interval in miliseconds to reset this aggregator
     * @param useSharedTimer
     *            if set to true, it will use a shared timer, as per
     *            {@link AbstractTimedAggregator#AbstractTimedAggregator(long, boolean)}
     *            ; otherwise if it's false it will use its own timer instance
     * @see AbstractTimedAggregator#AbstractTimedAggregator(long, boolean)
     */
    public AbstractListBackedAggregator(Function<List<T>, T> aggregationFunction, long interval,
            boolean useSharedTimer) {
        super(interval, useSharedTimer);
        this.aggregationFunction = Validate.notNull(aggregationFunction, "Function argument must not be null");
        this.series = createList();
    }

    /**
     * Adds data to the series which will be aggregated. This implementation
     * simply adds the data to the {@link #series} list.
     *
     * @param data
     *            Data to be added to the data series.
     */
    @Override
    public final void doAdd(T data) {
        series.add(data);
    }

    /**
     * The actual "beef" of this class: iterate through the list and aggregates
     * all the data and evaluates the result. This is done by calling
     * <code>aggregationFunction.evaluate(series)</code>.
     *
     * @return the result of <code>aggregationFunction.evaluate(series)</code>
     * @see Aggregator#evaluate()
     */
    @Override
    protected final T doEvaluate() {
        return aggregationFunction.evaluate(series);
    }

    /**
     * Resets the data series to the empty state.
     */
    @Override
    protected final void doReset() {
        series.clear();
    }

    /**
     * Allows subclasses to create the list which will store the {@link #series
     * data series}.
     *
     * @return an instance of <code>List</code> which will be used to store the
     *         data.
     */
    protected abstract List<T> createList();

    /**
     * Getter for {@link #series}.
     *
     * @return Value of {@link #series}
     */
    protected final List<T> getSeries() {
        return series;
    }

    /**
     * Simply returns the size of the data series which is the size of the list
     * used internally.
     *
     * @return Size of {@link #series} -- equivalent to
     *         <code>series.size()</code>
     */
    @Override
    protected final int retrieveDataSize() {
        return series.size();
    }

    /**
     * Getter for {@link #aggregationFunction}. Provided for testing purposes
     * only.
     *
     * @return Current value of {@link #aggregationFunction}
     */
    final Function<List<T>, T> getAggregationFunction() {
        return aggregationFunction;
    }

    @Override
    public String toString() {
        return AbstractListBackedAggregator.class.getName();
    }
}
