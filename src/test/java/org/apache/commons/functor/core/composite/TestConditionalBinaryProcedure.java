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
import static org.junit.Assert.assertTrue;

import org.apache.commons.functor.BaseFunctorTest;
import org.apache.commons.functor.BinaryProcedure;
import org.apache.commons.functor.core.Constant;
import org.apache.commons.functor.core.LeftIdentity;
import org.apache.commons.functor.core.NoOp;
import org.junit.Test;

/**
 * @version $Revision$ $Date$
 */
public class TestConditionalBinaryProcedure extends BaseFunctorTest {

    // Functor Testing Framework
    // ------------------------------------------------------------------------

    @Override
    protected Object makeFunctor() {
        return new ConditionalBinaryProcedure<Object, Object>(
            Constant.TRUE,
            NoOp.instance(),
            NoOp.instance());
    }

    // Tests
    // ------------------------------------------------------------------------

    @Test
    public void testRun() throws Exception {
        RunCounter left = new RunCounter();
        RunCounter right = new RunCounter();
        ConditionalBinaryProcedure<Boolean, Object> p = new ConditionalBinaryProcedure<Boolean, Object>(
            LeftIdentity.PREDICATE,
            left,
            right);
        assertEquals(0,left.count);
        assertEquals(0,right.count);
        p.run(Boolean.TRUE, null);
        assertEquals(1,left.count);
        assertEquals(0,right.count);
        p.run(Boolean.FALSE, null);
        assertEquals(1,left.count);
        assertEquals(1,right.count);
        p.run(Boolean.TRUE, null);
        assertEquals(2,left.count);
        assertEquals(1,right.count);
    }

    @Test
    public void testEquals() throws Exception {
        ConditionalBinaryProcedure<?, ?> p = new ConditionalBinaryProcedure<Boolean, Object>(
            LeftIdentity.PREDICATE,
            NoOp.instance(),
            NoOp.instance());
        assertEquals(p,p);
        assertObjectsAreEqual(p,new ConditionalBinaryProcedure<Boolean, Object>(
            LeftIdentity.PREDICATE,
            NoOp.instance(),
            NoOp.instance()));
        assertObjectsAreNotEqual(p,new ConditionalBinaryProcedure<Object, Object>(
            Constant.TRUE,
            NoOp.instance(),
            NoOp.instance()));
        assertObjectsAreNotEqual(p,new ConditionalBinaryProcedure<Boolean, Object>(
            LeftIdentity.PREDICATE,
            new RunCounter(),
            NoOp.instance()));
        assertObjectsAreNotEqual(p,new ConditionalBinaryProcedure<Boolean, Object>(
                LeftIdentity.PREDICATE,
            NoOp.instance(),
            new RunCounter()));
        assertObjectsAreNotEqual(p,new ConditionalBinaryProcedure<Boolean, Object>(
            Constant.TRUE,
            NoOp.instance()));
        assertTrue(!p.equals(null));
    }

    // Classes
    // ------------------------------------------------------------------------

    static class RunCounter implements BinaryProcedure<Object, Object> {
        public void run(Object left, Object right) {
            count++;
        }
        public int count = 0;
    }
}
