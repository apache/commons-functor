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
import org.apache.commons.functor.aggregator.functions.IntegerMedianValueAggregatorFunction;
import org.junit.Test;

/**
 * Unit test for {@link IntegerMedianValueAggregatorFunction}.
 */
public class IntMedianValueAggregatorFunctionTest extends BaseFunctorTest {
    @Override
    protected Object makeFunctor() throws Exception {
        return new IntegerMedianValueAggregatorFunction();
    }

    @Test
    public void testEmptyList() throws Exception {
        IntegerMedianValueAggregatorFunction fct = (IntegerMedianValueAggregatorFunction) makeFunctor();
        assertTrue(fct.isUseCopy());
        List<Integer> lst = null;
        Integer res = fct.evaluate(lst);
        assertNull(res);
        lst = new ArrayList<Integer>();
        res = fct.evaluate(lst);
        assertNull(res);
    }

    /**
     * Ensure we compute the median correctly and also we don't affect the
     * original list.
     */
    @Test
    public void testMedianCopy() throws Exception {
        IntegerMedianValueAggregatorFunction fct = new IntegerMedianValueAggregatorFunction(true);
        assertTrue(fct.isUseCopy());
        checkMedianCopy(fct);
        // this is also the default behaviour so ensure that is the case
        fct = (IntegerMedianValueAggregatorFunction) makeFunctor();
        checkMedianCopy(fct);
    }

    /**
     * Called by {@link #testMedianCopy()} internally.
     */
    void checkMedianCopy(IntegerMedianValueAggregatorFunction fct) throws Exception {
        List<Integer> lst = new ArrayList<Integer>();
        lst.add(10);
        int res = fct.evaluate(lst);
        assertEquals(res, 10);
        assertListEqualsArray(lst, new int[] { 10 });
        lst.add(1000);
        res = fct.evaluate(lst);
        assertEquals(res, 505);
        assertListEqualsArray(lst, new int[] { 10, 1000 });
        lst.add(30);
        res = fct.evaluate(lst);
        assertEquals(res, 30);
        assertListEqualsArray(lst, new int[] { 10, 1000, 30 });
        lst.add(100);
        res = fct.evaluate(lst);
        assertEquals(res, 65);
        assertListEqualsArray(lst, new int[] { 10, 1000, 30, 100 });
        lst.add(20);
        res = fct.evaluate(lst);
        assertEquals(res, 30);
        assertListEqualsArray(lst, new int[] { 10, 1000, 30, 100, 20 });
    }

    /**
     * Ensure we compute the median correctly and also we sort the original
     * list.
     */
    @Test
    public void testMedianNotCopy() throws Exception {
        IntegerMedianValueAggregatorFunction fct = new IntegerMedianValueAggregatorFunction(false);
        assertFalse(fct.isUseCopy());
        List<Integer> lst = new ArrayList<Integer>();
        lst.add(10);
        int res = fct.evaluate(lst);
        assertEquals(res, 10);
        assertListEqualsArray(lst, new int[] { 10 });
        lst.add(1000);
        res = fct.evaluate(lst);
        assertEquals(res, 505);
        assertListEqualsArray(lst, new int[] { 10, 1000 });
        lst.add(30);
        res = fct.evaluate(lst);
        assertEquals(res, 30);
        assertListEqualsArray(lst, new int[] { 10, 30, 1000 });
        lst.add(100);
        res = fct.evaluate(lst);
        assertEquals(res, 65);
        assertListEqualsArray(lst, new int[] { 10, 30, 100, 1000 });
        lst.add(20);
        res = fct.evaluate(lst);
        assertEquals(res, 30);
        assertListEqualsArray(lst, new int[] { 10, 20, 30, 100, 1000 });
    }

    /**
     * Utility function to check the elements of a list are in a given order.
     * Simply build an inline array and pass it in the <code>arr</code>
     * parameter.
     */
    void assertListEqualsArray(List<Integer> list, int arr[]) throws Exception {
        assertNotNull(list);
        assertNotNull(arr);
        assertEquals(list.size(), arr.length);
        for (int i = 0; i < arr.length; i++)
            assertEquals(list.get(i).intValue(), arr[i]);
    }
}
