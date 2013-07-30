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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.apache.commons.functor.BaseFunctorTest;
import org.apache.commons.functor.BinaryPredicate;
import org.apache.commons.functor.Predicate;
import org.junit.Test;

/**
 * @version $Revision$ $Date$
 */
public class TestIsInstance extends BaseFunctorTest {

    // Functor Testing Framework
    // ------------------------------------------------------------------------

    @Override
    protected Object makeFunctor() {
        return IsInstance.of(String.class);
    }

    // Tests
    // ------------------------------------------------------------------------

    @Test
    public void testTest() throws Exception {
        BinaryPredicate<Object, Class<?>> p = IsInstance.INSTANCE;
        assertFalse(p.test(null, Number.class));
        assertFalse(p.test("foo", Number.class));
        assertTrue(p.test(3, Number.class));
        assertTrue(p.test(3L, Number.class));
    }

    @Test
    public void testBoundTest() throws Exception {
        Predicate<Object> p = IsInstance.of(Number.class);
        assertFalse(p.test(null));
        assertFalse(p.test("foo"));
        assertTrue(p.test(3));
        assertTrue(p.test(3L));
    }

    @Test
    public void testInstanceOfNull() throws Exception {
        BinaryPredicate<Object, Class<?>> p = new IsInstance<Object>();
        try {
            p.test("foo", null);
            fail("Expected NullPointerException");
        } catch(NullPointerException e) {
            // expected
        }
    }

    @Test
    public void testEquals() throws Exception {
        BinaryPredicate<Object, Class<?>> p = IsInstance.INSTANCE;
        assertEquals(p, p);
        assertObjectsAreEqual(p, IsInstance.instance());
        assertObjectsAreNotEqual(p,Constant.truePredicate());
    }

    @Test
    public void testBoundEquals() throws Exception {
        Predicate<Object> p = IsInstance.of(Object.class);
        assertEquals(p,p);
        assertObjectsAreEqual(p,IsInstance.of(Object.class));
        assertObjectsAreNotEqual(p,Constant.truePredicate());
        assertObjectsAreNotEqual(p,IsInstance.of(null));
        assertObjectsAreNotEqual(p,IsInstance.of(String.class));
    }
}
