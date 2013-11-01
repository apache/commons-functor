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
import org.apache.commons.functor.Procedure;
import org.junit.Test;


/**
 * Tests for TransformedBinaryProcedure.
 * @version $Revision: $ $Date: $
 */
public class TestTransformedBinaryProcedure extends BaseFunctorTest {

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

    private static class Total implements Procedure<Integer> {
        private int total = 0;
        public void run(Integer obj) {
            total += obj;
        }
        public Integer getTotal() {
            return total;
        }
        @Override
        public boolean equals(Object obj) {
            return obj == this || obj != null && obj instanceof Total;
        }
        @Override
        public int hashCode() {
            return "Total".hashCode();
        }
    }

    @Override
    protected Object makeFunctor() throws Exception {
        return new TransformedBinaryProcedure<Integer, Integer>(new Sum(), new Total());
    }

    @Test
    public void testRun() {
        Total t = new Total();
        TransformedBinaryProcedure<Integer, Integer> transform = new TransformedBinaryProcedure<Integer, Integer>(new Sum(), t);
        transform.run(1, 2);
        assertEquals(Integer.valueOf(3), t.getTotal());
    }

    @Test
    public void testEquals() {
        TransformedBinaryProcedure<Integer, Integer> t = new TransformedBinaryProcedure<Integer, Integer>(new Sum(), new Total());
        BinaryFunction<Integer, Integer, Integer> f = new BinaryFunction<Integer, Integer, Integer>() {
            public Integer evaluate(Integer left, Integer right) {
                return left-right;
            }
        };
        Procedure<Integer> p = new Procedure<Integer>() {
            public void run(Integer obj) {
                // Do nothing
            }
        };
        assertEquals(t,t);
        assertObjectsAreEqual(t,new TransformedBinaryProcedure<Integer, Integer>(new Sum(), new Total()));
        assertObjectsAreNotEqual(t,new TransformedBinaryProcedure<Integer, Integer>(f, new Total()));
        assertObjectsAreNotEqual(t,new TransformedBinaryProcedure<Integer, Integer>(new Sum(), p));
        assertTrue(!t.equals(null));
    }

}
