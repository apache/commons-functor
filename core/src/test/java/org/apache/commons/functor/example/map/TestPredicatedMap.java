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

import org.apache.commons.functor.core.IsInstance;


/**
 * @version $Revision$ $Date$
 */
public class TestPredicatedMap extends TestCase {

    public TestPredicatedMap(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(TestPredicatedMap.class);
    }

    private Map<Object, Object> baseMap = null;
    private Map<Object, Object> predicatedMap = null;
    @Override
    public void setUp() throws Exception {
        super.setUp();
        baseMap = new HashMap<Object, Object>();
        predicatedMap = new PredicatedMap<Object, Object>(baseMap,IsInstance.of(String.class),IsInstance.of(Integer.class));
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
        baseMap = null;
        predicatedMap = null;
    }

    // tests

    public void testCanPutMatchingPair() {
        predicatedMap.put("xyzzy", Integer.valueOf(17));
    }
    public void testCantPutInvalidValue() {
        try {
            predicatedMap.put("xyzzy", "xyzzy");
            fail("Expected IllegalArgumentException");
        } catch(IllegalArgumentException e) {
            // expected
        }
    }

    public void testCantPutInvalidKey() {
        try {
            predicatedMap.put(Long.valueOf(1), Integer.valueOf(3));
            fail("Expected IllegalArgumentException");
        } catch(IllegalArgumentException e) {
            // expected
        }
    }

    public void testOnlyValidPairsAreAddedInPutAll() {
        HashMap<Object, Object> map = new HashMap<Object, Object>();
        map.put("one", Integer.valueOf(17));
        map.put("two", "rejected");
        map.put(Integer.valueOf(17), "xyzzy");
        map.put(Integer.valueOf(7), Integer.valueOf(3));

        predicatedMap.putAll(map);
        assertEquals(Integer.valueOf(17), predicatedMap.get("one"));
        assertFalse(predicatedMap.containsKey("two"));
    }
}
