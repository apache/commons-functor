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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.functor.BaseFunctorTest;
import org.apache.commons.functor.BinaryFunction;
import org.apache.commons.functor.generator.loop.IteratorToGeneratorAdapter;
import org.junit.Test;

/**
 * Tests {@link FoldRight} algorithm.
 */
public class TestFoldRight extends BaseFunctorTest {

    @Override
    protected Object makeFunctor() throws Exception {
        return new FoldRight<Object>(new StringConcatenator());
    }
    
    @Test
    public void testFoldRight() {
        FoldRight<Object> foldRight = new FoldRight<Object>(new BinaryFunction<Object, Object, Object>() {
            public Object evaluate(Object left, Object right) {
                StringBuilder buf = left instanceof StringBuilder ? (StringBuilder) left : new StringBuilder().append(left);
                return buf.append(right);
            }
        });
        assertEquals("0123456789", foldRight.evaluate(IteratorToGeneratorAdapter.adapt(list.iterator())).toString());
        assertEquals("0123456789x", foldRight.evaluate(IteratorToGeneratorAdapter.adapt(list.iterator()), "x").toString());
        assertEquals("x", foldRight.evaluate(IteratorToGeneratorAdapter.adapt(new ArrayList<Object>().iterator()), "x").toString());
    }

    // Attributes
    // ------------------------------------------------------------------------
    private List<Object> list = Arrays.<Object>asList(0,1,2,3,4,5,6,7,8,9);

    // Classes
    // ------------------------------------------------------------------------
    
    static class StringConcatenator implements BinaryFunction<Object, Object, Object> {

        public Object evaluate(Object left, Object right) {
            StringBuilder buf = left instanceof StringBuilder ? (StringBuilder) left : new StringBuilder().append(left);
            return buf.append(right);
        }
        
        @Override
        public boolean equals(Object obj) {
            if(this == obj) {
                return true;
            }
            return obj != null && obj instanceof StringConcatenator;
        }
        
        @Override
        public int hashCode() {
            return "StringConcatenator".hashCode();
        }
    }
    
}
