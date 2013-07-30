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
import org.apache.commons.functor.BinaryPredicate;
import org.apache.commons.functor.core.Constant;
import org.apache.commons.functor.core.Identity;
import org.apache.commons.functor.core.LeftIdentity;
import org.apache.commons.functor.core.RightIdentity;
import org.junit.Test;

/**
 * @version $Revision: 1365329 $ $Date: 2012-07-24 19:34:23 -0300 (Tue, 24 Jul 2012) $
 */
public class TestCompositeBinaryPredicate extends BaseFunctorTest {

    // Functor Testing Framework
    // ------------------------------------------------------------------------

    @Override
    protected Object makeFunctor() {
        return new CompositeBinaryPredicate<Boolean, Boolean>(
                RightIdentity.PREDICATE,
                Constant.FALSE,
                new Identity<Boolean>());
    }

    // Tests
    // ------------------------------------------------------------------------

    @Test
    public void testEvaluate() throws Exception {
        BinaryPredicate<Boolean, Boolean> f = new CompositeBinaryPredicate<Boolean, Boolean>(
                RightIdentity.PREDICATE,
                Constant.FALSE,
                new Identity<Boolean>());
        assertTrue(f.test(Boolean.TRUE,Boolean.TRUE));
        assertTrue(f.test(null,Boolean.TRUE));
    }

    @Test
    public void testEquals() throws Exception {
        BinaryPredicate<Boolean, Boolean> f = new CompositeBinaryPredicate<Boolean, Boolean>(
                LeftIdentity.PREDICATE,
                Constant.TRUE,
                Constant.FALSE);
        assertEquals(f,f);
        assertObjectsAreEqual(f,new CompositeBinaryPredicate<Boolean, Boolean>(
                LeftIdentity.PREDICATE,
                Constant.TRUE,
                Constant.FALSE));
        assertObjectsAreNotEqual(f,new CompositeBinaryPredicate<Boolean, Boolean>(
                RightIdentity.PREDICATE,
                Constant.TRUE,
                Constant.FALSE));
        assertObjectsAreNotEqual(f,new CompositeBinaryPredicate<Boolean, Boolean>(
                LeftIdentity.PREDICATE,
                Constant.FALSE,
                Constant.FALSE));
        assertObjectsAreNotEqual(f,new CompositeBinaryPredicate<Boolean, Boolean>(
                LeftIdentity.PREDICATE,
                new Identity<Boolean>(),
                Constant.TRUE));
        assertObjectsAreNotEqual(f,new CompositeBinaryPredicate<Boolean, Boolean>(
                LeftIdentity.PREDICATE,
                Constant.TRUE,
                new Identity<Boolean>()));
        assertTrue(!((CompositeBinaryPredicate<?,?>)f).equals(null));
    }

}
