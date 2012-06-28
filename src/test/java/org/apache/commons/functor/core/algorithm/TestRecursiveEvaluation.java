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
import static org.junit.Assert.fail;

import java.io.Serializable;

import org.apache.commons.functor.BaseFunctorTest;
import org.apache.commons.functor.Function;
import org.junit.Test;

/**
 * Tests {@link RecursiveEvaluation} algorithm.
 */
public class TestRecursiveEvaluation extends BaseFunctorTest {

    @Override
    protected Object makeFunctor() throws Exception {
        return new RecursiveEvaluation(new RecFunc(0, false));
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
    
    @Test
    public void testConstructors() {
        try {
            new RecursiveEvaluation(new RecFunc(0, false), java.lang.Integer.class);
            fail("Not supposed to get here.");
        } catch(IllegalArgumentException e) {}
        try {
            new RecursiveEvaluation(null);
            fail("Not supposed to get here.");
        } catch(NullPointerException e) {}
    }

    // Classes
    // ------------------------------------------------------------------------
    
    /** Recursive function for test. */
    static class RecFunc implements Function<Object>, Serializable {
        private static final long serialVersionUID = 1L;

        int times = 0; 
        boolean returnFunc = false;

        public RecFunc(int times, boolean returnFunc) {
            this.times = times;
            this.returnFunc = returnFunc;
        }

        public Object evaluate() {
            if (times < 5) {
                return new RecFunc(++times, returnFunc);
            } else {
                if (returnFunc) {
                    return new InnerFunction(times);
                } else {
                    return new Integer(times);
                }
            }
        }
        
        @Override
        public boolean equals(Object obj) {
            if(this == obj) {
                return true;
            }
            if(obj == null || obj.getClass() != this.getClass()) {
                return false;
            }
            return this.times == ((RecFunc)obj).times && this.returnFunc == ((RecFunc)obj).returnFunc;
        }
        
        @Override
        public int hashCode() {
            return "RecFunc".hashCode() << 2 ^ times;
        }
    }
    
    /** Inner function called from recursive function */
    static class InnerFunction implements Function<Object>, Serializable {
        private static final long serialVersionUID = 1L;
        
        private int times;
        
        public InnerFunction(int times) {
            this.times = times;
        }
        
        public Object evaluate() {
            return new Integer(times);
        }
        
        @Override
        public boolean equals(Object obj) {
            if(this == obj) {
                return true;
            }
            if(obj == null || ! (obj instanceof InnerFunction)) {
                return false;
            }
            return this.times == ((InnerFunction)obj).times;
        }
        
        @Override
        public int hashCode() {
            return "InnerFunction".hashCode() << 2 ^ times;
        }
    };

}
