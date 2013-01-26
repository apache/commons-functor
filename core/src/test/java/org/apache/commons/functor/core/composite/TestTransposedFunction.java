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
import org.apache.commons.functor.BinaryFunction;
import org.apache.commons.functor.core.Constant;
import org.apache.commons.functor.core.LeftIdentity;
import org.apache.commons.functor.core.RightIdentity;
import org.junit.Test;

/**
 * @version $Revision$ $Date$
 */
public class TestTransposedFunction extends BaseFunctorTest {

    // Functor Testing Framework
    // ------------------------------------------------------------------------

    @Override
    protected Object makeFunctor() {
        return new TransposedFunction<Object, Object, Object>(LeftIdentity.FUNCTION);
    }

    // Tests
    // ------------------------------------------------------------------------

    @Test
    public void testEvaluate() throws Exception {
        BinaryFunction<Object, Object, Object> f = new TransposedFunction<Object, Object, Object>(LeftIdentity.FUNCTION);
        assertEquals("xyzzy",f.evaluate(null,"xyzzy"));
        assertNull(f.evaluate("xyzzy",null));
    }

    @Test
    public void testEquals() throws Exception {
        BinaryFunction<Object, Object, Object> f = new TransposedFunction<Object, Object, Object>(LeftIdentity.FUNCTION);
        assertEquals(f,f);
        assertObjectsAreEqual(f,new TransposedFunction<Object, Object, Object>(LeftIdentity.FUNCTION));
        assertObjectsAreNotEqual(f,new TransposedFunction<Object, Object, Object>(RightIdentity.FUNCTION));
        assertObjectsAreNotEqual(f,Constant.of("y"));
        assertTrue(!f.equals((TransposedFunction<?, ?, ?>)null));
    }

    @Test
    public void testTransposeNull() throws Exception {
        assertNull(TransposedFunction.transpose(null));
    }

    @Test
    public void testTranspose() throws Exception {
        assertNotNull(TransposedFunction.transpose(Constant.of("x")));
    }
}
