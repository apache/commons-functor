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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.functor.BaseFunctorTest;
import org.apache.commons.functor.UnaryProcedure;
import org.apache.commons.functor.core.NoOp;
import org.junit.Test;

/**
 * @version $Revision$ $Date$
 * @author Rodney Waldhoff
 */
@SuppressWarnings("unchecked")
public class TestUnarySequence extends BaseFunctorTest {

    // Functor Testing Framework
    // ------------------------------------------------------------------------

    @Override
    protected Object makeFunctor() {
        return new UnarySequence<Object>(new NoOp(),new NoOp());
    }

    // Tests
    // ------------------------------------------------------------------------

    @Test
    public void testRunZero() throws Exception {
        UnarySequence<String> seq = new UnarySequence<String>();
        seq.run(null);
        seq.run("xyzzy");
    }

    @Test
    public void testRunOne() throws Exception {
        RunCounter counter = new RunCounter();
        UnarySequence<String> seq = new UnarySequence<String>(counter);
        assertEquals(0,counter.count);
        seq.run(null);
        assertEquals(1,counter.count);
        seq.run("xyzzy");
        assertEquals(2,counter.count);
    }

    @Test
    public void testRunTwo() throws Exception {
        RunCounter[] counter = { new RunCounter(), new RunCounter() };
        UnarySequence<String> seq = new UnarySequence<String>(counter[0],counter[1]);
        assertEquals(0,counter[0].count);
        assertEquals(0,counter[1].count);
        seq.run(null);
        assertEquals(1,counter[0].count);
        assertEquals(1,counter[1].count);
        seq.run("xyzzy");
        assertEquals(2,counter[0].count);
        assertEquals(2,counter[1].count);
    }

    @Test
    public void testThen() throws Exception {
        List<RunCounter> list = new ArrayList<RunCounter>();
        UnarySequence<String> seq = new UnarySequence<String>();
        seq.run(null);
        for (int i=0;i<10;i++) {
            RunCounter counter = new RunCounter();
            seq.then(counter);
            list.add(counter);
            seq.run("xyzzy");
            for (int j=0;j<list.size();j++) {
                assertEquals(list.size()-j,((list.get(j)).count));
            }
        }
    }

    @Test
    public void testEquals() throws Exception {
        UnarySequence<?> p = new UnarySequence<Object>();
        assertEquals(p,p);
        UnarySequence<?> q = new UnarySequence<Object>();
        assertObjectsAreEqual(p,q);

        for (int i=0;i<3;i++) {
            p.then(new NoOp());
            assertObjectsAreNotEqual(p,q);
            q.then(new NoOp());
            assertObjectsAreEqual(p,q);
            p.then(new UnarySequence<Object>(new NoOp(),new NoOp()));
            assertObjectsAreNotEqual(p,q);
            q.then(new UnarySequence<Object>(new NoOp(),new NoOp()));
            assertObjectsAreEqual(p,q);
        }

        assertObjectsAreNotEqual(p,new NoOp());
    }

    // Classes
    // ------------------------------------------------------------------------

    static class RunCounter implements UnaryProcedure<Object> {
        public void run(Object that) {
            count++;
        }
        public int count = 0;
    }
}
