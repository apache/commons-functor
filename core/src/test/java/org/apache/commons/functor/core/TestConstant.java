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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.apache.commons.functor.BaseFunctorTest;
import org.junit.Test;

/**
 * @version $Revision$ $Date$
 */
public class TestConstant extends BaseFunctorTest {

    // Functor Testing Framework
    // ------------------------------------------------------------------------

    @Override
    protected Object makeFunctor() {
        return new Constant<Object>("K");
    }

    // Tests
    // ------------------------------------------------------------------------

    @Test
    public void testEvaluate() throws Exception {
        Constant<Object> f = new Constant<Object>("xyzzy");
        assertEquals("xyzzy", f.evaluate());
        assertEquals("xyzzy", f.evaluate(null));
        assertEquals("xyzzy", f.evaluate(null, null));
        assertEquals("xyzzy", f.evaluate());
        assertEquals("xyzzy", f.evaluate("foo"));
        assertEquals("xyzzy", f.evaluate("foo", Integer.valueOf(2)));
    }

    @Test
    public void testEvaluateConstantNull() throws Exception {
        Constant<Object> f = new Constant<Object>(null);
        assertNull(f.evaluate());
        assertNull(f.evaluate(null));
        assertNull(f.evaluate(null, null));
        assertNull(f.evaluate());
        assertNull(f.evaluate("foo"));
        assertNull(f.evaluate("foo", Integer.valueOf(2)));
    }

    @Test
    public void testConstantTrue() throws Exception {
        Constant<Object> truePred = new Constant<Object>(Boolean.TRUE);
        assertTrue(truePred.test());
        assertTrue(truePred.test(null));
        assertTrue(truePred.test(null, null));

        assertTrue(truePred.test());
        assertTrue(truePred.test("foo"));
        assertTrue(truePred.test("foo", Integer.valueOf(2)));
    }

    @Test
    public void testConstantFalse() throws Exception {
        Constant<Object> falsePred = new Constant<Object>(Boolean.FALSE);
        assertTrue(!falsePred.test());
        assertTrue(!falsePred.test(null));
        assertTrue(!falsePred.test(null, null));

        assertTrue(!falsePred.test());
        assertTrue(!falsePred.test("foo"));
        assertTrue(!falsePred.test("foo", Integer.valueOf(2)));
    }

    @Test
    public void testEquals() throws Exception {
        Constant<Object> f = new Constant<Object>("xyzzy");
        assertEquals(f, f);

        assertObjectsAreEqual(f, new Constant<Object>("xyzzy"));
        assertObjectsAreNotEqual(f, new Constant<Object>("abcde"));
        assertObjectsAreNotEqual(f, new Constant<Object>(null));
        assertObjectsAreEqual(new Constant<Object>(null), new Constant<Object>(null));
        assertTrue(!f.equals(null));
    }

    @Test
    public void testConstants() throws Exception {
        assertEquals(Constant.predicate(true), Constant.TRUE);

        assertEquals(Constant.truePredicate(), Constant.TRUE);
        assertSame(Constant.truePredicate(), Constant.TRUE);

        assertEquals(Constant.predicate(true), Constant.TRUE);
        assertSame(Constant.predicate(true), Constant.TRUE);

        assertEquals(Constant.falsePredicate(), Constant.FALSE);
        assertSame(Constant.falsePredicate(), Constant.FALSE);

        assertEquals(Constant.predicate(false), Constant.FALSE);
        assertSame(Constant.predicate(false), Constant.FALSE);
    }

}
