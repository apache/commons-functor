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
import org.apache.commons.functor.aggregator.functions.IntegerMaxAggregatorBinaryFunction;
import org.junit.Test;

/**
 * Unit test for {@link IntegerMaxAggregatorBinaryFunction}.
 */
public class IntMaxAggregatorBinaryFunctionTest extends BaseFunctorTest {
    @Override
    protected Object makeFunctor() throws Exception {
        return new IntegerMaxAggregatorBinaryFunction();
    }

    @Test
    public void testNulls() throws Exception {
        IntegerMaxAggregatorBinaryFunction fct = (IntegerMaxAggregatorBinaryFunction) makeFunctor();
        Integer d = fct.evaluate(null, null);
        assertNull(d);
        d = fct.evaluate(null, 1);
        assertEquals(1, d.intValue());
        d = fct.evaluate(2, null);
        assertEquals(2, d.intValue());
    }

    @Test
    public void testMax() throws Exception {
        IntegerMaxAggregatorBinaryFunction fct = (IntegerMaxAggregatorBinaryFunction) makeFunctor();
        int max = 0;
        int result = 0;
        int number1 = 0;
        int number2 = 0;
        int calls = 31;
        Random rnd = new Random();
        for (int i = 0; i < calls; i++) {
            number1 = rnd.nextInt();
            number2 = rnd.nextInt();
            max = Math.max(number1, number2);
            result = fct.evaluate(number1, number2);
            assertEquals(result, max);
        }
    }
}
