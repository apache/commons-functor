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
import org.apache.commons.functor.BinaryProcedure;
import org.apache.commons.functor.core.NoOp;
import org.junit.Test;

/**
 * @version $Revision$ $Date$
 */
public class TestTransposedProcedure extends BaseFunctorTest {

    // Functor Testing Framework
    // ------------------------------------------------------------------------

    @Override
    protected Object makeFunctor() {
        return new TransposedProcedure<Object, Object>(NoOp.INSTANCE);
    }

    // Tests
    // ------------------------------------------------------------------------

    @Test
    public void testEvaluate() throws Exception {
        LeftNotNullCounter counter = new LeftNotNullCounter();
        BinaryProcedure<Object, Object> p = new TransposedProcedure<Object, Object>(counter);
        assertEquals(0,counter.count);
        p.run(null,"not null");
        assertEquals(1,counter.count);
        p.run("not null",null);
        assertEquals(1,counter.count);
    }

    @Test
    public void testEquals() throws Exception {
        BinaryProcedure<Object, Object> p = new TransposedProcedure<Object, Object>(NoOp.INSTANCE);
        assertEquals(p,p);
        assertObjectsAreEqual(p,new TransposedProcedure<Object, Object>(NoOp.INSTANCE));
        assertObjectsAreEqual(p,TransposedProcedure.transpose(NoOp.INSTANCE));
        assertObjectsAreNotEqual(p,new TransposedProcedure<Object, Object>(new TransposedProcedure<Object, Object>(NoOp.INSTANCE)));
        assertObjectsAreNotEqual(p,NoOp.INSTANCE);
        assertTrue(!p.equals((TransposedProcedure<?, ?>)null));
    }

    @Test
    public void testTransposeNull() throws Exception {
        assertNull(TransposedProcedure.transpose(null));
    }

    @Test
    public void testTranspose() throws Exception {
        assertNotNull(TransposedProcedure.transpose(NoOp.INSTANCE));
    }

    // Classes
    // ------------------------------------------------------------------------

    static class LeftNotNullCounter implements BinaryProcedure<Object, Object> {
        public void run(Object a, Object b) {
            if (null != a) {
                count++;
            }
        }
        public int count = 0;
    }

}
