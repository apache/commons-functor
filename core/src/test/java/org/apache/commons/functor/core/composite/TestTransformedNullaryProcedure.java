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
import org.apache.commons.functor.Procedure;
import org.junit.Test;

/**
 * Tests for TransformedNullaryProcedure.
 * 
 * @version $Revision: $ $Date: $
 */
public class TestTransformedNullaryProcedure extends BaseFunctorTest {

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

    private static class AggregatorProcedure implements Procedure<Integer> {
        private int total = 0;

        public void run(Integer obj) {
            total += obj;
        }

        public int getTotal() {
            return total;
        }

        @Override
        public boolean equals(Object obj) {
            return obj == this || obj != null && obj instanceof AggregatorProcedure;
        }

        @Override
        public int hashCode() {
            return "AggregatorProcedure".hashCode();
        }
    };

    private One one = new One();
    private AggregatorProcedure aggregator = new AggregatorProcedure();

    @Override
    protected Object makeFunctor() throws Exception {
        return new TransformedNullaryProcedure(one, aggregator);
    }

    @Test
    public void testRun() {
        TransformedNullaryProcedure p = new TransformedNullaryProcedure(one, aggregator);
        p.run();
        assertEquals(1, aggregator.getTotal());
    }

    @Test
    public void testEquals() {
        TransformedNullaryProcedure t = new TransformedNullaryProcedure(one, aggregator);
        NullaryFunction<Integer> f = new NullaryFunction<Integer>() {
            public Integer evaluate() {
                return Integer.valueOf(2);
            }
        };
        Procedure<Integer> p = new Procedure<Integer>() {
            public void run(Integer obj) {
                // Do nothing
            }
        };
        assertEquals(t, t);
        assertObjectsAreEqual(t, new TransformedNullaryProcedure(one, aggregator));
        assertObjectsAreNotEqual(t, new TransformedNullaryProcedure(f, aggregator));
        assertObjectsAreNotEqual(t, new TransformedNullaryProcedure(one, p));
        assertTrue(!t.equals(null));
    }
}
