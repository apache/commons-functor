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
package org.apache.commons.functor.core.composite;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.apache.commons.functor.BaseFunctorTest;
import org.apache.commons.functor.BinaryPredicate;
import org.apache.commons.functor.core.Constant;
import org.junit.Test;

/**
 * @version $Revision$ $Date$
 * @author Rodney Waldhoff
 */
public class TestBinaryNot extends BaseFunctorTest {

    // Functor Testing Framework
    // ------------------------------------------------------------------------

    protected Object makeFunctor() {
        return new BinaryNot<Object, Object>(Constant.TRUE);
    }

    // Tests
    // ------------------------------------------------------------------------

    @Test
    public void testTest() throws Exception {
        BinaryPredicate<Object, Object> truePred = new BinaryNot<Object, Object>(Constant.FALSE);
        assertTrue(truePred.test(null,null));
        assertTrue(truePred.test("xyzzy","abcde"));
        assertTrue(truePred.test("xyzzy",new Integer(3)));
    }

    @Test
    public void testEquals() throws Exception {
        BinaryNot<Object, Object> p = new BinaryNot<Object, Object>(Constant.TRUE);
        assertEquals(p,p);
        assertObjectsAreEqual(p,new BinaryNot<Object, Object>(Constant.TRUE));
        assertObjectsAreEqual(p,BinaryNot.not(Constant.TRUE));
        assertObjectsAreNotEqual(p,new BinaryNot<Object, Object>(Constant.FALSE));
        assertObjectsAreNotEqual(p,Constant.TRUE);
    }

    @Test
    public void testNotNull() throws Exception {
        assertNull(BinaryNot.not(null));
    }

    @Test
    public void testNotNotNull() throws Exception {
        assertNotNull(BinaryNot.not(Constant.truePredicate()));
    }
}
