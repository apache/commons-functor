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
 * @version $Revision$ $Date$
 */
public class TestIsEquivalent extends BaseComparisonPredicateTest {

    // Functor Testing Framework
    // ------------------------------------------------------------------------

    @Override
    protected Object makeFunctor() {
        return IsEquivalent.INSTANCE;
    }

    // Tests
    // ------------------------------------------------------------------------

    @Test
    public void testTest() throws Exception {
        IsEquivalent<Integer> p = IsEquivalent.<Integer> instance();
        assertTrue(!p.test(Integer.valueOf(2), Integer.valueOf(4)));
        assertTrue(!p.test(Integer.valueOf(3), Integer.valueOf(4)));
        assertTrue(p.test(Integer.valueOf(4), Integer.valueOf(4)));
        assertTrue(!p.test(Integer.valueOf(5), Integer.valueOf(4)));
        assertTrue(!p.test(Integer.valueOf(6), Integer.valueOf(4)));
    }

    @Test
    public void testInstance() {
        assertTrue(IsEquivalent.instance("Xyzzy").test("Xyzzy"));
        assertTrue(!IsEquivalent.instance("Xyzzy").test("z"));
    }

    @Test
    public void testEquals() throws Exception {
        IsEquivalent<Comparable<Integer>> p = IsEquivalent.instance();
        assertEquals(p, p);

        assertObjectsAreEqual(p, new IsEquivalent<Comparable<?>>());
        assertObjectsAreEqual(p, new IsEquivalent<Integer>(ComparableComparator.<Integer> instance()));
        assertObjectsAreNotEqual(p, Constant.FALSE);
        assertFalse(p.equals(null));
    }

}
