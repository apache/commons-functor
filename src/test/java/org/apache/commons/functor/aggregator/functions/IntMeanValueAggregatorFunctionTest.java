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
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.functor.BaseFunctorTest;
import org.apache.commons.functor.aggregator.functions.IntegerMeanValueAggregatorFunction;
import org.junit.Test;

/**
 * Unit test for {@link IntegerMeanValueAggregatorFunction}.
 */
public class IntMeanValueAggregatorFunctionTest extends BaseFunctorTest {
    @Override
    protected Object makeFunctor() throws Exception {
        return new IntegerMeanValueAggregatorFunction();
    }

    @Test
    public void testEmptyList() throws Exception {
        IntegerMeanValueAggregatorFunction fct = (IntegerMeanValueAggregatorFunction) makeFunctor();
        List<Integer> lst = null;
        Integer res = fct.evaluate(lst);
        assertNull(res);
        lst = new ArrayList<Integer>();
        res = fct.evaluate(lst);
        assertNull(res);
    }

    @Test
    public void testMean() throws Exception {
        IntegerMeanValueAggregatorFunction fct = (IntegerMeanValueAggregatorFunction) makeFunctor();
        List<Integer> lst = new ArrayList<Integer>();
        lst.add(0);
        int res = fct.evaluate(lst);
        assertEquals(res, 0);
        lst.add(1);
        res = fct.evaluate(lst);
        assertEquals(res, 0);
        lst.add(2);
        res = fct.evaluate(lst);
        assertEquals(res, 1);
        // finally carry out a random mean computation
        int calls = 31;
        int total;
        for (int i = 2; i <= calls; i++) {
            lst.clear();
            total = 0;
            for (int j = 1; j <= i; j++) {
                lst.add(j);
                total += j;
            }
            total /= i;
            res = fct.evaluate(lst);
            assertEquals(res, total);
        }
    }
}
