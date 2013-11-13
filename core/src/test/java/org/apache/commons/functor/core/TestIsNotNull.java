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
package org.apache.commons.functor.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.apache.commons.functor.BaseFunctorTest;
import org.apache.commons.functor.Predicate;
import org.junit.Test;

/**
 * @version $Revision$ $Date$
 */
public class TestIsNotNull extends BaseFunctorTest {

    // Functor Testing Framework
    // ------------------------------------------------------------------------

    @Override
    protected Object makeFunctor() {
        return new IsNotNull<Object>();
    }

    // Tests
    // ------------------------------------------------------------------------

    @Test
    public void testTest() throws Exception {
        Predicate<Object> p = new IsNotNull<Object>();
        assertTrue(!p.test(null));
        assertTrue(p.test("foo"));
        assertTrue(p.test(Integer.valueOf(3)));
    }

    @Test
    public void testEquals() throws Exception {
        Predicate<Object> p = new IsNotNull<Object>();
        assertEquals(p, p);
        assertObjectsAreEqual(p, new IsNotNull<Object>());
        assertObjectsAreEqual(p, IsNotNull.instance());
        assertObjectsAreNotEqual(p, Constant.TRUE);
    }

    @Test
    public void testConstant() throws Exception {
        assertEquals(IsNotNull.instance(), IsNotNull.instance());
    }
}
