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
import org.apache.commons.functor.Procedure;
import org.apache.commons.functor.core.NoOp;
import org.junit.Test;

/**
 * @version $Revision$ $Date$
 */
public class TestSequence extends BaseFunctorTest {

    // Functor Testing Framework
    // ------------------------------------------------------------------------

    @Override
    protected Object makeFunctor() {
        return new Sequence(new NoOp(),new NoOp());
    }

    // Tests
    // ------------------------------------------------------------------------

    @Test
    public void testConstructors() throws Exception {
        Sequence seq1 = new Sequence((Procedure)null);
        Sequence seq2 = new Sequence();
        assertObjectsAreEqual(seq1, seq2);
        
        RunCounter p1 = new RunCounter();
        RunCounter p2 = new RunCounter();
        List<Procedure> iterable = new ArrayList<Procedure>();
        iterable.add(p1);
        iterable.add(p2);
        Sequence seq3 = new Sequence(iterable);
        Sequence seq4 = new Sequence(p1, p2);
        assertObjectsAreEqual(seq3, seq4);
        
        Sequence seq5 = new Sequence((Iterable<Procedure>)null);
        Sequence seq6 = new Sequence((Procedure[])null);
        assertObjectsAreEqual(seq5, seq6);
    }

    @Test
    public void testRunZero() throws Exception {
        Sequence seq = new Sequence();
        seq.run();
    }

    @Test
    public void testRunOne() throws Exception {
        RunCounter counter = new RunCounter();
        Sequence seq = new Sequence(counter);
        assertEquals(0,counter.count);
        seq.run();
        assertEquals(1,counter.count);
    }

    @Test
    public void testRunTwo() throws Exception {
        RunCounter[] counter = { new RunCounter(), new RunCounter() };
        Sequence seq = new Sequence(counter[0],counter[1]);
        assertEquals(0,counter[0].count);
        assertEquals(0,counter[1].count);
        seq.run();
        assertEquals(1,counter[0].count);
        assertEquals(1,counter[1].count);
    }

    @Test
    public void testThen() throws Exception {
        List<RunCounter> list = new ArrayList<RunCounter>();
        Sequence seq = new Sequence();
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
        Sequence p = new Sequence();
        assertEquals(p,p);
        Sequence q = new Sequence();
        assertObjectsAreEqual(p,q);

        for (int i=0;i<3;i++) {
            p.then(new NoOp());
            assertObjectsAreNotEqual(p,q);
            q.then(new NoOp());
            assertObjectsAreEqual(p,q);
            p.then(new Sequence(new NoOp(),new NoOp()));
            assertObjectsAreNotEqual(p,q);
            q.then(new Sequence(new NoOp(),new NoOp()));
            assertObjectsAreEqual(p,q);
        }

        assertObjectsAreNotEqual(p,new NoOp());
        assertTrue(!p.equals(null));
    }

    // Classes
    // ------------------------------------------------------------------------

    static class RunCounter implements Procedure {
        public void run() {
            count++;
        }
        public int count = 0;
    }
}
