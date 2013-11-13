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
package org.apache.commons.functor.example.map;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.apache.commons.functor.core.collection.Size;

/**
 * @version $Revision$ $Date$
 */
public class TestLazyMap extends TestCase {

    public TestLazyMap(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(TestLazyMap.class);
    }

    private Map<Object, Integer> baseMap = null;
    private Map<Object, Integer> lazyMap = null;
    private Map<Object, Integer> expectedMap = null;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        expectedMap = new HashMap<Object, Integer>();
        expectedMap.put("one", Integer.valueOf(3));
        expectedMap.put("two", Integer.valueOf(3));
        expectedMap.put("three", Integer.valueOf(5));
        expectedMap.put("four", Integer.valueOf(4));
        expectedMap.put("five", Integer.valueOf(4));

        baseMap = new HashMap<Object, Integer>();
        lazyMap = new LazyMap<Object, Integer>(baseMap, Size.instance());
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
        baseMap = null;
        lazyMap = null;
        expectedMap = null;
    }

    // tests

    public void test() {
        for (Iterator<Object> iter = expectedMap.keySet().iterator(); iter.hasNext();) {
            Object key = iter.next();
            assertFalse(baseMap.containsKey(key));
            assertFalse(lazyMap.containsKey(key));
            assertEquals(expectedMap.get(key), lazyMap.get(key));
            assertEquals(expectedMap.get(key), baseMap.get(key));
            assertTrue(lazyMap.containsKey(key));
            assertTrue(baseMap.containsKey(key));
        }
        assertEquals(expectedMap, lazyMap);
        assertEquals(expectedMap, baseMap);
        baseMap.clear();
        for (Iterator<Object> iter = expectedMap.keySet().iterator(); iter.hasNext();) {
            Object key = iter.next();
            assertFalse(baseMap.containsKey(key));
            assertFalse(lazyMap.containsKey(key));
            assertEquals(expectedMap.get(key), lazyMap.get(key));
            assertEquals(expectedMap.get(key), baseMap.get(key));
            assertTrue(lazyMap.containsKey(key));
            assertTrue(baseMap.containsKey(key));
        }
        assertEquals(expectedMap, lazyMap);
        assertEquals(expectedMap, baseMap);
    }

    public void testBaseMapOverrides() {
        assertEquals(Integer.valueOf(5), lazyMap.get("xyzzy"));
        baseMap.put("xyzzy", Integer.valueOf(3));
        assertEquals(Integer.valueOf(3), lazyMap.get("xyzzy"));
    }

}
