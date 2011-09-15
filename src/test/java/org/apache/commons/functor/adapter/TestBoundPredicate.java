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
import org.apache.commons.functor.Predicate;
import org.apache.commons.functor.core.Constant;
import org.apache.commons.functor.core.Identity;
import org.junit.Test;

/**
 * @version $Revision$ $Date$
 * @author Rodney Waldhoff
 */
public class TestBoundPredicate extends BaseFunctorTest {

    // Functor Testing Framework
    // ------------------------------------------------------------------------

    protected Object makeFunctor() {
        return new BoundPredicate(Constant.TRUE,"xyzzy");
    }

    // Tests
    // ------------------------------------------------------------------------

    @Test
    public void testTest() throws Exception {
        {
            Predicate p = new BoundPredicate(new UnaryFunctionUnaryPredicate<Boolean>(Identity.<Boolean>instance()),Boolean.TRUE);
            assertEquals(true,p.test());
        }
        {
            Predicate p = new BoundPredicate(new UnaryFunctionUnaryPredicate<Boolean>(Identity.<Boolean>instance()),Boolean.FALSE);
            assertEquals(false,p.test());
        }
    }

    @Test
    public void testEquals() throws Exception {
        Predicate f = new BoundPredicate(Constant.TRUE,"xyzzy");
        assertEquals(f,f);
        assertObjectsAreEqual(f,new BoundPredicate(Constant.TRUE,"xyzzy"));
        assertObjectsAreNotEqual(f,Constant.TRUE);
        assertObjectsAreNotEqual(f,new BoundPredicate(Constant.TRUE,"foo"));
        assertObjectsAreNotEqual(f,new BoundPredicate(Constant.FALSE,"xyzzy"));
        assertObjectsAreNotEqual(f,new BoundPredicate(Constant.TRUE,null));
    }

    @Test
    public void testAdaptNull() throws Exception {
        assertNull(BoundPredicate.bind(null,"xyzzy"));
    }

    @Test
    public void testAdapt() throws Exception {
        assertNotNull(BoundPredicate.bind(Constant.TRUE,"xyzzy"));
        assertNotNull(BoundPredicate.bind(Constant.TRUE,null));
    }
}
