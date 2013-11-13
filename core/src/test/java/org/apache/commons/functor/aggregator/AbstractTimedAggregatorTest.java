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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.functor.BaseFunctorTest;
import org.apache.commons.functor.aggregator.AbstractTimedAggregator;
import org.apache.commons.functor.aggregator.TimedAggregatorListener;
import org.junit.Test;

public class AbstractTimedAggregatorTest extends BaseFunctorTest {
    /** Default timer interval used in the tests. */
    private static final long DEFAULT_INTERVAL = 500L; // 0.5 second

    /** Sleep extra msecs past the interval. */
    private static final long SLEEP            = 10L;

    @Override
    protected Object makeFunctor() throws Exception {
        return new SimpleStoreTimedAggregator();
    }

    @Test
    public void testCreateNoTimer() throws Exception {
        SimpleStoreTimedAggregator agg = (SimpleStoreTimedAggregator) makeFunctor();
        assertEquals(agg.getInterval(), 0L);
        assertFalse(agg.isTimerEnabled());
        assertFalse(agg.isSharedTimer());
        assertNull(agg.getTimerListeners());
        assertFalse(agg.removeTimerListener(null)); // shouldn't NPE here
                                                    // because the listeners
                                                    // list is null!
        agg.stop(); // shouldn't trigger any exceptions, even though there's no
                    // timer
    }

    private void standardTimerTesting(SimpleStoreTimedAggregator agg, long interval) throws Exception {
        final AtomicInteger count = new AtomicInteger();
        TimedAggregatorListener<Integer> timedListener = new TimedAggregatorListener<Integer>() {
            public void onTimer(AbstractTimedAggregator<Integer> aggregator, Integer evaluation) {
                count.incrementAndGet();
            }
        };

        assertEquals(agg.getInterval(), interval);
        assertEquals(agg.getTimerListeners().size(), 0);
        agg.addTimerListener(timedListener);
        List<TimedAggregatorListener<Integer>> timerListeners = agg.getTimerListeners();
        assertTrue(timerListeners.contains(timedListener));
        TimeUnit.MILLISECONDS.sleep(interval + SLEEP);
        // timer should have kicked in by now
        assertTrue(count.intValue() > 0);

        TimedAggregatorListener<Integer> wrongListener = new TimedAggregatorListener<Integer>() {
            public void onTimer(AbstractTimedAggregator<Integer> aggregator, Integer evaluation) {
                // do nothing in here
            }
        };
        assertFalse(agg.removeTimerListener(wrongListener));
        assertEquals(agg.getTimerListeners().size(), 1);
        assertTrue(agg.removeTimerListener(timedListener));
        int saveValue = count.get();
        assertEquals(agg.getTimerListeners().size(), 0);
        // give enough time for the timer to kick in again
        TimeUnit.MILLISECONDS.sleep(interval + SLEEP);
        assertEquals(saveValue, count.get());
    }

    @Test
    public void testCreateIntervalParam() throws Exception {
        SimpleStoreTimedAggregator agg = new SimpleStoreTimedAggregator(DEFAULT_INTERVAL);
        assertTrue(agg.isTimerEnabled());
        assertFalse(agg.isSharedTimer());
        standardTimerTesting(agg, DEFAULT_INTERVAL);
        agg.stop();
    }

    @Test
    public void testCreateNotSharedTimer() throws Exception {
        SimpleStoreTimedAggregator agg = new SimpleStoreTimedAggregator(DEFAULT_INTERVAL, false);
        assertTrue(agg.isTimerEnabled());
        assertFalse(agg.isSharedTimer());
        standardTimerTesting(agg, DEFAULT_INTERVAL);
        agg.stop();
    }

    @Test
    public void testCreateSharedTimer() throws Exception {
        SimpleStoreTimedAggregator agg = new SimpleStoreTimedAggregator(DEFAULT_INTERVAL, true);
        assertTrue(agg.isTimerEnabled());
        assertTrue(agg.isSharedTimer());
        standardTimerTesting(agg, DEFAULT_INTERVAL);
        agg.stop();
    }

    /**
     * Simple timed aggregator which just stores the latest object and returns
     * it when evaluating the result.
     *
     * @author Liviu Tudor http://about.me/liviutudor
     */
    class SimpleStoreTimedAggregator extends AbstractTimedAggregator<Integer> {
        private Integer object;

        public SimpleStoreTimedAggregator() {
            super();
        }

        public SimpleStoreTimedAggregator(long interval) {
            super(interval);
        }

        public SimpleStoreTimedAggregator(long interval, boolean useSharedTimer) {
            super(interval, useSharedTimer);
        }

        @Override
        protected void doAdd(Integer data) {
            this.object = data;
        }

        @Override
        protected Integer doEvaluate() {
            return object;
        }

        @Override
        protected int retrieveDataSize() {
            return 1;
        }

        @Override
        protected void doReset() {
            object = Integer.valueOf(0);
        }
    }
}
