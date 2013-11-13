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
package org.apache.commons.functor.generator.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.functor.BaseFunctorTest;
import org.apache.commons.functor.core.Limit;
import org.apache.commons.functor.core.Offset;
import org.apache.commons.functor.generator.loop.GenerateUntil;
import org.apache.commons.functor.generator.loop.GenerateWhile;
import org.apache.commons.functor.generator.loop.UntilGenerate;
import org.apache.commons.functor.generator.loop.WhileGenerate;
import org.junit.Before;
import org.junit.Test;

/**
 */
public class TestEachElement extends BaseFunctorTest {

    private List<Integer> list = null;
    private Map<String, String> map = null;
    private Object[] array = null;

    @Override
    protected Object makeFunctor() throws Exception {
        return EachElement.from(new ArrayList<Object>());
    }

    // Lifecycle
    // ------------------------------------------------------------------------

    @Before
    public void setUp() throws Exception {
        list = new ArrayList<Integer>();
        list.add(Integer.valueOf(0));
        list.add(Integer.valueOf(1));
        list.add(Integer.valueOf(2));
        list.add(Integer.valueOf(3));
        list.add(Integer.valueOf(4));

        map = new HashMap<String, String>();
        map.put("1", "1-1");
        map.put("2", "2-1");
        map.put("3", "3-1");
        map.put("4", "4-1");
        map.put("5", "5-1");

        array = new String[5];
        array[0] = "1";
        array[1] = "2";
        array[2] = "3";
        array[3] = "4";
        array[4] = "5";
    }

    // Tests
    // ------------------------------------------------------------------------

    @Test
    public void testFromNull() {
        assertNull(EachElement.from((Collection<?>) null));
        assertNull(EachElement.from((Map<?, ?>) null));
        assertNull(EachElement.from((Iterator<?>) null));
        assertNull(EachElement.from((Object[]) null));
    }


    @Test
    public void testWithList() {
        Collection<?> col = EachElement.from(list).toCollection();
        assertEquals("[0, 1, 2, 3, 4]", col.toString());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testWithMap() {
        List<?> col = (List<?>) EachElement.from(map).toCollection();
        int i = 0;
        for (;i<col.size();i++) {
            Map.Entry<String, String> entry = (Map.Entry<String, String>) col.get(i);
            if (entry.getKey().equals("1")) {
                assertEquals("1-1", entry.getValue());
            } else if (entry.getKey().equals("2")) {
                assertEquals("2-1", entry.getValue());
            } else if (entry.getKey().equals("3")) {
                assertEquals("3-1", entry.getValue());
            } else if (entry.getKey().equals("4")) {
                assertEquals("4-1", entry.getValue());
            } else if (entry.getKey().equals("5")) {
                assertEquals("5-1", entry.getValue());
            }
        }

        assertEquals(5, i);
    }

    @Test
    public void testWithArray() {
        Collection<?> col = EachElement.from(array).toCollection();
        assertEquals("[1, 2, 3, 4, 5]", col.toString());
    }

    @Test
    public void testWithStop() {
        assertEquals("[0, 1, 2]", new UntilGenerate<Integer>(new Offset(3), EachElement.from(list)).toCollection().toString());
        assertEquals("[0, 1, 2, 3]", new GenerateUntil<Integer>(EachElement.from(list), new Offset(3)).toCollection().toString());
        assertEquals("[0, 1, 2]", new WhileGenerate<Integer>(new Limit(3), EachElement.from(list)).toCollection().toString());
        assertEquals("[0, 1, 2, 3]", new GenerateWhile<Integer>(EachElement.from(list), new Limit(3)).toCollection().toString());
    }

    @Test
    public void testWithIterator() {
        Collection<?> col = EachElement.from(list.iterator()).toCollection();
        assertEquals("[0, 1, 2, 3, 4]", col.toString());
    }

}