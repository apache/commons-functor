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
import org.apache.commons.functor.UnaryPredicate;
import org.junit.Test;

/**
 * @version $Revision$ $Date$
 * @author Rodney Waldhoff
 */
public class TestIsNull extends BaseFunctorTest {

    // Functor Testing Framework
    // ------------------------------------------------------------------------

    protected Object makeFunctor() {
        return new IsNull<Object>();
    }

    // Tests
    // ------------------------------------------------------------------------

    @Test
    public void testTest() throws Exception {
        UnaryPredicate<Object> p = new IsNull<Object>();
        assertTrue(p.test(null));
        assertFalse(p.test("foo"));
        assertFalse(p.test(new Integer(3)));
    }

    @Test
    public void testAsBinary() throws Exception {
        assertTrue(IsNull.left().test(null,"not null"));
        assertFalse(IsNull.left().test("not null",null));
        assertTrue(IsNull.right().test("not null",null));
        assertFalse(IsNull.right().test(null,"not null"));
    }

    @Test
    public void testEquals() throws Exception {
        UnaryPredicate<Object> p = new IsNull<Object>();
        assertEquals(p,p);
        assertObjectsAreEqual(p,new IsNull<Object>());
        assertObjectsAreEqual(p,IsNull.instance());
        assertObjectsAreNotEqual(p,Constant.TRUE);
    }

    @Test
    public void testConstant() throws Exception {
        assertEquals(IsNull.instance(),IsNull.instance());
    }
}
