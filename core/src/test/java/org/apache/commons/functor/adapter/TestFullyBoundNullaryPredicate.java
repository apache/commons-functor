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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.apache.commons.functor.BaseFunctorTest;
import org.apache.commons.functor.NullaryPredicate;
import org.apache.commons.functor.core.Constant;
import org.apache.commons.functor.core.RightIdentity;
import org.junit.Test;

/**
 * @version $Revision: 1365377 $ $Date: 2012-07-24 21:59:23 -0300 (Tue, 24 Jul 2012) $
 */
public class TestFullyBoundNullaryPredicate extends BaseFunctorTest {

    // Functor Testing Framework
    // ------------------------------------------------------------------------

    @Override
    protected Object makeFunctor() {
        return new FullyBoundNullaryPredicate(Constant.TRUE, null, "xyzzy");
    }

    // Tests
    // ------------------------------------------------------------------------

    @Test
    public void testTest() throws Exception {
        NullaryPredicate p = new FullyBoundNullaryPredicate(
                new BinaryFunctionBinaryPredicate<Object, Boolean>(RightIdentity.<Object, Boolean> function()), "foo",
                Boolean.TRUE);
        assertTrue(p.test());
    }

    @Test
    public void testEquals() throws Exception {
        NullaryPredicate p = new FullyBoundNullaryPredicate(Constant.TRUE, "xyzzy", null);
        assertEquals(p, p);
        assertObjectsAreEqual(p, new FullyBoundNullaryPredicate(Constant.TRUE, "xyzzy", null));
        assertObjectsAreNotEqual(p, Constant.TRUE);
        assertObjectsAreNotEqual(p, new FullyBoundNullaryPredicate(Constant.FALSE, "xyzzy", null));
        assertObjectsAreNotEqual(p, new FullyBoundNullaryPredicate(Constant.TRUE, "foo", null));
        assertObjectsAreNotEqual(p, new FullyBoundNullaryPredicate(Constant.TRUE, null, "xyzzy"));
        assertObjectsAreNotEqual(new FullyBoundNullaryPredicate(Constant.TRUE, null, "xyzzy"), new FullyBoundNullaryPredicate(Constant.TRUE, null, null));
        assertObjectsAreEqual(new FullyBoundNullaryPredicate(Constant.TRUE, "foo", "xyzzy"), new FullyBoundNullaryPredicate(Constant.TRUE, "foo", "xyzzy"));
        assertTrue(!p.equals(null));
    }

    @Test
    public void testAdaptNull() throws Exception {
        assertNull(FullyBoundNullaryPredicate.bind(null, "xyzzy", null));
    }

    @Test
    public void testAdapt() throws Exception {
        assertNotNull(FullyBoundNullaryPredicate.bind(Constant.FALSE, "xyzzy", "foobar"));
        assertNotNull(FullyBoundNullaryPredicate.bind(Constant.FALSE, null, null));
    }
}
