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

import static org.junit.Assert.*;

import org.apache.commons.functor.BaseFunctorTest;
import org.apache.commons.functor.Function;
import org.apache.commons.functor.core.Constant;
import org.apache.commons.functor.core.IsNotSame;
import org.apache.commons.functor.core.IsSame;
import org.junit.Test;

/**
 * @version $Revision: 1345136 $ $Date: 2012-06-01 09:47:06 -0300 (Fri, 01 Jun 2012) $
 */
public class TestBinaryFunctionFunction extends BaseFunctorTest {

    // Functor Testing Framework
    // ------------------------------------------------------------------------

    @Override
    protected Object makeFunctor() {
        return new BinaryFunctionFunction<Object, Object>(BinaryPredicateBinaryFunction.adapt(IsSame.INSTANCE));
    }

    // Tests
    // ------------------------------------------------------------------------

    @Test
    public void testTestWhenTrue() throws Exception {
        Function<Object, Boolean> f = new BinaryFunctionFunction<Object, Boolean>(BinaryPredicateBinaryFunction.adapt(IsSame.INSTANCE));
        assertEquals(Boolean.TRUE, f.evaluate(null));
    }

    @Test
    public void testTestWhenFalse() throws Exception {
        Function<Object, Boolean> f = new BinaryFunctionFunction<Object, Boolean>(BinaryPredicateBinaryFunction.adapt(IsNotSame.INSTANCE));
        assertEquals(Boolean.FALSE, f.evaluate(null));
    }

    @Test
    public void testEquals() throws Exception {
        Function<Object, Boolean> f = new BinaryFunctionFunction<Object, Boolean>(BinaryPredicateBinaryFunction.adapt(IsSame.INSTANCE));
        assertEquals(f, f);
        assertObjectsAreEqual(f, new BinaryFunctionFunction<Object, Boolean>(BinaryPredicateBinaryFunction.adapt(IsSame.INSTANCE)));
        assertObjectsAreNotEqual(f, Constant.truePredicate());
        assertObjectsAreNotEqual(f, new BinaryFunctionFunction<Object, Boolean>(BinaryPredicateBinaryFunction.adapt(IsNotSame.INSTANCE)));
    }

    @Test
    public void testAdaptNull() throws Exception {
        assertNull(BinaryFunctionFunction.adapt(null));
    }

    @Test
    public void testAdapt() throws Exception {
        assertNotNull(BinaryFunctionFunction.adapt(Constant.TRUE));
    }
}
