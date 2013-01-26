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

import java.util.Random;

import org.apache.commons.functor.BaseFunctorTest;
import org.apache.commons.functor.aggregator.functions.DoubleMaxAggregatorBinaryFunction;
import org.junit.Test;

/**
 * Unit test for {@link DoubleMaxAggregatorBinaryFunction}.
 */
public class DoubleMaxAggregatorBinaryFunctionTest extends BaseFunctorTest {
    private static final double DELTA = 0.01; // make room for some poor
                                              // floating point support

    @Override
    protected Object makeFunctor() throws Exception {
        return new DoubleMaxAggregatorBinaryFunction();
    }

    @Test
    public void testNulls() throws Exception {
        DoubleMaxAggregatorBinaryFunction fct = (DoubleMaxAggregatorBinaryFunction) makeFunctor();
        Double d = fct.evaluate(null, null);
        assertNull(d);
        d = fct.evaluate(null, 1.0);
        assertEquals(1.0, d.doubleValue(), DELTA);
        d = fct.evaluate(2.0, null);
        assertEquals(2.0, d.doubleValue(), DELTA);
    }

    @Test
    public void testMax() throws Exception {
        DoubleMaxAggregatorBinaryFunction fct = (DoubleMaxAggregatorBinaryFunction) makeFunctor();
        double max = 0.0;
        double result = 0.0;
        double number1 = 0.0;
        double number2 = 0.0;
        int calls = 31;
        Random rnd = new Random();
        for (int i = 0; i < calls; i++) {
            number1 = rnd.nextDouble();
            number2 = rnd.nextDouble();
            max = Math.max(number1, number2);
            result = fct.evaluate(number1, number2);
            assertEquals(result, max, DELTA);
        }
    }

}
