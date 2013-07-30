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
import org.apache.commons.functor.Function;
import org.apache.commons.functor.Procedure;
import org.apache.commons.functor.core.Constant;
import org.apache.commons.functor.core.NoOp;
import org.junit.Test;

/**
 * @version $Revision$ $Date$
 */
public class TestFunctionProcedure extends BaseFunctorTest {

    // Functor Testing Framework
    // ------------------------------------------------------------------------

    @Override
    protected Object makeFunctor() {
        return new FunctionProcedure<Object>(Constant.of("K"));
    }

    // Tests
    // ------------------------------------------------------------------------

    @Test
    public void testRun() throws Exception {
        class EvaluateCounter implements Function<Object, Integer> {
            int count = 0;
            public Integer evaluate(Object a) { return Integer.valueOf(count++); }
        }
        EvaluateCounter counter = new EvaluateCounter();
        Procedure<Object> p = new FunctionProcedure<Object>(counter);
        assertEquals(0,counter.count);
        p.run(null);
        assertEquals(1,counter.count);
        p.run("x");
        assertEquals(2,counter.count);
    }

    @Test
    public void testEquals() throws Exception {
        Procedure<Object> p = new FunctionProcedure<Object>(Constant.of("K"));
        assertEquals(p,p);
        assertObjectsAreEqual(p,new FunctionProcedure<Object>(Constant.of("K")));
        assertObjectsAreNotEqual(p,NoOp.INSTANCE);
        assertObjectsAreNotEqual(p,new FunctionProcedure<Object>(Constant.of("J")));
        assertTrue(!p.equals(null));
    }

    @Test
    public void testAdaptNull() throws Exception {
        assertNull(FunctionProcedure.adapt(null));
    }

    @Test
    public void testAdapt() throws Exception {
        assertNotNull(FunctionProcedure.adapt(Constant.of("K")));
    }
}
