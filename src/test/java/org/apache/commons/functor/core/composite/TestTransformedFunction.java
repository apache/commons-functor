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
import org.apache.commons.functor.UnaryFunction;
import org.junit.Test;

/**
 * Tests for TransformedFunction.
 * @version $Revision: $ $Date: $
 */
public class TestTransformedFunction extends BaseFunctorTest {

    private static class One implements Function<Integer>, Serializable {
        private static final long serialVersionUID = 8160546546365936087L;
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

    private static class AddOne implements UnaryFunction<Integer, Integer>, Serializable {
        private static final long serialVersionUID = 2005155787293129721L;
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
        return new TransformedFunction<Integer>(one, addOne);
    }

    @Test
    public void testRun() {
        TransformedFunction<Integer> p = new TransformedFunction<Integer>(one, addOne);
        assertEquals(Integer.valueOf(2),p.evaluate());
    }

    @Test
    public void testEquals() {
        TransformedFunction<Integer> t = new TransformedFunction<Integer>(one, addOne);
        Function<Integer> f = new Function<Integer>() {
            public Integer evaluate() {
                return new Integer(2);
            }
        };
        UnaryFunction<Integer, Integer> p = new UnaryFunction<Integer, Integer>() {
            public Integer evaluate(Integer obj) {
                return obj + 2;
            }
        };
        assertEquals(t,t);
        assertObjectsAreEqual(t,new TransformedFunction<Integer>(one, addOne));
        assertObjectsAreNotEqual(t,new TransformedFunction<Integer>(f, addOne));
        assertObjectsAreNotEqual(t,new TransformedFunction<Integer>(one, p));
        assertTrue(!t.equals(null));
    }

}
