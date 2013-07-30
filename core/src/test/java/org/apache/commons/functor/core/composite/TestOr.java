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

import org.apache.commons.functor.BaseFunctorTest;
import org.apache.commons.functor.Predicate;
import org.apache.commons.functor.core.Constant;
import org.junit.Test;

/**
 * @version $Revision$ $Date$
 */
@SuppressWarnings("unchecked")
public class TestOr extends BaseFunctorTest {

    // Functor Testing Framework
    // ------------------------------------------------------------------------

    @Override
    protected Object makeFunctor() {
        return new Or<Object>(Constant.FALSE,Constant.TRUE);
    }

    // Tests
    // ------------------------------------------------------------------------

    @Test
    public void testTrue() throws Exception {
        assertTrue((new Or<Object>(Constant.TRUE)).test("xyzzy"));
        assertTrue((new Or<Object>(Constant.FALSE,Constant.TRUE)).test("xyzzy"));
        assertTrue((new Or<Object>(Constant.FALSE,Constant.FALSE,Constant.TRUE)).test("xyzzy"));

        Or<Object> p = new Or<Object>(Constant.TRUE);
        assertTrue(p.test("xyzzy"));
        for (int i=0;i<10;i++) {
            p.or(Constant.of(i%2==0));
            assertTrue(p.test("xyzzy"));
        }

        Or<Object> q = new Or<Object>(Constant.TRUE);
        assertTrue(q.test("xyzzy"));
        for (int i=0;i<10;i++) {
            q.or(Constant.of(i%2==0));
            assertTrue(q.test("xyzzy"));
        }

        Or<Object> r = new Or<Object>(p,q);
        assertTrue(r.test("xyzzy"));
    }

    @Test
    public void testFalse() throws Exception {
        assertTrue(!(new Or<Object>()).test("xyzzy"));
        assertTrue(!(new Or<Object>(Constant.FALSE)).test("xyzzy"));
        assertTrue(!(new Or<Object>(Constant.FALSE,Constant.FALSE)).test("xyzzy"));
        assertTrue(!(new Or<Object>(Constant.FALSE,Constant.FALSE,Constant.FALSE)).test("xyzzy"));

        Or<Object> p = new Or<Object>(Constant.FALSE);
        assertTrue(!p.test("xyzzy"));
        for (int i=0;i<10;i++) {
            p.or(Constant.FALSE);
            assertTrue(!p.test("xyzzy"));
        }

        Or<Object> q = new Or<Object>(Constant.FALSE);
        assertTrue(!q.test("xyzzy"));
        for (int i=0;i<10;i++) {
            q.or(Constant.FALSE);
            assertTrue(!q.test("xyzzy"));
        }

        Or<Object> r = new Or<Object>(p,q);
        assertTrue(!r.test("xyzzy"));
    }

    @Test
    public void testDuplicateAdd() throws Exception {
        Predicate<Object> p = Constant.TRUE;
        Or<Object> q = new Or<Object>(p,p);
        assertTrue(q.test("xyzzy"));
        for (int i=0;i<10;i++) {
            q.or(p);
            assertTrue(q.test("xyzzy"));
        }
    }

    @Test
    public void testEquals() throws Exception {
        Or<Object> p = new Or<Object>();
        assertEquals(p,p);

        Or<Object> q = new Or<Object>();
        assertObjectsAreEqual(p,q);

        And<Object> r = new And<Object>();
        assertObjectsAreNotEqual(p,r);

        for (int i=0;i<3;i++) {
            p.or(Constant.truePredicate());
            assertObjectsAreNotEqual(p,q);
            q.or(Constant.truePredicate());
            assertObjectsAreEqual(p,q);
            r.and(Constant.truePredicate());
            assertObjectsAreNotEqual(p,r);

            p.or(new Or<Object>(Constant.truePredicate(),Constant.falsePredicate()));
            assertObjectsAreNotEqual(p,q);
            q.or(new Or<Object>(Constant.truePredicate(),Constant.falsePredicate()));
            assertObjectsAreEqual(p,q);
            r.and(new Or<Object>(Constant.truePredicate(),Constant.falsePredicate()));
            assertObjectsAreNotEqual(p,r);
        }

        assertObjectsAreNotEqual(p,Constant.truePredicate());
        Or<Object> s = new Or<Object>();
        s.or(Constant.truePredicate());
        s.or(new Or<Object>(Constant.truePredicate(),Constant.falsePredicate()));
        assertObjectsAreEqual(s, new Or<Object>(s.getPredicateList()));
    }

}
