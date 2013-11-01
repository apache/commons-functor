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

import org.apache.commons.functor.BaseFunctorTest;
import org.apache.commons.functor.NullaryPredicate;
import org.apache.commons.functor.NullaryProcedure;
import org.apache.commons.functor.core.Constant;
import org.apache.commons.functor.core.NoOp;
import org.junit.Test;

/**
 * @version $Revision: 1365329 $ $Date: 2012-07-24 19:34:23 -0300 (Tue, 24 Jul 2012) $
 */
public class TestAbstractLoopNullaryProcedure extends BaseFunctorTest {

    // Functor Testing Framework
    // ------------------------------------------------------------------------

    @Override
    protected Object makeFunctor() {
        return new MockLoopProcedure(Constant.FALSE, NoOp.INSTANCE);
    }
    
    // Tests
    // ------------------------------------------------------------------------

    @Test
    public void testEquals() {
        MockLoopProcedure p = new MockLoopProcedure(Constant.FALSE, NoOp.INSTANCE);
        assertEquals(p,p);
        assertObjectsAreEqual(p,new MockLoopProcedure(Constant.FALSE, NoOp.INSTANCE));
        assertObjectsAreNotEqual(p,new MockLoopProcedure(Constant.TRUE, NoOp.INSTANCE));
        assertObjectsAreNotEqual(p,new MockLoopProcedure(Constant.FALSE, new NullarySequence()));
    }
}

class MockLoopProcedure extends AbstractLoopNullaryProcedure {
    public MockLoopProcedure(NullaryPredicate condition, NullaryProcedure action) {
        super(condition,action);
    }

    public void run() {
    }
}
