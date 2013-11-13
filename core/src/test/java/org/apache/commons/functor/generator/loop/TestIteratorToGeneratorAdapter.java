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
package org.apache.commons.functor.generator.loop;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.functor.BaseFunctorTest;
import org.apache.commons.functor.generator.Generator;
import org.apache.commons.functor.generator.loop.IteratorToGeneratorAdapter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the Iterator to Generator Adapter class.
 * @version $Revision: 1363514 $ $Date: 2012-07-19 17:13:49 -0300 (Thu, 19 Jul 2012) $
 */
@SuppressWarnings("unchecked")
public class TestIteratorToGeneratorAdapter extends BaseFunctorTest {

    @Override
    public Object makeFunctor() {
        List<String> list = new ArrayList<String>();
        list.add("1");
        return new IteratorToGeneratorAdapter<String>(list.iterator());
    }

    // Lifecycle
    // ------------------------------------------------------------------------

    private List<String> list = null;

    @Before
    public void setUp() throws Exception {
        list = new ArrayList<String>();
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
        assertNull(IteratorToGeneratorAdapter.adapt((Iterator<?>) null));
    }

    @Test
    public void testAdaptNonNull() {
        assertNotNull(IteratorToGeneratorAdapter.adapt(list.iterator()));
    }

    @Test
    public void testGenerate() {
        Iterator<String> iter = list.iterator();
        Generator<String> gen = new IteratorToGeneratorAdapter<String>(iter);
        List<String> list2 = new ArrayList<String>();
        list2.addAll((Collection<String>)gen.toCollection());
        assertEquals(list,list2);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructNull() {
        new IteratorToGeneratorAdapter<Object>(null);
    }

    @Test
    public void testEquals() {
        Iterator<String> iter = list.iterator();
        Generator<String> gen = new IteratorToGeneratorAdapter<String>(iter);
        assertObjectsAreEqual(gen,gen);
        assertObjectsAreEqual(gen,new IteratorToGeneratorAdapter<String>(iter));
    }
}