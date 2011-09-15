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

import org.apache.commons.functor.BaseFunctorTest;
import org.apache.commons.functor.Function;
import org.apache.commons.functor.core.Constant;
import org.apache.commons.functor.core.LeftIdentity;
import org.apache.commons.functor.core.RightIdentity;
import org.junit.Test;

/**
 * @version $Revision$ $Date$
 * @author Matt Benson
 */
public class TestFullyBoundFunction extends BaseFunctorTest {

    // Functor Testing Framework
    // ------------------------------------------------------------------------

    protected Object makeFunctor() {
        return new FullyBoundFunction<Object>(RightIdentity.FUNCTION, null, "xyzzy");
    }

    // Tests
    // ------------------------------------------------------------------------

    @Test
    public void testEvaluate() throws Exception {
        Function<Object> f = new FullyBoundFunction<Object>(RightIdentity.FUNCTION, null, "foo");
        assertEquals("foo", f.evaluate());
    }

    @Test
    public void testEquals() throws Exception {
        Function<Object> f = new FullyBoundFunction<Object>(RightIdentity.FUNCTION, null, "xyzzy");
        assertEquals(f, f);
        assertObjectsAreEqual(f, new FullyBoundFunction<Object>(RightIdentity.FUNCTION, null, "xyzzy"));
        assertObjectsAreNotEqual(f, Constant.of("xyzzy"));
        assertObjectsAreNotEqual(f, new FullyBoundFunction<Object>(LeftIdentity.FUNCTION, null, "xyzzy"));
        assertObjectsAreNotEqual(f, new FullyBoundFunction<Object>(RightIdentity.FUNCTION, null, "bar"));
    }

    @Test
    public void testAdaptNull() throws Exception {
        assertNull(FullyBoundFunction.bind(null, null, "xyzzy"));
    }

    @Test
    public void testAdapt() throws Exception {
        assertNotNull(FullyBoundFunction.bind(RightIdentity.FUNCTION, "xyzzy", "foobar"));
        assertNotNull(FullyBoundFunction.bind(RightIdentity.FUNCTION, null, null));
    }
}
