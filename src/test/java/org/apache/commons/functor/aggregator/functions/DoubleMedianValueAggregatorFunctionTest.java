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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.functor.BaseFunctorTest;
import org.apache.commons.functor.aggregator.functions.DoubleMedianValueAggregatorFunction;
import org.junit.Test;

/**
 * Unit test for {@link DoubleMedianValueAggregatorFunction}.
 */
public class DoubleMedianValueAggregatorFunctionTest extends BaseFunctorTest {
    private static final double DELTA = 0.01; // make room for some poor
                                              // floating point precision

    @Override
    protected Object makeFunctor() throws Exception {
        return new DoubleMedianValueAggregatorFunction();
    }

    @Test
    public void testEmptyList() throws Exception {
        DoubleMedianValueAggregatorFunction fct = (DoubleMedianValueAggregatorFunction) makeFunctor();
        assertTrue(fct.isUseCopy());
        List<Double> lst = null;
        Double res = fct.evaluate(lst);
        assertNull(res);
        lst = new ArrayList<Double>();
        res = fct.evaluate(lst);
        assertNull(res);
    }

    /**
     * Ensure we compute the median correctly and also we don't affect the
     * original list.
     */
    @Test
    public void testMedianCopy() throws Exception {
        DoubleMedianValueAggregatorFunction fct = new DoubleMedianValueAggregatorFunction(true);
        assertTrue(fct.isUseCopy());
        checkMedianCopy(fct);
        // this is also the default behaviour so ensure that is the case
        fct = (DoubleMedianValueAggregatorFunction) makeFunctor();
        checkMedianCopy(fct);
    }

    /**
     * Called by {@link #testMedianCopy()} internally.
     */
    void checkMedianCopy(DoubleMedianValueAggregatorFunction fct) throws Exception {
        List<Double> lst = new ArrayList<Double>();
        lst.add(10.0);
        double res = fct.evaluate(lst);
        assertEquals(res, 10.0, DELTA);
        assertListEqualsArray(lst, new double[] { 10.0 });
        lst.add(1000.0);
        res = fct.evaluate(lst);
        assertEquals(res, 505.0, DELTA);
        assertListEqualsArray(lst, new double[] { 10.0, 1000.0 });
        lst.add(30.0);
        res = fct.evaluate(lst);
        assertEquals(res, 30.0, DELTA);
        assertListEqualsArray(lst, new double[] { 10.0, 1000.0, 30.0 });
        lst.add(100.0);
        res = fct.evaluate(lst);
        assertEquals(res, 65.0, DELTA);
        assertListEqualsArray(lst, new double[] { 10.0, 1000.0, 30.0, 100.0 });
        lst.add(20.0);
        res = fct.evaluate(lst);
        assertEquals(res, 30.0, DELTA);
        assertListEqualsArray(lst, new double[] { 10.0, 1000.0, 30.0, 100.0, 20.0 });
    }

    /**
     * Ensure we compute the median correctly and also we sort the original
     * list.
     */
    @Test
    public void testMedianNotCopy() throws Exception {
        DoubleMedianValueAggregatorFunction fct = new DoubleMedianValueAggregatorFunction(false);
        assertFalse(fct.isUseCopy());
        List<Double> lst = new ArrayList<Double>();
        lst.add(10.0);
        double res = fct.evaluate(lst);
        assertEquals(res, 10.0, DELTA);
        assertListEqualsArray(lst, new double[] { 10.0 });
        lst.add(1000.0);
        res = fct.evaluate(lst);
        assertEquals(res, 505.0, DELTA);
        assertListEqualsArray(lst, new double[] { 10.0, 1000.0 });
        lst.add(30.0);
        res = fct.evaluate(lst);
        assertEquals(res, 30.0, DELTA);
        assertListEqualsArray(lst, new double[] { 10.0, 30.0, 1000.0 });
        lst.add(100.0);
        res = fct.evaluate(lst);
        assertEquals(res, 65.0, DELTA);
        assertListEqualsArray(lst, new double[] { 10.0, 30.0, 100.0, 1000.0 });
        lst.add(20.0);
        res = fct.evaluate(lst);
        assertEquals(res, 30.0, DELTA);
        assertListEqualsArray(lst, new double[] { 10.0, 20.0, 30.0, 100.0, 1000.0 });
    }

    /**
     * Utility function to check the elements of a list are in a given order.
     * Simply build an inline array and pass it in the <code>arr</code>
     * parameter.
     */
    void assertListEqualsArray(List<Double> list, double arr[]) throws Exception {
        assertNotNull(list);
        assertNotNull(arr);
        assertEquals(list.size(), arr.length);
        for (int i = 0; i < arr.length; i++) {
            assertEquals(list.get(i).doubleValue(), arr[i], DELTA);
        }
    }
}
