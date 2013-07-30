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
import static org.junit.Assert.assertTrue;

import org.apache.commons.functor.BaseFunctorTest;
import org.apache.commons.functor.Procedure;
import org.apache.commons.functor.core.LeftIdentity;
import org.apache.commons.functor.core.NoOp;
import org.apache.commons.functor.core.composite.BinarySequence;
import org.junit.Test;

/**
 * @version $Revision$ $Date$
 */
public class TestRightBoundProcedure extends BaseFunctorTest {

    // Functor Testing Framework
    // ------------------------------------------------------------------------

    @Override
    protected Object makeFunctor() {
        return new RightBoundProcedure<Object>(NoOp.INSTANCE,"xyzzy");
    }

    // Tests
    // ------------------------------------------------------------------------

    @Test
    public void testRun() throws Exception {
        Procedure<Object> p = new RightBoundProcedure<Object>(
                new BinaryFunctionBinaryProcedure<Object, Object>(LeftIdentity.FUNCTION), "foo");
        p.run(Boolean.TRUE);
        p.run(Boolean.FALSE);
    }

    @Test
    public void testEquals() throws Exception {
        Procedure<Object> f = new RightBoundProcedure<Object>(NoOp.INSTANCE,"xyzzy");
        assertEquals(f,f);
        assertObjectsAreEqual(f,new RightBoundProcedure<Object>(NoOp.INSTANCE,"xyzzy"));
        assertObjectsAreNotEqual(f,NoOp.INSTANCE);
        assertObjectsAreNotEqual(f,new RightBoundProcedure<Object>(new BinaryFunctionBinaryProcedure<Object, Object>(LeftIdentity.FUNCTION),"xyzzy"));
        assertObjectsAreNotEqual(f,new RightBoundProcedure<Object>(NoOp.INSTANCE,"foo"));
        assertObjectsAreNotEqual(f,new RightBoundProcedure<Object>(NoOp.INSTANCE,null));
        assertObjectsAreEqual(new RightBoundProcedure<Object>(NoOp.INSTANCE,null),new RightBoundProcedure<Object>(NoOp.INSTANCE,null));
        assertObjectsAreNotEqual(new RightBoundProcedure<Object>(new BinarySequence<Object, Object>(),null),new RightBoundProcedure<Object>(NoOp.INSTANCE,null));
        assertTrue(!f.equals(null));
    }

    @Test
    public void testAdaptNull() throws Exception {
        assertNull(RightBoundProcedure.bind(null,"xyzzy"));
    }

    @Test
    public void testAdapt() throws Exception {
        assertNotNull(RightBoundProcedure.bind(NoOp.INSTANCE,"xyzzy"));
        assertNotNull(RightBoundProcedure.bind(NoOp.INSTANCE,null));
    }
}
