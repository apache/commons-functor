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
import org.apache.commons.functor.NullaryProcedure;
import org.apache.commons.functor.core.Constant;
import org.apache.commons.functor.core.NoOp;
import org.junit.Test;

/**
 * @version $Revision: 1365329 $ $Date: 2012-07-24 19:34:23 -0300 (Tue, 24 Jul 2012) $
 */
public class TestConditionalNullaryProcedure extends BaseFunctorTest {

    // Functor Testing Framework
    // ------------------------------------------------------------------------

    @Override
    protected Object makeFunctor() {
        return new ConditionalNullaryProcedure(
            Constant.TRUE,
            NoOp.INSTANCE,
            NoOp.INSTANCE);
    }

    // Tests
    // ------------------------------------------------------------------------

    @Test
    public void testRun() throws Exception {
        {
            RunCounter left = new RunCounter();
            RunCounter right = new RunCounter();
            ConditionalNullaryProcedure p = new ConditionalNullaryProcedure(
                Constant.TRUE,
                left,
                right);
            assertEquals(0,left.count);
            assertEquals(0,right.count);
            p.run();
            assertEquals(1,left.count);
            assertEquals(0,right.count);
            p.run();
            assertEquals(2,left.count);
            assertEquals(0,right.count);
            p.run();
            assertEquals(3,left.count);
            assertEquals(0,right.count);
        }
        {
            RunCounter left = new RunCounter();
            RunCounter right = new RunCounter();
            ConditionalNullaryProcedure p = new ConditionalNullaryProcedure(
                Constant.FALSE,
                left,
                right);
            assertEquals(0,left.count);
            assertEquals(0,right.count);
            p.run();
            assertEquals(0,left.count);
            assertEquals(1,right.count);
            p.run();
            assertEquals(0,left.count);
            assertEquals(2,right.count);
            p.run();
            assertEquals(0,left.count);
            assertEquals(3,right.count);
        }
    }

    @Test
    public void testEquals() throws Exception {
        ConditionalNullaryProcedure p = new ConditionalNullaryProcedure(
            Constant.FALSE,
            NoOp.INSTANCE,
            NoOp.INSTANCE);
        assertEquals(p,p);
        assertObjectsAreEqual(p,new ConditionalNullaryProcedure(
            Constant.FALSE,
            NoOp.INSTANCE,
            NoOp.INSTANCE));
        assertObjectsAreNotEqual(p,new ConditionalNullaryProcedure(
            Constant.TRUE,
            NoOp.INSTANCE,
            NoOp.INSTANCE));
        assertObjectsAreNotEqual(p,new ConditionalNullaryProcedure(
            Constant.TRUE,
            NoOp.INSTANCE,
            NoOp.INSTANCE));
        assertObjectsAreNotEqual(p,new ConditionalNullaryProcedure(
            Constant.FALSE,
            new RunCounter(),
            NoOp.INSTANCE));
        assertObjectsAreNotEqual(p,new ConditionalNullaryProcedure(
            Constant.FALSE,
            NoOp.INSTANCE,
            new RunCounter()));
        assertObjectsAreNotEqual(p,new ConditionalNullaryProcedure(
            Constant.TRUE,
            NoOp.INSTANCE));
        assertTrue(!p.equals(null));
    }

    // Classes
    // ------------------------------------------------------------------------

    static class RunCounter implements NullaryProcedure {
        public void run() {
            count++;
        }
        public int count = 0;
    }
}
