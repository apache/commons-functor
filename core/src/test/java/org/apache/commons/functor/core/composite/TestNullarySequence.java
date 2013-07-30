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
package org.apache.commons.functor.core.composite;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.functor.BaseFunctorTest;
import org.apache.commons.functor.NullaryProcedure;
import org.apache.commons.functor.core.NoOp;
import org.junit.Test;

/**
 * @version $Revision: 1365329 $ $Date: 2012-07-24 19:34:23 -0300 (Tue, 24 Jul 2012) $
 */
public class TestNullarySequence extends BaseFunctorTest {

    // Functor Testing Framework
    // ------------------------------------------------------------------------

    @Override
    protected Object makeFunctor() {
        return new NullarySequence(new NoOp(),new NoOp());
    }

    // Tests
    // ------------------------------------------------------------------------

    @Test
    public void testConstructors() throws Exception {
        NullarySequence seq1 = new NullarySequence((NullaryProcedure)null);
        NullarySequence seq2 = new NullarySequence();
        assertObjectsAreEqual(seq1, seq2);
        
        RunCounter p1 = new RunCounter();
        RunCounter p2 = new RunCounter();
        List<NullaryProcedure> iterable = new ArrayList<NullaryProcedure>();
        iterable.add(p1);
        iterable.add(p2);
        NullarySequence seq3 = new NullarySequence(iterable);
        NullarySequence seq4 = new NullarySequence(p1, p2);
        assertObjectsAreEqual(seq3, seq4);
        
        NullarySequence seq5 = new NullarySequence((Iterable<NullaryProcedure>)null);
        NullarySequence seq6 = new NullarySequence((NullaryProcedure[])null);
        assertObjectsAreEqual(seq5, seq6);
    }

    @Test
    public void testRunZero() throws Exception {
        NullarySequence seq = new NullarySequence();
        seq.run();
    }

    @Test
    public void testRunOne() throws Exception {
        RunCounter counter = new RunCounter();
        NullarySequence seq = new NullarySequence(counter);
        assertEquals(0,counter.count);
        seq.run();
        assertEquals(1,counter.count);
    }

    @Test
    public void testRunTwo() throws Exception {
        RunCounter[] counter = { new RunCounter(), new RunCounter() };
        NullarySequence seq = new NullarySequence(counter[0],counter[1]);
        assertEquals(0,counter[0].count);
        assertEquals(0,counter[1].count);
        seq.run();
        assertEquals(1,counter[0].count);
        assertEquals(1,counter[1].count);
    }

    @Test
    public void testThen() throws Exception {
        List<RunCounter> list = new ArrayList<RunCounter>();
        NullarySequence seq = new NullarySequence();
        seq.run();
        for (int i=0;i<10;i++) {
            RunCounter counter = new RunCounter();
            seq.then(counter);
            list.add(counter);
            seq.run();
            for (int j=0;j<list.size();j++) {
                assertEquals(list.size()-j,(((RunCounter)(list.get(j))).count));
            }
        }
    }

    @Test
    public void testEquals() throws Exception {
        NullarySequence p = new NullarySequence();
        assertEquals(p,p);
        NullarySequence q = new NullarySequence();
        assertObjectsAreEqual(p,q);

        for (int i=0;i<3;i++) {
            p.then(new NoOp());
            assertObjectsAreNotEqual(p,q);
            q.then(new NoOp());
            assertObjectsAreEqual(p,q);
            p.then(new NullarySequence(new NoOp(),new NoOp()));
            assertObjectsAreNotEqual(p,q);
            q.then(new NullarySequence(new NoOp(),new NoOp()));
            assertObjectsAreEqual(p,q);
        }

        assertObjectsAreNotEqual(p,new NoOp());
        assertTrue(!p.equals(null));
    }

    // Classes
    // ------------------------------------------------------------------------

    static class RunCounter implements NullaryProcedure {
        public void run() {
            count++;
        }
        public int count = 0;
    }
}
