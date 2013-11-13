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
package org.apache.commons.functor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.functor.core.Identity;
import org.apache.commons.functor.core.composite.Not;
import org.apache.commons.functor.generator.FilteredGenerator;
import org.apache.commons.functor.generator.loop.IteratorToGeneratorAdapter;
import org.apache.commons.functor.generator.loop.LoopGenerator;
import org.apache.commons.functor.generator.loop.TransformedGenerator;
import org.apache.commons.functor.range.IntegerRange;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @version $Revision$ $Date$
 */
public class TestAlgorithms {

    // Lifecycle
    // ------------------------------------------------------------------------

    @Before
    public void setUp() throws Exception {
        list = new ArrayList<Integer>();
        evens = new ArrayList<Integer>();
        doubled = new ArrayList<Integer>();
        listWithDuplicates = new ArrayList<Integer>();
        sum = 0;
        for (int i = 0; i < 10; i++) {
            list.add(Integer.valueOf(i));
            doubled.add(Integer.valueOf(i * 2));
            listWithDuplicates.add(Integer.valueOf(i));
            listWithDuplicates.add(Integer.valueOf(i));
            sum += i;
            if (i % 2 == 0) {
                evens.add(Integer.valueOf(i));
            }
        }
    }

    @After
    public void tearDown() throws Exception {
        list = null;
        evens = null;
        listWithDuplicates = null;
        sum = 0;
    }

    // Tests
    // ------------------------------------------------------------------------

    @Test
    public void testRun() {
        Summer summer = new Summer();
        IteratorToGeneratorAdapter.adapt(list.iterator()).run(summer);
        assertEquals(sum, summer.sum);
    }

    @Test
    public void testSelect1() {
        Collection<Integer> result =
            new FilteredGenerator<Integer>(IteratorToGeneratorAdapter.adapt(list.iterator()), isEven).toCollection();
        assertNotNull(result);
        assertEquals(evens, result);
    }

    @Test
    public void testSelect2() {
        List<Integer> result = new ArrayList<Integer>();
        assertSame(result,
            new FilteredGenerator<Integer>(IteratorToGeneratorAdapter.adapt(list.iterator()), isEven).to(result));
        assertEquals(evens, result);
    }

    @Test
    public void testReject1() {
        Collection<Integer> result =
            new FilteredGenerator<Integer>(IteratorToGeneratorAdapter.adapt(list.iterator()), new Not<Integer>(isOdd))
                .toCollection();
        assertNotNull(result);
        assertEquals(evens, result);
    }

    @Test
    public void testReject2() {
        List<Object> result = new ArrayList<Object>();
        assertSame(result, new FilteredGenerator<Integer>(IteratorToGeneratorAdapter.adapt(list.iterator()),
            new Not<Integer>(isOdd)).to(result));
        assertEquals(evens, result);
    }

    @Test
    public void testApplyToGenerator() {
        LoopGenerator<Integer> gen = IteratorToGeneratorAdapter.adapt(new IntegerRange(1, 5));
        Summer summer = new Summer();

        new TransformedGenerator<Integer, Integer>(gen, new Doubler()).run(summer);

        assertEquals(2 * (1 + 2 + 3 + 4), summer.sum);
    }

    @Test
    public void testApply() {
        Collection<Integer> result =
            new TransformedGenerator<Integer, Integer>(IteratorToGeneratorAdapter.adapt(list.iterator()), new Doubler())
                .toCollection();
        assertNotNull(result);
        assertEquals(doubled, result);
    }

    @Test
    public void testApply2() {
        Set<Integer> set = new HashSet<Integer>();
        assertSame(set, new TransformedGenerator<Integer, Integer>(IteratorToGeneratorAdapter.adapt(list.iterator()),
            Identity.<Integer> instance()).to(set));
        assertEquals(list.size(), set.size());
        for (Iterator<Integer> iter = list.iterator(); iter.hasNext();) {
            assertTrue(set.contains(iter.next()));
        }
    }

    @Test
    public void testApply3() {
        Set<Object> set = new HashSet<Object>();
        assertSame(set,
            new TransformedGenerator<Object, Object>(IteratorToGeneratorAdapter.adapt(listWithDuplicates.iterator()),
                Identity.instance()).to(set));
        assertTrue(listWithDuplicates.size() > set.size());
        for (Iterator<Integer> iter = listWithDuplicates.iterator(); iter.hasNext();) {
            assertTrue(set.contains(iter.next()));
        }
    }

    // Attributes
    // ------------------------------------------------------------------------
    private List<Integer> list = null;
    private List<Integer> doubled = null;
    private List<Integer> evens = null;
    private List<Integer> listWithDuplicates = null;
    private int sum = 0;
    private Predicate<Integer> isEven = new Predicate<Integer>() {
        public boolean test(Integer obj) {
            return obj.intValue() % 2 == 0;
        }
    };
    private Predicate<Integer> isOdd = new Predicate<Integer>() {
        public boolean test(Integer obj) {
            return obj.intValue() % 2 != 0;
        }
    };

    // Classes
    // ------------------------------------------------------------------------

    static class Counter implements NullaryProcedure {
        public void run() {
            count++;
        }

        public int count = 0;
    }

    static class Summer implements Procedure<Integer> {
        public void run(Integer that) {
            sum += that.intValue();
        }

        public int sum = 0;
    }

    static class Doubler implements Function<Integer, Integer> {
        public Integer evaluate(Integer obj) {
            return Integer.valueOf(2 * obj.intValue());
        }
    }
}
