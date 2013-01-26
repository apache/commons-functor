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

import java.util.Arrays;

import org.apache.commons.functor.BaseFunctorTest;
import org.apache.commons.functor.BinaryPredicate;
import org.apache.commons.functor.core.Constant;
import org.junit.Test;

/**
 * @version $Revision$ $Date$
 */
@SuppressWarnings("unchecked")
public class TestBinaryAnd extends BaseFunctorTest {

    // Functor Testing Framework
    // ------------------------------------------------------------------------

    @Override
    protected Object makeFunctor() {
        return new BinaryAnd<Object, Object>(Constant.TRUE, Constant.TRUE);
    }

    // Tests
    // ------------------------------------------------------------------------

    @Test
    public void testTrue() throws Exception {
        assertTrue((new BinaryAnd<String, Integer>()).test("xyzzy",3));
        assertTrue((new BinaryAnd<String, Integer>(Constant.TRUE)).test("xyzzy",3));
        assertTrue((new BinaryAnd<String, Integer>(Constant.TRUE,Constant.TRUE)).test("xyzzy",3));
        assertTrue((new BinaryAnd<String, Integer>(Constant.TRUE,Constant.TRUE,Constant.TRUE)).test("xyzzy",3));

        BinaryAnd<String, Integer> p = new BinaryAnd<String, Integer>(Constant.TRUE);
        assertTrue(p.test("xyzzy",3));
        for (int i=0;i<10;i++) {
            p.and(Constant.TRUE);
            assertTrue(p.test("xyzzy",3));
        }

        BinaryAnd<String, Integer> q = new BinaryAnd<String, Integer>(Constant.TRUE);
        assertTrue(q.test("xyzzy",3));
        for (int i=0;i<10;i++) {
            q.and(Constant.TRUE);
            assertTrue(q.test("xyzzy",3));
        }

        BinaryAnd<String, Integer> r = new BinaryAnd<String, Integer>(p,q);
        assertTrue(r.test("xyzzy",3));
    }

    @Test
    public void testFalse() throws Exception {
        assertTrue(!(new BinaryAnd<String, Integer>(Constant.FALSE)).test("xyzzy",3));
        assertTrue(!(new BinaryAnd<String, Integer>(Constant.TRUE,Constant.FALSE)).test("xyzzy",3));
        assertTrue(!(new BinaryAnd<String, Integer>(Constant.TRUE,Constant.TRUE,Constant.FALSE)).test("xyzzy",3));

        BinaryAnd<String, Integer> p = new BinaryAnd<String, Integer>(Constant.FALSE);
        assertTrue(!p.test("xyzzy",3));
        for (int i=0;i<10;i++) {
            p.and(Constant.FALSE);
            assertTrue(!p.test("xyzzy",3));
        }

        BinaryAnd<String, Integer> q = new BinaryAnd<String, Integer>(Constant.TRUE);
        assertTrue(q.test("xyzzy",3));
        for (int i=0;i<10;i++) {
            q.and(Constant.TRUE);
            assertTrue(q.test("xyzzy",3));
        }

        BinaryAnd<String, Integer> r = new BinaryAnd<String, Integer>(p,q);
        assertTrue(!r.test("xyzzy",3));
    }

    @Test
    public void testDuplicateAdd() throws Exception {
        BinaryPredicate<Object, Object> p = Constant.TRUE;
        BinaryAnd<String, Integer> q = new BinaryAnd<String, Integer>(p,p);
        assertTrue(q.test("xyzzy",3));
        for (int i=0;i<10;i++) {
            q.and(p);
            assertTrue(q.test("xyzzy",3));
        }
    }

    @SuppressWarnings("rawtypes")
    @Test
    public void testEquals() throws Exception {
        BinaryAnd<Object, Object> p = new BinaryAnd<Object, Object>();
        assertEquals(p,p);
        BinaryAnd<Object, Object> q = new BinaryAnd<Object, Object>();
        assertObjectsAreEqual(p,q);
        BinaryOr<Object, Object> r = new BinaryOr<Object, Object>();
        assertObjectsAreNotEqual(p,r);

        for (int i=0;i<3;i++) {
            p.and(Constant.truePredicate());
            assertObjectsAreNotEqual(p,q);
            q.and(Constant.truePredicate());
            assertObjectsAreEqual(p,q);
            p.and(new BinaryAnd<Object, Object>(Constant.truePredicate(),Constant.falsePredicate()));
            assertObjectsAreNotEqual(p,q);
            q.and(new BinaryAnd<Object, Object>(Constant.truePredicate(),Constant.falsePredicate()));
            assertObjectsAreEqual(p,q);
        }

        assertObjectsAreNotEqual(p,Constant.truePredicate());
        Iterable<BinaryPredicate<Object, Object>> iterable = Arrays.<BinaryPredicate<Object, Object>>asList(
           (BinaryPredicate<Object, Object>)Constant.truePredicate());
        assertObjectsAreNotEqual(p,new BinaryAnd(iterable));
        assertObjectsAreNotEqual(p,new BinaryAnd((Iterable<BinaryPredicate<Object, Object>>)null));
        assertObjectsAreNotEqual(p,new BinaryAnd((BinaryPredicate<Object, Object>[])null));
        assertObjectsAreNotEqual(p,new BinaryAnd((BinaryPredicate<Object, Object>)null));
        assertTrue(!p.equals(null));
    }

}
