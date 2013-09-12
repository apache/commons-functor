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
package org.apache.commons.functor.generator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.functor.Predicate;
import org.apache.commons.functor.Procedure;
import org.apache.commons.functor.generator.loop.IteratorToGeneratorAdapter;
import org.apache.commons.functor.range.IntegerRange;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the Filtered Generator class.
 * @version $Revision$ $Date$
 */
public class TestFilteredGenerator
{

    @Before
    public void setUp() throws Exception {
        wrappedGenerator = IteratorToGeneratorAdapter.adapt(new IntegerRange(1, 10));
        filteredGenerator = new FilteredGenerator<Integer>(wrappedGenerator, isEven);
    }

    @After
    public void tearDown() {
        wrappedGenerator = null;
        isEven = null;
        filteredGenerator = null;
    }

    // Tests
    // ------------------------------------------------------------------------

    @Test(expected=NullPointerException.class)
    public void testConstructorProhibitsNullPredicate() {
        new FilteredGenerator<Integer>(filteredGenerator, null);
    }

    @Test(expected=NullPointerException.class)
    public void testConstructorProhibitsNullWrappedGenerator() {
        new FilteredGenerator<Integer>(null, isEven);
    }

    @Test(expected=NullPointerException.class)
    public void testConstructorProhibitsNullPredicateOrNullWrappedGenerator() {
        new FilteredGenerator<Integer>(null, null);
    }

    @Test
    public void testEquals() {
        Generator<Integer> anotherGenerate = new FilteredGenerator<Integer>(
                IteratorToGeneratorAdapter.adapt(new IntegerRange(1, 10)), isEven);
        assertEquals(filteredGenerator, filteredGenerator);
        assertEquals(filteredGenerator, anotherGenerate);
        assertTrue(!filteredGenerator.equals((FilteredGenerator<Integer>)null));

		Generator<Integer> aGenerateWithADifferentPredicate = new FilteredGenerator<Integer>(
		        IteratorToGeneratorAdapter.adapt(new IntegerRange(1, 10)), 
		        new Predicate<Integer>() {
				public boolean test(Integer obj) {
					return obj % 2 == 0;
				}
			});

        assertTrue(!filteredGenerator.equals(aGenerateWithADifferentPredicate));

        Generator<Integer> aGenerateWithADifferentWrapped = new FilteredGenerator<Integer>(
                IteratorToGeneratorAdapter.adapt(new IntegerRange(1,11)), isEven);
        assertTrue(!filteredGenerator.equals(aGenerateWithADifferentWrapped));
    }

    @Test
    public void testHashcode() {
        assertEquals(filteredGenerator.hashCode(), filteredGenerator.hashCode());
        assertEquals(filteredGenerator.hashCode(), new FilteredGenerator<Integer>(wrappedGenerator, isEven).hashCode());
    }

    @Test
    public void testGenerate() {
    	final List<Integer> evenNumbers = new ArrayList<Integer>();
    	filteredGenerator.run(new Procedure<Integer>() {
    		public void run(Integer obj) {
    			evenNumbers.add(obj);
    		}
		});
    	assertEquals(4, evenNumbers.size());

    	List<Integer> expected = Arrays.asList(2, 4, 6, 8);
    	assertEquals(expected, evenNumbers);
    }

    // Attributes
    // ------------------------------------------------------------------------
    private Generator<Integer> wrappedGenerator = null;
    private Predicate<Integer> isEven = new Predicate<Integer>()
    {
        public boolean test( Integer obj ) {
            return obj % 2 == 0;
        }
    };
    private Generator<Integer> filteredGenerator = null;

}
