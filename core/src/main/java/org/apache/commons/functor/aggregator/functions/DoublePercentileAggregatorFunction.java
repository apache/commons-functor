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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.functor.Function;

/**
 * Aggregator function to be used with subclasses of
 * {@link org.apache.commons.functor.aggregator.AbstractListBackedAggregator}
 * which retrieves the <a
 * href="http://en.wikipedia.org/wiki/Percentile">percentile</a> value for a
 * given percentile. The percentile rank/index for a value P can be obtained
 * using formula: <code>n = round((P / 100) * N + 0.5)</code> where N is the
 * number of items in a list.
 */
public class DoublePercentileAggregatorFunction implements Function<List<Double>, Double> {
    /** A percentile goes from 0 to 100% and that's it. */
    private static final double MAX_PERCENTAGE = 100.0;
    /**
     * Percentile value to calculate. 0 &lt; percentile &lt;= 100
     */
    private double              percentile;

    /**
     * Flag to indicate whether we are going to operate on a copy of the list
     * given or not. In order to compute the percentile, we need to sort the
     * list first (and then choose the item based on the formula given above).
     * This function offers 2 ways of doing the sorting:
     * <ul>
     * <li>by sorting (modifying) the original list (<code>useCopy=false</code>)
     * </li>
     * <li>by operating on a copy of the original list and leaving the original
     * untouched (<code>useCopy=true</code>)</li>
     * </ul>
     * NOTE: While using a copy ensures the original list is untouched, it does
     * mean we are creating a temporary list for the purpose of this computation
     * so it will have an impact on memory!
     */
    private boolean             useCopy;

    /**
     * Similar to {@link #DoublePercentileAggregatorFunction(double, boolean)
     * DoublePercentilAggregatorFunction(percentile,true)}.
     *
     * @param percentile
     *            Percentile this function will return the value for
     */
    public DoublePercentileAggregatorFunction(double percentile) {
        this(percentile, true);
    }

    /**
     * Initializes the function with the given percentile and decides whether
     * the function will modify the original list or not.
     *
     * @param percentile
     *            Percentile this function will return the value for
     * @param useCopy
     *            If set to true, the original list will not be modified and
     *            will contain the data in sorted order, if false, this instance
     *            will operate on a copy of the list
     */
    public DoublePercentileAggregatorFunction(double percentile, boolean useCopy) {
        if (percentile < 0.0 || percentile > MAX_PERCENTAGE) {
            throw new IllegalArgumentException("Invalid value for percentile: " + percentile);
        }
        this.percentile = percentile;
        this.useCopy = useCopy;
    }

    /**
     * Used internally to compute the rank of the item in the list for the
     * requested percentile. This is invoked internally from
     * {@link #evaluate(List)}.
     *
     * @param data
     *            List containing data. This cannot be <code>null</code> (throws
     *            <code>NullPointerException</code>) or empty (throws
     *            <code>ArrayIndexOutOfBoundsException</code>).
     * @return Index of the item for the requested percentile
     * @see #getPercentile()
     */
    final int computeRank(List<Double> data) {
        int maxRank = data.size() - 1;
        int rank = (int) Math.floor((percentile * maxRank) / MAX_PERCENTAGE);
        return rank;
    }

    /**
     * Traverses the list and computes the percentile. In doing so, it sorts the
     * list first -- and might or might not use the original list or a copy
     * depending on {@link #isUseCopy()}.
     *
     * @param data
     *            List to compute the percentile for
     * @return percentile of the given list or null if list is <code>null</code>
     *         or empty (zero size).
     */
    public Double evaluate(List<Double> data) {
        if (data == null || data.size() == 0) {
            return null;
        }
        List<Double> copy = data;
        if (useCopy) {
            copy = new ArrayList<Double>(data);
        }
        Collections.sort(copy);
        int rank = computeRank(data);
        return copy.get(rank);
    }

    /**
     * Getter for {@link #percentile}.
     *
     * @return Retrieves the percentile this instance will return
     */
    public double getPercentile() {
        return percentile;
    }

    /**
     * Does this instance modify the passed in list or not?
     *
     * @return If true, this instance will sort the list passed in and then use
     *         it to compute the percentile; if false, it will operate on a copy
     *         of the list
     * @see DoublePercentileAggregatorFunction#DoublePercentileAggregatorFunction(double,
     *      boolean)
     */
    public boolean isUseCopy() {
        return useCopy;
    }

    @Override
    public String toString() {
        return DoublePercentileAggregatorFunction.class.getName();
    }
}
