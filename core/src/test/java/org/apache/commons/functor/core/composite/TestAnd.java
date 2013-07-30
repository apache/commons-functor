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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.apache.commons.functor.BaseFunctorTest;
import org.apache.commons.functor.Predicate;
import org.apache.commons.functor.core.Constant;
import org.junit.Test;

/**
 * @version $Revision$ $Date$
 */
@SuppressWarnings("unchecked")
public class TestAnd extends BaseFunctorTest {

    // Functor Testing Framework
    // ------------------------------------------------------------------------

    @Override
    protected Object makeFunctor() {
        return new And<Object>(Constant.TRUE, Constant.TRUE);
    }

    // Tests
    // ------------------------------------------------------------------------

    @Test
    public void testTrue() throws Exception {
        assertTrue((new And<Object>()).test("xyzzy"));
        assertTrue((new And<Object>(Constant.TRUE)).test("xyzzy"));
        assertTrue((new And<Object>(Constant.TRUE,Constant.TRUE)).test("xyzzy"));
        assertTrue((new And<Object>(Constant.TRUE,Constant.TRUE,Constant.TRUE)).test("xyzzy"));

        And<Object> p = new And<Object>(Constant.TRUE);
        assertTrue(p.test("xyzzy"));
        for (int i=0;i<10;i++) {
            p.and(Constant.TRUE);
            assertTrue(p.test("xyzzy"));
        }

        And<Object> q = new And<Object>(Constant.TRUE);
        assertTrue(q.test("xyzzy"));
        for (int i=0;i<10;i++) {
            q.and(Constant.TRUE);
            assertTrue(q.test("xyzzy"));
        }

        And<Object> r = new And<Object>(p,q);
        assertTrue(r.test("xyzzy"));
    }

    @Test
    public void testFalse() throws Exception {
        assertFalse(new And<Object>(Constant.FALSE).test("xyzzy"));
        assertFalse(new And<Object>(Constant.TRUE,Constant.FALSE).test("xyzzy"));
        assertFalse(new And<Object>(Constant.TRUE,Constant.TRUE,Constant.FALSE).test("xyzzy"));

        And<Object> p = new And<Object>(Constant.FALSE);
        assertTrue(!p.test("xyzzy"));
        for (int i=0;i<10;i++) {
            p.and(Constant.TRUE);
            assertTrue(!p.test("xyzzy"));
        }

        And<Object> q = new And<Object>(Constant.TRUE);
        assertTrue(q.test("xyzzy"));
        for (int i=0;i<10;i++) {
            q.and(Constant.TRUE);
            assertTrue(q.test("xyzzy"));
        }

        And<Object> r = new And<Object>(p,q);
        assertTrue(!r.test("xyzzy"));
    }

    @Test
    public void testDuplicateAdd() throws Exception {
        Predicate<Object> p = Constant.TRUE;
        And<Object> q = new And<Object>(p,p);
        assertTrue(q.test("xyzzy"));
        for (int i=0;i<10;i++) {
            q.and(p);
            assertTrue(q.test("xyzzy"));
        }
    }

    @SuppressWarnings("rawtypes")
    @Test
    public void testEquals() throws Exception {
        And<Object> p = new And<Object>();
        assertEquals(p,p);
        And<Object> q = new And<Object>();
        assertObjectsAreEqual(p,q);

        for (int i=0;i<3;i++) {
            p.and(Constant.truePredicate());
            assertObjectsAreNotEqual(p,q);
            q.and(Constant.truePredicate());
            assertObjectsAreEqual(p,q);
            p.and(new And<Object>(Constant.truePredicate(),Constant.falsePredicate()));
            assertObjectsAreNotEqual(p,q);
            q.and(new And<Object>(Constant.truePredicate(),Constant.falsePredicate()));
            assertObjectsAreEqual(p,q);
        }

        assertObjectsAreNotEqual(p,Constant.truePredicate());
        And<Object> r = new And<Object>();
        r.and(Constant.truePredicate());
        r.and(new And<Object>(Constant.truePredicate(),Constant.falsePredicate()));
        assertObjectsAreEqual(r, new And<Object>(r.getPredicateList()));
        assertObjectsAreNotEqual(p, new And((Iterable<Predicate<Object>>)null));
        assertObjectsAreNotEqual(p, new And((Predicate<Object>[])null));
        assertObjectsAreNotEqual(p, new And((Predicate<Object>)null));
        assertTrue(!p.equals(null));
    }

}
