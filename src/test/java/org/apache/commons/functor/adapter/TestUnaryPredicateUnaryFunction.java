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
import org.apache.commons.functor.UnaryFunction;
import org.apache.commons.functor.core.Constant;
import org.junit.Test;

/**
 * @version $Revision$ $Date$
 * @author Rodney Waldhoff
 */
public class TestUnaryPredicateUnaryFunction extends BaseFunctorTest {

    // Functor Testing Framework
    // ------------------------------------------------------------------------

    protected Object makeFunctor() {
        return new UnaryPredicateUnaryFunction<Object>(Constant.TRUE);
    }

    // Tests
    // ------------------------------------------------------------------------

    @Test
    public void testTestWhenTrue() throws Exception {
        UnaryFunction<Object, Boolean> f = new UnaryPredicateUnaryFunction<Object>(Constant.TRUE);
        assertEquals(Boolean.TRUE,f.evaluate(null));
    }

    @Test
    public void testTestWhenFalse() throws Exception {
        UnaryFunction<Object, Boolean> f = new UnaryPredicateUnaryFunction<Object>(Constant.FALSE);
        assertEquals(Boolean.FALSE,f.evaluate(null));
    }

    @Test
    public void testEquals() throws Exception {
        UnaryFunction<Object, Boolean> f = new UnaryPredicateUnaryFunction<Object>(Constant.TRUE);
        assertEquals(f,f);
        assertObjectsAreEqual(f,new UnaryPredicateUnaryFunction<Object>(Constant.TRUE));
        assertObjectsAreNotEqual(f,Constant.of("x"));
        assertObjectsAreNotEqual(f,new UnaryPredicateUnaryFunction<Object>(Constant.FALSE));
    }

    @Test
    public void testAdaptNull() throws Exception {
        assertNull(UnaryFunctionUnaryPredicate.adapt(null));
    }

    @Test
    public void testAdapt() throws Exception {
        assertNotNull(UnaryPredicateUnaryFunction.adapt(Constant.TRUE));
    }
}
