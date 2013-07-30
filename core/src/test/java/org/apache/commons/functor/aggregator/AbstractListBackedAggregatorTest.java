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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.functor.BaseFunctorTest;
import org.apache.commons.functor.Function;
import org.apache.commons.functor.aggregator.AbstractListBackedAggregator;
import org.junit.Test;

/**
 * Unit test for {@link AbstractListBackedAggregator}. TODO: revisit after the
 * class hierarchy change
 */
public class AbstractListBackedAggregatorTest extends BaseFunctorTest {
    @Override
    protected Object makeFunctor() throws Exception {
        return new TestListBackedAggregator<Object>();
    }

    /**
     * Ensure <code>series</code> is created in constructor.
     */
    @Test
    public void testListCreated() throws Exception {
        @SuppressWarnings("unchecked")
        TestListBackedAggregator<Object> fct = (TestListBackedAggregator<Object>) makeFunctor();
        assertNotNull(fct.getSeries());
        assertEquals(fct.getSeries().size(), 0);
    }

    /**
     * Ensures beforeAdd/afterAdd is called correctly.
     */
    @Test
    public void testAdd() throws Exception {
        @SuppressWarnings("unchecked")
        TestListBackedAggregator<Object> fct = (TestListBackedAggregator<Object>) makeFunctor();
        int calls = 31; // nearly 10 pies :)
        for (int i = 1; i <= calls; i++) {
            fct.add(new Object());
            assertEquals(fct.getSeries().size(), i);
            assertEquals(fct.callsCreateList, 0);
        }
    }

    @Test
    public void testReset() throws Exception {
        @SuppressWarnings("unchecked")
        TestListBackedAggregator<Object> fct = (TestListBackedAggregator<Object>) makeFunctor();
        int calls = 31; // nearly 10 pies :)
        for (int i = 1; i <= calls; i++) {
            fct.reset();
            assertEquals(fct.getSeries().size(), 0);
            assertEquals(fct.callsCreateList, 0);
        }
    }

    /**
     * Ensures beforeEvaluate/afterEvauate is called correctly.
     */
    @Test
    public void testEvaluate() throws Exception {
        @SuppressWarnings("unchecked")
        TestListBackedAggregator<Object> fct = (TestListBackedAggregator<Object>) makeFunctor();
        TestFunction<Object> agg = (TestFunction<Object>) fct.getAggregationFunction();
        int calls = 31; // nearly 10 pies :)
        // test first without throwing an exception
        for (int i = 1; i <= calls; i++) {
            Object o = fct.evaluate();
            assertNull(o);
            assertEquals(fct.getSeries().size(), 0);
            assertEquals(agg.calls, i);
            assertEquals(fct.callsCreateList, 0);
        }

        // now throw the exception and make sure we still count ok
        fct.resetUsage();
        agg.exception = true;
        boolean exc = false;
        for (int i = 1; i <= calls; i++) {
            Object o = null;
            exc = false;
            try {
                o = fct.evaluate();
            } catch (Exception e) {
                exc = true;
            }
            assertNull(o);
            assertTrue(exc); // make sure we have actually thrown it!
            assertEquals(fct.getSeries().size(), 0);
            assertEquals(agg.calls, i);
            assertEquals(fct.callsCreateList, 0);
        }
    }

    /**
     * "Complete" add/evaluate/reset chain.
     */
    @Test
    public void testAddEvaluateReset() throws Exception {
        @SuppressWarnings("unchecked")
        TestListBackedAggregator<Object> fct = (TestListBackedAggregator<Object>) makeFunctor();
        TestFunction<Object> agg = (TestFunction<Object>) fct.getAggregationFunction();
        int callsAdd = 31; // nearly 10 pies :)
        int callsEvaluate = 2; // circumference (i.e. 2 pies)
        int callsReset = 17;
        for (int i = 1; i <= callsAdd; i++)
            fct.add(new Object());
        assertEquals(fct.getSeries().size(), callsAdd);
        assertEquals(agg.calls, 0);
        assertEquals(fct.callsCreateList, 0);

        for (int i = 1; i <= callsEvaluate; i++)
            fct.evaluate();
        assertEquals(fct.getSeries().size(), callsAdd);
        assertEquals(agg.calls, callsEvaluate);
        assertEquals(fct.callsCreateList, 0);

        for (int i = 1; i <= callsReset; i++)
            fct.reset();
        assertEquals(fct.getSeries().size(), 0);
        assertEquals(agg.calls, callsEvaluate);
        assertEquals(fct.callsCreateList, 0);
    }

    @Test
    public void testGetSize() throws Exception {
        @SuppressWarnings("unchecked")
        TestListBackedAggregator<Object> fct = (TestListBackedAggregator<Object>) makeFunctor();
        Random nAdds = new Random(); // this decides how many adds we do at each
                                     // step
        int callsAdd = 31;
        int currSize = 0;
        int maxAdds = 100; // maximum objects to add in one cycle
        for (int i = 0; i < callsAdd; i++) {
            int currAdds = nAdds.nextInt(maxAdds);
            currSize += currAdds;
            for (int j = 0; j < currAdds; j++) {
                fct.add(new Object());
            }
            assertEquals(currSize, fct.getDataSize());
        }
    }

    /**
     * Dummy Function which counts the number of calls to
     * {@link #evaluate(List)}.
     */
    class TestFunction<T> implements Function<List<T>, T> {
        int     calls     = 0;
        boolean exception = false; // when set to true, evaluate will throw an
                                   // exception

        public T evaluate(List<T> obj) {
            calls++;
            if (exception)
                throw new RuntimeException();
            return null;
        }
    }

    /**
     * Dummy aggregator which counts calls to before/after functions.
     */
    class TestListBackedAggregator<T> extends AbstractListBackedAggregator<T> {
        int callsCreateList;

        public TestListBackedAggregator() {
            super(new TestFunction<T>());
            resetUsage();
        }

        /**
         * Convenience method to reset all counters to zero (rather than
         * creating a new instance of this)
         */
        public void resetUsage() {
            callsCreateList = 0;
            TestFunction<T> fct = (TestFunction<T>) getAggregationFunction();
            fct.calls = 0;
        }

        @Override
        protected List<T> createList() {
            // for the purpose of testing we'll be using a sync'd implementation
            // of List
            callsCreateList++;
            return new CopyOnWriteArrayList<T>();
        }
    }
}
