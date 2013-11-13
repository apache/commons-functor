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

import org.apache.commons.functor.BaseFunctorTest;
import org.apache.commons.functor.BinaryPredicate;
import org.apache.commons.functor.Predicate;
import org.junit.Test;

/**
 * @version $Revision$ $Date$
 */
public class TestIsNotEqual extends BaseFunctorTest {

    // Functor Testing Framework
    // ------------------------------------------------------------------------

    @Override
    protected Object makeFunctor() {
        return new IsNotEqual<Object, Object>();
    }

    // Tests
    // ------------------------------------------------------------------------

    @Test
    public void testTest() throws Exception {
        IsNotEqual<Object, Object> p = new IsNotEqual<Object, Object>();
        assertTrue("For symmetry, two nulls should be equal", !p.test(null, null));
        assertTrue(!p.test("foo", "foo"));
        assertTrue(p.test(null, "foo"));
        assertTrue(p.test("foo", null));
        assertTrue(!p.test(Integer.valueOf(3), Integer.valueOf(3)));
        assertTrue(p.test(null, Integer.valueOf(3)));
        assertTrue(p.test(Integer.valueOf(3), null));

        assertTrue(p.test(Integer.valueOf(3), Integer.valueOf(4)));
        assertTrue(p.test(Integer.valueOf(4), Integer.valueOf(3)));
        assertTrue(p.test("3", Integer.valueOf(3)));
        assertTrue(p.test(Integer.valueOf(3), "3"));
    }

    @Test
    public void testEquals() throws Exception {
        BinaryPredicate<Object, Object> p = new IsNotEqual<Object, Object>();
        assertEquals(p, p);
        assertObjectsAreEqual(p, new IsNotEqual<Object, Object>());
        assertObjectsAreEqual(p, IsNotEqual.instance());
        assertObjectsAreNotEqual(p, Constant.truePredicate());
    }

    @Test
    public void testConstant() throws Exception {
        assertEquals(IsNotEqual.instance(), IsNotEqual.instance());
    }

    @Test
    public void testToPredicate() {
        Predicate<Integer> isNotEqual = IsNotEqual.to(Integer.valueOf(1));
        assertTrue(isNotEqual.test(Integer.valueOf(2)));
        assertFalse(isNotEqual.test(Integer.valueOf(1)));
    }
}
