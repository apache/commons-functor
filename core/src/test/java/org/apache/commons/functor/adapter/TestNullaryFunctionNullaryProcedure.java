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
import org.apache.commons.functor.NullaryFunction;
import org.apache.commons.functor.NullaryProcedure;
import org.apache.commons.functor.core.Constant;
import org.apache.commons.functor.core.NoOp;
import org.junit.Test;

/**
 * @version $Revision: 1365377 $ $Date: 2012-07-24 21:59:23 -0300 (Tue, 24 Jul 2012) $
 */
public class TestNullaryFunctionNullaryProcedure extends BaseFunctorTest {

    // Functor Testing Framework
    // ------------------------------------------------------------------------

    @Override
    protected Object makeFunctor() {
        return new NullaryFunctionNullaryProcedure(Constant.of("K"));
    }

    // Tests
    // ------------------------------------------------------------------------

    @Test
    public void testRun() throws Exception {
        class EvaluateCounter implements NullaryFunction<Integer> {
            int count = 0;
            public Integer evaluate() { return Integer.valueOf(count++); }
        }
        EvaluateCounter counter = new EvaluateCounter();
        NullaryProcedure p = new NullaryFunctionNullaryProcedure(counter);
        assertEquals(0,counter.count);
        p.run();
        assertEquals(1,counter.count);
        p.run();
        assertEquals(2,counter.count);
    }

    @Test
    public void testEquals() throws Exception {
        NullaryProcedure p = new NullaryFunctionNullaryProcedure(Constant.of("K"));
        assertEquals(p,p);
        assertObjectsAreEqual(p,new NullaryFunctionNullaryProcedure(Constant.of("K")));
        assertObjectsAreNotEqual(p,NoOp.INSTANCE);
        assertObjectsAreNotEqual(p,new NullaryFunctionNullaryProcedure(Constant.of("J")));
        assertTrue(!p.equals(null));
    }

    @Test
    public void testAdaptNull() throws Exception {
        assertNull(NullaryFunctionNullaryProcedure.adapt(null));
    }

    @Test
    public void testAdapt() throws Exception {
        assertNotNull(NullaryFunctionNullaryProcedure.adapt(Constant.of("K")));
    }
}
