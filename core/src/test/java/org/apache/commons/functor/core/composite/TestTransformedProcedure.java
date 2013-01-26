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

import java.io.Serializable;

import org.apache.commons.functor.BaseFunctorTest;
import org.apache.commons.functor.Function;
import org.apache.commons.functor.UnaryProcedure;
import org.junit.Test;

/**
 * Tests for TransformedProcedure.
 * @version $Revision: $ $Date: $
 */
public class TestTransformedProcedure extends BaseFunctorTest{

    private static class One implements Function<Integer>, Serializable {
        private static final long serialVersionUID = 7385852113529459456L;
        public Integer evaluate() {
            return new Integer(1);
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

    private static class AggregatorProcedure implements UnaryProcedure<Integer>, Serializable {
        private static final long serialVersionUID = -2744193737701268327L;
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
        return new TransformedProcedure(one, aggregator);
    }

    @Test
    public void testRun() {
        TransformedProcedure p = new TransformedProcedure(one, aggregator);
        p.run();
        assertEquals(1,aggregator.getTotal());
    }

    @Test
    public void testEquals() {
        TransformedProcedure t = new TransformedProcedure(one, aggregator);
        Function<Integer> f = new Function<Integer>() {
            public Integer evaluate() {
                return new Integer(2);
            }
        };
        UnaryProcedure<Integer> p = new UnaryProcedure<Integer>() {
            public void run(Integer obj) {
                // Do nothing
            }
        };
        assertEquals(t,t);
        assertObjectsAreEqual(t,new TransformedProcedure(one, aggregator));
        assertObjectsAreNotEqual(t,new TransformedProcedure(f, aggregator));
        assertObjectsAreNotEqual(t,new TransformedProcedure(one, p));
        assertTrue(!t.equals(null));
    }
}
