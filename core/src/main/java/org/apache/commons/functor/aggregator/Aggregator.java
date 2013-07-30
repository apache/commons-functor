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

import org.apache.commons.functor.NullaryFunction;

/**
 * Interface which offers a means of "aggregating" data. It offers functions
 * which allows for data to be added to a "series" and then aggregate them. The
 * term "aggregation" is possibly a bit loose here as this interface doesn't
 * enforce summing up or averaging or any other way of processing the data -- it
 * simply allows for the values to be processed in such a way that it generates
 * a result. The aggregation of data will be done in the <code>evaluate()</code>
 * function. This is the function at the core of the aggregator package as it
 * allows for a way to process the data series and get a result of the same type
 * out of this operation. Note that some aggregators will compute the data at
 * this point by iterating through the series of data previously stored (if any)
 * while others might prefer to compute this value on the fly, every time
 * {@link #add(Object)} is called. This interface doesn't make any assumption as
 * to when the aggregation result should be computed.
 *
 * @param <T>
 *            type of data to aggregate
 */
public interface Aggregator<T> extends NullaryFunction<T> {
    /**
     * Adds data to the series which will be aggregated. It doesn't enforce any
     * limitations on how much data can be stored (or in fact whether it should
     * be stored) nor does it make any assumptions on synchronization.
     *
     * @param data
     *            Data to be added to the series which this aggregator will
     *            process/aggregate.
     */
    void add(T data);

    /**
     * Resets any series of data previously stored and returns the aggregator in
     * the initial state.
     */
    void reset();
}
