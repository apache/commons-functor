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
package org.apache.commons.functor.core.algorithm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.apache.commons.functor.Function;
import org.apache.commons.functor.core.algorithm.RecursiveEvaluation;
import org.junit.Test;

/**
 * Tests {@link RecursiveEvaluation} algorithm.
 */
public class TestRecursiveEvaluation {

    @Test
    public final void testObjectEquals() throws Exception {
        Object obj = new RecursiveEvaluation(new RecFunc(0, false));
        assertEquals("equals must be reflexive",obj,obj);
        assertEquals("hashCode must be reflexive",obj.hashCode(),obj.hashCode());
        assertTrue(! obj.equals(null) ); // should be able to compare to null

        Object obj2 = new RecursiveEvaluation(new RecFunc(0, false));
        if (obj.equals(obj2)) {
            assertEquals("equals implies hash equals",obj.hashCode(),obj2.hashCode());
            assertEquals("equals must be symmetric",obj2,obj);
        } else {
            assertTrue("equals must be symmetric",! obj2.equals(obj));
        }
    }

    @Test
    public void testRecurse() {
        assertEquals(new Integer(5), new RecursiveEvaluation(new RecFunc(0, false)).evaluate());

        // this version will return a function. since it is not the same type
        // as RecFunc recursion will end.
        @SuppressWarnings({ "unchecked", "rawtypes" })
        Function<Object> func = (Function) new RecursiveEvaluation(new RecFunc(0, true)).evaluate();
        assertEquals(new Integer(5), func.evaluate());
    }

    /** Recursive function for test. */
    class RecFunc implements Function<Object> {
        int times = 0; boolean returnFunc = false;

        public RecFunc(int times, boolean returnFunc) {
            this.times = times;
            this.returnFunc = returnFunc;
        }

        public Object evaluate() {
            if (times < 5) {
                return new RecFunc(++times, returnFunc);
            } else {
                if (returnFunc) {
                    return new Function<Object>() {
                        public Object evaluate() {
                            return new Integer(times);
                        }
                    };
                } else {
                    return new Integer(times);
                }
            }
        }
    }

}
