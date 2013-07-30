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
import org.apache.commons.functor.NullaryPredicate;
import org.apache.commons.functor.core.Constant;
import org.junit.Test;

/**
 * @version $Revision: 1365329 $ $Date: 2012-07-24 19:34:23 -0300 (Tue, 24 Jul 2012) $
 */
public class TestNullaryAnd extends BaseFunctorTest {

    // Functor Testing Framework
    // ------------------------------------------------------------------------

    @Override
    protected Object makeFunctor() {
        return new NullaryAnd(Constant.TRUE, Constant.TRUE);
    }

    // Tests
    // ------------------------------------------------------------------------

    @Test
    public void testTrue() throws Exception {
        assertTrue((new NullaryAnd()).test());
        assertTrue((new NullaryAnd(Constant.TRUE)).test());
        assertTrue((new NullaryAnd(Constant.TRUE,Constant.TRUE)).test());
        assertTrue((new NullaryAnd(Constant.TRUE,Constant.TRUE,Constant.TRUE)).test());

        NullaryAnd p = new NullaryAnd(Constant.TRUE);
        assertTrue(p.test());
        for (int i=0;i<10;i++) {
            p.and(Constant.TRUE);
            assertTrue(p.test());
        }

        NullaryAnd q = new NullaryAnd(Constant.TRUE);
        assertTrue(q.test());
        for (int i=0;i<10;i++) {
            q.and(Constant.TRUE);
            assertTrue(q.test());
        }

        NullaryAnd r = new NullaryAnd(p,q);
        assertTrue(r.test());
    }

    @Test
    public void testFalse() throws Exception {
        assertTrue(!(new NullaryAnd(Constant.FALSE)).test());
        assertTrue(!(new NullaryAnd(Constant.TRUE,Constant.FALSE)).test());
        assertTrue(!(new NullaryAnd(Constant.TRUE,Constant.TRUE,Constant.FALSE)).test());

        NullaryAnd p = new NullaryAnd(Constant.FALSE);
        assertTrue(!p.test());
        for (int i=0;i<10;i++) {
            p.and(Constant.FALSE);
            assertTrue(!p.test());
        }

        NullaryAnd q = new NullaryAnd(Constant.TRUE);
        assertTrue(q.test());
        for (int i=0;i<10;i++) {
            q.and(Constant.TRUE);
            assertTrue(q.test());
        }

        NullaryAnd r = new NullaryAnd(p,q);
        assertTrue(!r.test());
    }

    @Test
    public void testDuplicateAdd() throws Exception {
        NullaryPredicate p = Constant.TRUE;
        NullaryAnd q = new NullaryAnd(p,p);
        assertTrue(q.test());
        for (int i=0;i<10;i++) {
            q.and(p);
            assertTrue(q.test());
        }
    }

    @Test
    public void testEquals() throws Exception {
        NullaryAnd p = new NullaryAnd();
        assertEquals(p,p);
        NullaryAnd q = new NullaryAnd();
        assertObjectsAreEqual(p,q);

        for (int i=0;i<3;i++) {
            p.and(Constant.TRUE);
            assertObjectsAreNotEqual(p,q);
            q.and(Constant.truePredicate());
            assertObjectsAreEqual(p,q);
            p.and(new NullaryAnd(Constant.TRUE,Constant.FALSE));
            assertObjectsAreNotEqual(p,q);
            q.and(new NullaryAnd(Constant.TRUE,Constant.FALSE));
            assertObjectsAreEqual(p,q);
        }

        assertObjectsAreNotEqual(p,Constant.TRUE);
        Iterable<NullaryPredicate> iterable = Arrays.<NullaryPredicate>asList(
            Constant.TRUE,
            new NullaryAnd(Constant.TRUE, Constant.FALSE),
            Constant.TRUE,
            new NullaryAnd(Constant.TRUE, Constant.FALSE),
            Constant.TRUE,
            new NullaryAnd(Constant.TRUE, Constant.FALSE));
        assertObjectsAreEqual(p,new NullaryAnd(iterable));

        assertObjectsAreNotEqual(p,new NullaryAnd((Iterable<NullaryPredicate>)null));
        assertObjectsAreNotEqual(p,new NullaryAnd((NullaryPredicate[])null));
        assertObjectsAreNotEqual(p,new NullaryAnd((NullaryPredicate)null));

        assertTrue(!p.equals(null));
    }

}
