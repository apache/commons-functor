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

import java.util.Collections;

import org.apache.commons.functor.BaseFunctorTest;
import org.apache.commons.functor.Function;
import org.junit.Test;

/**
 * @version $Revision$ $Date$
 */
public class TestMax extends BaseFunctorTest {

    // Framework
    // ------------------------------------------------------------------------

    @Override
    protected Object makeFunctor() {
        return Max.instance();
    }

    private Integer MIN = Integer.valueOf(Integer.MIN_VALUE);
    private Integer MINUS_TWO = Integer.valueOf(-2);
    private Integer ZERO = Integer.valueOf(0);
    private Integer ONE = Integer.valueOf(1);
    private Integer MAX = Integer.valueOf(Integer.MAX_VALUE);
    // Tests
    // ------------------------------------------------------------------------

    @Test
    public void testEvaluate() {
        Max<Integer> f = Max.instance();
        assertEquals(ONE,f.evaluate(ONE,ONE));
        assertEquals(ONE,f.evaluate(ZERO,ONE));
        assertEquals(ONE,f.evaluate(ONE,ZERO));
        assertEquals(MAX,f.evaluate(ONE,MAX));
        assertEquals(MAX,f.evaluate(MIN,MAX));
        assertEquals(MINUS_TWO,f.evaluate(MIN,MINUS_TWO));
    }

    @Test
    public void testEquals() {
        Max<Comparable<?>> f = Max.instance();
        assertObjectsAreEqual(f,f);
        assertObjectsAreEqual(f,Max.instance());
        assertObjectsAreEqual(f,new Max<Integer>(ComparableComparator.<Integer>instance()));
        assertObjectsAreNotEqual(f,new Max<Comparable<?>>(Collections.<Comparable<?>>reverseOrder()));
        assertFalse(f.equals(null));
    }

    @Test
    public void testFunctionMin() {
        Function<Integer, Integer> max = Max.instance(ONE);
        assertEquals(ONE,max.evaluate(ZERO));
    }
}
