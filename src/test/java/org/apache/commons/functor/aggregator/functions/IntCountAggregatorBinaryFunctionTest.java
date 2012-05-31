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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.apache.commons.functor.BaseFunctorTest;
import org.apache.commons.functor.aggregator.functions.IntegerCountAggregatorBinaryFunction;
import org.junit.Test;

/**
 * Unit test for {@link IntegerCountAggregatorBinaryFunction}.
 */
public class IntCountAggregatorBinaryFunctionTest extends BaseFunctorTest {
    @Override
    protected Object makeFunctor() throws Exception {
        return new IntegerCountAggregatorBinaryFunction();
    }

    @Test
    public void testNull() throws Exception {
        IntegerCountAggregatorBinaryFunction fct = (IntegerCountAggregatorBinaryFunction)makeFunctor();
        Integer i = fct.evaluate(null, null);
        assertNull( i );
        i = fct.evaluate( null, 1 );
        assertNull( i );
        i = fct.evaluate( 2, null );
        assertNotNull( i );
        assertEquals( i.intValue(), 3 );
    }

    @Test
    public void testCount() throws Exception {
        IntegerCountAggregatorBinaryFunction fct = (IntegerCountAggregatorBinaryFunction)makeFunctor();
        int count = 0;
        int calls = 31;
        for( int i = 1; i <= calls; i++ ) {
            count = fct.evaluate(count, 12345);
            assertEquals( i, count );
        }
    }
}
