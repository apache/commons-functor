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
import static org.junit.Assert.assertTrue;

import org.apache.commons.functor.BaseFunctorTest;
import org.junit.Test;

/**
 * @version $Revision$ $Date$
 */
public class TestIsWithinRange extends BaseFunctorTest {

    // Functor Testing Framework
    // ------------------------------------------------------------------------

    @Override
    protected Object makeFunctor() {
        return new IsWithinRange<Integer>(Integer.valueOf(5), Integer.valueOf(10));
    }

    // Tests
    // ------------------------------------------------------------------------

    @Test
    public void testTest() throws Exception {
        IsWithinRange<Integer> p = new IsWithinRange<Integer>(Integer.valueOf(5), Integer.valueOf(10));
        assertTrue(p.test(Integer.valueOf(5)));
        assertTrue(p.test(Integer.valueOf(6)));
        assertTrue(p.test(Integer.valueOf(7)));
        assertTrue(p.test(Integer.valueOf(8)));
        assertTrue(p.test(Integer.valueOf(9)));
        assertTrue(p.test(Integer.valueOf(10)));

        assertTrue(!p.test(Integer.valueOf(4)));
        assertTrue(!p.test(Integer.valueOf(11)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidRange() {
        new IsWithinRange<Integer>(Integer.valueOf(5), Integer.valueOf(4));
    }

    @Test(expected = NullPointerException.class)
    public void testInvalidRange2() {
        new IsWithinRange<Integer>(Integer.valueOf(5), null);
    }

    @Test
    public void testEquals() throws Exception {
        IsWithinRange<Integer> p1 = new IsWithinRange<Integer>(Integer.valueOf(5), Integer.valueOf(10));
        IsWithinRange<Integer> p2 = new IsWithinRange<Integer>(Integer.valueOf(5), Integer.valueOf(10));
        assertEquals(p1, p2);
        p2 = new IsWithinRange<Integer>(Integer.valueOf(5), Integer.valueOf(11));
        assertTrue(!p1.equals(p2));
        p2 = new IsWithinRange<Integer>(Integer.valueOf(6), Integer.valueOf(10));
        assertTrue(!p1.equals(p2));
    }

    @Test
    public void testFactory() throws Exception {
        IsWithinRange<Integer> p = IsWithinRange.instance(Integer.valueOf(5), Integer.valueOf(10));
        assertTrue(p.test(Integer.valueOf(5)));
        assertTrue(p.test(Integer.valueOf(6)));
        assertTrue(p.test(Integer.valueOf(7)));
        assertTrue(p.test(Integer.valueOf(8)));
        assertTrue(p.test(Integer.valueOf(9)));
        assertTrue(p.test(Integer.valueOf(10)));

        assertTrue(!p.test(Integer.valueOf(4)));
        assertTrue(!p.test(Integer.valueOf(11)));
    }
}
