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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.commons.functor.BaseFunctorTest;
import org.apache.commons.functor.UnaryPredicate;
import org.apache.commons.functor.core.Constant;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @version $Revision$ $Date$
 */
public class TestFilteredIterator extends BaseFunctorTest {

    @Override
    public Object makeFunctor() {
        List<String> list = new ArrayList<String>();
        list.add("xyzzy");
        return FilteredIterator.filter(list.iterator(),Constant.truePredicate());
    }

    @Before
    public void setUp() throws Exception {
        list = new ArrayList<Integer>();
        evens = new ArrayList<Integer>();
        for (int i=0;i<10;i++) {
            list.add(new Integer(i));
            if (i%2 == 0) {
                evens.add(new Integer(i));
            }
        }
    }

    @After
    public void tearDown() throws Exception {
        list = null;
        evens = null;
    }

    // Tests
    // ------------------------------------------------------------------------

    @Test
    public void testSomePass() {
        Iterator<Integer> expected = evens.iterator();
        Iterator<Integer> testing = new FilteredIterator<Integer>(list.iterator(),isEven);
        while(expected.hasNext()) {
            assertTrue(testing.hasNext());
            assertEquals(expected.next(),testing.next());
        }
        assertTrue(!testing.hasNext());
    }

    @Test
    public void testAllPass() {
        Iterator<Integer> expected = list.iterator();
        Iterator<Integer> testing = new FilteredIterator<Integer>(list.iterator(),Constant.truePredicate());
        while(expected.hasNext()) {
            assertTrue(testing.hasNext());
            assertEquals(expected.next(),testing.next());
        }
        assertTrue(!testing.hasNext());
    }

    @Test
    public void testAllPass2() {
        Iterator<Integer> expected = list.iterator();
        Iterator<Integer> testing = new FilteredIterator<Integer>(list.iterator(),Constant.truePredicate());
        while(expected.hasNext()) {
            assertTrue(testing.hasNext());
            assertEquals(expected.next(),testing.next());
        }
        assertTrue(!testing.hasNext());
    }

    @Test
    public void testEmptyList() {
        Iterator<Integer> testing = new FilteredIterator<Integer>(new ArrayList<Integer>().iterator(),isEven);
        assertTrue(!testing.hasNext());
    }

    @Test
    public void testNonePass() {
        Iterator<Integer> testing = new FilteredIterator<Integer>(new ArrayList<Integer>().iterator(),Constant.falsePredicate());
        assertTrue(!testing.hasNext());
    }

    @Test
    public void testNextWithoutHasNext() {
        Iterator<Integer> testing = new FilteredIterator<Integer>(list.iterator(),isEven);
        Iterator<Integer> expected = evens.iterator();
        while(expected.hasNext()) {
            assertEquals(expected.next(),testing.next());
        }
        assertTrue(!(testing.hasNext()));
    }

    @Test
    public void testNextAfterEndOfList() {
        Iterator<Integer> testing = new FilteredIterator<Integer>(list.iterator(),isEven);
        Iterator<Integer> expected = evens.iterator();
        while(expected.hasNext()) {
            assertEquals(expected.next(),testing.next());
        }
        try {
            testing.next();
            fail("ExpectedNoSuchElementException");
        } catch(NoSuchElementException e) {
            // expected
        }
    }

    @Test
    public void testNextOnEmptyList() {
        Iterator<Integer> testing = new FilteredIterator<Integer>(new ArrayList<Integer>().iterator(),isEven);
        try {
            testing.next();
            fail("ExpectedNoSuchElementException");
        } catch(NoSuchElementException e) {
            // expected
        }
    }

    @Test
    public void testRemoveBeforeNext() {
        Iterator<Integer> testing = new FilteredIterator<Integer>(list.iterator(),isEven);
        try {
            testing.remove();
            fail("IllegalStateException");
        } catch(IllegalStateException e) {
            // expected
        }
    }

    @Test
    public void testRemoveAfterNext() {
        Iterator<Integer> testing = new FilteredIterator<Integer>(list.iterator(),isEven);
        testing.next();
        testing.remove();
        try {
            testing.remove();
            fail("IllegalStateException");
        } catch(IllegalStateException e) {
            // expected
        }
    }

    @Test
    public void testRemoveSome() {
        Iterator<Integer> testing = new FilteredIterator<Integer>(list.iterator(),isEven);
        while(testing.hasNext()) {
            testing.next();
            testing.remove();
        }
        for (Iterator<Integer> iter = list.iterator(); iter.hasNext();) {
            assertTrue(! isEven.test(iter.next()) );
        }
    }

    @Test
    public void testRemoveAll() {
        Iterator<Integer> testing = new FilteredIterator<Integer>(list.iterator(),Constant.truePredicate());
        while(testing.hasNext()) {
            testing.next();
            testing.remove();
        }
        assertTrue(list.isEmpty());
    }

    @Test
    public void testRemoveWithoutHasNext() {
        Iterator<Integer> testing = new FilteredIterator<Integer>(list.iterator(),Constant.truePredicate());
        for (int i=0,m = list.size();i<m;i++) {
            testing.next();
            testing.remove();
        }
        assertTrue(list.isEmpty());
    }

    @Test
    public void testFilterWithNullIteratorReturnsNull() {
        assertNull(FilteredIterator.filter(null,Constant.truePredicate()));
    }

    @Test
    public void testFilterWithNullPredicateReturnsIdentity() {
        Iterator<Integer> iter = list.iterator();
        assertSame(iter,FilteredIterator.filter(iter,null));
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorProhibitsNull() {
        new FilteredIterator<Integer>(null,null);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorProhibitsNull2() {
        new FilteredIterator<Integer>(null,Constant.truePredicate());
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorProhibitsNull3() {
        new FilteredIterator<Integer>(list.iterator(),null);
    }

    // Attributes
    // ------------------------------------------------------------------------
    private List<Integer> list = null;
    private List<Integer> evens = null;
    private UnaryPredicate<Integer> isEven = new UnaryPredicate<Integer>() {
        public boolean test(Integer obj) {
            return obj.intValue() % 2 == 0;
        }
    };

}
