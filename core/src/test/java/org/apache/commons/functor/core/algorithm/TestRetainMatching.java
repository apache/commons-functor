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
package org.apache.commons.functor.core.algorithm;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.functor.BaseFunctorTest;
import org.apache.commons.functor.Function;
import org.apache.commons.functor.Predicate;
import org.apache.commons.functor.core.algorithm.RetainMatching;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests {@link RetainMatching} algorithm.
 */
public class TestRetainMatching extends BaseFunctorTest {

    // Lifecycle
    // ------------------------------------------------------------------------

    @Before
    public void setUp() throws Exception {
        list = new ArrayList<Integer>();
        odds = new ArrayList<Integer>();
        for (int i = 0; i < 10; i++) {
            list.add(Integer.valueOf(i));
            if (i % 2 != 0) {
                odds.add(Integer.valueOf(i));
            }
        }
    }

    @After
    public void tearDown() throws Exception {
        list = null;
        odds = null;
    }

    // Tests
    // ------------------------------------------------------------------------

    @Override
    protected Object makeFunctor() throws Exception {
        return RetainMatching.instance();
    }

    @Test
    public void testRemove() {
        new RetainMatching<Integer>().run(list.iterator(), isOdd);
        assertEquals(odds, list);
    }

    // Attributes
    // ------------------------------------------------------------------------
    private List<Integer> list = null;
    private List<Integer> odds = null;
    private Predicate<Integer> isOdd = new Predicate<Integer>() {
        public boolean test(Integer obj) {
            return obj % 2 != 0;
        }
    };

    // Classes
    // ------------------------------------------------------------------------

    static class Doubler implements Function<Integer, Integer> {
        public Integer evaluate(Integer obj) {
            return Integer.valueOf(2 * obj);
        }
    }

}
