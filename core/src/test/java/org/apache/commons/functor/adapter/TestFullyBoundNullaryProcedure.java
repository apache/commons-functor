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
import org.apache.commons.functor.NullaryProcedure;
import org.apache.commons.functor.core.NoOp;
import org.apache.commons.functor.core.RightIdentity;
import org.junit.Test;

/**
 * @version $Revision: 1365377 $ $Date: 2012-07-24 21:59:23 -0300 (Tue, 24 Jul 2012) $
 */
public class TestFullyBoundNullaryProcedure extends BaseFunctorTest {

    // Functor Testing Framework
    // ------------------------------------------------------------------------

    @Override
    protected Object makeFunctor() {
        return new FullyBoundNullaryProcedure(NoOp.INSTANCE, "xyzzy", null);
    }

    // Tests
    // ------------------------------------------------------------------------

    @Test
    public void testRun() throws Exception {
        NullaryProcedure p = new FullyBoundNullaryProcedure(new BinaryFunctionBinaryProcedure<Object, Object>(
                RightIdentity.FUNCTION), "foo", null);
        p.run();
    }

    @Test
    public void testEquals() throws Exception {
        NullaryProcedure f = new FullyBoundNullaryProcedure(NoOp.INSTANCE, "xyzzy", null);
        assertEquals(f, f);
        assertObjectsAreEqual(f, new FullyBoundNullaryProcedure(NoOp.INSTANCE, "xyzzy", null));
        assertObjectsAreNotEqual(f, new NoOp());
        assertObjectsAreNotEqual(f, new FullyBoundNullaryProcedure(
                new BinaryFunctionBinaryProcedure<Object, Object>(RightIdentity.FUNCTION), "xyzzy", null));
        assertObjectsAreNotEqual(f, new FullyBoundNullaryProcedure(NoOp.INSTANCE, "foo", null));
        assertObjectsAreNotEqual(f, new FullyBoundNullaryProcedure(NoOp.INSTANCE, "xyzzy", "yzzyx"));
        assertObjectsAreNotEqual(new FullyBoundNullaryProcedure(NoOp.INSTANCE, "xyzzy", "yzzyx"), new FullyBoundNullaryProcedure(NoOp.INSTANCE, "xyzzy", null));
        assertObjectsAreNotEqual(f, new FullyBoundNullaryProcedure(NoOp.INSTANCE, null, null));
        assertObjectsAreEqual(new FullyBoundNullaryProcedure(NoOp.INSTANCE, null, null), new FullyBoundNullaryProcedure(NoOp.INSTANCE, null, null));
        assertObjectsAreEqual(new FullyBoundNullaryProcedure(NoOp.INSTANCE, "foo", null), new FullyBoundNullaryProcedure(NoOp.INSTANCE, "foo", null));
        assertObjectsAreEqual(new FullyBoundNullaryProcedure(NoOp.INSTANCE, "foo", "xyzzy"), new FullyBoundNullaryProcedure(NoOp.INSTANCE, "foo", "xyzzy"));
        assertTrue(!f.equals(null));
    }

    @Test
    public void testAdaptNull() throws Exception {
        assertNull(FullyBoundNullaryProcedure.bind(null, "xyzzy", null));
    }

    @Test
    public void testAdapt() throws Exception {
        assertNotNull(FullyBoundNullaryProcedure.bind(new NoOp(), "xyzzy", "foobar"));
        assertNotNull(FullyBoundNullaryProcedure.bind(new NoOp(), null, null));
    }
}
