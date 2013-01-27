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

import org.apache.commons.functor.UnaryPredicate;
import org.apache.commons.functor.UnaryProcedure;
import org.apache.commons.functor.generator.Generator;
import org.apache.commons.functor.generator.range.IntegerRange;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the Until Generate class.
 * @version $Revision$ $Date$
 */
public class TestUntilGenerate
{

    @Before
    public void setUp() throws Exception {
        wrappedGenerator = new IntegerRange(1, 10);
        untilGenerate = new UntilGenerate<Integer>(isLessThanFive, wrappedGenerator);
    }

    @After
    public void tearDown() {
        wrappedGenerator = null;
        isLessThanFive = null;
        untilGenerate = null;
    }

    // Tests
    // ------------------------------------------------------------------------

    @Test(expected=NullPointerException.class)
    public void testConstructorProhibitsNullUnaryPredicate() {
        new UntilGenerate<Integer>(null, untilGenerate);
    }

    @Test(expected=NullPointerException.class)
    public void testConstructorProhibitsNullWrappedGenerator() {
        new UntilGenerate<Integer>(isLessThanFive, null);
    }

    @Test(expected=NullPointerException.class)
    public void testConstructorProhibitsNullUnaryPredicateOrNullWrappedGenerator() {
        new UntilGenerate<Integer>(null, null);
    }

    @Test
    public void testEquals() {
        Generator<Integer> anotherGenerate = new UntilGenerate<Integer>(isLessThanFive, new IntegerRange(1, 10));
        assertEquals(untilGenerate, untilGenerate);
        assertEquals(untilGenerate, anotherGenerate);
        assertTrue(!untilGenerate.equals((UntilGenerate<Integer>)null));

		Generator<Integer> aGenerateWithADifferentPredicate = new UntilGenerate<Integer>(
			new UnaryPredicate<Integer>() {
				public boolean test(Integer obj) {
					return obj < FIVE;
				}
			}, new IntegerRange(1, 10));
        assertTrue(!untilGenerate.equals(aGenerateWithADifferentPredicate));

        Generator<Integer> aGenerateWithADifferentWrapped = new UntilGenerate<Integer>(isLessThanFive, new IntegerRange(1,2));
        assertTrue(!untilGenerate.equals(aGenerateWithADifferentWrapped));
    }

    @Test
    public void testHashcode() {
        assertEquals(untilGenerate.hashCode(), untilGenerate.hashCode());
        assertEquals(untilGenerate.hashCode(), new UntilGenerate<Integer>(isLessThanFive, wrappedGenerator).hashCode());
    }

    @Test
    public void testGenerate() {
        final List<Integer> numbersGreaterThanFive = new ArrayList<Integer>();
        untilGenerate.run(new UnaryProcedure<Integer>() {
            public void run( Integer obj ) {
                numbersGreaterThanFive.add(obj);
            }
        });
        assertEquals(5, numbersGreaterThanFive.size());

        final List<Integer> expected = Arrays.asList(5, 6, 7, 8, 9);
        assertEquals(expected, numbersGreaterThanFive);
    }

    // Attributes
    // ------------------------------------------------------------------------
    private static final Integer FIVE = new Integer(5);

    private LoopGenerator<Integer> wrappedGenerator = null;
    private UnaryPredicate<Integer> isLessThanFive = new UnaryPredicate<Integer>() {
        public boolean test( Integer obj ) {
            return obj < FIVE;
        }
    };
    private LoopGenerator<Integer> untilGenerate = null;
}
