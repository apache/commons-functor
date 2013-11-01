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
import org.apache.commons.functor.BinaryFunction;
import org.apache.commons.functor.Function;
import org.junit.Test;


/**
 * Tests for TransformedBinaryFunction.
 * @version $Revision: $ $Date: $
 */
public class TestTransformedBinaryFunction extends BaseFunctorTest {

    private static class Sum implements BinaryFunction<Integer, Integer, Integer> {
        public Integer evaluate(Integer left, Integer right) {
            return left+right;
        }
        @Override
        public boolean equals(Object obj) {
            return obj == this || obj != null && obj instanceof Sum;
        }
        @Override
        public int hashCode() {
            return "Sum".hashCode();
        }
    }

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
    }

    @Override
    protected Object makeFunctor() throws Exception {
        return new TransformedBinaryFunction<Integer, Integer, Integer>(new Sum(), new AddOne());
    }

    @Test
    public void testRun() {
        TransformedBinaryFunction<Integer, Integer, Integer> transform = new TransformedBinaryFunction<Integer, Integer, Integer>(new Sum(), new AddOne());
        assertEquals(Integer.valueOf(4), transform.evaluate(1, 2));
    }

    @Test
    public void testEquals() {
        TransformedBinaryFunction<Integer, Integer, Integer> t = new TransformedBinaryFunction<Integer, Integer, Integer>(new Sum(), new AddOne());
        BinaryFunction<Integer, Integer, Integer> f = new BinaryFunction<Integer, Integer, Integer>() {
            public Integer evaluate(Integer left, Integer right) {
                return left-right;
            }
        };
        Function<Integer, Integer> p = new Function<Integer, Integer>() {
            public Integer evaluate(Integer obj) {
                return obj-1;
            }
        };
        assertEquals(t,t);
        assertObjectsAreEqual(t,new TransformedBinaryFunction<Integer, Integer, Integer>(new Sum(), new AddOne()));
        assertObjectsAreNotEqual(t,new TransformedBinaryFunction<Integer, Integer, Integer>(f, new AddOne()));
        assertObjectsAreNotEqual(t,new TransformedBinaryFunction<Integer, Integer, Integer>(new Sum(), p));
        assertTrue(!t.equals(null));
    }

}
