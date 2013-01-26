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
import org.apache.commons.functor.aggregator.functions.IntegerPercentileAggregatorFunction;
import org.junit.Test;

/**
 * Unit test for {@link IntegerPercentileAggregatorFunction}.
 */
public class IntPercentileAggregatorFunctionTest extends BaseFunctorTest {
    private static final double DELTA    = 0.01; // make room for some poor
                                                 // floating point support

    private static final double DEF_PERC = 90;  // by default use the 90th
                                                 // percentile

    @Override
    protected Object makeFunctor() throws Exception {
        return new IntegerPercentileAggregatorFunction(DEF_PERC);
    }

    @Test
    public void testCreate() throws Exception {
        // default
        IntegerPercentileAggregatorFunction fct = (IntegerPercentileAggregatorFunction) makeFunctor();
        assertTrue(fct.isUseCopy());
        assertEquals(fct.getPercentile(), DEF_PERC, DELTA);

        // different percentage
        double perc = 50.0;
        fct = new IntegerPercentileAggregatorFunction(perc);
        assertTrue(fct.isUseCopy());
        assertEquals(fct.getPercentile(), perc, DELTA);

        // use copy
        fct = new IntegerPercentileAggregatorFunction(perc, false);
        assertFalse(fct.isUseCopy());
        assertEquals(fct.getPercentile(), perc, DELTA);

        // test illegal exc
        boolean exc = false;
        fct = null;
        try {
            fct = new IntegerPercentileAggregatorFunction(-100);
        } catch (IllegalArgumentException e) {
            exc = true;
        }
        assertTrue(exc);

        fct = null;
        exc = false;
        try {
            fct = new IntegerPercentileAggregatorFunction(101);
        } catch (IllegalArgumentException e) {
            exc = true;
        }
        assertTrue(exc);
    }

    @Test
    public void testComputeRank() throws Exception {
        List<Integer> data = new ArrayList<Integer>();
        data.add(0);
        data.add(1);
        data.add(2);
        data.add(3);
        data.add(4);
        // size of list is 5 now

        // first item
        IntegerPercentileAggregatorFunction fct = new IntegerPercentileAggregatorFunction(0.0);
        int rank = fct.computeRank(data);
        assertEquals(rank, 0);
        assertEquals(data.get(rank).intValue(), 0);
        // last item
        fct = new IntegerPercentileAggregatorFunction(100.0);
        rank = fct.computeRank(data);
        assertEquals(rank, data.size() - 1);
        assertEquals(data.get(rank).intValue(), 4);

        // middle one
        fct = new IntegerPercentileAggregatorFunction(50.0);
        rank = fct.computeRank(data);
        assertEquals(rank, 2);
        assertEquals(data.get(rank).intValue(), 2);

        // 40% = 2nd item
        fct = new IntegerPercentileAggregatorFunction(40.0);
        rank = fct.computeRank(data);
        assertEquals(rank, 1);
        assertEquals(data.get(rank).intValue(), 1);

        // 80% = 4th item
        fct = new IntegerPercentileAggregatorFunction(80.0);
        rank = fct.computeRank(data);
        assertEquals(rank, 3);
        assertEquals(data.get(rank).intValue(), 3);

        // in between (e.g. 70%) means 3rd item
        fct = new IntegerPercentileAggregatorFunction(70.0);
        rank = fct.computeRank(data);
        assertEquals(rank, 2);
        assertEquals(data.get(rank).intValue(), 2);

        // but 75% means 4th item
        fct = new IntegerPercentileAggregatorFunction(75.0);
        rank = fct.computeRank(data);
        assertEquals(rank, 3);
        assertEquals(data.get(rank).intValue(), 3);
    }

    @Test
    public void testEvaluateNullEmpty() throws Exception {
        IntegerPercentileAggregatorFunction fct = (IntegerPercentileAggregatorFunction) makeFunctor();
        List<Integer> data = null;
        Integer d = fct.evaluate(data);
        assertNull(d);
        data = new ArrayList<Integer>();
        d = fct.evaluate(data);
        assertNull(d);
    }

    @Test
    public void testEvaluateNoCopy() throws Exception {
        // using a copy
        IntegerPercentileAggregatorFunction fct = (IntegerPercentileAggregatorFunction) makeFunctor();
        List<Integer> data = new ArrayList<Integer>();
        data.add(4);
        data.add(3);
        data.add(2);
        data.add(1);
        data.add(0);
        // size of list is 5 now

        Integer d = fct.evaluate(data);
        assertEquals(d.intValue(), 3);
        assertEquals(data.get(0).intValue(), 4);
        assertEquals(data.get(1).intValue(), 3);
        assertEquals(data.get(2).intValue(), 2);
        assertEquals(data.get(3).intValue(), 1);
        assertEquals(data.get(4).intValue(), 0);

        // using a copy (explicitly)
        fct = new IntegerPercentileAggregatorFunction(DEF_PERC, true);
        d = fct.evaluate(data);
        assertEquals(d.intValue(), 3);
        assertEquals(data.get(0).intValue(), 4);
        assertEquals(data.get(1).intValue(), 3);
        assertEquals(data.get(2).intValue(), 2);
        assertEquals(data.get(3).intValue(), 1);
        assertEquals(data.get(4).intValue(), 0);

        // operate on the list directly
        fct = new IntegerPercentileAggregatorFunction(DEF_PERC, false);
        d = fct.evaluate(data);
        assertEquals(d.intValue(), 3);
        assertEquals(data.get(4).intValue(), 4);
        assertEquals(data.get(3).intValue(), 3);
        assertEquals(data.get(2).intValue(), 2);
        assertEquals(data.get(1).intValue(), 1);
        assertEquals(data.get(0).intValue(), 0);
    }
}
