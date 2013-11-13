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
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * @version $Revision$ $Date$
 */
public class TestFixedSizeMap extends TestCase {

    public TestFixedSizeMap(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(TestFixedSizeMap.class);
    }

    private Map<Object, Object> baseMap = null;
    private Map<Object, Object> fixedMap = null;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        baseMap = new HashMap<Object, Object>();
        baseMap.put(Integer.valueOf(1), "one");
        baseMap.put(Integer.valueOf(2), "two");
        baseMap.put(Integer.valueOf(3), "three");
        baseMap.put(Integer.valueOf(4), "four");
        baseMap.put(Integer.valueOf(5), "five");

        fixedMap = new FixedSizeMap<Object, Object>(baseMap);
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
        baseMap = null;
        fixedMap = null;
    }

    // tests

    public void testCantPutNewPair() {
        try {
            fixedMap.put("xyzzy", "xyzzy");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    public void testCantPutNewPairViaPutAll() {
        Map<Object, Object> map = new HashMap<Object, Object>();
        map.put(Integer.valueOf(1), "uno");
        map.put("xyzzy", "xyzzy");
        map.put(Integer.valueOf(2), "dos");

        try {
            fixedMap.putAll(map);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }

        assertEquals("one", fixedMap.get(Integer.valueOf(1)));
        assertEquals("two", fixedMap.get(Integer.valueOf(2)));
    }

    public void testCantClear() {
        try {
            fixedMap.clear();
            fail("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            // expected
        }
    }

    public void testCantRemove() {
        try {
            fixedMap.remove(Integer.valueOf(1));
            fail("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            // expected
        }
    }

    public void testCanAssociateNewValueWithOldKey() {
        fixedMap.put(Integer.valueOf(1), "uno");
        assertEquals("uno", fixedMap.get(Integer.valueOf(1)));
        assertEquals("two", fixedMap.get(Integer.valueOf(2)));
        assertEquals("three", fixedMap.get(Integer.valueOf(3)));
    }

    public void testCanAssociateNewValueWithOldKeyViaPutAll() {
        Map<Object, Object> map = new HashMap<Object, Object>();
        map.put(Integer.valueOf(1), "uno");
        map.put(Integer.valueOf(2), "dos");

        fixedMap.putAll(map);

        assertEquals("uno", fixedMap.get(Integer.valueOf(1)));
        assertEquals("dos", fixedMap.get(Integer.valueOf(2)));
        assertEquals("three", fixedMap.get(Integer.valueOf(3)));
    }

}
