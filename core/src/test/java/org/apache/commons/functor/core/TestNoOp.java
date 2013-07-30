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
package org.apache.commons.functor.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import org.apache.commons.functor.BaseFunctorTest;
import org.apache.commons.functor.BinaryProcedure;
import org.apache.commons.functor.NullaryProcedure;
import org.apache.commons.functor.Procedure;
import org.junit.Test;

/**
 * @version $Revision$ $Date$
 */
public class TestNoOp extends BaseFunctorTest {

    // Functor Testing Framework
    // ------------------------------------------------------------------------

    @Override
    protected Object makeFunctor() {
        return new NoOp();
    }

    // Tests
    // ------------------------------------------------------------------------

    @Test
    public void testRun() throws Exception {
        NoOp p = new NoOp();
        p.run();
        p.run(null);
        p.run(null,null);
        p.run("foo");
        p.run("foo",null);
        p.run(null,"bar");
        p.run("foo","bar");
    }

    @Test
    public void testEquals() throws Exception {
        NoOp p = new NoOp();
        assertEquals(p,p);
        assertObjectsAreEqual(p,new NoOp());
        assertObjectsAreEqual(p,NoOp.instance());
        assertObjectsAreNotEqual(p,new NullaryProcedure() { public void run() { } });
        assertObjectsAreNotEqual(p,new Procedure<Object>() { public void run(Object a) { } });
        assertObjectsAreNotEqual(p,new BinaryProcedure<Object, Object>() { public void run(Object a, Object b) { } });
    }

    @Test
    public void testConstant() throws Exception {
        assertEquals(NoOp.instance(),NoOp.instance());
        assertSame(NoOp.instance(),NoOp.instance());
        assertEquals(NoOp.unaryInstance(),NoOp.unaryInstance());
        assertSame(NoOp.unaryInstance(),NoOp.unaryInstance());
        assertEquals(NoOp.binaryInstance(),NoOp.binaryInstance());
        assertSame(NoOp.binaryInstance(),NoOp.binaryInstance());
    }
}
