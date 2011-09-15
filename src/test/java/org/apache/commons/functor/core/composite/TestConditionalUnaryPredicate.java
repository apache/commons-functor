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
import org.apache.commons.functor.core.Constant;
import org.apache.commons.functor.core.Identity;
import org.junit.Test;

/**
 * @version $Revision$ $Date$
 * @author Rodney Waldhoff
 */
public class TestConditionalUnaryPredicate extends BaseFunctorTest {

    // Functor Testing Framework
    // ------------------------------------------------------------------------

    protected Object makeFunctor() {
        return new ConditionalUnaryPredicate<Object>(
            Constant.TRUE,
            Constant.FALSE,
            Constant.TRUE);
    }

    // Tests
    // ------------------------------------------------------------------------

    @Test
    public void testTest() throws Exception {
        ConditionalUnaryPredicate<Object> p = new ConditionalUnaryPredicate<Object>(
            Identity.INSTANCE,
            Constant.TRUE,
            Constant.FALSE);
        assertTrue(p.test(Boolean.TRUE));
        assertTrue(!p.test(Boolean.FALSE));
    }

    @Test
    public void testEquals() throws Exception {
        ConditionalUnaryPredicate<Object> p = new ConditionalUnaryPredicate<Object>(
            Identity.INSTANCE,
            Constant.TRUE,
            Constant.TRUE);
        assertEquals(p,p);
        assertObjectsAreEqual(p,new ConditionalUnaryPredicate<Object>(
            Identity.INSTANCE,
            Constant.TRUE,
            Constant.TRUE));
        assertObjectsAreNotEqual(p,new ConditionalUnaryPredicate<Object>(
            Identity.INSTANCE,
            Constant.FALSE,
            Constant.TRUE));
        assertObjectsAreNotEqual(p,new ConditionalUnaryPredicate<Object>(
            Constant.TRUE,
            Constant.TRUE,
            Constant.TRUE));
    }
}
