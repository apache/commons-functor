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
 * @version $Revision: $ $Date: $
 */
public class TestCharacterRange extends BaseFunctorTest {
    // A base range with all chars between a and m
    private final List<Character> fullRange = Collections.unmodifiableList(Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm'));

    // Attributes
    // ------------------------------------------------------------------------

    private CharacterRange ascCharRange = null;
    private CharacterRange descCharRange = null;
    private Collection<Character> expectedAsc = null;
    private Collection<Character> expectedDesc = null;
    
    // Test set up
    // ------------------------------------------------------------------------
    @Before
    public void setUp() {
        ascCharRange = Ranges.characterRange('b', 'k');
        descCharRange = Ranges.characterRange('k', 'b');
    	expectedAsc = Arrays.asList('b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k');
    	expectedDesc = Arrays.asList('k', 'j', 'i', 'h', 'g', 'f', 'e', 'd', 'c', 'b');
    }
    
    @After
    public void tearDown() {
    	ascCharRange = null;
    	descCharRange = null;
    }
    
    @Override
    protected Object makeFunctor()
        throws Exception {
        return Ranges.characterRange('b', 'k');
    }

    // Generator tests
    // ---------------------------------------------------------------

    @Test
    public void testStepChecking() {
        {
            Ranges.characterRange('b', 'b', 0); // step of 0 is ok when range is
                                             // empty
        }
        {
            Ranges.characterRange('b', 'b', 1); // positive step is ok when range
                                             // is empty
        }
        {
            Ranges.characterRange('b', 'b', -1); // negative step is ok when range
                                              // is empty
        }
        {
            Ranges.characterRange('a', 'b', 10); // big steps are ok
        }
        {
            Ranges.characterRange('b', 'a', -10); // big steps are ok
        }
        try {
            Ranges.characterRange('a', 'b', 0);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
        try {
            Ranges.characterRange('a', 'b', -1);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
        try {
            Ranges.characterRange('b', 'a', 1);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    @Test
    public void testObjectConstructor() {
        CharacterRange range = Ranges.characterRange(Character.valueOf('a'),
                                                     Character.valueOf('e'));
        assertEquals("[a, b, c, d, e]", IteratorToGeneratorAdapter.adapt(range).toCollection().toString());
        range = Ranges.characterRange(Character.valueOf('a'), Character.valueOf('e'),
                                      Integer.valueOf(1));
        assertEquals("[a, b, c, d, e]", IteratorToGeneratorAdapter.adapt(range).toCollection().toString());
    }

    @Test
    public void testReverseStep() {
        CharacterRange range = Ranges.characterRange('k', 'a', -2);
        assertEquals("[k, i, g, e, c, a]", IteratorToGeneratorAdapter.adapt(range).toCollection().toString());
    }

    @Test
    public void testStep() {
        CharacterRange range = Ranges.characterRange('a', 'k', 2);
        assertEquals("[a, c, e, g, i, k]", IteratorToGeneratorAdapter.adapt(range).toCollection().toString());
    }

    @Test
    public void testForwardRange() {
        CharacterRange range = Ranges.characterRange('a', 'e');
        assertEquals("[a, b, c, d, e]", IteratorToGeneratorAdapter.adapt(range).toCollection().toString());
    }

    @Test
    public void testReverseRange() {
        CharacterRange range = Ranges.characterRange('e', 'a');
        assertEquals("[e, d, c, b, a]", IteratorToGeneratorAdapter.adapt(range).toCollection().toString());
    }

    // @Test
    // public void testEdgeCase() {
    // CharacterRange range = Ranges.characterRange(Character.MAX_VALUE-3,
    // Character.MAX_VALUE);
    // assertEquals("[2147483644, 2147483645, 2147483646]",
    // IteratorToGeneratorAdapter.adapt(range).toCollection().toString());
    // assertEquals("[2147483644, 2147483645, 2147483646]",
    // IteratorToGeneratorAdapter.adapt(range).toCollection().toString());
    // }

    @Test
    public void testBoundaries() {
        CharacterRange range = Ranges.characterRange('b', 'l');
        assertEquals(new Endpoint<Comparable<?>>('b', BoundType.CLOSED),
                     range.getLeftEndpoint());
        assertEquals(new Endpoint<Comparable<?>>('l', BoundType.CLOSED),
                     range.getRightEndpoint());
    }

    @Test
    public void testClosedClosedAscending() {
        // [b, l], 3 = b, e, h, k
        CharacterRange range =  Ranges.characterRange('b', BoundType.CLOSED, 'l', BoundType.CLOSED, 3);
        // [b, l], 3 = b, e, h, k
        List<Character> expected = Arrays.asList('b', 'e', 'h', 'k');
        Collection<Character> elements = IteratorToGeneratorAdapter.adapt(range).toCollection();
        assertEquals(expected, elements);
    }

    @Test
    public void testOpenClosedAscending() {
        // (b, l], 3 = e, h, k
        CharacterRange range =  Ranges.characterRange('b', BoundType.OPEN, 'l', BoundType.CLOSED, 3);
        // (b, l], 3 = e, h, k
        List<Character> expected = Arrays.asList('e', 'h', 'k');
        Collection<Character> elements = IteratorToGeneratorAdapter.adapt(range).toCollection();
        assertEquals(expected, elements);
    }

    @Test
    public void testClosedOpenAscending() {
        // [b, l), 3 = b, e, h, k
        CharacterRange range =  Ranges.characterRange('b', BoundType.CLOSED, 'l', BoundType.OPEN, 3);
        // [b, l), 3 = b, e, h, k
        List<Character> expected = Arrays.asList('b', 'e', 'h', 'k');
        Collection<Character> elements = IteratorToGeneratorAdapter.adapt(range).toCollection();
        assertEquals(expected, elements);
    }

    @Test
    public void testOpenOpenAscending() {
        // (b, l), 3 = e, h, k
        CharacterRange range =  Ranges.characterRange('b', BoundType.OPEN, 'l', BoundType.OPEN, 3);
        // (b, l), 3 = e, h, k
        List<Character> expected = Arrays.asList('e', 'h', 'k');
        Collection<Character> elements = IteratorToGeneratorAdapter.adapt(range).toCollection();
        assertEquals(expected, elements);
    }

    @Test
    public void testSingleStepAscending() {
        // (d, h], 1 = e, f, g, h
        CharacterRange range =  Ranges.characterRange('d', BoundType.OPEN, 'h', BoundType.CLOSED, 1);
        // (d, h], 1 = e, f, g, h
        List<Character> expected = Arrays.asList('e', 'f', 'g', 'h');
        Collection<Character> elements = IteratorToGeneratorAdapter.adapt(range).toCollection();
        assertEquals(expected, elements);
    }

    @Test
    public void testClosedClosedDescending() {
        // [l, b], -3 = l, i, f, c
        CharacterRange range = Ranges.characterRange('l', BoundType.CLOSED, 'b',
                                                  BoundType.CLOSED, -3);
        // [l, b], -3 = l, i, f, c
        List<Character> expected = Arrays.asList('l', 'i', 'f', 'c');
        Collection<Character> elements = IteratorToGeneratorAdapter.adapt(range).toCollection();
        assertEquals(expected, elements);
    }

    @Test
    public void testOpenClosedDescending() {
        // (l, b], -3 = i, f, c
        CharacterRange range = Ranges.characterRange('l', BoundType.OPEN, 'b',
                                                  BoundType.CLOSED, -3);
        // (l, b], -3 = i, f, c
        List<Character> expected = Arrays.asList('i', 'f', 'c');
        Collection<Character> elements = IteratorToGeneratorAdapter.adapt(range).toCollection();
        assertEquals(expected, elements);
    }

    @Test
    public void testClosedOpenDescending() {
        // [l, b), -3 = l, i, f, c
        CharacterRange range = Ranges.characterRange('l', BoundType.CLOSED, 'b',
                                                  BoundType.OPEN, -3);
        // [l, b), -3 = l, i, f, c
        List<Character> expected = Arrays.asList('l', 'i', 'f', 'c');
        Collection<Character> elements = IteratorToGeneratorAdapter.adapt(range).toCollection();
        assertEquals(expected, elements);
    }

    @Test
    public void testOpenOpenDescending() {
        // (l, b), -3 = i, f, c
        CharacterRange range = Ranges.characterRange('l', BoundType.OPEN, 'b',
                                                  BoundType.OPEN, -3);
        // (l, b), -3 = i, f, c
        List<Character> expected = Arrays.asList('i', 'f', 'c');
        Collection<Character> elements = IteratorToGeneratorAdapter.adapt(range).toCollection();
        assertEquals(expected, elements);
    }

    @Test
    public void testSingleStepDescending() {
        // [h, d), -1 = h, g, f
        CharacterRange range = Ranges.characterRange('h', BoundType.CLOSED, 'e',
                                                  BoundType.OPEN, -1);
        // [h, d), -1 = h, g, f
        List<Character> expected = Arrays.asList('h', 'g', 'f');
        Collection<Character> elements = IteratorToGeneratorAdapter.adapt(range).toCollection();
        assertEquals(expected, elements);
    }

    @Test
    public void testAscending() {
        final List<Character> list = new ArrayList<Character>();
        for (char c : ascCharRange) {
            list.add(c);
        }
        assertTrue(expectedAsc.containsAll(list));
    }

    @Test
    public void testDescending() {
        final List<Character> list = new ArrayList<Character>();
        for (char c : ascCharRange) {
            list.add(c);
        }
        assertTrue(expectedDesc.containsAll(list));
    }

    @Test
    public void testToCollection() {
        Collection<Character> ascCol = IteratorToGeneratorAdapter.adapt(ascCharRange).toCollection();
        assertEquals("Different collections", expectedAsc, ascCol);
        Collection<Character> descCol = IteratorToGeneratorAdapter.adapt(descCharRange).toCollection();
        assertEquals("Different collections", expectedDesc, descCol);
    }

    @Test
    public void testTransformedGenerator() {
        int expected = 10;
        List<Character> list = IteratorToGeneratorAdapter.adapt(ascCharRange)
            .to(new Function<Generator<? extends Character>, List<Character>>() {

                public List<Character> evaluate(Generator<? extends Character> obj) {
                    List<Character> chars = new ArrayList<Character>();
                    for (Object element : obj.toCollection()) {
                        chars.add((Character) element);
                    }
                    return chars;
                }
            });
        assertEquals(expected, list.size());
        expected = 10;
        list = IteratorToGeneratorAdapter.adapt(descCharRange)
            .to(new Function<Generator<? extends Character>, List<Character>>() {

                public List<Character> evaluate(Generator<? extends Character> obj) {
                    List<Character> chars = new ArrayList<Character>();
                    for (Object element : obj.toCollection()) {
                        chars.add((Character) element);
                    }
                    return chars;
                }
            });
        assertEquals(expected, list.size());
    }

    // Range tests
    // ---------------------------------------------------------------

    @Test
    public void testEmptyRanges() {
        CharacterRange empty1 = Ranges.characterRange('a', BoundType.OPEN, 'b',
                                                   BoundType.OPEN, 2);
        assertTrue("The range was expected to be empty.", empty1.isEmpty());
        CharacterRange empty2 = Ranges.characterRange('c', BoundType.OPEN, 'a',
                                                   BoundType.OPEN, -2);
        assertTrue("The range was expected to be empty.", empty2.isEmpty());
        CharacterRange empty3 = Ranges.characterRange('a', BoundType.OPEN, 'b',
                                                   BoundType.CLOSED, 2);
        assertTrue("The range was expected to be empty.", empty3.isEmpty());
        CharacterRange empty4 = Ranges.characterRange('a', BoundType.OPEN, 'a',
                                                   BoundType.OPEN, 1);
        assertTrue("The range was expected to be empty.", empty4.isEmpty());
        CharacterRange empty5 = Ranges.characterRange('b', BoundType.CLOSED, 'b',
                                                   BoundType.OPEN, 1);
        assertTrue("The range was expected to be empty.", empty5.isEmpty());
        CharacterRange empty6 = Ranges.characterRange('d', BoundType.OPEN, 'c',
                                                   BoundType.CLOSED, -2);
        assertTrue("The range was expected to be empty.", empty6.isEmpty());
        CharacterRange notEmpty1 = Ranges.characterRange('a', BoundType.CLOSED,
                                                      'a', BoundType.CLOSED, 1);
        assertFalse("The range was not expected to be empty.",
                    notEmpty1.isEmpty());
        CharacterRange notEmpty2 = Ranges.characterRange('a', BoundType.OPEN, 'b',
                                                      BoundType.CLOSED, 1);
        assertFalse("The range was not expected to be empty.",
                    notEmpty2.isEmpty());
        CharacterRange notEmpty3 = Ranges.characterRange('b', BoundType.OPEN, 'a',
                                                      BoundType.CLOSED, -1);
        assertFalse("The range was not expected to be empty.",
                    notEmpty3.isEmpty());
        CharacterRange notEmpty4 = Ranges.characterRange('b', BoundType.CLOSED,
                                                      'a', BoundType.OPEN, -1);
        assertFalse("The range was not expected to be empty.",
                    notEmpty4.isEmpty());
        CharacterRange notEmpty5 = Ranges.characterRange('a', BoundType.CLOSED,
                                                      'b', BoundType.OPEN, 1);
        assertFalse("The range was not expected to be empty.",
                    notEmpty5.isEmpty());
    }

    @Test
    public void testClosedClosedAscendingContains() {
        // [b, l], 3 = 'b', 'e', 'h', 'k'
        CharacterRange range = Ranges.characterRange('b', BoundType.CLOSED, 'l',
                                                  BoundType.CLOSED, 3);
        // [b, l], 3 = 'b', 'e', 'h', 'k'
        List<Character> arr = Arrays.asList('b', 'e', 'h', 'k');
        for (Character element : arr) {
            assertTrue("Expected element [" + element +
                               "] is missing in range [" + range + "]",
                       range.contains(element));
        }
        List<Character> elementsNotPresent = new ArrayList<Character>(fullRange);
        elementsNotPresent.removeAll(arr);
        for (Character element : elementsNotPresent) {
            assertFalse("Unexpected element [" + element +
                                "] is present in range [" + range + "]",
                        range.contains(element));
        }
    }

    @Test
    public void testOpenClosedAscendingContains() {
        // (b, l], 3 = 'e', 'h', 'k'
        CharacterRange range = Ranges.characterRange('b', BoundType.OPEN, 'l',
                                                  BoundType.CLOSED, 3);
        // (b, l], 3 = 'e', 'h', 'k'
        List<Character> arr = Arrays.asList('e', 'h', 'k');
        for (Character element : arr) {
            assertTrue("Expected element [" + element +
                               "] is missing in range [" + range + "]",
                       range.contains(element));
        }
        List<Character> elementsNotPresent = new ArrayList<Character>(fullRange);
        elementsNotPresent.removeAll(arr);
        for (Character element : elementsNotPresent) {
            assertFalse("Unexpected element [" + element +
                                "] is present in range [" + range + "]",
                        range.contains(element));
        }
    }

    @Test
    public void testClosedOpenAscendingContains() {
        // [b, l), 3 = 'b', 'e', 'h', 'k'
        CharacterRange range = Ranges.characterRange('b', BoundType.CLOSED, 'l',
                                                  BoundType.OPEN, 3);
        // [b, l), 3 = 'b', 'e', 'h', 'k'
        List<Character> arr = Arrays.asList('b', 'e', 'h', 'k');
        for (Character element : arr) {
            assertTrue("Expected element [" + element +
                               "] is missing in range [" + range + "]",
                       range.contains(element));
        }
        List<Character> elementsNotPresent = new ArrayList<Character>(fullRange);
        elementsNotPresent.removeAll(arr);
        for (Character element : elementsNotPresent) {
            assertFalse("Unexpected element [" + element +
                                "] is present in range [" + range + "]",
                        range.contains(element));
        }
    }

    @Test
    public void testOpenOpenAscendingContains() {
        // (b, l), 3 = 'e', 'h', 'k'
        CharacterRange range = Ranges.characterRange('b', BoundType.OPEN, 'l',
                                                  BoundType.OPEN, 3);
        // (b, l), 3 = 'e', 'h', 'k'
        List<Character> arr = Arrays.asList('e', 'h', 'k');
        for (Character element : arr) {
            assertTrue("Expected element [" + element +
                               "] is missing in range [" + range + "]",
                       range.contains(element));
        }
        List<Character> elementsNotPresent = new ArrayList<Character>(fullRange);
        elementsNotPresent.removeAll(arr);
        for (Character element : elementsNotPresent) {
            assertFalse("Unexpected element [" + element +
                                "] is present in range [" + range + "]",
                        range.contains(element));
        }
    }

    @Test
    public void testContainsSingleStepAscending() {
        // (b, l], 1 = 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l'
        CharacterRange ascendingRange = Ranges.characterRange('b', BoundType.OPEN,
                                                           'l',
                                                           BoundType.CLOSED, 1);
        // (b, l], 1 = 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l'
        List<Character> arr = Arrays.asList('c', 'd', 'e', 'f', 'g', 'h', 'i',
                                            'j', 'k', 'l');
        for (Character element : arr) {
            assertTrue("Expected element [" + element +
                               "] is missing in range [" + ascendingRange + "]",
                       ascendingRange.contains(element));
        }
        List<Character> elementsNotPresent = new ArrayList<Character>(fullRange);
        elementsNotPresent.removeAll(arr);
        for (Character element : elementsNotPresent) {
            assertFalse("Unexpected element [" + element +
                                "] is present in range [" + ascendingRange +
                                "]",
                        ascendingRange.contains(element));
        }
    }

    @Test
    public void testClosedClosedDescendingContains() {
        // [l, b], -3 = 'l', 'i', 'f', 'c'
        CharacterRange range = Ranges.characterRange('l', BoundType.CLOSED, 'b',
                                                  BoundType.CLOSED, -3);
        // [l, b], -3 = 'l', 'i', 'f', 'c'
        List<Character> arr = Arrays.asList('l', 'i', 'f', 'c');
        for (Character element : arr) {
            assertTrue("Expected element [" + element +
                               "] is missing in range [" + range + "]",
                       range.contains(element));
        }
        List<Character> elementsNotPresent = new ArrayList<Character>(fullRange);
        elementsNotPresent.removeAll(arr);
        for (Character element : elementsNotPresent) {
            assertFalse("Unexpected element [" + element +
                                "] is present in range [" + range + "]",
                        range.contains(element));
        }
    }

    @Test
    public void testOpenClosedDescendingContains() {
        // (l, b], -3 = 'i', 'f', 'c'
        CharacterRange range = Ranges.characterRange('l', BoundType.OPEN, 'b',
                                                  BoundType.CLOSED, -3);
        // (l, b], -3 = 'i', 'f', 'c'
        List<Character> arr = Arrays.asList('i', 'f', 'c');
        for (Character element : arr) {
            assertTrue("Expected element [" + element +
                               "] is missing in range [" + range + "]",
                       range.contains(element));
        }
        List<Character> elementsNotPresent = new ArrayList<Character>(fullRange);
        elementsNotPresent.removeAll(arr);
        for (Character element : elementsNotPresent) {
            assertFalse("Unexpected element [" + element +
                                "] is present in range [" + range + "]",
                        range.contains(element));
        }
    }

    @Test
    public void testClosedOpenDescendingContains() {
        // [l, b), -3 = 'l', 'i', 'f', 'c'
        CharacterRange range = Ranges.characterRange('l', BoundType.CLOSED, 'b',
                                                  BoundType.OPEN, -3);
        // [l, b), -3 = 'l', 'i', 'f', 'c'
        List<Character> arr = Arrays.asList('l', 'i', 'f', 'c');
        for (Character element : arr) {
            assertTrue("Expected element [" + element +
                               "] is missing in range [" + range + "]",
                       range.contains(element));
        }
        List<Character> elementsNotPresent = new ArrayList<Character>(fullRange);
        elementsNotPresent.removeAll(arr);
        for (Character element : elementsNotPresent) {
            assertFalse("Unexpected element [" + element +
                                "] is present in range [" + range + "]",
                        range.contains(element));
        }
    }

    @Test
    public void testOpenOpenDescendingContains() {
        // (l, b), -3 = 'i', 'f', 'c'
        CharacterRange range = Ranges.characterRange('l', BoundType.OPEN, 'b',
                                                  BoundType.OPEN, -3);
        // (l, b), -3 = 'i', 'f', 'c'
        List<Character> arr = Arrays.asList('i', 'f', 'c');
        for (Character element : arr) {
            assertTrue("Expected element [" + element +
                               "] is missing in range [" + range + "]",
                       range.contains(element));
        }
        List<Character> elementsNotPresent = new ArrayList<Character>(fullRange);
        elementsNotPresent.removeAll(arr);
        for (Character element : elementsNotPresent) {
            assertFalse("Unexpected element [" + element +
                                "] is present in range [" + range + "]",
                        range.contains(element));
        }
    }

    @Test
    public void testContainsSingleStepDescending() {
        // [l, b), -1 = 'l', 'k', 'j', 'i', 'h', 'g', 'f', 'e', 'd', 'c'
        CharacterRange descendingRange = Ranges.characterRange('l',
                                                            BoundType.CLOSED,
                                                            'b',
                                                            BoundType.OPEN, -1);
        // [l, b), -1 = 'l', 'k', 'j', 'i', 'h', 'g', 'f', 'e', 'd', 'c'
        List<Character> arr = Arrays.asList('l', 'k', 'j', 'i', 'h', 'g', 'f',
                                            'e', 'd', 'c');
        for (Character element : arr) {
            assertTrue("Expected element [" + element +
                               "] is missing in range [" + descendingRange +
                               "]",
                       descendingRange.contains(element));
        }
        List<Character> elementsNotPresent = new ArrayList<Character>(fullRange);
        elementsNotPresent.removeAll(arr);
        for (Character element : elementsNotPresent) {
            assertFalse("Unexpected element [" + element +
                                "] is present in range [" + descendingRange +
                                "]",
                        descendingRange.contains(element));
        }
    }

    @Test
    public void testContainsNullOrEmpty() {
        CharacterRange range = Ranges.characterRange('a', BoundType.OPEN, 'r',
                                                  BoundType.CLOSED, 1);
        assertFalse(range.contains(null));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testContainsAll() {
        // (c, g], 1 = d, e, f, g
        CharacterRange range = Ranges.characterRange('c', BoundType.OPEN, 'g',
                                                  BoundType.CLOSED, 1);
        List<Character> list = Arrays.asList('d', 'e', 'f', 'g');
        assertTrue("Range [" + range +
                   "] was expected to contain all elements from list [" + list +
                   "]", range.containsAll(list));
        List<Character> listWithExtraElements = Arrays.asList('a', 'd', 'e',
                                                              'f', 'g', 'z');
        assertFalse("Range [" + range + "] has more elements than expected",
                    range.containsAll(listWithExtraElements));
        assertFalse(range.containsAll(null));
        assertFalse(range.containsAll(Collections.EMPTY_LIST));
    }

    @Test
    public void testEquals()
        throws Exception {
        // equals basic properties
        CharacterRange range = Ranges.characterRange('a', BoundType.CLOSED, 'e',
                                                  BoundType.OPEN, 1);
        assertEquals("equals must be reflexive", range, range);
        assertEquals("hashCode must be reflexive", range.hashCode(),
                     range.hashCode());
        assertTrue(!range.equals(null)); // should be able to compare to null

        Object range2 = Ranges.characterRange('a', BoundType.CLOSED, 'e',
                                           BoundType.OPEN, 1);
        if (range.equals(range2)) {
            assertEquals("equals implies hash equals", range.hashCode(),
                         range2.hashCode());
            assertEquals("equals must be symmetric", range2, range);
        } else {
            assertTrue("equals must be symmetric", !range2.equals(range));
        }

        // Changing attributes
        Object range3 = Ranges.characterRange('c', BoundType.CLOSED, 'e',
                                           BoundType.OPEN, 1);
        assertFalse("Invalid equals after changing attributes",
                    range.equals(range3));

        Object range4 = Ranges.characterRange('a', BoundType.OPEN, 'e',
                                           BoundType.OPEN, 1);
        assertFalse("Invalid equals after changing attributes",
                    range.equals(range4));

        Object range5 = Ranges.characterRange('a', BoundType.CLOSED, 'r',
                                           BoundType.OPEN, 1);
        assertFalse("Invalid equals after changing attributes",
                    range.equals(range5));

        Object range6 = Ranges.characterRange('a', BoundType.CLOSED, 'e',
                                           BoundType.CLOSED, 1);
        assertFalse("Invalid equals after changing attributes",
                    range.equals(range6));

        Object range7 = Ranges.characterRange('a', BoundType.CLOSED, 'e',
                                           BoundType.OPEN, 2);
        assertFalse("Invalid equals after changing attributes",
                    range.equals(range7));

        // Using different constructors
        Endpoint<Character> leftEndpoint = new Endpoint<Character>(
                                                                   'a',
                                                                   BoundType.CLOSED);
        Endpoint<Character> rightEndpoint = new Endpoint<Character>(
                                                                    'e',
                                                                    BoundType.OPEN);
        CharacterRange range8 = Ranges.characterRange(leftEndpoint, rightEndpoint,
                                                   1);
        assertEquals("Invalid equals using different constructor", range,
                     range8);
    }

    @Test
    public void testToString() {
        CharacterRange range = Ranges.characterRange('a', BoundType.OPEN, 'b',
                                                  BoundType.CLOSED, 1);
        assertEquals("Wrong string value", "CharacterRange<(a, b], 1>",
                     range.toString());
    }

    @Test
    public void testConstructorUsingSameEndpoint() {
        Endpoint<Character> uniqueEndpoint = new Endpoint<Character>(
                                                                     'a',
                                                                     BoundType.CLOSED);
        try {
            Ranges.characterRange(uniqueEndpoint, uniqueEndpoint, 1);
        } catch (IllegalArgumentException e) {
            fail("Not expected to get here");
        }
    }

    @Test
    public void testInvalidRange() {
        try {
            Ranges.characterRange('a', BoundType.OPEN, 'z', BoundType.CLOSED, -100);
            fail("Not expected to get here");
        } catch (IllegalArgumentException e) {
            // Do nothing
        }
        Endpoint<Character> leftEndpoint = new Endpoint<Character>(
                                                                   'a',
                                                                   BoundType.CLOSED);
        Endpoint<Character> rightEndpoint = new Endpoint<Character>(
                                                                    'z',
                                                                    BoundType.OPEN);
        try {
            Ranges.characterRange(leftEndpoint, rightEndpoint, -100);
            fail("Not expected to get here");
        } catch (IllegalArgumentException e) {
            // Do nothing
        }
    }

    @Test
    public void testDefaultStep() {
        assertEquals("Invalid default step", Integer.valueOf(-1),
                     CharacterRange.DEFAULT_STEP.evaluate('c', 'a'));
        assertEquals("Invalid default step", Integer.valueOf(1),
                     CharacterRange.DEFAULT_STEP.evaluate('a', 'c'));
    }

}
