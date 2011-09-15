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
import java.util.Collections;
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
 * @author Rodney Waldhoff
 */
@SuppressWarnings("unchecked")
public class TestFilteredIterator extends BaseFunctorTest {

    public Object makeFunctor() {
        List list = new ArrayList();
        list.add("xyzzy");
        return FilteredIterator.filter(list.iterator(),Constant.truePredicate());
    }

    @Before
    public void setUp() throws Exception {
        list = new ArrayList();
        evens = new ArrayList();
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
        Iterator expected = evens.iterator();
        Iterator testing = new FilteredIterator(list.iterator(),isEven);
        while(expected.hasNext()) {
            assertTrue(testing.hasNext());
            assertEquals(expected.next(),testing.next());
        }
        assertTrue(!testing.hasNext());
    }

    @Test
    public void testAllPass() {
        Iterator expected = evens.iterator();
        Iterator testing = new FilteredIterator(evens.iterator(),isEven);
        while(expected.hasNext()) {
            assertTrue(testing.hasNext());
            assertEquals(expected.next(),testing.next());
        }
        assertTrue(!testing.hasNext());
    }

    @Test
    public void testAllPass2() {
        Iterator expected = list.iterator();
        Iterator testing = new FilteredIterator(list.iterator(),Constant.truePredicate());
        while(expected.hasNext()) {
            assertTrue(testing.hasNext());
            assertEquals(expected.next(),testing.next());
        }
        assertTrue(!testing.hasNext());
    }

    @Test
    public void testEmptyList() {
        Iterator testing = new FilteredIterator(Collections.EMPTY_LIST.iterator(),isEven);
        assertTrue(!testing.hasNext());
    }

    @Test
    public void testNonePass() {
        Iterator testing = new FilteredIterator(Collections.EMPTY_LIST.iterator(),Constant.falsePredicate());
        assertTrue(!testing.hasNext());
    }

    @Test
    public void testNextWithoutHasNext() {
        Iterator testing = new FilteredIterator(list.iterator(),isEven);
        Iterator expected = evens.iterator();
        while(expected.hasNext()) {
            assertEquals(expected.next(),testing.next());
        }
        assertTrue(!(testing.hasNext()));
    }

    @Test
    public void testNextAfterEndOfList() {
        Iterator testing = new FilteredIterator(list.iterator(),isEven);
        Iterator expected = evens.iterator();
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
        Iterator testing = new FilteredIterator(Collections.EMPTY_LIST.iterator(),isEven);
        try {
            testing.next();
            fail("ExpectedNoSuchElementException");
        } catch(NoSuchElementException e) {
            // expected
        }
    }

    @Test
    public void testRemoveBeforeNext() {
        Iterator testing = new FilteredIterator(list.iterator(),isEven);
        try {
            testing.remove();
            fail("IllegalStateException");
        } catch(IllegalStateException e) {
            // expected
        }
    }

    @Test
    public void testRemoveAfterNext() {
        Iterator testing = new FilteredIterator(list.iterator(),isEven);
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
        Iterator testing = new FilteredIterator(list.iterator(),isEven);
        while(testing.hasNext()) {
            testing.next();
            testing.remove();
        }
        for (Iterator iter = list.iterator(); iter.hasNext();) {
            assertTrue(! isEven.test(iter.next()) );
        }
    }

    @Test
    public void testRemoveAll() {
        Iterator testing = new FilteredIterator(list.iterator(),Constant.truePredicate());
        while(testing.hasNext()) {
            testing.next();
            testing.remove();
        }
        assertTrue(list.isEmpty());
    }

    @Test
    public void testRemoveWithoutHasNext() {
        Iterator testing = new FilteredIterator(list.iterator(),Constant.truePredicate());
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
        Iterator iter = list.iterator();
        assertSame(iter,FilteredIterator.filter(iter,null));
    }

    @Test
    public void testConstructorProhibitsNull() {
        try {
            new FilteredIterator(null,null);
            fail("ExpectedNullPointerException");
        } catch(IllegalArgumentException e) {
            // expected
        }
        try {
            new FilteredIterator(null,Constant.truePredicate());
            fail("ExpectedNullPointerException");
        } catch(IllegalArgumentException e) {
            // expected
        }
        try {
            new FilteredIterator(list.iterator(),null);
            fail("ExpectedNullPointerException");
        } catch(IllegalArgumentException e) {
            // expected
        }
    }


    // Attributes
    // ------------------------------------------------------------------------
    private List list = null;
    private List evens = null;
    private UnaryPredicate isEven = new UnaryPredicate() {
        public boolean test(Object obj) {
            return ((Number) obj).intValue() % 2 == 0;
        }
    };

}
