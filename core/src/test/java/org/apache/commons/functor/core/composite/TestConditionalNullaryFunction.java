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
import org.junit.Test;

/**
 * @version $Revision: 1365329 $ $Date: 2012-07-24 19:34:23 -0300 (Tue, 24 Jul 2012) $
 */
public class TestConditionalNullaryFunction extends BaseFunctorTest {

    // Functor Testing Framework
    // ------------------------------------------------------------------------

    @Override
    protected Object makeFunctor() {
        return new ConditionalNullaryFunction<Object>(
            Constant.TRUE,
            Constant.of("left"),
            Constant.of("right"));
    }

    // Tests
    // ------------------------------------------------------------------------

    @Test
    public void testEvaluate() throws Exception {
        {
            ConditionalNullaryFunction<Object> f = new ConditionalNullaryFunction<Object>(
                Constant.TRUE,
                Constant.of("left"),
                Constant.of("right"));
            assertEquals("left",f.evaluate());
        }
        {
            ConditionalNullaryFunction<Object> f = new ConditionalNullaryFunction<Object>(
                Constant.FALSE,
                Constant.of("left"),
                Constant.of("right"));
            assertEquals("right",f.evaluate());
        }
    }

    @Test
    public void testEquals() throws Exception {
        ConditionalNullaryFunction<Object> f = new ConditionalNullaryFunction<Object>(
            Constant.TRUE,
            Constant.of("left"),
            Constant.of("right"));
        assertEquals(f,f);
        assertObjectsAreEqual(f,new ConditionalNullaryFunction<Object>(
            Constant.TRUE,
            Constant.of("left"),
            Constant.of("right")));
        assertObjectsAreNotEqual(f,new ConditionalNullaryFunction<Object>(
            Constant.TRUE,
            Constant.of(null),
            Constant.of("right")));
        assertObjectsAreNotEqual(f,new ConditionalNullaryFunction<Object>(
            Constant.TRUE,
            Constant.of("left"),
            Constant.of(null)));
        assertObjectsAreNotEqual(f,new ConditionalNullaryFunction<Object>(
            Constant.FALSE,
            Constant.of("left"),
            Constant.of("right")));
        assertTrue(!f.equals(null));
    }
}
