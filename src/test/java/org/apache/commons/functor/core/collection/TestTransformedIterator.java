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
import org.apache.commons.functor.UnaryFunction;
import org.apache.commons.functor.core.Identity;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @version $Revision$ $Date$
 * @author Rodney Waldhoff
 */
@SuppressWarnings("unchecked")
public class TestTransformedIterator extends BaseFunctorTest {

    public Object makeFunctor() {
        List list = new ArrayList();
        list.add("xyzzy");
        return TransformedIterator.transform(list.iterator(),Identity.instance());
    }

    // Lifecycle
    // ------------------------------------------------------------------------

    @Before
    public void setUp() throws Exception {
        list = new ArrayList();
        negatives = new ArrayList();
        for (int i=0;i<10;i++) {
            list.add(new Integer(i));
            negatives.add(new Integer(i*-1));
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
        Iterator expected = negatives.iterator();
        Iterator testing = new TransformedIterator(list.iterator(),negate);
        while(expected.hasNext()) {
            assertTrue(testing.hasNext());
            assertEquals(expected.next(),testing.next());
        }
        assertTrue(!testing.hasNext());
    }

    @Test
    public void testEmptyList() {
        Iterator testing = new TransformedIterator(Collections.EMPTY_LIST.iterator(),negate);
        assertTrue(!testing.hasNext());
    }

    @Test
    public void testNextWithoutHasNext() {
        Iterator testing = new TransformedIterator(list.iterator(),negate);
        Iterator expected = negatives.iterator();
        while(expected.hasNext()) {
            assertEquals(expected.next(),testing.next());
        }
        assertTrue(!(testing.hasNext()));
    }

    @Test
    public void testNextAfterEndOfList() {
        Iterator testing = new TransformedIterator(list.iterator(),negate);
        Iterator expected = negatives.iterator();
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
        Iterator testing = new TransformedIterator(Collections.EMPTY_LIST.iterator(),negate);
        try {
            testing.next();
            fail("ExpectedNoSuchElementException");
        } catch(NoSuchElementException e) {
            // expected
        }
    }

    @Test
    public void testRemoveBeforeNext() {
        Iterator testing = new TransformedIterator(list.iterator(),negate);
        try {
            testing.remove();
            fail("IllegalStateException");
        } catch(IllegalStateException e) {
            // expected
        }
    }

    @Test
    public void testRemoveAfterNext() {
        Iterator testing = new TransformedIterator(list.iterator(),negate);
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
    public void testRemoveAll() {
        Iterator testing = new TransformedIterator(list.iterator(),negate);
        while(testing.hasNext()) {
            testing.next();
            testing.remove();
        }
        assertTrue(list.isEmpty());
    }

    @Test
    public void testRemoveWithoutHasNext() {
        Iterator testing = new TransformedIterator(list.iterator(),negate);
        for (int i=0,m = list.size();i<m;i++) {
            testing.next();
            testing.remove();
        }
        assertTrue(list.isEmpty());
    }

    @Test
    public void testTransformWithNullIteratorReturnsNull() {
        assertNull(TransformedIterator.transform(null,negate));
    }

    @Test
    public void testTransformWithNullPredicateReturnsIdentity() {
        Iterator iter = list.iterator();
        assertSame(iter,TransformedIterator.maybeTransform(iter,null));
    }

    @Test
    public void testConstructorProhibitsNull() {
        try {
            new TransformedIterator(null,null);
            fail("ExpectedNullPointerException");
        } catch(IllegalArgumentException e) {
            // expected
        }
        try {
            new TransformedIterator(null,negate);
            fail("ExpectedNullPointerException");
        } catch(IllegalArgumentException e) {
            // expected
        }
        try {
            new TransformedIterator(list.iterator(),null);
            fail("ExpectedNullPointerException");
        } catch(IllegalArgumentException e) {
            // expected
        }
    }


    // Attributes
    // ------------------------------------------------------------------------
    private List list = null;
    private List negatives = null;
    private UnaryFunction negate = new UnaryFunction() {
        public Object evaluate(Object obj) {
            return new Integer(((Number) obj).intValue() * -1);
        }
    };

}
