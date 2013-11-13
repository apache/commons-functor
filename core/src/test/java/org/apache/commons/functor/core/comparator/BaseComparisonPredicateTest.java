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

import static org.junit.Assert.fail;

import org.apache.commons.functor.BaseFunctorTest;
import org.apache.commons.functor.BinaryPredicate;
import org.junit.Test;

/**
 * @version $Revision$ $Date$
 */
public abstract class BaseComparisonPredicateTest extends BaseFunctorTest {

    // Tests
    // ------------------------------------------------------------------------

    @Test
    public final void testTestNull() throws Exception {
        @SuppressWarnings("unchecked")
        BinaryPredicate<Object, Object> p = (BinaryPredicate<Object, Object>) (makeFunctor());
        try {
            p.test(Integer.valueOf(2), null);
            fail("Expected NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
        try {
            p.test(null, Integer.valueOf(2));
            fail("Expected NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
        try {
            p.test(null, null);
            fail("Expected NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
    }

    @Test
    public final void testTestNonComparable() throws Exception {
        @SuppressWarnings("unchecked")
        BinaryPredicate<Object, Object> p = (BinaryPredicate<Object, Object>) (makeFunctor());
        try {
            p.test(Integer.valueOf(2), new Object());
            fail("Expected ClassCastException");
        } catch (ClassCastException e) {
            // expected
        }
        try {
            p.test(new Object(), Integer.valueOf(2));
            fail("Expected ClassCastException");
        } catch (ClassCastException e) {
            // expected
        }
        try {
            p.test(new Object(), new Object());
            fail("Expected ClassCastException");
        } catch (ClassCastException e) {
            // expected
        }
    }

}
