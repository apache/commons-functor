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
import java.util.Random;

import org.apache.commons.functor.BaseFunctorTest;
import org.apache.commons.functor.aggregator.functions.IntegerMaxAggregatorFunction;
import org.junit.Test;

/**
 * Unit test for {@link IntegerMaxAggregatorFunction}.
 */
public class IntMaxAggregatorFunctionTest extends BaseFunctorTest {
    @Override
    protected Object makeFunctor() throws Exception {
        return new IntegerMaxAggregatorFunction();
    }

    @Test
    public void testEmptyList() throws Exception {
        IntegerMaxAggregatorFunction fct = (IntegerMaxAggregatorFunction) makeFunctor();
        List<Integer> lst = null;
        Integer res = fct.evaluate(lst);
        assertNull(res);
        lst = new ArrayList<Integer>();
        res = fct.evaluate(lst);
        assertNull(res);
    }

    @Test
    public void testSum() throws Exception {
        IntegerMaxAggregatorFunction fct = (IntegerMaxAggregatorFunction) makeFunctor();
        List<Integer> lst = new ArrayList<Integer>();
        lst.add(0);
        int res = fct.evaluate(lst);
        assertEquals(res, 0);
        lst.add(1);
        res = fct.evaluate(lst);
        assertEquals(res, 1);
        lst.add(2);
        res = fct.evaluate(lst);
        assertEquals(res, 2);
        // finally carry out a random addition
        lst.clear();
        int calls = 31;
        int max = 0;
        Random rnd = new Random();
        for (int i = 0; i < calls; i++) {
            int random = rnd.nextInt(Integer.MAX_VALUE / calls);    //prevent overflow
            lst.add(random);
            if( random > max ) {
                max = random;
            }
            res = fct.evaluate(lst);
            assertEquals(res, max);
        }
    }
}
