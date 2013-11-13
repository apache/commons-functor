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
public class TestIntegerRange extends BaseFunctorTest {

    // Attributes
    // ------------------------------------------------------------------------
    private IntegerRange ascIntRange = null;
    private IntegerRange descIntRange = null;
    private Collection<Integer> expectedAsc = null;
    private Collection<Integer> expectedDesc = null;

    // Test set up
    // ------------------------------------------------------------------------
    @Before
    public void setUp() {
        ascIntRange = Ranges.integerRange(0, 10);
        descIntRange = Ranges.integerRange(10, 0);
        expectedAsc = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        expectedDesc = Arrays.asList(10, 9, 8, 7, 6, 5, 4, 3, 2, 1);
    }

    @After
    public void tearDown() {
        ascIntRange = null;
        descIntRange = null;
    }
    
    @Override
    protected Object makeFunctor() throws Exception {
        return Ranges.integerRange(10, 20);
    }

    // Generator tests
    // ------------------------------------------------------------------------

    @Test
    public void testGenerateListExample() {
        // generates a collection of Integers from 0 (inclusive) to 10 (exclusive)
        {
            List<? super Integer> list = (List<? super Integer>) (
                IteratorToGeneratorAdapter.adapt(Ranges.integerRange(0,10)).to(new ArrayList<Integer>()));
            for (int i=0;i<10;i++) {
                assertEquals(Integer.valueOf(i),list.get(i));
            }
        }

        // generates a collection of Integers from 10 (inclusive) to 0 (exclusive)
        {
            List<? super Integer> list = (List<? super Integer>) (
                IteratorToGeneratorAdapter.adapt(Ranges.integerRange(10,0)).to(new ArrayList<Integer>()));
            for (int i=10;i>0;i--) {
                assertEquals(Integer.valueOf(i),list.get(10-i));
            }
        }
    }

    @Test
    public void testStepChecking() {
        {
            Ranges.integerRange(2, 2, 0); // step of 0 is ok when range is empty
        }
        {
            Ranges.integerRange(2, 2, 1); // positive step is ok when range is empty
        }
        {
            Ranges.integerRange(2, 2, -1); // negative step is ok when range is empty
        }
        {
            Ranges.integerRange(0, 1, 10); // big steps are ok
        }
        {
            Ranges.integerRange(1, 0, -10); // big steps are ok
        }
        try {
            Ranges.integerRange(0, 1, 0);
            fail("Expected IllegalArgumentException");
        } catch(IllegalArgumentException e) {
            // expected
        }
        try {
            Ranges.integerRange(0, 1, -1);
            fail("Expected IllegalArgumentException");
        } catch(IllegalArgumentException e) {
            // expected
        }
        try {
            Ranges.integerRange(0, -1, 1);
            fail("Expected IllegalArgumentException");
        } catch(IllegalArgumentException e) {
            // expected
        }
    }

    @Test
    public void testObjectConstructor() {
        IntegerRange range = Ranges.integerRange(Integer.valueOf(0), Integer.valueOf(5));
        assertEquals("[0, 1, 2, 3, 4]", IteratorToGeneratorAdapter.adapt(range).toCollection().toString());
        range = Ranges.integerRange(Integer.valueOf(0), Integer.valueOf(5), Integer.valueOf(1));
        assertEquals("[0, 1, 2, 3, 4]", IteratorToGeneratorAdapter.adapt(range).toCollection().toString());
    }


    @Test
    public void testReverseStep() {
        IntegerRange range = Ranges.integerRange(10, 0, -2);
        assertEquals("[10, 8, 6, 4, 2]", IteratorToGeneratorAdapter.adapt(range).toCollection().toString());
    }

    @Test
    public void testStep() {
        IntegerRange range = Ranges.integerRange(0, 10, 2);
        assertEquals("[0, 2, 4, 6, 8]", IteratorToGeneratorAdapter.adapt(range).toCollection().toString());
    }

    @Test
    public void testForwardRange() {
        IntegerRange range = Ranges.integerRange(0, 5);
        assertEquals("[0, 1, 2, 3, 4]", IteratorToGeneratorAdapter.adapt(range).toCollection().toString());
    }

    @Test
    public void testReverseRange() {
        IntegerRange range = Ranges.integerRange(5, 0);
        assertEquals("[5, 4, 3, 2, 1]", IteratorToGeneratorAdapter.adapt(range).toCollection().toString());
    }

    @Test
    public void testEdgeCase() {
        IntegerRange range = Ranges.integerRange(Integer.MAX_VALUE - 3, Integer.MAX_VALUE);
        assertEquals("[2147483644, 2147483645, 2147483646]", IteratorToGeneratorAdapter.adapt(range).toCollection().toString());
    }

    @Test
    public void testBoundaries() {
        IntegerRange range = Ranges.integerRange(0, 10);
        assertEquals(new Endpoint<Comparable<?>>(0, BoundType.CLOSED),
                     range.getLeftEndpoint());
        assertEquals(new Endpoint<Comparable<?>>(10, BoundType.OPEN),
                     range.getRightEndpoint());
    }

    @Test
    public void testClosedClosedAscending() {
        // [-5, 5], 3 = -5, -2, 1, 4
        IntegerRange range = Ranges.integerRange(-5, BoundType.CLOSED, 5,
                                              BoundType.CLOSED, 3);
        // [-5, 5], 3 = -5, -2, 1, 4
        List<Integer> expected = Arrays.asList(-5, -2, 1, 4);
        Collection<Integer> elements = IteratorToGeneratorAdapter.adapt(range).toCollection();
        assertEquals(expected, elements);
    }

    @Test
    public void testOpenClosedAscending() {
        // (-5, 5], 3 = -2, 1, 4
        IntegerRange range = Ranges.integerRange(-5, BoundType.OPEN, 5,
                                              BoundType.CLOSED, 3);
        // (-5, 5], 3 = -2, 1, 4
        List<Integer> expected = Arrays.asList(-2, 1, 4);
        Collection<Integer> elements = IteratorToGeneratorAdapter.adapt(range).toCollection();
        assertEquals(expected, elements);
    }

    @Test
    public void testClosedOpenAscending() {
        // [-5, 5), 3 = -5, -2, 1, 4
        IntegerRange range = Ranges.integerRange(-5, BoundType.CLOSED, 5,
                                              BoundType.OPEN, 3);
        // (-5, 5], 3 = -5, -2, 1, 4
        List<Integer> expected = Arrays.asList(-5, -2, 1, 4);
        Collection<Integer> elements = IteratorToGeneratorAdapter.adapt(range).toCollection();
        assertEquals(expected, elements);
    }

    @Test
    public void testOpenOpenAscending() {
        // (-5, 5), 3 = -2, 1, 4
        IntegerRange range = Ranges.integerRange(-5, BoundType.OPEN, 5,
                                              BoundType.OPEN, 3);
        // (-5, 5), 3 = -2, 1, 4
        List<Integer> expected = Arrays.asList(-2, 1, 4);
        Collection<Integer> elements = IteratorToGeneratorAdapter.adapt(range).toCollection();
        assertEquals(expected, elements);
    }

    @Test
    public void testSingleStepAscending() {
        // (-2, 2], 1 = -1, 0, 1, 2
        IntegerRange range = Ranges.integerRange(-2, BoundType.OPEN, 2,
                                              BoundType.CLOSED, 1);
        // (-2, 2], 1 = -1, 0, 1, 2
        List<Integer> expected = Arrays.asList(-1, 0, 1, 2);
        Collection<Integer> elements = IteratorToGeneratorAdapter.adapt(range).toCollection();
        assertEquals(expected, elements);
    }

    @Test
    public void testClosedClosedDescending() {
        // [5, -5], -3 = 5, 2, -1, -4
        IntegerRange range = Ranges.integerRange(5, BoundType.CLOSED, -5,
                                              BoundType.CLOSED, -3);
        // [5, -5], -3 = 5, 2, -1, -4
        List<Integer> expected = Arrays.asList(5, 2, -1, -4);
        Collection<Integer> elements = IteratorToGeneratorAdapter.adapt(range).toCollection();
        assertEquals(expected, elements);
    }

    @Test
    public void testOpenClosedDescending() {
        // (5, -5], -3 = 2, -1, -4
        IntegerRange range = Ranges.integerRange(5, BoundType.OPEN, -5,
                                              BoundType.CLOSED, -3);
        // (5, -5], -3 = 2, -1, -4
        List<Integer> expected = Arrays.asList(2, -1, -4);
        Collection<Integer> elements = IteratorToGeneratorAdapter.adapt(range).toCollection();
        assertEquals(expected, elements);
    }

    @Test
    public void testClosedOpenDescending() {
        // [5, -5), -3 = 5, 2, -1, -4
        IntegerRange range = Ranges.integerRange(5, BoundType.CLOSED, -5,
                                              BoundType.OPEN, -3);
        // [5, -5), -3 = 5, 2, -1, -4
        List<Integer> expected = Arrays.asList(5, 2, -1, -4);
        Collection<Integer> elements = IteratorToGeneratorAdapter.adapt(range).toCollection();
        assertEquals(expected, elements);
    }

    @Test
    public void testOpenOpenDescending() {
        // (5, -5), -3 = 2, -1, -4
        IntegerRange range = Ranges.integerRange(5, BoundType.OPEN, -5,
                                              BoundType.OPEN, -3);
        // (5, -5), -3 = 2, -1, -4
        List<Integer> expected = Arrays.asList(2, -1, -4);
        Collection<Integer> elements = IteratorToGeneratorAdapter.adapt(range).toCollection();
        assertEquals(expected, elements);
    }

    @Test
    public void testSingleStepDescending() {
        // [2, -2), -1 = 2, 1, 0, -1
        IntegerRange range = Ranges.integerRange(2, BoundType.CLOSED, -2,
                                              BoundType.OPEN, -1);
        // [2, -2), -1 = 2, 1, 0, -1
        List<Integer> expected = Arrays.asList(2, 1, 0, -1);
        Collection<Integer> elements = IteratorToGeneratorAdapter.adapt(range).toCollection();
        assertEquals(expected, elements);
    }

    @Test
    public void testAscending() {
        final List<Integer> list = new ArrayList<Integer>();
        for (int i : ascIntRange) {
            list.add(i);
        }
        assertTrue(expectedAsc.containsAll(list));
    }

    @Test
    public void testDescending() {
        final List<Integer> list = new ArrayList<Integer>();
        for (int i : descIntRange) {
            list.add(i);
        }
        assertTrue(expectedDesc.containsAll(list));
    }

    @Test
    public void testToCollection() {
        Collection<Integer> ascCol = IteratorToGeneratorAdapter.adapt(ascIntRange).toCollection();
        assertEquals("Different collections", expectedAsc, ascCol);
        Collection<Integer> descCol = IteratorToGeneratorAdapter.adapt(descIntRange).toCollection();
        assertEquals("Different collections", expectedDesc, descCol);
    }

    @Test
    public void testTransformedGenerator() {
        int expected = 45;
        int total = IteratorToGeneratorAdapter.adapt(ascIntRange)
            .to(new Function<Generator<? extends Integer>, Integer>() {

                public Integer evaluate(Generator<? extends Integer> obj) {
                    int total = 0;
                    for (Object element : obj.toCollection()) {
                        total += (Integer) element;
                    }
                    return total;
                }
            });
        assertEquals(expected, total);
        expected = 55;
        total = IteratorToGeneratorAdapter.adapt(descIntRange)
            .to(new Function<Generator<? extends Integer>, Integer>() {

                public Integer evaluate(Generator<? extends Integer> obj) {
                    int total = 0;
                    for (Object element : obj.toCollection()) {
                        total += (Integer) element;
                    }
                    return total;
                }
            });
        assertEquals(expected, total);
    }

    // Range tests
    // ---------------------------------------------------------------

    // A base range with all integers between -6 and 6
    private final List<Integer> fullRange = Collections.unmodifiableList(Arrays
        .asList(-6, -5, -4, -3, -2, -1, 0, 1, 2, 3, 4, 5, 6));

    @Test
    public void testEmptyRanges() {
        IntegerRange empty1 = Ranges.integerRange(-2, BoundType.OPEN, -1,
                                               BoundType.OPEN, 2);
        assertTrue("The range was expected to be empty.", empty1.isEmpty());
        IntegerRange empty2 = Ranges.integerRange(2, BoundType.OPEN, 0,
                                               BoundType.OPEN, -2);
        assertTrue("The range was expected to be empty.", empty2.isEmpty());
        IntegerRange empty3 = Ranges.integerRange(0, BoundType.OPEN, 1,
                                               BoundType.CLOSED, 2);
        assertTrue("The range was expected to be empty.", empty3.isEmpty());
        IntegerRange empty4 = Ranges.integerRange(-3, BoundType.OPEN, -3,
                                               BoundType.OPEN, 1);
        assertTrue("The range was expected to be empty.", empty4.isEmpty());
        IntegerRange empty5 = Ranges.integerRange(-3, BoundType.CLOSED, -3,
                                               BoundType.OPEN, 1);
        assertTrue("The range was expected to be empty.", empty5.isEmpty());
        IntegerRange empty6 = Ranges.integerRange(1, BoundType.OPEN, 0,
                                               BoundType.CLOSED, -2);
        assertTrue("The range was expected to be empty.", empty6.isEmpty());
        IntegerRange notEmpty1 = Ranges.integerRange(-3, BoundType.CLOSED, -3,
                                                  BoundType.CLOSED, 1);
        assertFalse("The range was not expected to be empty.",
                    notEmpty1.isEmpty());
        IntegerRange notEmpty2 = Ranges.integerRange(-3, BoundType.OPEN, -2,
                                                  BoundType.CLOSED, 1);
        assertFalse("The range was not expected to be empty.",
                    notEmpty2.isEmpty());
        IntegerRange notEmpty3 = Ranges.integerRange(2, BoundType.OPEN, 1,
                                                  BoundType.CLOSED, -1);
        assertFalse("The range was not expected to be empty.",
                    notEmpty3.isEmpty());
        IntegerRange notEmpty4 = Ranges.integerRange(2, BoundType.CLOSED, 1,
                                                  BoundType.OPEN, -1);
        assertFalse("The range was not expected to be empty.",
                    notEmpty4.isEmpty());
        IntegerRange notEmpty5 = Ranges.integerRange(1, BoundType.CLOSED, 2,
                                                  BoundType.OPEN, 1);
        assertFalse("The range was not expected to be empty.",
                    notEmpty5.isEmpty());
    }

    @Test
    public void testClosedClosedAscendingContains() {
        // [-5, 5], 3 = -5, -2, 1, 4
        IntegerRange range = Ranges.integerRange(-5, BoundType.CLOSED, 5,
                                              BoundType.CLOSED, 3);
        // [-5, 5], 3 = -5, -2, 1, 4
        List<Integer> arr = Arrays.asList(-5, -2, 1, 4);
        for (Integer element : arr) {
            assertTrue("Expected element [" + element +
                               "] is missing in range [" + range + "]",
                       range.contains(element));
        }
        List<Integer> elementsNotPresent = new ArrayList<Integer>(fullRange);
        elementsNotPresent.removeAll(arr);
        for (Integer element : elementsNotPresent) {
            assertFalse("Unexpected element [" + element +
                                "] is present in range [" + range + "]",
                        range.contains(element));
        }
    }

    @Test
    public void testOpenClosedAscendingContains() {
        // (-5, 5], 3 = -2, 1, 4
        IntegerRange range = Ranges.integerRange(-5, BoundType.OPEN, 5,
                                              BoundType.CLOSED, 3);
        // (-5, 5], 3 = -2, 1, 4
        List<Integer> arr = Arrays.asList(-2, 1, 4);
        for (Integer element : arr) {
            assertTrue("Expected element [" + element +
                               "] is missing in range [" + range + "]",
                       range.contains(element));
        }
        List<Integer> elementsNotPresent = new ArrayList<Integer>(fullRange);
        elementsNotPresent.removeAll(arr);
        for (Integer element : elementsNotPresent) {
            assertFalse("Unexpected element [" + element +
                                "] is present in range [" + range + "]",
                        range.contains(element));
        }
    }

    @Test
    public void testClosedOpenAscendingContains() {
        // [-5, 5), 3 = -5, -2, 1, 4
        IntegerRange range = Ranges.integerRange(-5, BoundType.CLOSED, 5,
                                              BoundType.OPEN, 3);
        // (-5, 5], 3 = -5, -2, 1, 4
        List<Integer> arr = Arrays.asList(-5, -2, 1, 4);
        for (Integer element : arr) {
            assertTrue("Expected element [" + element +
                               "] is missing in range [" + range + "]",
                       range.contains(element));
        }
        List<Integer> elementsNotPresent = new ArrayList<Integer>(fullRange);
        elementsNotPresent.removeAll(arr);
        for (Integer element : elementsNotPresent) {
            assertFalse("Unexpected element [" + element +
                                "] is present in range [" + range + "]",
                        range.contains(element));
        }
    }

    @Test
    public void testOpenOpenAscendingContains() {
        // (-5, 5), 3 = -2, 1, 4
        IntegerRange range = Ranges.integerRange(-5, BoundType.OPEN, 5,
                                              BoundType.OPEN, 3);
        // (-5, 5), 3 = -2, 1, 4
        List<Integer> arr = Arrays.asList(-2, 1, 4);
        for (Integer element : arr) {
            assertTrue("Expected element [" + element +
                               "] is missing in range [" + range + "]",
                       range.contains(element));
        }
        List<Integer> elementsNotPresent = new ArrayList<Integer>(fullRange);
        elementsNotPresent.removeAll(arr);
        for (Integer element : elementsNotPresent) {
            assertFalse("Unexpected element [" + element +
                                "] is present in range [" + range + "]",
                        range.contains(element));
        }
    }

    @Test
    public void testContainsSingleStepAscending() {
        // (-2, 2], 1 = -1, 0, 1, 2
        IntegerRange ascendingRange = Ranges.integerRange(-2, BoundType.OPEN, 2,
                                                       BoundType.CLOSED, 1);
        // (-2, 2], 1 = -1, 0, 1, 2
        List<Integer> arr = Arrays.asList(-1, 0, 1, 2);
        for (Integer element : arr) {
            assertTrue("Expected element [" + element +
                               "] is missing in range [" + ascendingRange + "]",
                       ascendingRange.contains(element));
        }
        List<Integer> elementsNotPresent = new ArrayList<Integer>(fullRange);
        elementsNotPresent.removeAll(arr);
        for (Integer element : elementsNotPresent) {
            assertFalse("Unexpected element [" + element +
                                "] is present in range [" + ascendingRange +
                                "]",
                        ascendingRange.contains(element));
        }
    }

    @Test
    public void testClosedClosedDescendingContains() {
        // [5, -5], -3 = 5, 2, -1, -4
        IntegerRange range = Ranges.integerRange(5, BoundType.CLOSED, -5,
                                              BoundType.CLOSED, -3);
        // [5, -5], -3 = 5, 2, -1, -4
        List<Integer> arr = Arrays.asList(5, 2, -1, -4);
        for (Integer element : arr) {
            assertTrue("Expected element [" + element +
                               "] is missing in range [" + range + "]",
                       range.contains(element));
        }
        List<Integer> elementsNotPresent = new ArrayList<Integer>(fullRange);
        elementsNotPresent.removeAll(arr);
        for (Integer element : elementsNotPresent) {
            assertFalse("Unexpected element [" + element +
                                "] is present in range [" + range + "]",
                        range.contains(element));
        }
    }

    @Test
    public void testOpenClosedDescendingContains() {
        // (5, -5], -3 = 2, -1, -4
        IntegerRange range = Ranges.integerRange(5, BoundType.OPEN, -5,
                                              BoundType.CLOSED, -3);
        // (5, -5], -3 = 2, -1, -4
        List<Integer> arr = Arrays.asList(2, -1, -4);
        for (Integer element : arr) {
            assertTrue("Expected element [" + element +
                               "] is missing in range [" + range + "]",
                       range.contains(element));
        }
        List<Integer> elementsNotPresent = new ArrayList<Integer>(fullRange);
        elementsNotPresent.removeAll(arr);
        for (Integer element : elementsNotPresent) {
            assertFalse("Unexpected element [" + element +
                                "] is present in range [" + range + "]",
                        range.contains(element));
        }
    }

    @Test
    public void testClosedOpenDescendingContains() {
        // [5, -5), -3 = 5, 2, -1, -4
        IntegerRange range = Ranges.integerRange(5, BoundType.CLOSED, -5,
                                              BoundType.OPEN, -3);
        // [5, -5), -3 = 5, 2, -1, -4
        List<Integer> arr = Arrays.asList(5, 2, -1, -4);
        for (Integer element : arr) {
            assertTrue("Expected element [" + element +
                               "] is missing in range [" + range + "]",
                       range.contains(element));
        }
        List<Integer> elementsNotPresent = new ArrayList<Integer>(fullRange);
        elementsNotPresent.removeAll(arr);
        for (Integer element : elementsNotPresent) {
            assertFalse("Unexpected element [" + element +
                                "] is present in range [" + range + "]",
                        range.contains(element));
        }
    }

    @Test
    public void testOpenOpenDescendingContains() {
        // (5, -5), -3 = 2, -1, -4
        IntegerRange range = Ranges.integerRange(5, BoundType.OPEN, -5,
                                              BoundType.OPEN, -3);
        // (5, -5), -3 = 2, -1, -4
        List<Integer> arr = Arrays.asList(2, -1, -4);
        for (Integer element : arr) {
            assertTrue("Expected element [" + element +
                               "] is missing in range [" + range + "]",
                       range.contains(element));
        }
        List<Integer> elementsNotPresent = new ArrayList<Integer>(fullRange);
        elementsNotPresent.removeAll(arr);
        for (Integer element : elementsNotPresent) {
            assertFalse("Unexpected element [" + element +
                                "] is present in range [" + range + "]",
                        range.contains(element));
        }
    }

    @Test
    public void testContainsSingleStepDescending() {
        // [2, -2), -1 = 2, 1, 0, -1
        IntegerRange descendingRange = Ranges.integerRange(2, BoundType.CLOSED,
                                                        -2, BoundType.OPEN, -1);
        // [2, -2), -1 = 2, 1, 0, -1
        List<Integer> arr = Arrays.asList(2, 1, 0, -1);
        for (Integer element : arr) {
            assertTrue("Expected element [" + element +
                               "] is missing in range [" + descendingRange +
                               "]",
                       descendingRange.contains(element));
        }
        List<Integer> elementsNotPresent = new ArrayList<Integer>(fullRange);
        elementsNotPresent.removeAll(arr);
        for (Integer element : elementsNotPresent) {
            assertFalse("Unexpected element [" + element +
                                "] is present in range [" + descendingRange +
                                "]",
                        descendingRange.contains(element));
        }
    }

    @Test
    public void testContainsNullOrEmpty() {
        IntegerRange range = Ranges.integerRange(-2, BoundType.OPEN, 2,
                                              BoundType.CLOSED, 1);
        assertFalse(range.contains(null));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testContainsAll() {
        // (-2, 2], 1 = -1, 0, 1, 2
        IntegerRange range = Ranges.integerRange(-2, BoundType.OPEN, 2,
                                              BoundType.CLOSED, 1);
        List<Integer> list = Arrays.asList(-1, 0, 1, 2);
        assertTrue("Range [" + range +
                   "] was expected to contain all elements from list [" + list +
                   "]", range.containsAll(list));
        List<Integer> listWithExtraElements = Arrays.asList(2, -1, 0, 1, 2, 3);
        assertFalse("Range [" + range + "] has more elements than expected",
                    range.containsAll(listWithExtraElements));
        assertFalse(range.containsAll(null));
        assertFalse(range.containsAll(Collections.EMPTY_LIST));
    }

    @Test
    public void testEquals()
        throws Exception {
        // equals basic properties
        IntegerRange range = Ranges.integerRange(-2, BoundType.CLOSED, 2,
                                              BoundType.OPEN, 1);
        assertEquals("equals must be reflexive", range, range);
        assertEquals("hashCode must be reflexive", range.hashCode(),
                     range.hashCode());
        assertTrue(!range.equals(null)); // should be able to compare to null

        Object range2 = Ranges.integerRange(-2, BoundType.CLOSED, 2,
                                         BoundType.OPEN, 1);
        if (range.equals(range2)) {
            assertEquals("equals implies hash equals", range.hashCode(),
                         range2.hashCode());
            assertEquals("equals must be symmetric", range2, range);
        } else {
            assertTrue("equals must be symmetric", !range2.equals(range));
        }

        // Changing attributes
        Object range3 = Ranges.integerRange(-1, BoundType.CLOSED, 2,
                                         BoundType.OPEN, 1);
        assertFalse("Invalid equals after changing attributes",
                    range.equals(range3));

        Object range4 = Ranges.integerRange(-2, BoundType.OPEN, 2, BoundType.OPEN,
                                         1);
        assertFalse("Invalid equals after changing attributes",
                    range.equals(range4));

        Object range5 = Ranges.integerRange(-2, BoundType.CLOSED, 1,
                                         BoundType.OPEN, 1);
        assertFalse("Invalid equals after changing attributes",
                    range.equals(range5));

        Object range6 = Ranges.integerRange(-2, BoundType.CLOSED, 2,
                                         BoundType.CLOSED, 1);
        assertFalse("Invalid equals after changing attributes",
                    range.equals(range6));

        Object range7 = Ranges.integerRange(-2, BoundType.CLOSED, 2,
                                         BoundType.OPEN, 2);
        assertFalse("Invalid equals after changing attributes",
                    range.equals(range7));

        // Using different constructors
        IntegerRange range8 = Ranges.integerRange(Long.valueOf(-2),
                                                  Long.valueOf(2), Long.valueOf(1));
        assertEquals("Invalid equals using different constructor", range,
                     range8);

        IntegerRange range9 = Ranges.integerRange(Long.valueOf(-2),
                                                  Long.valueOf(2));
        assertEquals("Invalid equals using different constructor", range,
                     range9);

        Endpoint<Integer> leftEndpoint = new Endpoint<Integer>(-2,
                                                               BoundType.CLOSED);
        Endpoint<Integer> rightEndpoint = new Endpoint<Integer>(2,
                                                                BoundType.OPEN);
        IntegerRange range10 = Ranges.integerRange(leftEndpoint, rightEndpoint, 1);
        assertEquals("Invalid equals using different constructor", range,
                     range10);
    }

    @Test
    public void testToString() {
        IntegerRange range = Ranges.integerRange(-2, BoundType.OPEN, 2,
                                              BoundType.CLOSED, 1);
        assertEquals("Wrong string value", "IntegerRange<(-2, 2], 1>",
                     range.toString());
    }

    @Test
    public void testConstructorUsingSameEndpoint() {
        Endpoint<Integer> uniqueEndpoint = new Endpoint<Integer>(
                                                                 10,
                                                                 BoundType.CLOSED);
        try {
            Ranges.integerRange(uniqueEndpoint, uniqueEndpoint, 1);
        } catch (IllegalArgumentException e) {
            fail("Not expected to get here");
        }
    }

    @Test
    public void testInvalidRange() {
        try {
            Ranges.integerRange(10, BoundType.OPEN, -5, BoundType.CLOSED, 10);
            fail("Not expected to get here");
        } catch (IllegalArgumentException e) {
            // Do nothing
        }
        Endpoint<Integer> leftEndpoint = new Endpoint<Integer>(10,
                                                               BoundType.CLOSED);
        Endpoint<Integer> rightEndpoint = new Endpoint<Integer>(-5,
                                                                BoundType.OPEN);
        try {
            Ranges.integerRange(leftEndpoint, rightEndpoint, 1);
            fail("Not expected to get here");
        } catch (IllegalArgumentException e) {
            // Do nothing
        }
    }

    @Test
    public void testDefaultStep() {
        assertEquals("Invalid default step", Integer.valueOf(-1),
                     IntegerRange.DEFAULT_STEP.evaluate(10, 1));
        assertEquals("Invalid default step", Integer.valueOf(1),
                     IntegerRange.DEFAULT_STEP.evaluate(1, 10));
    }

}