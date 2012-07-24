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
package org.apache.commons.functor.aggregator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.apache.commons.functor.BaseFunctorTest;
import org.apache.commons.functor.BinaryFunction;
import org.apache.commons.functor.aggregator.AbstractNoStoreAggregator;
import org.junit.Test;

/**
 * Unit test for {@link AbstractNoStoreAggregator}. TODO: revisit after the
 * class hierarchy change
 */
public class AbstractNoStoreAggregatorTest extends BaseFunctorTest {
    private static final String INITIAL = "abc";

    @Override
    protected Object makeFunctor() throws Exception {
        // return new TestListBackedAggregator<Object>();
        return new TestNoStoreAggregator<Object>(new String(INITIAL));
    }

    /**
     * Ensure data is initialised in constructor.
     */
    @Test
    public void testInitialised() throws Exception {
        @SuppressWarnings("unchecked")
        TestNoStoreAggregator<Object> fct = (TestNoStoreAggregator<Object>) makeFunctor();
        assertNotNull(fct.getResult());
        TestBinaryFunction<Object> agg = (TestBinaryFunction<Object>) fct.getAggregationFunction();
        assertEquals(fct.callsInitialValue, 1);
        assertEquals(agg.calls, 0);
    }

    @Test
    public void testAdd() throws Exception {
        @SuppressWarnings("unchecked")
        TestNoStoreAggregator<Object> fct = (TestNoStoreAggregator<Object>) makeFunctor();
        TestBinaryFunction<Object> agg = (TestBinaryFunction<Object>) fct.getAggregationFunction();
        int calls = 31; // nearly 10 pies :)
        for (int i = 1; i <= calls; i++) {
            fct.add(new Object());
            assertEquals(INITIAL, fct.getResult());
            assertEquals(fct.callsInitialValue, 1);
            assertEquals(agg.calls, i);
        }

        // now throw the exception and make sure we still count ok
        fct.resetUsage();
        agg.exception = true;
        boolean exc = false;
        for (int i = 1; i <= calls; i++) {
            exc = false;
            try {
                fct.add(new Object());
            } catch (Exception e) {
                exc = true;
            }
            assertTrue(exc); // make sure we have actually thrown it!
            assertEquals(INITIAL, fct.getResult());
            assertEquals(agg.calls, i);
            assertEquals(fct.callsInitialValue, 0);
        }
    }

    /**
     * Ensures beforeReset/afterReset is called correctly.
     */
    @Test
    public void testReset() throws Exception {
        @SuppressWarnings("unchecked")
        TestNoStoreAggregator<Object> fct = (TestNoStoreAggregator<Object>) makeFunctor();
        int calls = 31; // nearly 10 pies :)
        for (int i = 1; i <= calls; i++) {
            fct.reset();
            assertEquals(INITIAL, fct.getResult());
            assertEquals(fct.callsInitialValue, i + 1);
        }
    }

    /**
     * Ensures beforeEvaluate/afterEvauate is called correctly.
     */
    @Test
    public void testEvaluate() throws Exception {
        @SuppressWarnings("unchecked")
        TestNoStoreAggregator<Object> fct = (TestNoStoreAggregator<Object>) makeFunctor();
        TestBinaryFunction<Object> agg = (TestBinaryFunction<Object>) fct.getAggregationFunction();
        int calls = 31; // nearly 10 pies :)
        // test first without throwing an exception
        for (int i = 1; i <= calls; i++) {
            Object o = fct.evaluate();
            assertSame(o, fct.getResult());
            assertEquals(INITIAL, fct.getResult());
            assertEquals(agg.calls, 0);
            assertEquals(fct.callsInitialValue, 1);
        }
    }

    @Test
    public void testDataSize() {
        assertEquals(0, new TestNoStoreAggregator<Object>(new Object()).retrieveDataSize());
    }

    /**
     * Dummy binary function which always returns the first parameter.
     */
    class TestBinaryFunction<T> implements BinaryFunction<T, T, T> {
        int     calls     = 0;
        boolean exception = false; // when set to true, evaluate will throw an
                                   // exception

        public T evaluate(T left, T right) {
            calls++;
            if (exception)
                throw new RuntimeException();
            return left;
        }
    }

    /**
     * Dummy aggregator which counts calls to before/after functions.
     */
    class TestNoStoreAggregator<T> extends AbstractNoStoreAggregator<T> {
        int callsInitialValue;

        T   initial;

        public TestNoStoreAggregator(T initial) {
            super(new TestBinaryFunction<T>());
            this.initial = initial;
            resetUsage();
            this.setResult(initialValue());
        }

        /**
         * Convenience method to reset all counters to zero (rather than
         * creating a new instance of this)
         */
        public void resetUsage() {
            callsInitialValue = 0;
            TestBinaryFunction<T> fct = (TestBinaryFunction<T>) getAggregationFunction();
            fct.calls = 0;
        }

        @Override
        protected T initialValue() {
            callsInitialValue++;
            return initial;
        }
    }
}
