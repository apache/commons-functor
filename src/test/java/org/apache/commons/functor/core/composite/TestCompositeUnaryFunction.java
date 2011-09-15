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

import org.apache.commons.functor.BaseFunctorTest;
import org.apache.commons.functor.UnaryFunction;
import org.apache.commons.functor.core.Constant;
import org.junit.Test;

/**
 * @version $Revision$ $Date$
 * @author Rodney Waldhoff
 */
public class TestCompositeUnaryFunction extends BaseFunctorTest {

    // Functor Testing Framework
    // ------------------------------------------------------------------------

    protected Object makeFunctor() {
        return Composite.function(Constant.of(3));
    }

    // Tests
    // ------------------------------------------------------------------------

    @Test
    public void testEvaluate() throws Exception {

        assertEquals(new Integer(4),(new CompositeUnaryFunction<Object, Integer>(Constant.of(4))).evaluate(null));

        assertEquals(new Integer(4),(Composite.function(Constant.of(4)).of(Constant.of(3)).evaluate("xyzzy")));
        assertEquals(new Integer(3),(new CompositeUnaryFunction<Object, Integer>(Constant.of(3)).of(Constant.of(4)).evaluate("xyzzy")));
    }

    @Test
    public void testOf() throws Exception {
        UnaryFunction<Object, Integer> uf = new UnaryFunction<Object, Integer>() {
            public Integer evaluate(Object obj) {
                if (obj instanceof Integer) {
                    return (((Integer) obj).intValue()) + 1;
                } else {
                    return 1;
                }
            }
        };
        CompositeUnaryFunction<Object, Integer> f = null;
        for (int i = 0; i < 10; i++) {
            f = f == null ? new CompositeUnaryFunction<Object, Integer>(uf) : f.of(uf);
            assertEquals(Integer.valueOf(i+1),f.evaluate(null));
        }
    }

    @Test
    public void testEquals() throws Exception {
        CompositeUnaryFunction<Object, String> f = new CompositeUnaryFunction<Object, String>(Constant.of("x"));
        assertEquals(f,f);
        
        CompositeUnaryFunction<Object, String> g = new CompositeUnaryFunction<Object, String>(Constant.of("x"));
        assertObjectsAreEqual(f,g);

        for (int i=0;i<3;i++) {
            f = f.of(Constant.of("y")).of(Constant.of("z"));
            assertObjectsAreNotEqual(f,g);
            g = g.of(Constant.of("y")).of(Constant.of("z"));
            assertObjectsAreEqual(f,g);
        }

        assertObjectsAreNotEqual(f, Constant.of("y"));
    }

}
