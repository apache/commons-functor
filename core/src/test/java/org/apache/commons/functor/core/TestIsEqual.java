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
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.apache.commons.functor.BaseFunctorTest;
import org.apache.commons.functor.BinaryPredicate;
import org.apache.commons.functor.Predicate;
import org.junit.Test;

/**
 * @version $Revision$ $Date$
 */
public class TestIsEqual extends BaseFunctorTest {

    // Functor Testing Framework
    // ------------------------------------------------------------------------

    @Override
    protected Object makeFunctor() {
        return new IsEqual<Object, Object>();
    }

    // Tests
    // ------------------------------------------------------------------------

    @Test
    public void testTest() throws Exception {
        IsEqual<Object, Object> p = new IsEqual<Object, Object>();
        assertTrue("For symmetry, two nulls should be equal", p.test(null, null));
        assertTrue(p.test("foo", "foo"));
        assertFalse(p.test(null, "foo"));
        assertFalse(p.test("foo", null));
        assertTrue(p.test(Integer.valueOf(3), Integer.valueOf(3)));
        assertFalse(p.test(null, Integer.valueOf(3)));
        assertFalse(p.test(Integer.valueOf(3), null));

        assertFalse(p.test(Integer.valueOf(3), Integer.valueOf(4)));
        assertFalse(p.test(Integer.valueOf(4), Integer.valueOf(3)));
        assertFalse(p.test("3", Integer.valueOf(3)));
        assertFalse(p.test(Integer.valueOf(3), "3"));
    }

    @Test
    public void testEquals() throws Exception {
        BinaryPredicate<Object, Object> f = new IsEqual<Object, Object>();
        assertEquals(f, f);

        assertObjectsAreEqual(f, new IsEqual<Object, Object>());
        assertObjectsAreEqual(f, IsEqual.instance());
        assertObjectsAreNotEqual(f, Constant.truePredicate());
    }

    @Test
    public void testConstant() throws Exception {
        assertEquals(IsEqual.instance(), IsEqual.instance());
        assertNotSame(IsEqual.instance(), IsEqual.instance());
        assertSame(IsEqual.INSTANCE, IsEqual.INSTANCE);
    }

    @Test
    public void testToPredicate() {
       Predicate<Integer> isEqual = IsEqual.to(Integer.valueOf(1));
       assertTrue(isEqual.test(Integer.valueOf(1)));
       assertFalse(isEqual.test(Integer.valueOf(2)));
    }
}
