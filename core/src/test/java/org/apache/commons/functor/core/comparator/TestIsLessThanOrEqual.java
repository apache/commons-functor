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
package org.apache.commons.functor.core.comparator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.apache.commons.functor.core.Constant;
import org.junit.Test;

/**
 */
public class TestIsLessThanOrEqual extends BaseComparisonPredicateTest {

    // Functor Testing Framework
    // ------------------------------------------------------------------------

    @Override
    protected Object makeFunctor() {
        return new IsLessThanOrEqual<Comparable<?>>();
    }

    // Tests
    // ------------------------------------------------------------------------

    @Test
    public void testTest() throws Exception {
        IsLessThanOrEqual<Integer> p = new IsLessThanOrEqual<Integer>();
        assertTrue(p.test(Integer.valueOf(2),Integer.valueOf(4)));
        assertTrue(p.test(Integer.valueOf(3),Integer.valueOf(4)));
        assertTrue(p.test(Integer.valueOf(4),Integer.valueOf(4)));
        assertFalse(p.test(Integer.valueOf(5),Integer.valueOf(4)));
        assertFalse(p.test(Integer.valueOf(6),Integer.valueOf(4)));
    }

    @Test
    public void testInstance() {
        assertFalse(IsLessThanOrEqual.instance(Integer.valueOf(7)).test(Integer.valueOf(8)));
        assertTrue(IsLessThanOrEqual.instance(Integer.valueOf(7)).test(Integer.valueOf(6)));
    }

    @Test
    public void testEquals() throws Exception {
        IsLessThanOrEqual<Comparable<?>> p = new IsLessThanOrEqual<Comparable<?>>();
        assertEquals(p,p);

        assertObjectsAreEqual(p,new IsLessThanOrEqual<Comparable<?>>());
        assertObjectsAreEqual(p,new IsLessThanOrEqual<Integer>(ComparableComparator.<Integer>instance()));
        assertObjectsAreEqual(p,IsLessThanOrEqual.instance());
        assertObjectsAreNotEqual(p,Constant.FALSE);
        assertFalse(p.equals(null));
    }

}
