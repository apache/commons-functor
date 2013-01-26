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
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.functor.BaseFunctorTest;
import org.apache.commons.functor.UnaryFunction;
import org.apache.commons.functor.core.algorithm.InPlaceTransform;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests {@link InPlaceTransform} algorithm.
 */
public class TestInPlaceTransform extends BaseFunctorTest {

    // Lifecycle
    // ------------------------------------------------------------------------

    @Before
    public void setUp() throws Exception {
        list = new ArrayList<Integer>();
        doubled = new ArrayList<Integer>();
        for (int i=0;i<10;i++) {
            list.add(new Integer(i));
            doubled.add(new Integer(i*2));
        }
    }

    @After
    public void tearDown() throws Exception {
        list = null;
        doubled = null;
    }

    // Tests
    // ------------------------------------------------------------------------

    @Override
    protected Object makeFunctor() throws Exception {
        return InPlaceTransform.instance();
    }

    @Test
    public void testTransform() {
        new InPlaceTransform<Integer>().run(
            list.listIterator(),
            new UnaryFunction<Integer, Integer>() {
                public Integer evaluate(Integer obj) {
                    return new Integer(obj*2);
                }
            }
        );
        assertEquals(doubled,list);
    }

    public void testInstance() {
        assertNotNull("InPlaceTransform must not be null", InPlaceTransform.instance());
    }

    // Attributes
    // ------------------------------------------------------------------------
    private List<Integer> list = null;
    private List<Integer> doubled = null;

    // Classes
    // ------------------------------------------------------------------------

    static class Doubler implements UnaryFunction<Integer, Integer> {
        public Integer evaluate(Integer obj) {
            return new Integer(2*obj);
        }
    }

}
