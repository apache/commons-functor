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

import org.junit.Test;

/**
 * @version $Revision$ $Date$
 */
public class TestComparableComparator {

    // Tests
    // ------------------------------------------------------------------------

    @Test
    public void testCompareIntegers() {
        assertTrue(ComparableComparator.<Integer>instance().compare(new Integer(Integer.MIN_VALUE),new Integer(Integer.MIN_VALUE)) == 0);
        assertTrue(ComparableComparator.<Integer>instance().compare(new Integer(-1),new Integer(-1)) == 0);
        assertTrue(ComparableComparator.<Integer>instance().compare(new Integer(0),new Integer(0)) == 0);
        assertTrue(ComparableComparator.<Integer>instance().compare(new Integer(Integer.MAX_VALUE),new Integer(Integer.MAX_VALUE)) == 0);
        assertTrue(ComparableComparator.<Integer>instance().compare(new Integer(1),new Integer(1)) == 0);
    }

    @Test(expected=NullPointerException.class)
    public void testCompareNull() {
        ComparableComparator.<Integer>instance().compare(null,new Integer(2));
    }

    @Test
    public void testEqualsAndHashCode() {
        assertEquals(new ComparableComparator<Integer>(),new ComparableComparator<Integer>());
        assertEquals(new ComparableComparator<Integer>().hashCode(),new ComparableComparator<Integer>().hashCode());
        assertTrue(!new ComparableComparator<Integer>().equals(null));
    }
}
