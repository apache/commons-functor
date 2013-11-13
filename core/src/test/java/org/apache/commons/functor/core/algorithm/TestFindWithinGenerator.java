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
package org.apache.commons.functor.core.algorithm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.commons.functor.BaseFunctorTest;
import org.apache.commons.functor.Predicate;
import org.apache.commons.functor.adapter.LeftBoundPredicate;
import org.apache.commons.functor.core.IsEqual;
import org.apache.commons.functor.generator.loop.IteratorToGeneratorAdapter;
import org.junit.Test;

/**
 * Tests {@link FindWithinGenerator} algorithm.
 */
public class TestFindWithinGenerator extends BaseFunctorTest {

    @Override
    protected Object makeFunctor() throws Exception {
        return new FindWithinGenerator<Object>();
    }

    @Test
    public void testObjectEqualsWithIfNone() throws Exception {
        Object obj = new FindWithinGenerator<Object>(1);
        assertEquals("equals must be reflexive", obj, obj);
        assertEquals("hashCode must be reflexive", obj.hashCode(), obj.hashCode());
        assertTrue(!obj.equals(null)); // should be able to compare to null

        Object obj2 = new FindWithinGenerator<Object>(1);
        if (obj.equals(obj2)) {
            assertEquals("equals implies hash equals", obj.hashCode(), obj2.hashCode());
            assertEquals("equals must be symmetric", obj2, obj);
        } else {
            assertTrue("equals must be symmetric", !obj2.equals(obj));
        }
    }

    @Test
    public void testObjectEqualsWithNullDefault() throws Exception {
        Object obj = new FindWithinGenerator<Object>(null);
        assertEquals("equals must be reflexive", obj, obj);
        assertEquals("hashCode must be reflexive", obj.hashCode(), obj.hashCode());
        assertTrue(!obj.equals(null)); // should be able to compare to null

        Object obj2 = new FindWithinGenerator<Object>(null);
        if (obj.equals(obj2)) {
            assertEquals("equals implies hash equals", obj.hashCode(), obj2.hashCode());
            assertEquals("equals must be symmetric", obj2, obj);
        } else {
            assertTrue("equals must be symmetric", !obj2.equals(obj));
        }
    }

    @Test
    public void testEquals() {
        FindWithinGenerator<Object> f = new FindWithinGenerator<Object>();
        assertEquals(f, f);

        assertObjectsAreEqual(f, new FindWithinGenerator<Object>());
        assertObjectsAreEqual(new FindWithinGenerator<Object>(Double.valueOf(0)), new FindWithinGenerator<Object>(
            Double.valueOf(0)));
        assertObjectsAreNotEqual(f, new FindWithinGenerator<Object>(Integer.valueOf(0)));
    }

    @Test(expected = NoSuchElementException.class)
    public void testDetect() {
        assertEquals(Integer.valueOf(3), new FindWithinGenerator<Integer>().evaluate(
            IteratorToGeneratorAdapter.adapt(numbers.iterator()), equalsThree));
        new FindWithinGenerator<Integer>().evaluate(IteratorToGeneratorAdapter.adapt(numbers.iterator()),
            equalsTwentyThree);
    }

    @Test
    public void testDetectIfNone() {
        assertEquals(
            Integer.valueOf(3),
            new FindWithinGenerator<Integer>(Integer.valueOf(3)).evaluate(
                IteratorToGeneratorAdapter.adapt(numbers.iterator()), equalsThree));
        assertEquals("Xyzzy", new FindWithinGenerator<String>("Xyzzy").evaluate(
            IteratorToGeneratorAdapter.adapt(strings.iterator()), equalsXyZ));
    }

    @Test
    public void testInstance() {
        assertNotNull("FindWithinGenerator instance must not be null", FindWithinGenerator.instance());
    }

    // Attributes
    // ------------------------------------------------------------------------

    private List<Integer> numbers = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
    private List<String> strings = Arrays.asList("Zyx", "xxyZ");
    private Predicate<Integer> equalsThree = LeftBoundPredicate.bind(IsEqual.instance(), Integer.valueOf(3));
    private Predicate<Integer> equalsTwentyThree = LeftBoundPredicate.bind(IsEqual.instance(), Integer.valueOf(23));
    private Predicate<String> equalsXyZ = LeftBoundPredicate.bind(IsEqual.instance(), "xyZ");

}
