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
 * Tests for double range.
 * 
 * @since 1.0
 * @version $Revision: $ $Date: $
 */
public class TestDoubleRange extends BaseFunctorTest {

    // A base range with all longs between -6 and 6
    private final List<Double> fullRange = Collections.unmodifiableList(Arrays
        .asList(-6.0, -5.0, -4.0, -3.0, -2.0, -1.0, 0.0, 1.0, 2.0, 3.0, 4.0,
                5.0, 6.0));

    // Attributes
    // ------------------------------------------------------------------------
    private DoubleRange ascDoubleRange = null;
    private DoubleRange descDoubleRange = null;
    private Collection<Double> expectedAsc = null;
    private Collection<Double> expectedDesc = null;

    // Test set up
    // ------------------------------------------------------------------------
    @Before
    public void setUp() {
        ascDoubleRange = Ranges.doubleRange(0.0d, 10.0d);
        descDoubleRange = Ranges.doubleRange(10.0d, 0.0d);
        expectedAsc = Arrays.asList(0.0d, 1.0d, 2.0d, 3.0d, 4.0d, 5.0d, 6.0d,
                                    7.0d, 8.0d, 9.0d);
        expectedDesc = Arrays.asList(10.0d, 9.0d, 8.0d, 7.0d, 6.0d, 5.0d, 4.0d,
                                     3.0d, 2.0d, 1.0d);
    }

    @After
    public void tearDown() {
        ascDoubleRange = null;
        descDoubleRange = null;
    }

    @Override
    protected Object makeFunctor()
        throws Exception {
        return Ranges.doubleRange(10, 20);
    }

    // Generator tests
    // ---------------------------------------------------------------

    @Test
    public void testGenerateListExample() {
        // generates a collection of Doubles from 0 (inclusive) to 10
        // (exclusive)
        {
            List<? super Double> list = (List<? super Double>) (
                IteratorToGeneratorAdapter.adapt(Ranges.doubleRange(0, 10))
                    .to(new ArrayList<Double>()));
            for (int i = 0; i < 10; i++) {
                assertEquals(Double.valueOf(i), list.get(i));
            }
        }

        // generates a collection of Doubles from 10 (inclusive) to 0
        // (exclusive)
        {
            List<? super Double> list = (List<? super Double>) (
                IteratorToGeneratorAdapter.adapt(Ranges.doubleRange(10, 0))
                .to(new ArrayList<Double>()));
            for (int i = 10; i > 0; i--) {
                assertEquals(Double.valueOf(i), list.get(10 - i));
            }
        }
    }

    @Test
    public void testStepChecking() {
        {
            Ranges.doubleRange(2, 2, 0); // step of 0 is ok when range is empty
        }
        {
            Ranges.doubleRange(2, 2, 1); // positive step is ok when range is
                                          // empty
        }
        {
            Ranges.doubleRange(2, 2, -1); // negative step is ok when range is
                                           // empty
        }
        {
            Ranges.doubleRange(0, 1, 10); // big steps are ok
        }
        {
            Ranges.doubleRange(1, 0, -10); // big steps are ok
        }
        try {
            Ranges.doubleRange(0, 1, 0);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
        try {
            Ranges.doubleRange(0, 1, -1);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
        try {
            Ranges.doubleRange(0, -1, 1);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    @Test
    public void testObjectConstructor() {
        DoubleRange range = Ranges.doubleRange(Double.valueOf(0),
                                                    Double.valueOf(5));
        assertEquals("[0.0, 1.0, 2.0, 3.0, 4.0]", IteratorToGeneratorAdapter.adapt(range).toCollection()
            .toString());
        range = Ranges.doubleRange(Double.valueOf(0), Double.valueOf(5), Double.valueOf(1));
    }

    @Test
    public void testReverseStep() {
        DoubleRange range = Ranges.doubleRange(10, 0, -2);
        assertEquals("[10.0, 8.0, 6.0, 4.0, 2.0]", IteratorToGeneratorAdapter.adapt(range).toCollection()
            .toString());
    }

    @Test
    public void testStep() {
        DoubleRange range = Ranges.doubleRange(0, 10, 2);
        assertEquals("[0.0, 2.0, 4.0, 6.0, 8.0]", IteratorToGeneratorAdapter.adapt(range).toCollection()
            .toString());
    }

    @Test
    public void testForwardRange() {
        DoubleRange range = Ranges.doubleRange(0, 5);
        assertEquals("[0.0, 1.0, 2.0, 3.0, 4.0]", IteratorToGeneratorAdapter.adapt(range).toCollection()
            .toString());
    }

    @Test
    public void testReverseRange() {
        DoubleRange range = Ranges.doubleRange(5, 0);
        assertEquals("[5.0, 4.0, 3.0, 2.0, 1.0]", IteratorToGeneratorAdapter.adapt(range).toCollection()
            .toString());
    }

    // @Test
    // public void testEdgeCase() {
    // DoubleRange range = Ranges.doubleRange(Double.MAX_VALUE - 3.0d,
    // Double.MAX_VALUE);
    // assertEquals("[9223372036854775804, 9223372036854775805, 9223372036854775806]",
    // IteratorToGeneratorAdapter.adapt(range).toCollection().toString());
    // assertEquals("[9223372036854775804, 9223372036854775805, 9223372036854775806]",
    // IteratorToGeneratorAdapter.adapt(range).toCollection().toString());
    // }

    @Test
    public void testBoundaries() {
        DoubleRange range = Ranges.doubleRange(0.0d, 10.0d);
        assertEquals(new Endpoint<Comparable<?>>(0.0d, BoundType.CLOSED),
                     range.getLeftEndpoint());
        assertEquals(new Endpoint<Comparable<?>>(10.0d, BoundType.OPEN),
                     range.getRightEndpoint());
    }

    @Test
    public void testClosedClosedAscending() {
        // [-5.0d, 5.0d], 3.0d = -5.0d, -2.0d, 1.0d, 4.0d
        DoubleRange range = Ranges.doubleRange(-5.0d, BoundType.CLOSED, 5.0d,
                                            BoundType.CLOSED, 3.0d);
        // [-5.0d, 5.0d], 3.0d = -5.0d, -2.0d, 1.0d, 4.0d
        List<Double> expected = Arrays.asList(-5.0d, -2.0d, 1.0d, 4.0d);
        Collection<Double> elements = IteratorToGeneratorAdapter.adapt(range).toCollection();
        assertEquals(expected, elements);
    }

    @Test
    public void testOpenClosedAscending() {
        // (-5.0d, 5.0d], 3.0d = -2.0d, 1.0d, 4.0d
        DoubleRange range = Ranges.doubleRange(-5.0d, BoundType.OPEN, 5.0d,
                                            BoundType.CLOSED, 3.0d);
        // (-5.0d, 5.0d], 3.0d = -2.0d, 1.0d, 4.0d
        List<Double> expected = Arrays.asList(-2.0d, 1.0d, 4.0d);
        Collection<Double> elements = IteratorToGeneratorAdapter.adapt(range).toCollection();
        assertEquals(expected, elements);
    }

    @Test
    public void testClosedOpenAscending() {
        // [-5.0d, 5.0d), 3.0d = -5.0d, -2.0d, 1.0d, 4.0d
        DoubleRange range = Ranges.doubleRange(-5.0d, BoundType.CLOSED, 5.0d,
                                            BoundType.OPEN, 3.0d);
        // (-5.0d, 5.0d], 3.0d = -5.0d, -2.0d, 1.0d, 4.0d
        List<Double> expected = Arrays.asList(-5.0d, -2.0d, 1.0d, 4.0d);
        Collection<Double> elements = IteratorToGeneratorAdapter.adapt(range).toCollection();
        assertEquals(expected, elements);
    }

    @Test
    public void testOpenOpenAscending() {
        // (-5.0d, 5.0d), 3.0d = -2.0d, 1.0d, 4.0d
        DoubleRange range = Ranges.doubleRange(-5.0d, BoundType.OPEN, 5.0d,
                                            BoundType.OPEN, 3.0d);
        // (-5.0d, 5.0d), 3.0d = -2.0d, 1.0d, 4.0d
        List<Double> expected = Arrays.asList(-2.0d, 1.0d, 4.0d);
        Collection<Double> elements = IteratorToGeneratorAdapter.adapt(range).toCollection();
        assertEquals(expected, elements);
    }

    @Test
    public void testSingleStepAscending() {
        // (-2.0d, 2.0d], 1.0d = -1.0d, 0.0d, 1.0d, 2.0d
        DoubleRange range = Ranges.doubleRange(-2.0d, BoundType.OPEN, 2.0d,
                                            BoundType.CLOSED, 1.0d);
        // (-2.0d, 2.0d], 1.0d = -1.0d, 0.0d, 1.0d, 2.0d
        List<Double> expected = Arrays.asList(-1.0d, 0.0d, 1.0d, 2.0d);
        Collection<Double> elements = IteratorToGeneratorAdapter.adapt(range).toCollection();
        assertEquals(expected, elements);
    }

    @Test
    public void testClosedClosedDescending() {
        // [5.0d, -5.0d], -3.0d = 5.0d, 2.0d, -1.0d, -4.0d
        DoubleRange range = Ranges.doubleRange(5.0d, BoundType.CLOSED, -5.0d,
                                            BoundType.CLOSED, -3.0d);
        // [5.0d, -5.0d], -3.0d = 5.0d, 2.0d, -1.0d, -4.0d
        List<Double> expected = Arrays.asList(5.0d, 2.0d, -1.0d, -4.0d);
        Collection<Double> elements = IteratorToGeneratorAdapter.adapt(range).toCollection();
        assertEquals(expected, elements);
    }

    @Test
    public void testOpenClosedDescending() {
        // (5.0d, -5.0d], -3.0d = 2.0d, -1.0d, -4.0d
        DoubleRange range = Ranges.doubleRange(5.0d, BoundType.OPEN, -5.0d,
                                            BoundType.CLOSED, -3.0d);
        // (5.0d, -5.0d], -3.0d = 2.0d, -1.0d, -4.0d
        List<Double> expected = Arrays.asList(2.0d, -1.0d, -4.0d);
        Collection<Double> elements = IteratorToGeneratorAdapter.adapt(range).toCollection();
        assertEquals(expected, elements);
    }

    @Test
    public void testClosedOpenDescending() {
        // [5.0d, -5.0d), -3.0d = 5.0d, 2.0d, -1.0d, -4.0d
        DoubleRange range = Ranges.doubleRange(5.0d, BoundType.CLOSED, -5.0d,
                                            BoundType.OPEN, -3.0d);
        // [5.0d, -5.0d), -3.0d = 5.0d, 2.0d, -1.0d, -4.0d
        List<Double> expected = Arrays.asList(5.0d, 2.0d, -1.0d, -4.0d);
        Collection<Double> elements = IteratorToGeneratorAdapter.adapt(range).toCollection();
        assertEquals(expected, elements);
    }

    @Test
    public void testOpenOpenDescending() {
        // (5.0d, -5.0d), -3.0d = 2.0d, -1.0d, -4.0d
        DoubleRange range = Ranges.doubleRange(5.0d, BoundType.OPEN, -5.0d,
                                            BoundType.OPEN, -3.0d);
        // (5.0d, -5.0d), -3.0d = 2.0d, -1.0d, -4.0d
        List<Double> expected = Arrays.asList(2.0d, -1.0d, -4.0d);
        Collection<Double> elements = IteratorToGeneratorAdapter.adapt(range).toCollection();
        assertEquals(expected, elements);
    }

    @Test
    public void testSingleStepDescending() {
        // [2.0d, -2.0d), -1.0d = 2.0d, 1.0d, 0.0d, -1.0d
        DoubleRange range = Ranges.doubleRange(2.0d, BoundType.CLOSED, -2.0d,
                                            BoundType.OPEN, -1.0d);
        // [2.0d, -2.0d), -1.0d = 2.0d, 1.0d, 0.0d, -1.0d
        List<Double> expected = Arrays.asList(2.0d, 1.0d, 0.0d, -1.0d);
        Collection<Double> elements = IteratorToGeneratorAdapter.adapt(range).toCollection();
        assertEquals(expected, elements);
    }

    @Test
    public void testAscending() {
        final List<Double> list = new ArrayList<Double>();
        for (double d : ascDoubleRange) {
            list.add(d);
        }
        assertTrue(expectedAsc.containsAll(list));
    }

    @Test
    public void testDescending() {
        final List<Double> list = new ArrayList<Double>();
        for (double d : descDoubleRange) {
            list.add(d);
        }
        assertTrue(expectedDesc.containsAll(list));
    }

    @Test
    public void testToCollection() {
        Collection<Double> ascCol = IteratorToGeneratorAdapter.adapt(ascDoubleRange).toCollection();
        assertEquals("Different collections", expectedAsc, ascCol);
        Collection<Double> descCol = IteratorToGeneratorAdapter.adapt(descDoubleRange).toCollection();
        assertEquals("Different collections", expectedDesc, descCol);
    }

    @Test
    public void testTransformedGenerator() {
        double expected = 45.0d;
        double total = IteratorToGeneratorAdapter.adapt(ascDoubleRange)
            .to(new Function<Generator<? extends Double>, Double>() {

                public Double evaluate(Generator<? extends Double> obj) {
                    double total = 0.0d;
                    for (Object element : obj.toCollection()) {
                        total += (Double) element;
                    }
                    return total;
                }
            });
        assertTrue(expected == total);
        expected = 55.0d;
        total = IteratorToGeneratorAdapter.adapt(descDoubleRange)
            .to(new Function<Generator<? extends Double>, Double>() {

                public Double evaluate(Generator<? extends Double> obj) {
                    double total = 0.0d;
                    for (Object element : obj.toCollection()) {
                        total += (Double) element;
                    }
                    return total;
                }
            });
        assertTrue(expected == total);
    }

    // Range tests
    // ---------------------------------------------------------------

    @Test
    public void testEmptyRanges() {
        DoubleRange empty1 = Ranges.doubleRange(-2, BoundType.OPEN, -1,
                                             BoundType.OPEN, 2);
        assertTrue("The range was expected to be empty.", empty1.isEmpty());
        DoubleRange empty2 = Ranges.doubleRange(2, BoundType.OPEN, 0,
                                             BoundType.OPEN, -2);
        assertTrue("The range was expected to be empty.", empty2.isEmpty());
        DoubleRange empty3 = Ranges.doubleRange(0, BoundType.OPEN, 1,
                                             BoundType.CLOSED, 2);
        assertTrue("The range was expected to be empty.", empty3.isEmpty());
        DoubleRange empty4 = Ranges.doubleRange(-3, BoundType.OPEN, -3,
                                             BoundType.OPEN, 1);
        assertTrue("The range was expected to be empty.", empty4.isEmpty());
        DoubleRange empty5 = Ranges.doubleRange(-3, BoundType.CLOSED, -3,
                                             BoundType.OPEN, 1);
        assertTrue("The range was expected to be empty.", empty5.isEmpty());
        DoubleRange empty6 = Ranges.doubleRange(1, BoundType.OPEN, 0,
                                             BoundType.CLOSED, -2);
        assertTrue("The range was expected to be empty.", empty6.isEmpty());
        DoubleRange notEmpty1 = Ranges.doubleRange(-3, BoundType.CLOSED, -3,
                                                BoundType.CLOSED, 1);
        assertFalse("The range was not expected to be empty.",
                    notEmpty1.isEmpty());
        DoubleRange notEmpty2 = Ranges.doubleRange(-3, BoundType.OPEN, -2,
                                                BoundType.CLOSED, 1);
        assertFalse("The range was not expected to be empty.",
                    notEmpty2.isEmpty());
        DoubleRange notEmpty3 = Ranges.doubleRange(2, BoundType.OPEN, 1,
                                                BoundType.CLOSED, -1);
        assertFalse("The range was not expected to be empty.",
                    notEmpty3.isEmpty());
        DoubleRange notEmpty4 = Ranges.doubleRange(2, BoundType.CLOSED, 1,
                                                BoundType.OPEN, -1);
        assertFalse("The range was not expected to be empty.",
                    notEmpty4.isEmpty());
        DoubleRange notEmpty5 = Ranges.doubleRange(1, BoundType.CLOSED, 2,
                                                BoundType.OPEN, 1);
        assertFalse("The range was not expected to be empty.",
                    notEmpty5.isEmpty());
    }

    @Test
    public void testClosedClosedAscendingContains() {
        // [-5, 5], 3 = -5, -2, 1, 4
        DoubleRange range = Ranges.doubleRange(-5, BoundType.CLOSED, 5,
                                            BoundType.CLOSED, 3);
        // [-5, 5], 3 = -5, -2, 1, 4
        List<Double> arr = Arrays.asList(-5.0, -2.0, 1.0, 4.0);
        for (Double element : arr) {
            assertTrue("Expected element [" + element +
                               "] is missing in range [" + range + "]",
                       range.contains(element));
        }
        List<Double> elementsNotPresent = new ArrayList<Double>(fullRange);
        elementsNotPresent.removeAll(arr);
        for (Double element : elementsNotPresent) {
            assertFalse("Unexpected element [" + element +
                                "] is present in range [" + range + "]",
                        range.contains(element));
        }
    }

    @Test
    public void testOpenClosedAscendingContains() {
        // (-5, 5], 3 = -2, 1, 4
        DoubleRange range = Ranges.doubleRange(-5, BoundType.OPEN, 5,
                                            BoundType.CLOSED, 3);
        // (-5, 5], 3 = -2, 1, 4
        List<Double> arr = Arrays.asList(-2.0, 1.0, 4.0);
        for (Double element : arr) {
            assertTrue("Expected element [" + element +
                               "] is missing in range [" + range + "]",
                       range.contains(element));
        }
        List<Double> elementsNotPresent = new ArrayList<Double>(fullRange);
        elementsNotPresent.removeAll(arr);
        for (Double element : elementsNotPresent) {
            assertFalse("Unexpected element [" + element +
                                "] is present in range [" + range + "]",
                        range.contains(element));
        }
    }

    @Test
    public void testClosedOpenAscendingContains() {
        // [-5, 5), 3 = -5, -2, 1, 4
        DoubleRange range = Ranges.doubleRange(-5, BoundType.CLOSED, 5,
                                            BoundType.OPEN, 3);
        // (-5, 5], 3 = -5, -2, 1, 4
        List<Double> arr = Arrays.asList(-5.0, -2.0, 1.0, 4.0);
        for (Double element : arr) {
            assertTrue("Expected element [" + element +
                               "] is missing in range [" + range + "]",
                       range.contains(element));
        }
        List<Double> elementsNotPresent = new ArrayList<Double>(fullRange);
        elementsNotPresent.removeAll(arr);
        for (Double element : elementsNotPresent) {
            assertFalse("Unexpected element [" + element +
                                "] is present in range [" + range + "]",
                        range.contains(element));
        }
    }

    @Test
    public void testOpenOpenAscendingContains() {
        // (-5, 5), 3 = -2, 1, 4
        DoubleRange range = Ranges.doubleRange(-5, BoundType.OPEN, 5,
                                            BoundType.OPEN, 3);
        // (-5, 5), 3 = -2, 1, 4
        List<Double> arr = Arrays.asList(-2.0, 1.0, 4.0);
        for (Double element : arr) {
            assertTrue("Expected element [" + element +
                               "] is missing in range [" + range + "]",
                       range.contains(element));
        }
        List<Double> elementsNotPresent = new ArrayList<Double>(fullRange);
        elementsNotPresent.removeAll(arr);
        for (Double element : elementsNotPresent) {
            assertFalse("Unexpected element [" + element +
                                "] is present in range [" + range + "]",
                        range.contains(element));
        }
    }

    @Test
    public void testContainsSingleStepAscending() {
        // (-2, 2], 1 = -1, 0, 1, 2
        DoubleRange ascendingRange = Ranges.doubleRange(-2, BoundType.OPEN, 2,
                                                     BoundType.CLOSED, 1);
        // (-2, 2], 1 = -1, 0, 1, 2
        List<Double> arr = Arrays.asList(-1.0, 0.0, 1.0, 2.0);
        for (Double element : arr) {
            assertTrue("Expected element [" + element +
                               "] is missing in range [" + ascendingRange + "]",
                       ascendingRange.contains(element));
        }
        List<Double> elementsNotPresent = new ArrayList<Double>(fullRange);
        elementsNotPresent.removeAll(arr);
        for (Double element : elementsNotPresent) {
            assertFalse("Unexpected element [" + element +
                                "] is present in range [" + ascendingRange +
                                "]",
                        ascendingRange.contains(element));
        }
    }

    @Test
    public void testClosedClosedDescendingContains() {
        // [5, -5], -3 = 5, 2, -1, -4
        DoubleRange range = Ranges.doubleRange(5, BoundType.CLOSED, -5,
                                            BoundType.CLOSED, -3);
        // [5, -5], -3 = 5, 2, -1, -4
        List<Double> arr = Arrays.asList(5.0, 2.0, -1.0, -4.0);
        for (Double element : arr) {
            assertTrue("Expected element [" + element +
                               "] is missing in range [" + range + "]",
                       range.contains(element));
        }
        List<Double> elementsNotPresent = new ArrayList<Double>(fullRange);
        elementsNotPresent.removeAll(arr);
        for (Double element : elementsNotPresent) {
            assertFalse("Unexpected element [" + element +
                                "] is present in range [" + range + "]",
                        range.contains(element));
        }
    }

    @Test
    public void testOpenClosedDescendingContains() {
        // (5, -5], -3 = 2, -1, -4
        DoubleRange range = Ranges.doubleRange(5, BoundType.OPEN, -5,
                                            BoundType.CLOSED, -3);
        // (5, -5], -3 = 2, -1, -4
        List<Double> arr = Arrays.asList(2.0, -1.0, -4.0);
        for (Double element : arr) {
            assertTrue("Expected element [" + element +
                               "] is missing in range [" + range + "]",
                       range.contains(element));
        }
        List<Double> elementsNotPresent = new ArrayList<Double>(fullRange);
        elementsNotPresent.removeAll(arr);
        for (Double element : elementsNotPresent) {
            assertFalse("Unexpected element [" + element +
                                "] is present in range [" + range + "]",
                        range.contains(element));
        }
    }

    @Test
    public void testClosedOpenDescendingContains() {
        // [5, -5), -3 = 5, 2, -1, -4
        DoubleRange range = Ranges.doubleRange(5, BoundType.CLOSED, -5,
                                            BoundType.OPEN, -3);
        // [5, -5), -3 = 5, 2, -1, -4
        List<Double> arr = Arrays.asList(5.0, 2.0, -1.0, -4.0);
        for (Double element : arr) {
            assertTrue("Expected element [" + element +
                               "] is missing in range [" + range + "]",
                       range.contains(element));
        }
        List<Double> elementsNotPresent = new ArrayList<Double>(fullRange);
        elementsNotPresent.removeAll(arr);
        for (Double element : elementsNotPresent) {
            assertFalse("Unexpected element [" + element +
                                "] is present in range [" + range + "]",
                        range.contains(element));
        }
    }

    @Test
    public void testOpenOpenDescendingContains() {
        // (5, -5), -3 = 2, -1, -4
        DoubleRange range = Ranges.doubleRange(5, BoundType.OPEN, -5,
                                            BoundType.OPEN, -3);
        // (5, -5), -3 = 2, -1, -4
        List<Double> arr = Arrays.asList(2.0, -1.0, -4.0);
        for (Double element : arr) {
            assertTrue("Expected element [" + element +
                               "] is missing in range [" + range + "]",
                       range.contains(element));
        }
        List<Double> elementsNotPresent = new ArrayList<Double>(fullRange);
        elementsNotPresent.removeAll(arr);
        for (Double element : elementsNotPresent) {
            assertFalse("Unexpected element [" + element +
                                "] is present in range [" + range + "]",
                        range.contains(element));
        }
    }

    @Test
    public void testContainsSingleStepDescending() {
        // [2, -2), -1 = 2, 1, 0, -1
        DoubleRange descendingRange = Ranges.doubleRange(2, BoundType.CLOSED, -2,
                                                      BoundType.OPEN, -1);
        // [2, -2), -1 = 2, 1, 0, -1
        List<Double> arr = Arrays.asList(2.0, 1.0, 0.0, -1.0);
        for (Double element : arr) {
            assertTrue("Expected element [" + element +
                               "] is missing in range [" + descendingRange +
                               "]",
                       descendingRange.contains(element));
        }
        List<Double> elementsNotPresent = new ArrayList<Double>(fullRange);
        elementsNotPresent.removeAll(arr);
        for (Double element : elementsNotPresent) {
            assertFalse("Unexpected element [" + element +
                                "] is present in range [" + descendingRange +
                                "]",
                        descendingRange.contains(element));
        }
    }

    @Test
    public void testContainsNullOrEmpty() {
        DoubleRange range = Ranges.doubleRange(-2, BoundType.OPEN, 2,
                                            BoundType.CLOSED, 1);
        assertFalse(range.contains(null));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testContainsAll() {
        // (-2, 2], 1 = -1, 0, 1, 2
        DoubleRange range = Ranges.doubleRange(-2, BoundType.OPEN, 2,
                                            BoundType.CLOSED, 1);
        List<Double> list = Arrays.asList(-1.0, 0.0, 1.0, 2.0);
        assertTrue("Range [" + range +
                   "] was expected to contain all elements from list [" + list +
                   "]", range.containsAll(list));
        List<Double> listWithExtraElements = Arrays.asList(2.0, -1.0, 0.0, 1.0,
                                                           2.0, 3.0);
        assertFalse("Range [" + range + "] has more elements than expected",
                    range.containsAll(listWithExtraElements));
        assertFalse(range.containsAll(null));
        assertFalse(range.containsAll(Collections.EMPTY_LIST));
    }

    @Test
    public void testEquals()
        throws Exception {
        // equals basic properties
        DoubleRange range = Ranges.doubleRange(-2, BoundType.CLOSED, 2,
                                            BoundType.OPEN, 1);
        assertEquals("equals must be reflexive", range, range);
        assertEquals("hashCode must be reflexive", range.hashCode(),
                     range.hashCode());
        assertTrue(!range.equals(null)); // should be able to compare to null

        Object range2 = Ranges.doubleRange(-2, BoundType.CLOSED, 2,
                                        BoundType.OPEN, 1);
        if (range.equals(range2)) {
            assertEquals("equals implies hash equals", range.hashCode(),
                         range2.hashCode());
            assertEquals("equals must be symmetric", range2, range);
        } else {
            assertTrue("equals must be symmetric", !range2.equals(range));
        }

        // Changing attributes
        Object range3 = Ranges.doubleRange(-1, BoundType.CLOSED, 2,
                                        BoundType.OPEN, 1);
        assertFalse("Invalid equals after changing attributes",
                    range.equals(range3));

        Object range4 = Ranges.doubleRange(-2, BoundType.OPEN, 2, BoundType.OPEN,
                                        1);
        assertFalse("Invalid equals after changing attributes",
                    range.equals(range4));

        Object range5 = Ranges.doubleRange(-2, BoundType.CLOSED, 1,
                                        BoundType.OPEN, 1);
        assertFalse("Invalid equals after changing attributes",
                    range.equals(range5));

        Object range6 = Ranges.doubleRange(-2, BoundType.CLOSED, 2,
                                        BoundType.CLOSED, 1);
        assertFalse("Invalid equals after changing attributes",
                    range.equals(range6));

        Object range7 = Ranges.doubleRange(-2, BoundType.CLOSED, 2,
                                        BoundType.OPEN, 2);
        assertFalse("Invalid equals after changing attributes",
                    range.equals(range7));

        // Using different constructors
        DoubleRange range8 = Ranges.doubleRange(Long.valueOf(-2), Long.valueOf(2),
                                             Long.valueOf(1));
        assertEquals("Invalid equals using different constructor", range,
                     range8);

        DoubleRange range9 = Ranges.doubleRange(Long.valueOf(-2), Long.valueOf(2));
        assertEquals("Invalid equals using different constructor", range,
                     range9);

        Endpoint<Double> leftEndpoint = new Endpoint<Double>(-2.0d,
                                                             BoundType.CLOSED);
        Endpoint<Double> rightEndpoint = new Endpoint<Double>(2.0d,
                                                              BoundType.OPEN);
        DoubleRange range10 = Ranges.doubleRange(leftEndpoint, rightEndpoint, 1.0d);
        assertEquals("Invalid equals using different constructor", range,
                     range10);
    }

    @Test
    public void testToString() {
        DoubleRange range = Ranges.doubleRange(-2, BoundType.OPEN, 2,
                                            BoundType.CLOSED, 1);
        assertEquals("Wrong string value", "DoubleRange<(-2.0, 2.0], 1.0>",
                     range.toString());
    }

    @Test
    public void testConstructorUsingSameEndpoint() {
        Endpoint<Double> uniqueEndpoint = new Endpoint<Double>(10.0d,
                                                               BoundType.CLOSED);
        try {
            Ranges.doubleRange(uniqueEndpoint, uniqueEndpoint, 1.0d);
        } catch (IllegalArgumentException e) {
            fail("Not expected to get here");
        }
    }

    @Test
    public void testInvalidRange() {
        try {
            Ranges.doubleRange(10.0d, BoundType.OPEN, -5.0d, BoundType.CLOSED,
                            10.0d);
            fail("Not expected to get here");
        } catch (IllegalArgumentException e) {
            // Do nothing
        }
        Endpoint<Double> leftEndpoint = new Endpoint<Double>(10.0d,
                                                             BoundType.CLOSED);
        Endpoint<Double> rightEndpoint = new Endpoint<Double>(-5.0d,
                                                              BoundType.OPEN);
        try {
            Ranges.doubleRange(leftEndpoint, rightEndpoint, 1.0f);
            fail("Not expected to get here");
        } catch (IllegalArgumentException e) {
            // Do nothing
        }
    }

    @Test
    public void testDefaultStep() {
        assertEquals("Invalid default step", Double.valueOf(-1.0d),
                     DoubleRange.DEFAULT_STEP.evaluate(10.0d, 1.0d));
        assertEquals("Invalid default step", Double.valueOf(1.0d),
                     DoubleRange.DEFAULT_STEP.evaluate(1.0d, 10.0d));
    }

}
