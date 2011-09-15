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
package org.apache.commons.functor.generator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.functor.BaseFunctorTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @version $Revision$ $Date$
 * @author Rodney Waldhoff
 */
@SuppressWarnings("unchecked")
public class TestIteratorToGeneratorAdapter extends BaseFunctorTest {

    public Object makeFunctor() {
        List list = new ArrayList();
        list.add("1");
        return new IteratorToGeneratorAdapter(list.iterator());
    }

    // Lifecycle
    // ------------------------------------------------------------------------

    private List list = null;

    @Before
    public void setUp() throws Exception {
        list = new ArrayList();
        list.add("1");
        list.add("two");
        list.add("c");
    }

    @After
    public void tearDown() throws Exception {
        list = null;
    }

    // Tests
    // ------------------------------------------------------------------------

    @Test
    public void testAdaptNull() {
        assertNull(IteratorToGeneratorAdapter.adapt(null));
    }

    @Test
    public void testAdaptNonNull() {
        assertNotNull(IteratorToGeneratorAdapter.adapt(list.iterator()));
    }

    @Test
    public void testGenerate() {
        Iterator iter = list.iterator();
        Generator gen = new IteratorToGeneratorAdapter(iter);
        List list2 = new ArrayList();
        list2.addAll(gen.toCollection());
        assertEquals(list,list2);
    }

    @Test
    public void testConstructNull() {
        try {
            new IteratorToGeneratorAdapter(null);
            fail("Expected NullPointerException");
        } catch(IllegalArgumentException e) {
            // expected
        }
    }

    @Test
    public void testEquals() {
        Iterator iter = list.iterator();
        Generator gen = new IteratorToGeneratorAdapter(iter);
        assertObjectsAreEqual(gen,gen);
        assertObjectsAreEqual(gen,new IteratorToGeneratorAdapter(iter));
    }
}