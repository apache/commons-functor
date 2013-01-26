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
import org.apache.commons.functor.aggregator.functions.DoubleSumAggregatorFunction;
import org.junit.Test;

/**
 * Unit test for {@link DoubleSumAggregatorFunction}.
 */
public class DoubleSumAggregatorFunctionTest extends BaseFunctorTest {
    @Override
    protected Object makeFunctor() throws Exception {
        return new DoubleSumAggregatorFunction();
    }

    @Test
    public void testEmptyList() throws Exception {
        DoubleSumAggregatorFunction fct = (DoubleSumAggregatorFunction) makeFunctor();
        List<Double> lst = null;
        Double res = fct.evaluate(lst);
        assertNull(res);
        lst = new ArrayList<Double>();
        res = fct.evaluate(lst);
        assertNull(res);
    }

    @Test
    public void testSum() throws Exception {
        DoubleSumAggregatorFunction fct = (DoubleSumAggregatorFunction) makeFunctor();
        List<Double> lst = new ArrayList<Double>();
        lst.add(0.0);
        double res = fct.evaluate(lst);
        assertEquals(res, 0.0, 0.01);
        lst.add(1.0);
        res = fct.evaluate(lst);
        assertEquals(res, 1.0, 0.01);
        lst.add(2.0);
        res = fct.evaluate(lst);
        assertEquals(res, 3.0, 0.01);
        // finally carry out a random addition
        lst.clear();
        int calls = 31;
        double total = 0.0;
        Random rnd = new Random();
        for (int i = 0; i < calls; i++) {
            double random = rnd.nextDouble();
            lst.add(random);
            total += random;
            res = fct.evaluate(lst);
            assertEquals(res, total, 0.01);
        }
    }
}
