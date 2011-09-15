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
import org.apache.commons.functor.UnaryPredicate;
import org.apache.commons.functor.core.Constant;
import org.apache.commons.functor.core.LeftIdentity;
import org.junit.Test;

/**
 * @version $Revision$ $Date$
 * @author Rodney Waldhoff
 */
public class TestRightBoundPredicate extends BaseFunctorTest {

    // Functor Testing Framework
    // ------------------------------------------------------------------------

    protected Object makeFunctor() {
        return new RightBoundPredicate<Object>(Constant.TRUE, "xyzzy");
    }

    // Tests
    // ------------------------------------------------------------------------

    @Test
    public void testTest() throws Exception {
        UnaryPredicate<Boolean> f = new RightBoundPredicate<Boolean>(
                new BinaryFunctionBinaryPredicate<Boolean, Object>(LeftIdentity.<Boolean, Object> function()), "foo");
        assertEquals(true, f.test(Boolean.TRUE));
        assertEquals(false, f.test(Boolean.FALSE));
    }

    @Test
    public void testEquals() throws Exception {
        UnaryPredicate<Boolean> f = new RightBoundPredicate<Boolean>(Constant.TRUE, "xyzzy");
        assertEquals(f, f);
        assertObjectsAreEqual(f, new RightBoundPredicate<Boolean>(Constant.TRUE, "xyzzy"));
        assertObjectsAreNotEqual(f, Constant.TRUE);
        assertObjectsAreNotEqual(f, new RightBoundPredicate<Boolean>(Constant.FALSE, "xyzzy"));
        assertObjectsAreNotEqual(f, new RightBoundPredicate<Boolean>(Constant.TRUE, "foo"));
        assertObjectsAreNotEqual(f, new RightBoundPredicate<Boolean>(Constant.TRUE, null));
    }

    @Test
    public void testAdaptNull() throws Exception {
        assertNull(RightBoundPredicate.bind(null, "xyzzy"));
    }

    @Test
    public void testAdapt() throws Exception {
        assertNotNull(RightBoundPredicate.bind(Constant.FALSE, "xyzzy"));
        assertNotNull(RightBoundPredicate.bind(Constant.FALSE, null));
    }
}
