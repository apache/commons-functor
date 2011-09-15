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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import org.apache.commons.functor.adapter.LeftBoundPredicate;
import org.apache.commons.functor.core.Identity;
import org.apache.commons.functor.core.IsEqual;
import org.apache.commons.functor.core.Limit;
import org.apache.commons.functor.core.Offset;
import org.apache.commons.functor.core.algorithm.DoUntil;
import org.apache.commons.functor.core.algorithm.DoWhile;
import org.apache.commons.functor.core.algorithm.FindWithinGenerator;
import org.apache.commons.functor.core.algorithm.FoldLeft;
import org.apache.commons.functor.core.algorithm.FoldRight;
import org.apache.commons.functor.core.algorithm.GeneratorContains;
import org.apache.commons.functor.core.algorithm.InPlaceTransform;
import org.apache.commons.functor.core.algorithm.RecursiveEvaluation;
import org.apache.commons.functor.core.algorithm.RemoveMatching;
import org.apache.commons.functor.core.algorithm.RetainMatching;
import org.apache.commons.functor.core.algorithm.UntilDo;
import org.apache.commons.functor.core.algorithm.WhileDo;
import org.apache.commons.functor.core.composite.UnaryNot;
import org.apache.commons.functor.generator.FilteredGenerator;
import org.apache.commons.functor.generator.Generator;
import org.apache.commons.functor.generator.IteratorToGeneratorAdapter;
import org.apache.commons.functor.generator.TransformedGenerator;
import org.apache.commons.functor.generator.util.IntegerRange;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @version $Revision$ $Date$
 * @author Rodney Waldhoff
 */
@SuppressWarnings("unchecked")
public class TestAlgorithms {

    // Lifecycle
    // ------------------------------------------------------------------------

    @Before
    public void setUp() throws Exception {
        list = new ArrayList();
        evens = new ArrayList();
        doubled = new ArrayList();
        listWithDuplicates = new ArrayList();
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
        list = null;
        evens = null;
        listWithDuplicates = null;
        sum = 0;
    }

    // Tests
    // ------------------------------------------------------------------------

    @Test
    public void testDetect() {
        assertEquals(new Integer(3),FindWithinGenerator.instance().evaluate(IteratorToGeneratorAdapter.adapt(list.iterator()),equalsThree));
        try {
            FindWithinGenerator.instance().evaluate(IteratorToGeneratorAdapter.adapt(list.iterator()),equalsTwentyThree);
            fail("Expected NoSuchElementException");
        } catch(NoSuchElementException e) {
            // expected
        }
    }

    @Test
    public void testDetectIfNone() {
        assertEquals(new Integer(3),new FindWithinGenerator("Xyzzy").evaluate(IteratorToGeneratorAdapter.adapt(list.iterator()),equalsThree));
        assertEquals("Xyzzy",new FindWithinGenerator("Xyzzy").evaluate(IteratorToGeneratorAdapter.adapt(list.iterator()),equalsTwentyThree));
    }

    @Test
    public void testRun() {
        Summer summer = new Summer();
        IteratorToGeneratorAdapter.adapt(list.iterator()).run(summer);
        assertEquals(sum,summer.sum);
    }

    @Test
    public void testSelect1() {
        Collection result = new FilteredGenerator(IteratorToGeneratorAdapter.adapt(list.iterator()),isEven).toCollection();
        assertNotNull(result);
        assertEquals(evens,result);
    }

    @Test
    public void testSelect2() {
        ArrayList result = new ArrayList();
        assertSame(result,new FilteredGenerator(IteratorToGeneratorAdapter.adapt(list.iterator()),isEven).to(result));
        assertEquals(evens,result);
    }

    @Test
    public void testReject1() {
        Collection result = new FilteredGenerator(IteratorToGeneratorAdapter.adapt(list.iterator()),new UnaryNot(isOdd)).toCollection();
        assertNotNull(result);
        assertEquals(evens,result);
    }

    @Test
    public void testReject2() {
        ArrayList result = new ArrayList();
        assertSame(result,new FilteredGenerator(IteratorToGeneratorAdapter.adapt(list.iterator()),new UnaryNot(isOdd)).to(result));
        assertEquals(evens,result);
    }

    @Test
    public void testRetain() {
        RetainMatching.instance().run(list.iterator(),isEven);
        assertEquals(evens,list);
    }

    @Test
    public void testRemove() {
        RemoveMatching.instance().run(list.iterator(),isOdd);
        assertEquals(evens,list);
    }

    @Test
    public void testTransform() {
        InPlaceTransform.instance().run(
            list.listIterator(),
            new UnaryFunction() {
                public Object evaluate(Object obj) {
                    return new Integer(((Number) obj).intValue()*2);
                }
            }
        );
        assertEquals(doubled,list);
    }

    @Test
    public void testApplyToGenerator() {
        Generator gen = new IntegerRange(1,5);
        Summer summer = new Summer();

        new TransformedGenerator(gen, new Doubler()).run(summer);

        assertEquals(2*(1+2+3+4),summer.sum);
    }

    @Test
    public void testApply() {
        Collection result = new TransformedGenerator(IteratorToGeneratorAdapter.adapt(list.iterator()), new Doubler())
                .toCollection();
        assertNotNull(result);
        assertEquals(doubled,result);
    }

    @Test
    public void testApply2() {
        Set set = new HashSet();
        assertSame(set, new TransformedGenerator(IteratorToGeneratorAdapter.adapt(list.iterator()), Identity.instance())
                .to(set));
        assertEquals(list.size(),set.size());
        for (Iterator iter = list.iterator(); iter.hasNext(); ) {
            assertTrue(set.contains(iter.next()));
        }
    }

    @Test
    public void testApply3() {
        Set set = new HashSet();
        assertSame(set, new TransformedGenerator(IteratorToGeneratorAdapter.adapt(listWithDuplicates.iterator()),
                Identity.instance()).to(set));
        assertTrue(listWithDuplicates.size() > set.size());
        for (Iterator iter = listWithDuplicates.iterator(); iter.hasNext(); ) {
            assertTrue(set.contains(iter.next()));
        }
    }

    @Test
    public void testContains() {
        assertTrue(GeneratorContains.instance().test(IteratorToGeneratorAdapter.adapt(list.iterator()),equalsThree));
        assertFalse(GeneratorContains.instance().test(IteratorToGeneratorAdapter.adapt(list.iterator()),equalsTwentyThree));
    }

    @Test
    public void testFoldLeft() {
        FoldLeft foldLeft = new FoldLeft(new BinaryFunction() {
            public Object evaluate(Object a, Object b) {
                return new Integer(((Number) a).intValue() + ((Number) b).intValue());
            }
        });
        assertEquals(new Integer(sum), foldLeft.evaluate(IteratorToGeneratorAdapter.adapt(list.iterator())));
        assertEquals(new Integer(sum), foldLeft.evaluate(IteratorToGeneratorAdapter.adapt(list.iterator()), new Integer(0)));
    }

    @Test
    public void testFoldRight() {
        FoldRight foldRight = new FoldRight(new BinaryFunction() {
            public Object evaluate(Object left, Object right) {
                StringBuffer buf = left instanceof StringBuffer ? (StringBuffer) left : new StringBuffer().append(left);
                return buf.append(right);
            }
        });
        assertEquals("0123456789", foldRight.evaluate(IteratorToGeneratorAdapter.adapt(list.iterator())).toString());
        assertEquals("0123456789x", foldRight.evaluate(IteratorToGeneratorAdapter.adapt(list.iterator()), "x").toString());
    }

    @Test
    public void testDoUntil() {
        for (int i=0;i<3;i++){
            Counter counter = new Counter();
            new DoUntil(counter, new Offset(i)).run();
            assertEquals(i+1,counter.count);
        }
    }

    @Test
    public void testDoWhile() {
        for (int i=0;i<3;i++){
            Counter counter = new Counter();
            new DoWhile(counter, new Limit(i)).run();
            assertEquals(i+1,counter.count);
        }
    }

    @Test
    public void testUntilDo() {
        for (int i=0;i<3;i++){
            Counter counter = new Counter();
            new UntilDo(new Offset(i), counter).run();
            assertEquals(i,counter.count);
        }
    }

    @Test
    public void testWhileDo() {
        for (int i=0;i<3;i++){
            Counter counter = new Counter();
            new WhileDo(new Limit(i),counter).run();
            assertEquals(i,counter.count);
        }
    }

    @Test
    public void testRecurse() {
        assertEquals(new Integer(5), new RecursiveEvaluation(new RecFunc(0, false)).evaluate());

        // this version will return a function. since it is not the same type
        // as RecFunc recursion will end.
        Function func = (Function) new RecursiveEvaluation(new RecFunc(0, true)).evaluate();
        assertEquals(new Integer(5), func.evaluate());
    }

    /** Recursive function for test. */
    class RecFunc implements Function {
        int times = 0; boolean returnFunc = false;

        public RecFunc(int times, boolean returnFunc) {
            this.times = times;
            this.returnFunc = returnFunc;
        }

        public Object evaluate() {
            if (times < 5) {
                return new RecFunc(++times, returnFunc);
            } else {
                if (returnFunc) {
                    return new Function() {
                        public Object evaluate() {
                            return new Integer(times);
                        }
                    };
                } else {
                    return new Integer(times);
                }
            }
        }
    }

    // Attributes
    // ------------------------------------------------------------------------
    private List list = null;
    private List doubled = null;
    private List evens = null;
    private List listWithDuplicates = null;
    private int sum = 0;
    private UnaryPredicate equalsThree = LeftBoundPredicate.bind(IsEqual.instance(),new Integer(3));
    private UnaryPredicate equalsTwentyThree = LeftBoundPredicate.bind(IsEqual.instance(),new Integer(23));
    private UnaryPredicate isEven = new UnaryPredicate() {
        public boolean test(Object obj) {
            return ((Number) obj).intValue() % 2 == 0;
        }
    };
    private UnaryPredicate isOdd = new UnaryPredicate() {
        public boolean test(Object obj) {
            return ((Number) obj).intValue() % 2 != 0;
        }
    };

    // Classes
    // ------------------------------------------------------------------------

    static class Counter implements Procedure {
        public void run() {
            count++;
        }
        public int count = 0;
    }

    static class Summer implements UnaryProcedure {
        public void run(Object that) {
            sum += ((Number) that).intValue();
        }
        public int sum = 0;
    }

    static class Doubler implements UnaryFunction {
        public Object evaluate(Object obj) {
            return new Integer(2*((Number) obj).intValue());
        }
    }
}
