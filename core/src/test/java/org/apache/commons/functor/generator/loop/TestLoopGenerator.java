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
import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.functor.UnaryProcedure;
import org.apache.commons.functor.generator.util.CollectionTransformer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the Loop Generator class.
 */
@SuppressWarnings("unchecked")
public class TestLoopGenerator {

    private LoopGenerator<Integer> simpleGenerator = null;

    // Lifecycle
    // ------------------------------------------------------------------------

    @Before
    public void setUp() throws Exception {
        simpleGenerator = new LoopGenerator<Integer>() {
            public void run(UnaryProcedure<? super Integer> proc) {
                for (int i=0;i<5;i++) {
                    proc.run(new Integer(i));
                    if (isStopped()) {
                        break;
                    }
                }
            }
        };

        list = new ArrayList<Integer>();
        evens = new ArrayList<Integer>();
        doubled = new ArrayList<Integer>();
        listWithDuplicates = new ArrayList<Integer>();
        sum = 0;
        for (int i=0;i<10;i++) {
            list.add(new Integer(i));
            doubled.add(new Integer(i*2));
            listWithDuplicates.add(new Integer(i));
            listWithDuplicates.add(new Integer(i));
            sum += i;
            if (i%2 == 0) {
                evens.add(new Integer(i));
            }
        }
    }

    @After
    public void tearDown() throws Exception {
        simpleGenerator = null;
        list = null;
        evens = null;
        listWithDuplicates = null;
        sum = 0;
    }

    // Tests
    // ------------------------------------------------------------------------

    @Test
    public void testSimpleGenerator() {
        final StringBuffer result = new StringBuffer();
        simpleGenerator.run(new UnaryProcedure<Integer>() {
            public void run(Integer obj) {
                result.append(obj);
            }
        });

        assertEquals("01234", result.toString());
    }

    @Test
    public void testStop() {
        final StringBuffer result = new StringBuffer();
        simpleGenerator.run(new UnaryProcedure<Integer>() {
            int i=0;
            public void run(Integer obj) {
                result.append(obj);
                if (i++ > 1) {
                    simpleGenerator.stop();
                }
            }
        });

        assertEquals("012", result.toString());
    }

    @Test
    public void testWrappingGenerator() {
        final StringBuffer result = new StringBuffer();
        final LoopGenerator<Integer> gen = new LoopGenerator<Integer>(simpleGenerator) {
            public void run(final UnaryProcedure<? super Integer> proc) {
                LoopGenerator<Integer> wrapped = (LoopGenerator<Integer>)getWrappedGenerator();
                assertSame(simpleGenerator, wrapped);
                wrapped.run(new UnaryProcedure<Integer>() {
                    public void run(Integer obj) {
                        proc.run(new Integer(obj.intValue() + 1));
                    }
                });
            }
        };

        gen.run(new UnaryProcedure<Integer>() {
            public void run(Integer obj) {
                result.append(obj);
            }
        });

        assertEquals("12345", result.toString());

        // try to stop the wrapped generator
        final StringBuffer result2 = new StringBuffer();
        gen.run(new UnaryProcedure<Integer>() {
            int i=0;
            public void run(Integer obj) {
                result2.append(obj);
                if (i++ > 1) {
                    gen.stop();
                }
            }
        });

        assertEquals("123", result2.toString());
    }

    // Tests
    // ------------------------------------------------------------------------

    @Test
    public void testTo() {
        Collection<Integer> col = simpleGenerator.to(CollectionTransformer.<Integer> toCollection());
        assertEquals("[0, 1, 2, 3, 4]", col.toString());

        Collection<Integer> fillThis = new LinkedList<Integer>();
        col = simpleGenerator.to(new CollectionTransformer<Integer, Collection<Integer>>(fillThis));
        assertSame(fillThis, col);
        assertEquals("[0, 1, 2, 3, 4]", col.toString());

        col = (Collection<Integer>)simpleGenerator.toCollection();
        assertEquals("[0, 1, 2, 3, 4]", col.toString());
        assertEquals("[0, 1, 2, 3, 4]", col.toString());

        fillThis = new LinkedList<Integer>();
        col = (Collection<Integer>)simpleGenerator.to(fillThis);
        assertSame(fillThis, col);
        assertEquals("[0, 1, 2, 3, 4]", col.toString());
    }

    // Attributes
    // ------------------------------------------------------------------------
    private List<Integer> list = null;
    private List<Integer> doubled = null;
    private List<Integer> evens = null;
    private List<Integer> listWithDuplicates = null;
    @SuppressWarnings("unused")
    private int sum = 0;
//    private UnaryPredicate equalsThree = LeftBoundPredicate.bind(IsEqual.instance(),new Integer(3));
//    private UnaryPredicate equalsTwentyThree = LeftBoundPredicate.bind(IsEqual.instance(),new Integer(23));
//    private UnaryPredicate isEven = new UnaryPredicate() {
//        public boolean test(Object obj) {
//            return ((Number) obj).intValue() % 2 == 0;
//        }
//    };
//    private UnaryPredicate isOdd = new UnaryPredicate() {
//        public boolean test(Object obj) {
//            return ((Number) obj).intValue() % 2 != 0;
//        }
//    };

    // Classes
    // ------------------------------------------------------------------------

    static class Summer implements UnaryProcedure<Number> {
        public void run(Number that) {
            sum += (that).intValue();
        }
        public int sum = 0;
    }
}