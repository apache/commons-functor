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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.functor.BaseFunctorTest;
import org.apache.commons.functor.BinaryFunction;
import org.apache.commons.functor.generator.loop.IteratorToGeneratorAdapter;
import org.junit.Test;

/**
 * Tests {@link FoldLeft} algorithm.
 */
public class TestFoldLeft extends BaseFunctorTest {

    @Override
    protected Object makeFunctor() throws Exception {
        return new FoldLeft<Integer>(new Sum());
    }
    
    @Test
    public void testFoldLeft() {
        FoldLeft<Integer> foldLeft = new FoldLeft<Integer>(new BinaryFunction<Integer, Integer, Integer>() {
            public Integer evaluate(Integer a, Integer b) {
                return new Integer(a + b);
            }
        });
        assertEquals(new Integer(sum), foldLeft.evaluate(IteratorToGeneratorAdapter.adapt(list.iterator())));
        assertEquals(new Integer(sum), foldLeft.evaluate(IteratorToGeneratorAdapter.adapt(list.iterator()), new Integer(0)));
        assertEquals(new Integer(0), foldLeft.evaluate(IteratorToGeneratorAdapter.adapt(new ArrayList<Integer>(0).iterator()), new Integer(0)), new Integer(0));
    }

    // Attributes
    // ------------------------------------------------------------------------
    private List<Integer> list = Arrays.asList(0,1,2);
    private int sum = 3;

    // Classes
    // ------------------------------------------------------------------------
    
    static class Sum implements BinaryFunction<Integer, Integer, Integer>, Serializable {
        private static final long serialVersionUID = 1L;
        
        public Integer evaluate(Integer a, Integer b) {
            return new Integer(a + b);
        }
        
        @Override
        public boolean equals(Object obj) {
            if(this == obj) {
                return true;
            }
            return obj != null && obj.getClass().equals(this.getClass());
        }
        
        @Override
        public int hashCode() {
            return "Sum".hashCode();
        }
    }
    
}
