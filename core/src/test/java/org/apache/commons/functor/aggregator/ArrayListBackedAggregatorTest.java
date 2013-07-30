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
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.functor.BaseFunctorTest;
import org.apache.commons.functor.Function;
import org.apache.commons.functor.aggregator.ArrayListBackedAggregator;
import org.junit.Test;

/**
 * Unit test for {@link ArrayListBackedAggregator}. TODO: Some multi-threaded
 * testing
 */
public class ArrayListBackedAggregatorTest extends BaseFunctorTest {
    @Override
    protected Object makeFunctor() throws Exception {
        return new ArrayListBackedAggregator<Object>(new SelectFirstFunction<Object>());
    }

    @Test
    public void testCreateList() throws Exception {
        @SuppressWarnings("unchecked")
        ArrayListBackedAggregator<Object> fct = (ArrayListBackedAggregator<Object>) makeFunctor();
        assertNotNull(fct.getSeries());
        assertTrue(fct.getSeries() instanceof ArrayList);
        assertEquals(fct.getSeries().size(), 0);
        // NOTE: would be good to be able to check the ArrayList capacity!
        int initialSize = 31; // nearly 10 pies
        fct = new ArrayListBackedAggregator<Object>(new SelectFirstFunction<Object>(), initialSize);
        assertNotNull(fct.getSeries());
        assertTrue(fct.getSeries() instanceof ArrayList);
        assertEquals(fct.getSeries().size(), 0);
    }

    /**
     * Dummy Function which counts the number of calls to
     * {@link #evaluate(List)} and always selects the first item in the given
     * list or null if list is null or empty.
     */
    class SelectFirstFunction<T> implements Function<List<T>, T> {
        int     calls     = 0;
        boolean exception = false; // when set to true, evaluate will throw an
                                   // exception

        public T evaluate(List<T> obj) {
            calls++;
            if (exception)
                throw new RuntimeException();
            if (obj == null || obj.size() == 0)
                return null;
            return obj.get(0);
        }
    }
}
