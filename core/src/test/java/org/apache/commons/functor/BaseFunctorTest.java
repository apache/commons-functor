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
package org.apache.commons.functor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * @version $Revision$ $Date$
 */
public abstract class BaseFunctorTest {

    // Framework
    // ------------------------------------------------------------------------

    protected abstract Object makeFunctor() throws Exception;

    // Tests
    // ------------------------------------------------------------------------

    @Test
    public final void testObjectEquals() throws Exception {
        Object obj = makeFunctor();
        assertEquals("equals must be reflexive", obj, obj);
        assertEquals("hashCode must be reflexive", obj.hashCode(), obj.hashCode());
        assertTrue(!obj.equals(null)); // should be able to compare to null

        Object obj2 = makeFunctor();
        if (obj.equals(obj2)) {
            assertEquals("equals implies hash equals", obj.hashCode(), obj2.hashCode());
            assertEquals("equals must be symmetric", obj2, obj);
        } else {
            assertTrue("equals must be symmetric", !obj2.equals(obj));
        }

        assertTrue("a functor is not equal to an integer", !obj.equals(Integer.valueOf(1)));
    }

    @Test
    public void testToStringIsOverridden() throws Exception {
        Object obj = makeFunctor();
        assertNotNull("toString should never return null", obj.toString());
        assertTrue(obj.getClass().getName() + " should override toString(), found \"" + obj.toString() + "\"", !obj
            .toString().equals(objectToString(obj)));
    }

    // protected utils
    // ------------------------------------------------------------------------

    public static void assertObjectsAreEqual(Object a, Object b) {
        assertEquals(a, b);
        assertEquals(b, a);
        assertEquals(a.hashCode(), b.hashCode());
        assertEquals(a.toString(), b.toString()); // not strictly required
    }

    public static void assertObjectsAreNotEqual(Object a, Object b) {
        assertTrue(!a.equals(b));
        assertTrue(!b.equals(a));
        assertTrue(a.hashCode() != b.hashCode()); // not strictly required
        assertTrue(!a.toString().equals(b.toString())); // not strictly required
    }

    // private utils
    // ------------------------------------------------------------------------
    private String objectToString(Object obj) {
        return obj.getClass().getName() + "@" + Integer.toHexString(obj.hashCode());
    }
}
