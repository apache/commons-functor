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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.apache.commons.functor.BaseFunctorTest;
import org.apache.commons.functor.BinaryPredicate;
import org.apache.commons.functor.adapter.BinaryFunctionBinaryPredicate;
import org.apache.commons.functor.core.Constant;
import org.apache.commons.functor.core.LeftIdentity;
import org.junit.Test;

/**
 * @version $Revision$ $Date$
 */
public class TestTransposedPredicate extends BaseFunctorTest {

    // Functor Testing Framework
    // ------------------------------------------------------------------------

    @Override
    protected Object makeFunctor() {
        return new TransposedPredicate<Object, Object>(Constant.TRUE);
    }

    // Tests
    // ------------------------------------------------------------------------

    @Test
    public void testEvaluate() throws Exception {
        BinaryPredicate<Boolean, Boolean> p = new TransposedPredicate<Boolean, Boolean>(BinaryFunctionBinaryPredicate
                .adapt(LeftIdentity.<Boolean, Boolean> function()));
        assertTrue(p.test(Boolean.FALSE,Boolean.TRUE));
        assertFalse(p.test(Boolean.TRUE,Boolean.FALSE));
    }

    @Test
    public void testEquals() throws Exception {
        BinaryPredicate<Object, Object> p = new TransposedPredicate<Object, Object>(Constant.TRUE);
        assertEquals(p,p);
        assertObjectsAreEqual(p,new TransposedPredicate<Object, Object>(Constant.TRUE));
        assertObjectsAreEqual(p,TransposedPredicate.transpose(Constant.TRUE));
        assertObjectsAreNotEqual(p,new TransposedPredicate<Object, Object>(Constant.FALSE));
        assertObjectsAreNotEqual(p,Constant.TRUE);
        assertTrue(!p.equals((TransposedPredicate<?, ?>)null));
    }

    @Test
    public void testTransposeNull() throws Exception {
        assertNull(TransposedPredicate.transpose(null));
    }

    @Test
    public void testTranspose() throws Exception {
        assertNotNull(TransposedPredicate.transpose(Constant.TRUE));
    }
}
