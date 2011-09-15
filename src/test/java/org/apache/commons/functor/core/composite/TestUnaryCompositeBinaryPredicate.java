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
import org.apache.commons.functor.BinaryPredicate;
import org.apache.commons.functor.core.Constant;
import org.apache.commons.functor.core.Identity;
import org.apache.commons.functor.core.LeftIdentity;
import org.apache.commons.functor.core.RightIdentity;
import org.junit.Test;

/**
 * @version $Revision$ $Date$
 * @author Rodney Waldhoff
 */
public class TestUnaryCompositeBinaryPredicate extends BaseFunctorTest {

    // Functor Testing Framework
    // ------------------------------------------------------------------------

    protected Object makeFunctor() {
        return new UnaryCompositeBinaryPredicate<Boolean, Boolean>(
                RightIdentity.PREDICATE,
                Constant.FALSE,
                new Identity<Boolean>());
    }

    // Tests
    // ------------------------------------------------------------------------

    @Test
    public void testEvaluate() throws Exception {
        BinaryPredicate<Boolean, Boolean> f = new UnaryCompositeBinaryPredicate<Boolean, Boolean>(
                RightIdentity.PREDICATE,
                Constant.FALSE,
                new Identity<Boolean>());
        assertEquals(true,f.test(Boolean.TRUE,Boolean.TRUE));
        assertEquals(true,f.test(null,Boolean.TRUE));
    }

    @Test
    public void testEquals() throws Exception {
        BinaryPredicate<Boolean, Boolean> f = new UnaryCompositeBinaryPredicate<Boolean, Boolean>(
                LeftIdentity.PREDICATE,
                Constant.TRUE,
                Constant.FALSE);
        assertEquals(f,f);
        assertObjectsAreEqual(f,new UnaryCompositeBinaryPredicate<Boolean, Boolean>(
                LeftIdentity.PREDICATE,
                Constant.TRUE,
                Constant.FALSE));
        assertObjectsAreNotEqual(f,new UnaryCompositeBinaryPredicate<Boolean, Boolean>(
                RightIdentity.PREDICATE,
                Constant.TRUE,
                Constant.FALSE));
        assertObjectsAreNotEqual(f,new UnaryCompositeBinaryPredicate<Boolean, Boolean>(
                LeftIdentity.PREDICATE,
                new Identity<Boolean>(),
                Constant.TRUE));
    }

}
