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
import static org.junit.Assert.assertTrue;

import org.apache.commons.functor.BaseFunctorTest;
import org.apache.commons.functor.Function;
import org.apache.commons.functor.core.Constant;
import org.apache.commons.functor.core.LeftIdentity;
import org.apache.commons.functor.core.RightIdentity;
import org.junit.Test;

/**
 * @version $Revision$ $Date$
 */
public class TestLeftBoundFunction extends BaseFunctorTest {

    // Functor Testing Framework
    // ------------------------------------------------------------------------

    @Override
    protected Object makeFunctor() {
        return new LeftBoundFunction<Object, Object>(RightIdentity.FUNCTION,"xyzzy");
    }

    // Tests
    // ------------------------------------------------------------------------

    @Test
    public void testEvaluate() throws Exception {
        Function<Object, Object> f = new LeftBoundFunction<Object, Object>(RightIdentity.FUNCTION,"foo");
        assertEquals("xyzzy",f.evaluate("xyzzy"));
    }

    @Test
    public void testEquals() throws Exception {
        Function<Object, Object> f = new LeftBoundFunction<Object, Object>(RightIdentity.FUNCTION,"xyzzy");
        assertEquals(f,f);
        assertObjectsAreEqual(f,new LeftBoundFunction<Object, Object>(RightIdentity.FUNCTION,"xyzzy"));
        assertObjectsAreNotEqual(f,Constant.of("xyzzy"));
        assertObjectsAreNotEqual(f,new LeftBoundFunction<Object, Object>(LeftIdentity.FUNCTION,"xyzzy"));
        assertObjectsAreNotEqual(f,new LeftBoundFunction<Object, Object>(RightIdentity.FUNCTION,"bar"));
        assertObjectsAreNotEqual(f,new LeftBoundFunction<Object, Object>(RightIdentity.FUNCTION,null));
        assertObjectsAreEqual(new LeftBoundFunction<Object, Object>(RightIdentity.FUNCTION, null),new LeftBoundFunction<Object, Object>(RightIdentity.FUNCTION, null));
        assertTrue(!f.equals(null));
    }

    @Test
    public void testAdaptNull() throws Exception {
        assertNull(LeftBoundFunction.bind(null,"xyzzy"));
    }

    @Test
    public void testAdapt() throws Exception {
        assertNotNull(LeftBoundFunction.bind(RightIdentity.FUNCTION,"xyzzy"));
        assertNotNull(LeftBoundFunction.bind(RightIdentity.FUNCTION,null));
    }
}
