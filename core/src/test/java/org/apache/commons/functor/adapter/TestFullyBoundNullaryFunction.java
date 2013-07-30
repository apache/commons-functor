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
import org.apache.commons.functor.NullaryFunction;
import org.apache.commons.functor.core.Constant;
import org.apache.commons.functor.core.LeftIdentity;
import org.apache.commons.functor.core.RightIdentity;
import org.junit.Test;

/**
 * @version $Revision: 1365377 $ $Date: 2012-07-24 21:59:23 -0300 (Tue, 24 Jul 2012) $
 */
public class TestFullyBoundNullaryFunction extends BaseFunctorTest {

    // Functor Testing Framework
    // ------------------------------------------------------------------------

    @Override
    protected Object makeFunctor() {
        return new FullyBoundNullaryFunction<Object>(RightIdentity.FUNCTION, null, "xyzzy");
    }

    // Tests
    // ------------------------------------------------------------------------

    @Test
    public void testEvaluate() throws Exception {
        NullaryFunction<Object> f = new FullyBoundNullaryFunction<Object>(RightIdentity.FUNCTION, null, "foo");
        assertEquals("foo", f.evaluate());
    }

    @Test
    public void testEquals() throws Exception {
        NullaryFunction<Object> f = new FullyBoundNullaryFunction<Object>(RightIdentity.FUNCTION, null, "xyzzy");
        assertEquals(f, f);
        assertObjectsAreEqual(f, new FullyBoundNullaryFunction<Object>(RightIdentity.FUNCTION, null, "xyzzy"));
        assertObjectsAreEqual(new FullyBoundNullaryFunction<Object>(RightIdentity.FUNCTION, "bar", "xyzzy"),
                              new FullyBoundNullaryFunction<Object>(RightIdentity.FUNCTION, "bar", "xyzzy"));
        assertObjectsAreEqual(new FullyBoundNullaryFunction<Object>(RightIdentity.FUNCTION, "bar", null),
                              new FullyBoundNullaryFunction<Object>(RightIdentity.FUNCTION, "bar", null));
        assertObjectsAreNotEqual(f, Constant.of("xyzzy"));
        assertObjectsAreNotEqual(f, new FullyBoundNullaryFunction<Object>(LeftIdentity.FUNCTION, null, "xyzzy"));
        assertObjectsAreNotEqual(f, new FullyBoundNullaryFunction<Object>(RightIdentity.FUNCTION, "bar", "xyzzy"));
        assertObjectsAreNotEqual(f, new FullyBoundNullaryFunction<Object>(RightIdentity.FUNCTION, null, "bar"));
        assertObjectsAreNotEqual(f, new FullyBoundNullaryFunction<Object>(RightIdentity.FUNCTION, null, null));
        assertObjectsAreNotEqual(new FullyBoundNullaryFunction<Object>(RightIdentity.FUNCTION, "xyzzy", "bar"), new FullyBoundNullaryFunction<Object>(RightIdentity.FUNCTION, null, "bar"));
        assertObjectsAreNotEqual(new FullyBoundNullaryFunction<Object>(RightIdentity.FUNCTION, "xyzzy", "bar"), new FullyBoundNullaryFunction<Object>(RightIdentity.FUNCTION, "bar", "bar"));
        assertTrue(!f.equals(null));
    }

    @Test
    public void testAdaptNull() throws Exception {
        assertNull(FullyBoundNullaryFunction.bind(null, null, "xyzzy"));
    }

    @Test
    public void testAdapt() throws Exception {
        assertNotNull(FullyBoundNullaryFunction.bind(RightIdentity.FUNCTION, "xyzzy", "foobar"));
        assertNotNull(FullyBoundNullaryFunction.bind(RightIdentity.FUNCTION, null, null));
    }
}
