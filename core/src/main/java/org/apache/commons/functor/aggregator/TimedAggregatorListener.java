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

/**
 * Listener to be used with instances of {@link AbstractTimedAggregator} to
 * receive notifications when the timer kicks in.
 *
 * @param <T>
 *            Type of object the <code>Aggregator</code> is operating on.
 */
public interface TimedAggregatorListener<T> {
    /**
     * Received when the aggregator listening to has triggered the timer.
     *
     * @param aggregator
     *            Aggregator which has triggered the time event in the first
     *            place.
     * @param evaluation
     *            Current {@link AbstractTimedAggregator#evaluate() evaluation
     *            result} of the aggregator which triggered the event
     */
    void onTimer(AbstractTimedAggregator<T> aggregator, T evaluation);
}
