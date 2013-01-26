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
import java.util.Arrays;
import java.util.List;

import org.apache.commons.functor.BaseFunctorTest;
import org.apache.commons.functor.BinaryProcedure;
import org.apache.commons.functor.core.NoOp;
import org.junit.Test;

/**
 * @version $Revision$ $Date$
 */
@SuppressWarnings("unchecked")
public class TestBinarySequence extends BaseFunctorTest {

    // Functor Testing Framework
    // ------------------------------------------------------------------------

    @Override
    protected Object makeFunctor() {
        return new BinarySequence<String, String>(new NoOp(),new NoOp());
    }

    // Tests
    // ------------------------------------------------------------------------

    @Test
    public void testRunZero() throws Exception {
        BinarySequence<String, String> seq = new BinarySequence<String, String>();
        seq.run(null,null);
        seq.run("xyzzy","xyzzy");
    }

    @Test
    public void testRunOne() throws Exception {
        RunCounter counter = new RunCounter();
        BinarySequence<String, String> seq = new BinarySequence<String, String>(counter);
        assertEquals(0,counter.count);
        seq.run(null,null);
        assertEquals(1,counter.count);
        seq.run("xyzzy","xyzzy");
        assertEquals(2,counter.count);
    }

    @Test
    public void testRunTwo() throws Exception {
        RunCounter[] counter = { new RunCounter(), new RunCounter() };
        BinarySequence<String, String> seq = new BinarySequence<String, String>(counter[0],counter[1]);
        assertEquals(0,counter[0].count);
        assertEquals(0,counter[1].count);
        seq.run(null,null);
        assertEquals(1,counter[0].count);
        assertEquals(1,counter[1].count);
        seq.run("xyzzy","xyzzy");
        assertEquals(2,counter[0].count);
        assertEquals(2,counter[1].count);
    }

    @Test
    public void testThen() throws Exception {
        List<RunCounter> list = new ArrayList<RunCounter>();
        BinarySequence<String, String> seq = new BinarySequence<String, String>();
        seq.run(null,null);
        for (int i=0;i<10;i++) {
            RunCounter counter = new RunCounter();
            seq.then(counter);
            list.add(counter);
            seq.run("xyzzy","xyzzy");
            for (int j=0;j<list.size();j++) {
                assertEquals(list.size()-j,(((RunCounter)(list.get(j))).count));
            }
        }
    }

    @SuppressWarnings("rawtypes")
    @Test
    public void testEquals() throws Exception {
        BinarySequence<String, String> p = new BinarySequence<String, String>();
        assertEquals(p,p);
        BinarySequence<String, String> q = new BinarySequence<String, String>();
        assertObjectsAreEqual(p,q);

        for (int i=0;i<3;i++) {
            p.then(new NoOp());
            assertObjectsAreNotEqual(p,q);
            q.then(new NoOp());
            assertObjectsAreEqual(p,q);
            p.then(new BinarySequence<String, String>(new NoOp(),new NoOp()));
            assertObjectsAreNotEqual(p,q);
            q.then(new BinarySequence<String, String>(new NoOp(),new NoOp()));
            assertObjectsAreEqual(p,q);
        }

        assertObjectsAreNotEqual(p,new NoOp());
        assertObjectsAreNotEqual(p,new BinarySequence<String, String>((BinaryProcedure<String, String>[])null));
        assertObjectsAreNotEqual(p,new BinarySequence<String, String>((BinaryProcedure<String, String>)null));
        assertObjectsAreNotEqual(p,new BinarySequence((Iterable<BinaryProcedure>)null));
        Iterable<BinaryProcedure> iterable = Arrays.<BinaryProcedure>asList(
            new NoOp(), new BinarySequence<String, String>(new NoOp(),new NoOp()),
            new NoOp(), new BinarySequence<String, String>(new NoOp(),new NoOp()),
            new NoOp(), new BinarySequence<String, String>(new NoOp(),new NoOp()));
        assertObjectsAreEqual(p,new BinarySequence(iterable));
        assertTrue(!p.equals(null));
    }

    // Classes
    // ------------------------------------------------------------------------

    static class RunCounter implements BinaryProcedure<String, String> {
        public void run(String a, String b) {
            count++;
        }
        public int count = 0;
    }
}
