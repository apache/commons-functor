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
package org.apache.commons.functor.core.collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.commons.functor.BaseFunctorTest;
import org.apache.commons.functor.Function;
import org.apache.commons.functor.core.Identity;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @version $Revision$ $Date$
 */
public class TestTransformedIterator extends BaseFunctorTest {

    @Override
    public Object makeFunctor() {
        List<String> list1 = new ArrayList<String>();
        list1.add("xyzzy");
        return TransformedIterator.transform(list1.iterator(), Identity.instance());
    }

    // Lifecycle
    // ------------------------------------------------------------------------

    @Before
    public void setUp() throws Exception {
        list = new ArrayList<Integer>();
        negatives = new ArrayList<Integer>();
        for (int i = 0; i < 10; i++) {
            list.add(Integer.valueOf(i));
            negatives.add(Integer.valueOf(i * -1));
        }
    }

    @After
    public void tearDown() throws Exception {
        list = null;
        negatives = null;
    }

    // Tests
    // ------------------------------------------------------------------------

    @Test
    public void testBasicTransform() {
        Iterator<Integer> expected = negatives.iterator();
        Iterator<Integer> testing = new TransformedIterator<Integer, Integer>(list.iterator(), negate);
        while (expected.hasNext()) {
            assertTrue(testing.hasNext());
            assertEquals(expected.next(), testing.next());
        }
        assertTrue(!testing.hasNext());
    }

    @Test
    public void testEmptyList() {
        Iterator<?> testing =
            new TransformedIterator<Integer, Integer>(Collections.<Integer> emptyList().iterator(), negate);
        assertTrue(!testing.hasNext());
    }

    @Test
    public void testNextWithoutHasNext() {
        Iterator<Integer> testing = new TransformedIterator<Integer, Integer>(list.iterator(), negate);
        Iterator<Integer> expected = negatives.iterator();
        while (expected.hasNext()) {
            assertEquals(expected.next(), testing.next());
        }
        assertTrue(!(testing.hasNext()));
    }

    @Test(expected = NoSuchElementException.class)
    public void testNextAfterEndOfList() {
        Iterator<Integer> testing = new TransformedIterator<Integer, Integer>(list.iterator(), negate);
        Iterator<Integer> expected = negatives.iterator();
        while (expected.hasNext()) {
            assertEquals(expected.next(), testing.next());
        }
        testing.next();
    }

    @Test(expected = NoSuchElementException.class)
    public void testNextOnEmptyList() {
        Iterator<Integer> testing =
            new TransformedIterator<Integer, Integer>(Collections.<Integer> emptyList().iterator(), negate);
        testing.next();
    }

    @Test(expected = IllegalStateException.class)
    public void testRemoveBeforeNext() {
        Iterator<Integer> testing = new TransformedIterator<Integer, Integer>(list.iterator(), negate);
        testing.remove();
    }

    @Test(expected = IllegalStateException.class)
    public void testRemoveAfterNext() {
        Iterator<Integer> testing = new TransformedIterator<Integer, Integer>(list.iterator(), negate);
        testing.next();
        testing.remove();
        testing.remove();
    }

    @Test
    public void testRemoveAll() {
        Iterator<Integer> testing = new TransformedIterator<Integer, Integer>(list.iterator(), negate);
        while (testing.hasNext()) {
            testing.next();
            testing.remove();
        }
        assertTrue(list.isEmpty());
    }

    @Test
    public void testRemoveWithoutHasNext() {
        Iterator<Integer> testing = new TransformedIterator<Integer, Integer>(list.iterator(), negate);
        for (int i = 0, m = list.size(); i < m; i++) {
            testing.next();
            testing.remove();
        }
        assertTrue(list.isEmpty());
    }

    @Test
    public void testTransformWithNullIteratorReturnsNull() {
        assertNull(TransformedIterator.transform(null, negate));
    }

    @Test
    public void testTransformWithNullFunctionReturnsIdentity() {
        Iterator<Integer> iter = list.iterator();
        assertSame(iter, TransformedIterator.maybeTransform(iter, null));
    }

    @Test
    public void testTransformWithNullIteratorAndNullFunctionReturnsNull() {
        assertSame(null, TransformedIterator.maybeTransform(null, null));
    }

    @Test
    public void testTransform() {
        Iterator<Integer> iter = list.iterator();
        assertNotSame(iter, TransformedIterator.maybeTransform(iter, negate));
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorProhibitsNull() {
        new TransformedIterator<Integer, Integer>(null, null);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorProhibitsNull2() {
        new TransformedIterator<Integer, Integer>(null, negate);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorProhibitsNull3() {
        new TransformedIterator<Integer, Integer>(list.iterator(), null);
    }

    @Test
    public void testEquals() {
        Iterator<Integer> iter = list.iterator();
        TransformedIterator<Integer, Integer> t = new TransformedIterator<Integer, Integer>(iter, negate);
        Function<Number, Double> negateDouble = new Function<Number, Double>() {
            public Double evaluate(Number obj) {
                return Double.valueOf(obj.intValue() * -1);
            }
        };
        assertEquals(t, new TransformedIterator<Integer, Integer>(iter, negate));
        assertTrue(!t.equals(new TransformedIterator<Integer, Double>(list.iterator(), negateDouble)));
        assertTrue(!t.equals(new TransformedIterator<Float, Integer>(Arrays.asList(0.0f, 0.1f).iterator(), negate)));
        assertTrue(!t.equals(null));
    }

    // Attributes
    // ------------------------------------------------------------------------
    private List<Integer> list = null;
    private List<Integer> negatives = null;
    private Function<Number, Integer> negate = new Function<Number, Integer>() {
        public Integer evaluate(Number obj) {
            return Integer.valueOf(obj.intValue() * -1);
        }
    };

}
