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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.functor.BaseFunctorTest;
import org.apache.commons.functor.aggregator.functions.DoublePercentileAggregatorFunction;
import org.junit.Test;

/**
 * Unit test for {@link DoublePercentileAggregatorFunction}.
 */
public class DoublePercentileAggregatorFunctionTest extends BaseFunctorTest {
    private static final double DELTA    = 0.01; // make room for some poor
                                                 // floating point precision
    private static final double DEF_PERC = 90;  // by default use the 90th
                                                 // percentile

    @Override
    protected Object makeFunctor() throws Exception {
        return new DoublePercentileAggregatorFunction(DEF_PERC);
    }

    @Test
    public void testCreate() throws Exception {
        // default
        DoublePercentileAggregatorFunction fct = (DoublePercentileAggregatorFunction) makeFunctor();
        assertTrue(fct.isUseCopy());
        assertEquals(fct.getPercentile(), DEF_PERC, DELTA);

        // different percentage
        double perc = 50.0;
        fct = new DoublePercentileAggregatorFunction(perc);
        assertTrue(fct.isUseCopy());
        assertEquals(fct.getPercentile(), perc, DELTA);

        // use copy
        fct = new DoublePercentileAggregatorFunction(perc, false);
        assertFalse(fct.isUseCopy());
        assertEquals(fct.getPercentile(), perc, DELTA);

        // test illegal exc
        boolean exc = false;
        fct = null;
        try {
            fct = new DoublePercentileAggregatorFunction(-100);
        } catch (IllegalArgumentException e) {
            exc = true;
        }
        assertTrue(exc);

        fct = null;
        exc = false;
        try {
            fct = new DoublePercentileAggregatorFunction(101);
        } catch (IllegalArgumentException e) {
            exc = true;
        }
        assertTrue(exc);
    }

    @Test
    public void testComputeRank() throws Exception {
        List<Double> data = new ArrayList<Double>();
        data.add(0.0);
        data.add(1.0);
        data.add(2.0);
        data.add(3.0);
        data.add(4.0);
        // size of list is 5 now

        // first item
        DoublePercentileAggregatorFunction fct = new DoublePercentileAggregatorFunction(0.0);
        int rank = fct.computeRank(data);
        assertEquals(rank, 0);
        assertEquals(data.get(rank).doubleValue(), 0.0, DELTA);
        // last item
        fct = new DoublePercentileAggregatorFunction(100.0);
        rank = fct.computeRank(data);
        assertEquals(rank, data.size() - 1);
        assertEquals(data.get(rank).doubleValue(), 4.0, DELTA);

        // middle one
        fct = new DoublePercentileAggregatorFunction(50.0);
        rank = fct.computeRank(data);
        assertEquals(rank, 2);
        assertEquals(data.get(rank).doubleValue(), 2.0, DELTA);

        // 40% = 2nd item
        fct = new DoublePercentileAggregatorFunction(40.0);
        rank = fct.computeRank(data);
        assertEquals(rank, 1);
        assertEquals(data.get(rank).doubleValue(), 1.0, DELTA);

        // 80% = 4th item
        fct = new DoublePercentileAggregatorFunction(80.0);
        rank = fct.computeRank(data);
        assertEquals(rank, 3);
        assertEquals(data.get(rank).doubleValue(), 3.0, DELTA);

        // in between (e.g. 70%) means 3rd item
        fct = new DoublePercentileAggregatorFunction(70.0);
        rank = fct.computeRank(data);
        assertEquals(rank, 2);
        assertEquals(data.get(rank).doubleValue(), 2.0, DELTA);

        // but 75% means 4th item
        fct = new DoublePercentileAggregatorFunction(75.0);
        rank = fct.computeRank(data);
        assertEquals(rank, 3);
        assertEquals(data.get(rank).doubleValue(), 3.0, DELTA);
    }

    @Test
    public void testEvaluateNullEmpty() throws Exception {
        DoublePercentileAggregatorFunction fct = (DoublePercentileAggregatorFunction) makeFunctor();
        List<Double> data = null;
        Double d = fct.evaluate(data);
        assertNull(d);
        data = new ArrayList<Double>();
        d = fct.evaluate(data);
        assertNull(d);
    }

    @Test
    public void testEvaluateNoCopy() throws Exception {
        // using a copy
        DoublePercentileAggregatorFunction fct = (DoublePercentileAggregatorFunction) makeFunctor();
        List<Double> data = new ArrayList<Double>();
        data.add(4.0);
        data.add(3.0);
        data.add(2.0);
        data.add(1.0);
        data.add(0.0);
        // size of list is 5 now

        Double d = fct.evaluate(data);
        assertEquals(d, 3.0, DELTA);
        assertEquals(data.get(0), 4.0, DELTA);
        assertEquals(data.get(1), 3.0, DELTA);
        assertEquals(data.get(2), 2.0, DELTA);
        assertEquals(data.get(3), 1.0, DELTA);
        assertEquals(data.get(4), 0.0, DELTA);

        // using a copy (explicitly)
        fct = new DoublePercentileAggregatorFunction(DEF_PERC, true);
        d = fct.evaluate(data);
        assertEquals(d, 3.0, DELTA);
        assertEquals(data.get(0), 4.0, DELTA);
        assertEquals(data.get(1), 3.0, DELTA);
        assertEquals(data.get(2), 2.0, DELTA);
        assertEquals(data.get(3), 1.0, DELTA);
        assertEquals(data.get(4), 0.0, DELTA);

        // operate on the list directly
        fct = new DoublePercentileAggregatorFunction(DEF_PERC, false);
        d = fct.evaluate(data);
        assertEquals(d, 3.0, DELTA);
        assertEquals(data.get(4), 4.0, DELTA);
        assertEquals(data.get(3), 3.0, DELTA);
        assertEquals(data.get(2), 2.0, DELTA);
        assertEquals(data.get(1), 1.0, DELTA);
        assertEquals(data.get(0), 0.0, DELTA);
    }
}
