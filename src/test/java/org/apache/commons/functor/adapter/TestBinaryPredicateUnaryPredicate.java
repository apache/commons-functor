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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.apache.commons.functor.BaseFunctorTest;
import org.apache.commons.functor.UnaryPredicate;
import org.apache.commons.functor.core.Constant;
import org.apache.commons.functor.core.IsNotSame;
import org.apache.commons.functor.core.IsSame;
import org.junit.Test;

/**
 * @version $Revision$ $Date$
 * @author Matt Benson
 */
public class TestBinaryPredicateUnaryPredicate extends BaseFunctorTest {

    // Functor Testing Framework
    // ------------------------------------------------------------------------

    protected Object makeFunctor() {
        return new BinaryPredicateUnaryPredicate<Object>(IsSame.INSTANCE);
    }

    // Tests
    // ------------------------------------------------------------------------

    @Test
    public void testTestWhenTrue() throws Exception {
        UnaryPredicate<Object> p = new BinaryPredicateUnaryPredicate<Object>(IsSame.INSTANCE);
        assertTrue(p.test(null));
    }

    @Test
    public void testTestWhenFalse() throws Exception {
        UnaryPredicate<Object> p = new BinaryPredicateUnaryPredicate<Object>(IsNotSame.INSTANCE);
        assertFalse(p.test(null));
    }

    @Test
    public void testEquals() throws Exception {
        UnaryPredicate<Object> p = new BinaryPredicateUnaryPredicate<Object>(IsSame.INSTANCE);
        assertEquals(p, p);
        assertObjectsAreEqual(p, new BinaryPredicateUnaryPredicate<Object>(IsSame.INSTANCE));
        assertObjectsAreNotEqual(p, Constant.truePredicate());
        assertObjectsAreNotEqual(p, new BinaryPredicateUnaryPredicate<Object>(IsNotSame.INSTANCE));
    }

    @Test
    public void testAdaptNull() throws Exception {
        assertNull(BinaryPredicateUnaryPredicate.adapt(null));
    }

    @Test
    public void testAdapt() throws Exception {
        assertNotNull(BinaryPredicateUnaryPredicate.adapt(Constant.TRUE));
    }
}
