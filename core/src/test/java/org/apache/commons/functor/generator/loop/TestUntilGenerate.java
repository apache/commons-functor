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
package org.apache.commons.functor.generator.loop;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.functor.Predicate;
import org.apache.commons.functor.Procedure;
import org.apache.commons.functor.generator.Generator;
import org.apache.commons.functor.generator.loop.UntilGenerate;
import org.apache.commons.functor.range.IntegerRange;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the Until Generate class.
 * @version $Revision: 1508677 $ $Date: 2013-07-30 19:48:02 -0300 (Tue, 30 Jul 2013) $
 */
public class TestUntilGenerate
{

    @Before
    public void setUp() throws Exception {
        wrappedGenerator = IteratorToGeneratorAdapter.adapt(new IntegerRange(1, 10));
        untilGenerate = new UntilGenerate<Integer>(isGreaterThanFive, wrappedGenerator);
    }

    @After
    public void tearDown() {
        wrappedGenerator = null;
        isGreaterThanFive = null;
        untilGenerate = null;
    }

    // Tests
    // ------------------------------------------------------------------------

    @Test(expected=NullPointerException.class)
    public void testConstructorProhibitsNullPredicate() {
        new UntilGenerate<Integer>(null, untilGenerate);
    }

    @Test(expected=NullPointerException.class)
    public void testConstructorProhibitsNullWrappedGenerator() {
        new UntilGenerate<Integer>(isGreaterThanFive, null);
    }

    @Test(expected=NullPointerException.class)
    public void testConstructorProhibitsNullPredicateOrNullWrappedGenerator() {
        new UntilGenerate<Integer>(null, null);
    }

    @Test
    public void testEquals() {
        Generator<Integer> anotherGenerate = new UntilGenerate<Integer>(
                isGreaterThanFive, IteratorToGeneratorAdapter.adapt(new IntegerRange(1, 10)));
        assertEquals(untilGenerate, untilGenerate);
        assertEquals(untilGenerate, anotherGenerate);
        assertTrue(!untilGenerate.equals((UntilGenerate<Integer>)null));

		Generator<Integer> aGenerateWithADifferentPredicate = new UntilGenerate<Integer>(
			new Predicate<Integer>() {
				public boolean test(Integer obj) {
					return obj < FIVE;
				}
			}, IteratorToGeneratorAdapter.adapt(new IntegerRange(1, 10)));
        assertTrue(!untilGenerate.equals(aGenerateWithADifferentPredicate));

        Generator<Integer> aGenerateWithADifferentWrapped = new UntilGenerate<Integer>(
                isGreaterThanFive, IteratorToGeneratorAdapter.adapt(new IntegerRange(1,2)));
        assertTrue(!untilGenerate.equals(aGenerateWithADifferentWrapped));
    }

    @Test
    public void testHashcode() {
        assertEquals(untilGenerate.hashCode(), untilGenerate.hashCode());
        assertEquals(untilGenerate.hashCode(), new UntilGenerate<Integer>(isGreaterThanFive, wrappedGenerator).hashCode());
    }

    @Test
    public void testGenerate() {
        final List<Integer> numbersGreaterThanFive = new ArrayList<Integer>();
        untilGenerate.run(new Procedure<Integer>() {
            public void run( Integer obj ) {
                numbersGreaterThanFive.add(obj);
            }
        });
        assertEquals(5, numbersGreaterThanFive.size());

        final List<Integer> expected = Arrays.asList(1, 2, 3, 4, 5);
        assertEquals(expected, numbersGreaterThanFive);
    }

    // Attributes
    // ------------------------------------------------------------------------
    private static final Integer FIVE = Integer.valueOf(5);

    private Generator<Integer> wrappedGenerator = null;
    private Predicate<Integer> isGreaterThanFive = new Predicate<Integer>() {
        public boolean test( Integer obj ) {
            return obj > FIVE;
        }
    };
    private Generator<Integer> untilGenerate = null;
}
