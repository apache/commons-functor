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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.commons.functor.BaseFunctorTest;
import org.apache.commons.functor.Predicate;
import org.apache.commons.functor.core.Constant;
import org.apache.commons.functor.core.IsEqual;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @version $Revision$ $Date$
 */
@SuppressWarnings("unchecked")
public class TestFilteredIterable extends BaseFunctorTest {

    // Attributes
    // ------------------------------------------------------------------------
    private List<Integer> list = null;
    private List<Integer> evens = null;

    private Predicate<Integer> isEven = new Predicate<Integer>() {
        public boolean test(Integer obj) {
            return obj != null && obj % 2 == 0;
        }
    };

    @Override
    public Object makeFunctor() {
        List<String> list = new ArrayList<String>();
        list.add("xyzzy");
        return FilteredIterable.of(list);
    }

    // Lifecycle
    // ------------------------------------------------------------------------

    @Before
    public void setUp() throws Exception {
        list = new ArrayList<Integer>();
        evens = new ArrayList<Integer>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
            if (i % 2 == 0) {
                evens.add(i);
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

        FilteredIterable<Integer> filter = FilteredIterable.of(list);

        for (Integer i : filter.retain(isEven)) {
            assertTrue(expected.hasNext());
            assertEquals(expected.next(), i);
        }
        assertFalse(expected.hasNext());
        assertEquals(filter,filter.retain(isEven));
    }

    @Test
    public void testAllPass() {
        Iterator<Integer> expected = evens.iterator();

        for (Integer i : FilteredIterable.of(evens)) {
            assertTrue(expected.hasNext());
            assertEquals(expected.next(), i);
        }
        assertFalse(expected.hasNext());
    }

    @Test
    public void testAllPass2() {
        Iterator<Integer> expected = list.iterator();
        for (Integer i : FilteredIterable.of(list)) {
            assertTrue(expected.hasNext());
            assertEquals(expected.next(), i);
        }
        assertFalse(expected.hasNext());
    }

    @Test
    public void testEmptyFilteredIterable() {
        FilteredIterable<Integer> emptyFilter = FilteredIterable.empty();
        assertFalse(emptyFilter.iterator().hasNext());
        assertEquals(emptyFilter,emptyFilter.retain(isEven));
        assertEquals(emptyFilter,emptyFilter.retain(Integer.class));
        assertEquals(emptyFilter,emptyFilter.retain(Integer.class,Number.class));
    }

    @Test
    public void testEmptyList() {
        assertFalse(FilteredIterable.of(Collections.EMPTY_LIST).iterator().hasNext());
    }

    @Test
    public void testNonePass() {
        assertFalse(FilteredIterable.of(list).retain(Constant.falsePredicate()).iterator().hasNext());
    }

    @Test
    public void testNextWithoutHasNext() {
        Iterator<Integer> testing = FilteredIterable.of(list).retain(isEven).iterator();
        Iterator<Integer> expected = evens.iterator();
        while (expected.hasNext()) {
            assertEquals(expected.next(), testing.next());
        }
        assertFalse(testing.hasNext());
    }

    @Test(expected=NoSuchElementException.class)
    public void testNextAfterEndOfList() {
        Iterator<Integer> testing = FilteredIterable.of(list).retain(isEven).iterator();
        Iterator<Integer> expected = evens.iterator();
        while (expected.hasNext()) {
            assertEquals(expected.next(), testing.next());
        }
        testing.next();
    }

    @Test(expected=NoSuchElementException.class)
    public void testNextOnEmptyList() {
        FilteredIterable.empty().iterator().next();
    }

    @Test(expected=IllegalStateException.class)
    public void testRemoveBeforeNext() {
        Iterator<Integer> testing = FilteredIterable.of(list).retain(isEven).iterator();
        testing.remove();
    }

    @Test(expected=IllegalStateException.class)
    public void testRemoveAfterNext() {
        Iterator<Integer> testing = FilteredIterable.of(list).retain(isEven).iterator();
        testing.next();
        testing.remove();
        testing.remove();
    }

    @Test
    public void testRemoveSome() {
        Iterator<Integer> testing = FilteredIterable.of(list).retain(isEven).iterator();
        while (testing.hasNext()) {
            testing.next();
            testing.remove();
        }
        assertTrue(Collections.disjoint(list, evens));
    }

    @Test
    public void testRemoveAll() {
        Iterator<Integer> testing = FilteredIterable.of(list).iterator();
        while (testing.hasNext()) {
            testing.next();
            testing.remove();
        }
        assertTrue(list.isEmpty());
    }

    @Test
    public void testRemoveWithoutHasNext() {
        Iterator<Integer> testing = FilteredIterable.of(list).iterator();
        for (int i = 0, m = list.size(); i < m; i++) {
            testing.next();
            testing.remove();
        }
        assertTrue(list.isEmpty());
    }

    @Test
    public void testFilterWithNullIteratorReturnsNull() {
        assertNull(FilteredIterable.of(null));
    }

    @Test
    public void testFilterWithFilteredIterableReturnsItself() {
        FilteredIterable<Integer> filter = FilteredIterable.of(list);
        assertEquals(filter,FilteredIterable.of(filter));
    }

    @Test
    public void testRetainOneType() {
        Iterable<Object> objects = Arrays.asList((Object) "foo", "bar", "baz", 2L, BigInteger.ZERO);
        Iterable<String> strings = FilteredIterable.of(objects).retain(String.class);
        for (String s : strings) {
            assertTrue(s instanceof String);
        }
    }

    @Test
    public void testRetainOneType2() {
        Iterable<Object> objects = Arrays.asList((Object) "foo", "bar", "baz", 2L, BigInteger.ZERO);
        Iterator<Number> iterator = FilteredIterable.of(objects).retain(Number.class).iterator();
        assertEquals(2L, iterator.next());
        assertEquals(BigInteger.ZERO, iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testRetainMultipleTypes() {
        Iterable<Object> objects = Arrays.asList((Object) "foo", "bar", "baz", 2L, BigInteger.ZERO);
        Iterator<Object> iterator = FilteredIterable.of(objects).retain(Long.class, BigInteger.class).iterator();
        assertEquals(2L, iterator.next());
        assertEquals(BigInteger.ZERO, iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testMultipleLevels() {
        Iterable<Object> objects = Arrays.asList((Object) "foo", "bar", "baz", 2L, BigInteger.ZERO);
        Iterator<String> iterator = FilteredIterable.of(objects).retain(String.class).retain(IsEqual.to("foo"))
                .iterator();
        assertEquals("foo", iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testMultipleLevels2() {
        Iterable<Object> objects = Arrays.asList((Object) "foo", "bar", "baz", 2L, BigInteger.ZERO);
        Iterator<Long> iterator = FilteredIterable.of(objects).retain(Number.class).retain(Long.class).iterator();
        assertEquals(2L, iterator.next().longValue());
        assertFalse(iterator.hasNext());
    }

    @Test(expected=NullPointerException.class)
    public void testRetainNullType() {
        FilteredIterable.of(Collections.singleton("foo")).retain((Class<?>) null);
    }

    @Test(expected=NullPointerException.class)
    public void testRetainNullTypes() {
        FilteredIterable.of(Collections.singleton("foo")).retain((Class<?>[]) null);
    }

    @Test(expected=NullPointerException.class)
    public void testRetainNullPredicate() {
        FilteredIterable.of(Collections.singleton("foo")).retain((Predicate<String>) null);
    }
}
