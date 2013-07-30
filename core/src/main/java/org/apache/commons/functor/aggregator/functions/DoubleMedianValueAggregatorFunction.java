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
 * which computes the <a href="http://en.wikipedia.org/wiki/Median">median</a>
 * of all the numbers in the list.
 */
public final class DoubleMedianValueAggregatorFunction implements Function<List<Double>, Double> {
    /**
     * Flag to indicate whether we are going to operate on a copy of the list
     * given or not. In order to compute the median, we need to sort the list
     * first (and then choose the item in the middle). This function offers 2
     * ways of doing the sorting:
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
    private boolean useCopy;

    /**
     * By default create a function which will operate on a copy of the original
     * list ({@link #useCopy} = true).
     *
     * @see #useCopy
     */
    public DoubleMedianValueAggregatorFunction() {
        this(true);
    }

    /**
     * Constructor which allows the caller to specify whether to operate on the
     * original list or a copy of it.
     *
     * @param useCopy
     *            Set to true to operate on a copy of the list or false to
     *            operate on the original list.
     * @see #useCopy
     */
    public DoubleMedianValueAggregatorFunction(boolean useCopy) {
        this.useCopy = useCopy;
    }

    /**
     * Getter for {@link #useCopy}.
     *
     * @return Current value of {@link #useCopy}.
     * @see #useCopy
     */
    public boolean isUseCopy() {
        return useCopy;
    }

    /**
     * Sorts the given list and chooses the median value. The sorting can be
     * carried out against the original list or a copy of it, based on the value
     * of {@link #useCopy}.
     *
     * @param data
     *            List to compute the median value for
     * @return the median value of the given list or <code>null</code> if the
     *         list is <code>null</code> or empty.
     */
    public Double evaluate(List<Double> data) {
        if (data == null || data.size() == 0) {
            return null;
        }
        // if only one element in it, it is the mean
        if (data.size() == 1) {
            return data.get(0);
        }
        List<Double> copy = data;
        if (useCopy) {
            copy = new ArrayList<Double>(data);
        }
        Collections.sort(copy);
        int n = copy.size();
        int middle = n / 2;
        if (n % 2 == 0) {
            // need to compute the mean of middle and middle-1 (zero based
            // index!)
            return (copy.get(middle) + copy.get(middle - 1)) / 2;
        }

        // we're already positioned on the element in the middle so just return
        // it
        return copy.get(middle);
    }

    @Override
    public String toString() {
        return DoubleMedianValueAggregatorFunction.class.getName();
    }
}
