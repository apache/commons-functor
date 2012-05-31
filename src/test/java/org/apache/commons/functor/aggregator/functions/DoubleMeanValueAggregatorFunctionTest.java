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
import org.apache.commons.functor.aggregator.functions.DoubleMeanValueAggregatorFunction;
import org.junit.Test;

/**
 * Unit test for {@link DoubleMeanValueAggregatorFunction}.
 */
public class DoubleMeanValueAggregatorFunctionTest extends BaseFunctorTest {
    @Override
    protected Object makeFunctor() throws Exception {
        return new DoubleMeanValueAggregatorFunction();
    }

    @Test
    public void testEmptyList() throws Exception {
        DoubleMeanValueAggregatorFunction fct = (DoubleMeanValueAggregatorFunction)makeFunctor();
        List<Double> lst = null;
        Double res = fct.evaluate(lst);
        assertNull( res );
        lst = new ArrayList<Double>();
        res = fct.evaluate(lst);
        assertNull( res );
    }

    @Test
    public void testMean() throws Exception {
        DoubleMeanValueAggregatorFunction fct = (DoubleMeanValueAggregatorFunction)makeFunctor();
        List<Double> lst = new ArrayList<Double>();
        lst.add( 0.0 );
        double res = fct.evaluate(lst);
        assertEquals(res, 0.0, 0.01);
        lst.add( 1.0 );
        res = fct.evaluate( lst );
        assertEquals( res, 0.5, 0.01 );
        lst.add( 2.0 );
        res = fct.evaluate( lst );
        assertEquals( res, 1.0, 0.01 );
        //finally carry out a random mean computation
        int calls = 31;
        double total;
        for( int i = 2; i <= calls; i++ ) {
            lst.clear();
            total = 0.0;
            for( int j = 1; j <= i; j++ ) {
                lst.add( (double)j );
                total += j;
            }
            total /= i;
            res = fct.evaluate( lst );
            assertEquals( res, total, 0.01 );
        }
    }
}
