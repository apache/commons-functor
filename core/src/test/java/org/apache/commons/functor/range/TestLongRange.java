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
package org.apache.commons.functor.range;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.functor.BaseFunctorTest;
import org.apache.commons.functor.Function;
import org.apache.commons.functor.generator.Generator;
import org.apache.commons.functor.generator.loop.IteratorToGeneratorAdapter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @version $Revision: 1515264 $ $Date: 2013-08-18 23:51:41 -0300 (Sun, 18 Aug 2013) $
 */
public class TestLongRange extends BaseFunctorTest {

    // A base range with all longs between -6 and 6
    private final List<Long> fullRange = Collections.unmodifiableList(Arrays
        .asList(-6L, -5L, -4L, -3L, -2L, -1L, 0L, 1L, 2L, 3L, 4L, 5L, 6L));

    // Attributes
    // ------------------------------------------------------------------------
    private LongRange ascLongRange = null;
    private LongRange descLongRange = null;
    private Collection<Long> expectedAsc = null;
    private Collection<Long> expectedDesc = null;
    
    // Test set up
    // ------------------------------------------------------------------------
    @Before
    public void setUp() {
        ascLongRange = Ranges.longRange(0L, 10L);
        descLongRange = Ranges.longRange(10L, 0L);
        expectedAsc = Arrays.asList(0L, 1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L);
        expectedDesc = Arrays.asList(10L, 9L, 8L, 7L, 6L, 5L, 4L, 3L, 2L, 1L);
    }

    @After
    public void tearDown() {
        ascLongRange = null;
        descLongRange = null;
    }

    @Override
    protected Object makeFunctor()
        throws Exception {
        return Ranges.longRange(10, 20);
    }

    // Generator tests
    // ------------------------------------------------------------------------

    @Test
    public void testGenerateListExample() {
        // generates a collection of Integers from 0 (inclusive) to 10 (exclusive)
        {
            LongRange range = Ranges.longRange(0, 10);
            Iterator<Long> iter = range.iterator();
            for (int i=0;i<10;i++) {
                assertEquals(Long.valueOf(i), iter.next());
            }
        }

        // generates a collection of Integers from 10 (inclusive) to 0 (exclusive)
        {
            LongRange range = Ranges.longRange(10, 0);
            Iterator<Long> iter = range.iterator();
            for (int i=10;i>0;i--) {
                assertEquals(Long.valueOf(i), iter.next());
            }
        }
    }

    @Test
    public void testStepChecking() {
        {
            Ranges.longRange(2, 2, 0); // step of 0 is ok when range is empty
        }
        {
            Ranges.longRange(2, 2, 1); // positive step is ok when range is empty
        }
        {
            Ranges.longRange(2, 2, -1); // negative step is ok when range is empty
        }
        {
            Ranges.longRange(0, 1, 10); // big steps are ok
        }
        {
            Ranges.longRange(1, 0, -10); // big steps are ok
        }
        try {
            Ranges.longRange(0, 1, 0);
            fail("Expected IllegalArgumentException");
        } catch(IllegalArgumentException e) {
            // expected
        }
        try {
            Ranges.longRange(0, 1, -1);
            fail("Expected IllegalArgumentException");
        } catch(IllegalArgumentException e) {
            // expected
        }
        try {
            Ranges.longRange(0, -1, 1);
            fail("Expected IllegalArgumentException");
        } catch(IllegalArgumentException e) {
            // expected
        }
    }

    @Test
    public void testObjectConstructor() {
        LongRange range = Ranges.longRange(Long.valueOf(0), Long.valueOf(5));
        assertEquals("[0, 1, 2, 3, 4]", IteratorToGeneratorAdapter.adapt(range).toCollection().toString());
        range = Ranges.longRange(Integer.valueOf(0), Long.valueOf(5), Long.valueOf(1));
        assertEquals("[0, 1, 2, 3, 4]", IteratorToGeneratorAdapter.adapt(range).toCollection().toString());
    }


    @Test
    public void testReverseStep() {
        LongRange range = Ranges.longRange(10, 0, -2);
        assertEquals("[10, 8, 6, 4, 2]", IteratorToGeneratorAdapter.adapt(range).toCollection().toString());
    }

    @Test
    public void testStep() {
        LongRange range = Ranges.longRange(0, 10, 2);
        assertEquals("[0, 2, 4, 6, 8]", IteratorToGeneratorAdapter.adapt(range).toCollection().toString());
    }

    @Test
    public void testForwardRange() {
        LongRange range = Ranges.longRange(0, 5);
        assertEquals("[0, 1, 2, 3, 4]", IteratorToGeneratorAdapter.adapt(range).toCollection().toString());
    }

    @Test
    public void testReverseRange() {
        LongRange range = Ranges.longRange(5, 0);
        assertEquals("[5, 4, 3, 2, 1]", IteratorToGeneratorAdapter.adapt(range).toCollection().toString());
    }

    @Test
    public void testEdgeCase() {
        LongRange range = Ranges.longRange(Long.MAX_VALUE - 3L, Long.MAX_VALUE);
        assertEquals("[9223372036854775804, 9223372036854775805, 9223372036854775806]", IteratorToGeneratorAdapter.adapt(range).toCollection().toString());
    }
    
    @Test
    public void testClosedClosedAscending() {
        // [-5L, 5L], 3L = -5L, -2L, 1L, 4L
        LongRange range = Ranges.longRange(-5L, BoundType.CLOSED, 5L,
                                        BoundType.CLOSED, 3L);
        // [-5L, 5L], 3L = -5L, -2L, 1L, 4L
        List<Long> expected = Arrays.asList(-5L, -2L, 1L, 4L);
        Collection<Long> elements = IteratorToGeneratorAdapter.adapt(range).toCollection();
        assertEquals(expected, elements);
    }

    @Test
    public void testOpenClosedAscending() {
        // (-5L, 5L], 3L = -2L, 1L, 4L
        LongRange range = Ranges.longRange(-5L, BoundType.OPEN, 5L,
                                        BoundType.CLOSED, 3L);
        // (-5L, 5L], 3L = -2L, 1L, 4L
        List<Long> expected = Arrays.asList(-2L, 1L, 4L);
        Collection<Long> elements = IteratorToGeneratorAdapter.adapt(range).toCollection();
        assertEquals(expected, elements);
    }

    @Test
    public void testClosedOpenAscending() {
        // [-5L, 5L), 3L = -5L, -2L, 1L, 4L
        LongRange range = Ranges.longRange(-5L, BoundType.CLOSED, 5L,
                                        BoundType.OPEN, 3L);
        // (-5L, 5L], 3L = -5L, -2L, 1L, 4L
        List<Long> expected = Arrays.asList(-5L, -2L, 1L, 4L);
        Collection<Long> elements = IteratorToGeneratorAdapter.adapt(range).toCollection();
        assertEquals(expected, elements);
    }

    @Test
    public void testOpenOpenAscending() {
        // (-5L, 5L), 3L = -2L, 1L, 4L
        LongRange range = Ranges.longRange(-5L, BoundType.OPEN, 5L,
                                        BoundType.OPEN, 3L);
        // (-5L, 5L), 3L = -2L, 1L, 4L
        List<Long> expected = Arrays.asList(-2L, 1L, 4L);
        Collection<Long> elements = IteratorToGeneratorAdapter.adapt(range).toCollection();
        assertEquals(expected, elements);
    }

    @Test
    public void testSingleStepAscending() {
        // (-2L, 2L], 1L = -1L, 0L, 1L, 2L
        LongRange range = Ranges.longRange(-2L, BoundType.OPEN, 2L,
                                        BoundType.CLOSED, 1L);
        // (-2L, 2L], 1L = -1L, 0L, 1L, 2L
        List<Long> expected = Arrays.asList(-1L, 0L, 1L, 2L);
        Collection<Long> elements = IteratorToGeneratorAdapter.adapt(range).toCollection();
        assertEquals(expected, elements);
    }

    @Test
    public void testClosedClosedDescending() {
        // [5L, -5L], -3L = 5L, 2L, -1L, -4L
        LongRange range = Ranges.longRange(5L, BoundType.CLOSED, -5L,
                                        BoundType.CLOSED, -3L);
        // [5L, -5L], -3L = 5L, 2L, -1L, -4L
        List<Long> expected = Arrays.asList(5L, 2L, -1L, -4L);
        Collection<Long> elements = IteratorToGeneratorAdapter.adapt(range).toCollection();
        assertEquals(expected, elements);
    }

    @Test
    public void testOpenClosedDescending() {
        // (5L, -5L], -3L = 2L, -1L, -4L
        LongRange range = Ranges.longRange(5L, BoundType.OPEN, -5L,
                                        BoundType.CLOSED, -3L);
        // (5L, -5L], -3L = 2L, -1L, -4L
        List<Long> expected = Arrays.asList(2L, -1L, -4L);
        Collection<Long> elements = IteratorToGeneratorAdapter.adapt(range).toCollection();
        assertEquals(expected, elements);
    }

    @Test
    public void testClosedOpenDescending() {
        // [5L, -5L), -3L = 5L, 2L, -1L, -4L
        LongRange range = Ranges.longRange(5L, BoundType.CLOSED, -5L,
                                        BoundType.OPEN, -3L);
        // [5L, -5L), -3L = 5L, 2L, -1L, -4L
        List<Long> expected = Arrays.asList(5L, 2L, -1L, -4L);
        Collection<Long> elements = IteratorToGeneratorAdapter.adapt(range).toCollection();
        assertEquals(expected, elements);
    }

    @Test
    public void testOpenOpenDescending() {
        // (5L, -5L), -3L = 2L, -1L, -4L
        LongRange range = Ranges.longRange(5L, BoundType.OPEN, -5L,
                                        BoundType.OPEN, -3L);
        // (5L, -5L), -3L = 2L, -1L, -4L
        List<Long> expected = Arrays.asList(2L, -1L, -4L);
        Collection<Long> elements = IteratorToGeneratorAdapter.adapt(range).toCollection();
        assertEquals(expected, elements);
    }

    @Test
    public void testSingleStepDescending() {
        // [2L, -2L), -1L = 2L, 1L, 0L, -1L
        LongRange range = Ranges.longRange(2L, BoundType.CLOSED, -2L,
                                        BoundType.OPEN, -1L);
        // [2L, -2L), -1L = 2L, 1L, 0L, -1L
        List<Long> expected = Arrays.asList(2L, 1L, 0L, -1L);
        Collection<Long> elements = IteratorToGeneratorAdapter.adapt(range).toCollection();
        assertEquals(expected, elements);
    }

    @Test
    public void testAscending() {
        final List<Long> list = new ArrayList<Long>();
        for (Long l : ascLongRange) {
            list.add(l);
        }
        assertTrue(expectedAsc.containsAll(list));
    }

    @Test
    public void testDescending() {
        final List<Long> list = new ArrayList<Long>();
        for (Long l : descLongRange) {
            list.add(l);
        }
        assertTrue(expectedDesc.containsAll(list));
    }

    @Test
    public void testToCollection() {
        Collection<Long> ascCol = IteratorToGeneratorAdapter.adapt(ascLongRange).toCollection();
        assertEquals("Different collections", expectedAsc, ascCol);
        Collection<Long> descCol = IteratorToGeneratorAdapter.adapt(descLongRange).toCollection();
        assertEquals("Different collections", expectedDesc, descCol);
    }

    @Test
    public void testTransformedGenerator() {
        long expected = 45L;
        long total = IteratorToGeneratorAdapter.adapt(ascLongRange)
            .to(new Function<Generator<? extends Long>, Long>() {

                public Long evaluate(Generator<? extends Long> obj) {
                    long total = 0L;
                    for (Object element : obj.toCollection()) {
                        total += (Long) element;
                    }
                    return total;
                }
            });
        assertEquals(expected, total);
        expected = 55L;
        total = IteratorToGeneratorAdapter.adapt(descLongRange)
            .to(new Function<Generator<? extends Long>, Long>() {

                public Long evaluate(Generator<? extends Long> obj) {
                    long total = 0L;
                    for (Object element : obj.toCollection()) {
                        total += (Long) element;
                    }
                    return total;
                }
            });
        assertEquals(expected, total);
    }

    // Range tests
    // ------------------------------------------------------------------------

    @Test
    public void testEmptyRanges() {
        LongRange empty1 = Ranges.longRange(-2, BoundType.OPEN, -1,
                                         BoundType.OPEN, 2);
        assertTrue("The range was expected to be empty.", empty1.isEmpty());
        LongRange empty2 = Ranges.longRange(2, BoundType.OPEN, 0, BoundType.OPEN,
                                         -2);
        assertTrue("The range was expected to be empty.", empty2.isEmpty());
        LongRange empty3 = Ranges.longRange(0, BoundType.OPEN, 1,
                                         BoundType.CLOSED, 2);
        assertTrue("The range was expected to be empty.", empty3.isEmpty());
        LongRange empty4 = Ranges.longRange(-3, BoundType.OPEN, -3,
                                         BoundType.OPEN, 1);
        assertTrue("The range was expected to be empty.", empty4.isEmpty());
        LongRange empty5 = Ranges.longRange(-3, BoundType.CLOSED, -3,
                                         BoundType.OPEN, 1);
        assertTrue("The range was expected to be empty.", empty5.isEmpty());
        LongRange empty6 = Ranges.longRange(1, BoundType.OPEN, 0,
                                         BoundType.CLOSED, -2);
        assertTrue("The range was expected to be empty.", empty6.isEmpty());
        LongRange notEmpty1 = Ranges.longRange(-3, BoundType.CLOSED, -3,
                                            BoundType.CLOSED, 1);
        assertFalse("The range was not expected to be empty.",
                    notEmpty1.isEmpty());
        LongRange notEmpty2 = Ranges.longRange(-3, BoundType.OPEN, -2,
                                            BoundType.CLOSED, 1);
        assertFalse("The range was not expected to be empty.",
                    notEmpty2.isEmpty());
        LongRange notEmpty3 = Ranges.longRange(2, BoundType.OPEN, 1,
                                            BoundType.CLOSED, -1);
        assertFalse("The range was not expected to be empty.",
                    notEmpty3.isEmpty());
        LongRange notEmpty4 = Ranges.longRange(2, BoundType.CLOSED, 1,
                                            BoundType.OPEN, -1);
        assertFalse("The range was not expected to be empty.",
                    notEmpty4.isEmpty());
        LongRange notEmpty5 = Ranges.longRange(1, BoundType.CLOSED, 2,
                                            BoundType.OPEN, 1);
        assertFalse("The range was not expected to be empty.",
                    notEmpty5.isEmpty());
    }

    @Test
    public void testBoundaries() {
        LongRange range = Ranges.longRange(0L, 10L);
        assertEquals(new Endpoint<Comparable<?>>(0L, BoundType.CLOSED),
                     range.getLeftEndpoint());
        assertEquals(new Endpoint<Comparable<?>>(10L, BoundType.OPEN),
                     range.getRightEndpoint());
    }

    @Test
    public void testClosedClosedAscendingContains() {
        // [-5, 5], 3 = -5, -2, 1, 4
        LongRange range = Ranges.longRange(-5, BoundType.CLOSED, 5,
                                        BoundType.CLOSED, 3);
        // [-5, 5], 3 = -5, -2, 1, 4
        List<Long> arr = Arrays.asList(-5L, -2L, 1L, 4L);
        for (Long element : arr) {
            assertTrue("Expected element [" + element +
                               "] is missing in range [" + range + "]",
                       range.contains(element));
        }
        List<Long> elementsNotPresent = new ArrayList<Long>(fullRange);
        elementsNotPresent.removeAll(arr);
        for (Long element : elementsNotPresent) {
            assertFalse("Unexpected element [" + element +
                                "] is present in range [" + range + "]",
                        range.contains(element));
        }
    }

    @Test
    public void testOpenClosedAscendingContains() {
        // (-5, 5], 3 = -2, 1, 4
        LongRange range = Ranges.longRange(-5, BoundType.OPEN, 5,
                                        BoundType.CLOSED, 3);
        // (-5, 5], 3 = -2, 1, 4
        List<Long> arr = Arrays.asList(-2L, 1L, 4L);
        for (Long element : arr) {
            assertTrue("Expected element [" + element +
                               "] is missing in range [" + range + "]",
                       range.contains(element));
        }
        List<Long> elementsNotPresent = new ArrayList<Long>(fullRange);
        elementsNotPresent.removeAll(arr);
        for (Long element : elementsNotPresent) {
            assertFalse("Unexpected element [" + element +
                                "] is present in range [" + range + "]",
                        range.contains(element));
        }
    }

    @Test
    public void testClosedOpenAscendingContains() {
        // [-5, 5), 3 = -5, -2, 1, 4
        LongRange range = Ranges.longRange(-5, BoundType.CLOSED, 5,
                                        BoundType.OPEN, 3);
        // (-5, 5], 3 = -5, -2, 1, 4
        List<Long> arr = Arrays.asList(-5L, -2L, 1L, 4L);
        for (Long element : arr) {
            assertTrue("Expected element [" + element +
                               "] is missing in range [" + range + "]",
                       range.contains(element));
        }
        List<Long> elementsNotPresent = new ArrayList<Long>(fullRange);
        elementsNotPresent.removeAll(arr);
        for (Long element : elementsNotPresent) {
            assertFalse("Unexpected element [" + element +
                                "] is present in range [" + range + "]",
                        range.contains(element));
        }
    }

    @Test
    public void testOpenOpenAscendingContains() {
        // (-5, 5), 3 = -2, 1, 4
        LongRange range = Ranges.longRange(-5, BoundType.OPEN, 5, BoundType.OPEN,
                                        3);
        // (-5, 5), 3 = -2, 1, 4
        List<Long> arr = Arrays.asList(-2L, 1L, 4L);
        for (Long element : arr) {
            assertTrue("Expected element [" + element +
                               "] is missing in range [" + range + "]",
                       range.contains(element));
        }
        List<Long> elementsNotPresent = new ArrayList<Long>(fullRange);
        elementsNotPresent.removeAll(arr);
        for (Long element : elementsNotPresent) {
            assertFalse("Unexpected element [" + element +
                                "] is present in range [" + range + "]",
                        range.contains(element));
        }
    }

    @Test
    public void testContainsSingleStepAscending() {
        // (-2, 2], 1 = -1, 0, 1, 2
        LongRange ascendingRange = Ranges.longRange(-2, BoundType.OPEN, 2,
                                                 BoundType.CLOSED, 1);
        // (-2, 2], 1 = -1, 0, 1, 2
        List<Long> arr = Arrays.asList(-1L, 0L, 1L, 2L);
        for (Long element : arr) {
            assertTrue("Expected element [" + element +
                               "] is missing in range [" + ascendingRange + "]",
                       ascendingRange.contains(element));
        }
        List<Long> elementsNotPresent = new ArrayList<Long>(fullRange);
        elementsNotPresent.removeAll(arr);
        for (Long element : elementsNotPresent) {
            assertFalse("Unexpected element [" + element +
                                "] is present in range [" + ascendingRange +
                                "]",
                        ascendingRange.contains(element));
        }
    }

    @Test
    public void testClosedClosedDescendingContains() {
        // [5, -5], -3 = 5, 2, -1, -4
        LongRange range = Ranges.longRange(5, BoundType.CLOSED, -5,
                                        BoundType.CLOSED, -3);
        // [5, -5], -3 = 5, 2, -1, -4
        List<Long> arr = Arrays.asList(5L, 2L, -1L, -4L);
        for (Long element : arr) {
            assertTrue("Expected element [" + element +
                               "] is missing in range [" + range + "]",
                       range.contains(element));
        }
        List<Long> elementsNotPresent = new ArrayList<Long>(fullRange);
        elementsNotPresent.removeAll(arr);
        for (Long element : elementsNotPresent) {
            assertFalse("Unexpected element [" + element +
                                "] is present in range [" + range + "]",
                        range.contains(element));
        }
    }

    @Test
    public void testOpenClosedDescendingContains() {
        // (5, -5], -3 = 2, -1, -4
        LongRange range = Ranges.longRange(5, BoundType.OPEN, -5,
                                        BoundType.CLOSED, -3);
        // (5, -5], -3 = 2, -1, -4
        List<Long> arr = Arrays.asList(2L, -1L, -4L);
        for (Long element : arr) {
            assertTrue("Expected element [" + element +
                               "] is missing in range [" + range + "]",
                       range.contains(element));
        }
        List<Long> elementsNotPresent = new ArrayList<Long>(fullRange);
        elementsNotPresent.removeAll(arr);
        for (Long element : elementsNotPresent) {
            assertFalse("Unexpected element [" + element +
                                "] is present in range [" + range + "]",
                        range.contains(element));
        }
    }

    @Test
    public void testClosedOpenDescendingContains() {
        // [5, -5), -3 = 5, 2, -1, -4
        LongRange range = Ranges.longRange(5, BoundType.CLOSED, -5,
                                        BoundType.OPEN, -3);
        // [5, -5), -3 = 5, 2, -1, -4
        List<Long> arr = Arrays.asList(5L, 2L, -1L, -4L);
        for (Long element : arr) {
            assertTrue("Expected element [" + element +
                               "] is missing in range [" + range + "]",
                       range.contains(element));
        }
        List<Long> elementsNotPresent = new ArrayList<Long>(fullRange);
        elementsNotPresent.removeAll(arr);
        for (Long element : elementsNotPresent) {
            assertFalse("Unexpected element [" + element +
                                "] is present in range [" + range + "]",
                        range.contains(element));
        }
    }

    @Test
    public void testOpenOpenDescendingContains() {
        // (5, -5), -3 = 2, -1, -4
        LongRange range = Ranges.longRange(5, BoundType.OPEN, -5, BoundType.OPEN,
                                        -3);
        // (5, -5), -3 = 2, -1, -4
        List<Long> arr = Arrays.asList(2L, -1L, -4L);
        for (Long element : arr) {
            assertTrue("Expected element [" + element +
                               "] is missing in range [" + range + "]",
                       range.contains(element));
        }
        List<Long> elementsNotPresent = new ArrayList<Long>(fullRange);
        elementsNotPresent.removeAll(arr);
        for (Long element : elementsNotPresent) {
            assertFalse("Unexpected element [" + element +
                                "] is present in range [" + range + "]",
                        range.contains(element));
        }
    }

    @Test
    public void testContainsSingleStepDescending() {
        // [2, -2), -1 = 2, 1, 0, -1
        LongRange descendingRange = Ranges.longRange(2, BoundType.CLOSED, -2,
                                                  BoundType.OPEN, -1);
        // [2, -2), -1 = 2, 1, 0, -1
        List<Long> arr = Arrays.asList(2L, 1L, 0L, -1L);
        for (Long element : arr) {
            assertTrue("Expected element [" + element +
                               "] is missing in range [" + descendingRange +
                               "]",
                       descendingRange.contains(element));
        }
        List<Long> elementsNotPresent = new ArrayList<Long>(fullRange);
        elementsNotPresent.removeAll(arr);
        for (Long element : elementsNotPresent) {
            assertFalse("Unexpected element [" + element +
                                "] is present in range [" + descendingRange +
                                "]",
                        descendingRange.contains(element));
        }
    }

    @Test
    public void testContainsNullOrEmpty() {
        LongRange range = Ranges.longRange(-2, BoundType.OPEN, 2,
                                        BoundType.CLOSED, 1);
        assertFalse(range.contains(null));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testContainsAll() {
        // (-2, 2], 1 = -1, 0, 1, 2
        LongRange range = Ranges.longRange(-2, BoundType.OPEN, 2,
                                        BoundType.CLOSED, 1);
        List<Long> list = Arrays.asList(-1L, 0L, 1L, 2L);
        assertTrue("Range [" + range +
                   "] was expected to contain all elements from list [" + list +
                   "]", range.containsAll(list));
        List<Long> listWithExtraElements = Arrays.asList(2L, -1L, 0L, 1L, 2L,
                                                         3L);
        assertFalse("Range [" + range + "] has more elements than expected",
                    range.containsAll(listWithExtraElements));
        assertFalse(range.containsAll(null));
        assertFalse(range.containsAll(Collections.EMPTY_LIST));
    }

    @Test
    public void testEquals()
        throws Exception {
        // equals basic properties
        LongRange range = Ranges.longRange(-2, BoundType.CLOSED, 2,
                                        BoundType.OPEN, 1);
        assertEquals("equals must be reflexive", range, range);
        assertEquals("hashCode must be reflexive", range.hashCode(),
                     range.hashCode());
        assertTrue(!range.equals(null)); // should be able to compare to null

        Object range2 = Ranges.longRange(-2, BoundType.CLOSED, 2, BoundType.OPEN,
                                      1);
        if (range.equals(range2)) {
            assertEquals("equals implies hash equals", range.hashCode(),
                         range2.hashCode());
            assertEquals("equals must be symmetric", range2, range);
        } else {
            assertTrue("equals must be symmetric", !range2.equals(range));
        }

        // Changing attributes
        Object range3 = Ranges.longRange(-1, BoundType.CLOSED, 2, BoundType.OPEN,
                                      1);
        assertFalse("Invalid equals after changing attributes",
                    range.equals(range3));

        Object range4 = Ranges.longRange(-2, BoundType.OPEN, 2, BoundType.OPEN, 1);
        assertFalse("Invalid equals after changing attributes",
                    range.equals(range4));

        Object range5 = Ranges.longRange(-2, BoundType.CLOSED, 1, BoundType.OPEN,
                                      1);
        assertFalse("Invalid equals after changing attributes",
                    range.equals(range5));

        Object range6 = Ranges.longRange(-2, BoundType.CLOSED, 2,
                                      BoundType.CLOSED, 1);
        assertFalse("Invalid equals after changing attributes",
                    range.equals(range6));

        Object range7 = Ranges.longRange(-2, BoundType.CLOSED, 2, BoundType.OPEN,
                                      2);
        assertFalse("Invalid equals after changing attributes",
                    range.equals(range7));

        // Using different constructors
        LongRange range8 = Ranges.longRange(Integer.valueOf(-2), Integer.valueOf(2),
                                            Integer.valueOf(1));
        assertEquals("Invalid equals using different constructor", range,
                     range8);

        LongRange range9 = Ranges.longRange(Integer.valueOf(-2),Integer.valueOf(2));
        assertEquals("Invalid equals using different constructor", range,
                     range9);

        Endpoint<Long> leftEndpoint = new Endpoint<Long>(-2L, BoundType.CLOSED);
        Endpoint<Long> rightEndpoint = new Endpoint<Long>(2L, BoundType.OPEN);
        LongRange range10 = Ranges.longRange(leftEndpoint, rightEndpoint, 1L);
        assertEquals("Invalid equals using different constructor", range,
                     range10);
    }

    @Test
    public void testToString() {
        LongRange range = Ranges.longRange(-2, BoundType.OPEN, 2,
                                        BoundType.CLOSED, 1);
        assertEquals("Wrong string value", "LongRange<(-2, 2], 1>",
                     range.toString());
    }

    @Test
    public void testConstructorUsingSameEndpoint() {
        Endpoint<Long> uniqueEndpoint = new Endpoint<Long>(10L,
                                                           BoundType.CLOSED);
        try {
            Ranges.longRange(uniqueEndpoint, uniqueEndpoint, 1L);
        } catch (IllegalArgumentException e) {
            fail("Not expected to get here");
        }
    }

    @Test
    public void testInvalidRange() {
        try {
            Ranges.longRange(10, BoundType.OPEN, -5, BoundType.CLOSED, 10);
            fail("Not expected to get here");
        } catch (IllegalArgumentException e) {
            // Do nothing
        }
        Endpoint<Long> leftEndpoint = new Endpoint<Long>(10L, BoundType.CLOSED);
        Endpoint<Long> rightEndpoint = new Endpoint<Long>(-5L, BoundType.OPEN);
        try {
            Ranges.longRange(leftEndpoint, rightEndpoint, 1L);
            fail("Not expected to get here");
        } catch (IllegalArgumentException e) {
            // Do nothing
        }
    }

    @Test
    public void testDefaultStep() {
        assertEquals("Invalid default step", Long.valueOf(-1L),
                     LongRange.DEFAULT_STEP.evaluate(10L, 1L));
        assertEquals("Invalid default step", Long.valueOf(1L),
                     LongRange.DEFAULT_STEP.evaluate(1L, 10L));
    }

}