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
package org.apache.commons.functor.core.composite;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.apache.commons.functor.BaseFunctorTest;
import org.apache.commons.functor.NullaryFunction;
import org.apache.commons.functor.Function;
import org.junit.Test;

/**
 * Tests for TransformedNullaryFunction.
 * 
 * @version $Revision: $ $Date: $
 */
public class TestTransformedNullaryFunction extends BaseFunctorTest {

    private static class One implements NullaryFunction<Integer> {
        public Integer evaluate() {
            return Integer.valueOf(1);
        }

        @Override
        public boolean equals(Object obj) {
            return obj == this || obj != null && obj instanceof One;
        }

        @Override
        public int hashCode() {
            return "One".hashCode();
        }
    };

    private static class AddOne implements Function<Integer, Integer> {
        public Integer evaluate(Integer obj) {
            return obj + 1;
        }

        @Override
        public boolean equals(Object obj) {
            return obj == this || obj != null && obj instanceof AddOne;
        }

        @Override
        public int hashCode() {
            return "AddOne".hashCode();
        }
    };

    private One one = new One();
    private AddOne addOne = new AddOne();

    @Override
    protected Object makeFunctor() throws Exception {
        return new TransformedNullaryFunction<Integer>(one, addOne);
    }

    @Test
    public void testRun() {
        TransformedNullaryFunction<Integer> p = new TransformedNullaryFunction<Integer>(one, addOne);
        assertEquals(Integer.valueOf(2), p.evaluate());
    }

    @Test
    public void testEquals() {
        TransformedNullaryFunction<Integer> t = new TransformedNullaryFunction<Integer>(one, addOne);
        NullaryFunction<Integer> f = new NullaryFunction<Integer>() {
            public Integer evaluate() {
                return Integer.valueOf(2);
            }
        };
        Function<Integer, Integer> p = new Function<Integer, Integer>() {
            public Integer evaluate(Integer obj) {
                return obj + 2;
            }
        };
        assertEquals(t, t);
        assertObjectsAreEqual(t, new TransformedNullaryFunction<Integer>(one, addOne));
        assertObjectsAreNotEqual(t, new TransformedNullaryFunction<Integer>(f, addOne));
        assertObjectsAreNotEqual(t, new TransformedNullaryFunction<Integer>(one, p));
        assertTrue(!t.equals(null));
    }

}
