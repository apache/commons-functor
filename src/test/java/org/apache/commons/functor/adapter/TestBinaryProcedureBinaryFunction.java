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

import org.apache.commons.functor.BaseFunctorTest;
import org.apache.commons.functor.BinaryFunction;
import org.apache.commons.functor.BinaryProcedure;
import org.apache.commons.functor.core.Constant;
import org.apache.commons.functor.core.NoOp;
import org.junit.Test;

/**
 * @version $Revision$ $Date$
 */
public class TestBinaryProcedureBinaryFunction extends BaseFunctorTest {

    // Functor Testing Framework
    // ------------------------------------------------------------------------

    @Override
    protected Object makeFunctor() {
        return new BinaryProcedureBinaryFunction<Object, Object, Object>(NoOp.instance());
    }

    // Tests
    // ------------------------------------------------------------------------

    @Test
    public void testEvaluate() throws Exception {
        BinaryFunction<Object, Object, Object> f = new BinaryProcedureBinaryFunction<Object, Object, Object>(NoOp.instance());
        assertNull(f.evaluate(null,null));
    }

    @Test
    public void testEquals() throws Exception {
        BinaryFunction<Object, Object, Object> f = new BinaryProcedureBinaryFunction<Object, Object, Object>(new NoOp());
        assertEquals(f,f);
        assertObjectsAreEqual(f,new BinaryProcedureBinaryFunction<Object, Object, Object>(new NoOp()));
        assertObjectsAreNotEqual(f,Constant.of("x"));
        assertObjectsAreNotEqual(f, new BinaryProcedureBinaryFunction<Object, Object, Object>(
                new BinaryProcedure<Object, Object>() {
                    public void run(Object a, Object b) {
                    }
                }));
        assertObjectsAreNotEqual(f,Constant.of(null));
    }

    @Test
    public void testAdaptNull() throws Exception {
        assertNull(BinaryProcedureBinaryFunction.adapt(null));
    }

    @Test
    public void testAdapt() throws Exception {
        assertNotNull(BinaryProcedureBinaryFunction.adapt(NoOp.instance()));
    }
}
