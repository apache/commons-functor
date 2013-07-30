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
package org.apache.commons.functor.adapter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.apache.commons.functor.BaseFunctorTest;
import org.apache.commons.functor.NullaryPredicate;
import org.apache.commons.functor.core.Constant;
import org.apache.commons.functor.core.Identity;
import org.junit.Test;

/**
 * @version $Revision: 1365377 $ $Date: 2012-07-24 21:59:23 -0300 (Tue, 24 Jul 2012) $
 */
public class TestBoundNullaryPredicate extends BaseFunctorTest {

    // Functor Testing Framework
    // ------------------------------------------------------------------------

    @Override
    protected Object makeFunctor() {
        return new BoundNullaryPredicate(Constant.TRUE,"xyzzy");
    }

    // Tests
    // ------------------------------------------------------------------------

    @Test
    public void testTest() throws Exception {
        {
            NullaryPredicate p = new BoundNullaryPredicate(new FunctionPredicate<Boolean>(Identity.<Boolean>instance()),Boolean.TRUE);
            assertTrue(p.test());
        }
        {
            NullaryPredicate p = new BoundNullaryPredicate(new FunctionPredicate<Boolean>(Identity.<Boolean>instance()),Boolean.FALSE);
            assertFalse(p.test());
        }
    }

    @Test
    public void testEquals() throws Exception {
        NullaryPredicate f = new BoundNullaryPredicate(Constant.TRUE,"xyzzy");
        assertEquals(f,f);
        assertObjectsAreEqual(f,new BoundNullaryPredicate(Constant.TRUE,"xyzzy"));
        assertObjectsAreNotEqual(f,Constant.TRUE);
        assertObjectsAreNotEqual(f,new BoundNullaryPredicate(Constant.TRUE,"foo"));
        assertObjectsAreNotEqual(f,new BoundNullaryPredicate(Constant.FALSE,"xyzzy"));
        assertObjectsAreNotEqual(f,new BoundNullaryPredicate(Constant.TRUE,null));
        assertObjectsAreEqual(new BoundNullaryPredicate(Constant.TRUE,null),new BoundNullaryPredicate(Constant.TRUE,null));
        assertTrue(!f.equals(null));
    }

    @Test
    public void testAdaptNull() throws Exception {
        assertNull(BoundNullaryPredicate.bind(null,"xyzzy"));
    }

    @Test
    public void testAdapt() throws Exception {
        assertNotNull(BoundNullaryPredicate.bind(Constant.TRUE,"xyzzy"));
        assertNotNull(BoundNullaryPredicate.bind(Constant.TRUE,null));
    }
}
