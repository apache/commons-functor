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

package org.apache.commons.functor.example.aggregator.nostore;

import static org.junit.Assert.assertEquals;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.commons.functor.aggregator.AbstractNoStoreAggregator;
import org.apache.commons.functor.aggregator.AbstractTimedAggregator;
import org.apache.commons.functor.aggregator.TimedAggregatorListener;
import org.apache.commons.functor.aggregator.functions.IntegerCountAggregatorBinaryFunction;
import org.apache.commons.functor.aggregator.functions.IntegerMaxAggregatorBinaryFunction;
import org.apache.commons.functor.aggregator.functions.IntegerSumAggregatorBinaryFunction;
import org.junit.Test;

/**
 * Examples of using a {@link AbstractNoStoreAggregator}.
 */
public class NoStoreAggregatorSample {
    /**
     * Shows how to use a simple <i>nostore</i> aggregator to just count number
     * of times data is added.
     */
    @Test
    public void countItems() {
        // no timer used
        AbstractNoStoreAggregator<Integer> counter = new AbstractNoStoreAggregator<Integer>(
                new IntegerCountAggregatorBinaryFunction()) {
            @Override
            protected Integer initialValue() {
                return 0;
            }
        };
        counter.add(100);
        int eval = counter.evaluate();
        // result of evaluate() is 1 as we only added 1 item
        assertEquals(eval, 1);
        counter.add(1);
        counter.add(2);
        eval = counter.evaluate();
        // result is now 3 as we added 2 more items
        assertEquals(eval, 3);

        // since there is no timer, we need to reset the data manually
        counter.reset();
        eval = counter.evaluate();
        assertEquals(eval, 0);
    }

    /**
     * Shows how to sum up all the items passed in and "flush" them regularly on
     * a timer. When the flushing occurs, we print the evaluation to console.
     */
    @Test
    public void sumItems() throws Exception {
        // prepare the listener
        TimedAggregatorListener<Integer> listener = new TimedAggregatorListener<Integer>() {
            public void onTimer(AbstractTimedAggregator<Integer> aggregator, Integer evaluation) {
                System.out.println("Current sum is :" + evaluation);
            }
        };
        // flush data regularly every 1/2 second
        AbstractNoStoreAggregator<Integer> sum = new AbstractNoStoreAggregator<Integer>(
                new IntegerSumAggregatorBinaryFunction(), 500) {
            @Override
            protected Integer initialValue() {
                return 0;
            }
        };
        sum.addTimerListener(listener);

        long startTime = System.currentTimeMillis();
        long stopAfter = 5000; // 5 seconds
        int maxInt = 100;
        Random rnd = new Random();
        while (System.currentTimeMillis() - startTime <= stopAfter) {
            sum.add(rnd.nextInt(maxInt));
            TimeUnit.MILLISECONDS.sleep(rnd.nextInt(maxInt));
        }
        sum.stop();
    }

    /**
     * Calculate the maximum value of a series of numbers.
     */
    @Test
    public void maxValue() throws Exception {
        // no timer
        AbstractNoStoreAggregator<Integer> sum = new AbstractNoStoreAggregator<Integer>(
                new IntegerMaxAggregatorBinaryFunction()) {
            @Override
            protected Integer initialValue() {
                return 0;
            }
        };

        long startTime = System.currentTimeMillis();
        long stopAfter = 5000; // 5 seconds
        int maxInt = 100;
        Random rnd = new Random();
        while (System.currentTimeMillis() - startTime <= stopAfter) {
            sum.add(rnd.nextInt());
            System.out.println( "Max so far is " + sum.evaluate());
            TimeUnit.MILLISECONDS.sleep(rnd.nextInt(maxInt));
        }
        sum.stop();
    }
}
