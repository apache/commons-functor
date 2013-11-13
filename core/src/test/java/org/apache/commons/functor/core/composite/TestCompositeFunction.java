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
import org.apache.commons.functor.Function;
import org.apache.commons.functor.core.Constant;
import org.apache.commons.functor.core.Identity;
import org.junit.Test;

/**
 * @version $Revision: 1365329 $ $Date: 2012-07-24 19:34:23 -0300 (Tue, 24 Jul 2012) $
 */
public class TestCompositeFunction extends BaseFunctorTest {

    // Functor Testing Framework
    // ------------------------------------------------------------------------

    @Override
    protected Object makeFunctor() {
        return Composite.function(Constant.of(3));
    }

    // Tests
    // ------------------------------------------------------------------------

    @Test
    public void testEvaluate() throws Exception {

        assertEquals(Integer.valueOf(4),(new CompositeFunction<Object, Integer>(Constant.of(4))).evaluate(null));

        assertEquals(Integer.valueOf(4),(Composite.function(Constant.of(4)).of(Constant.of(3)).evaluate("xyzzy")));
        assertEquals(Integer.valueOf(3),(new CompositeFunction<Object, Integer>(Constant.of(3)).of(Constant.of(4)).evaluate("xyzzy")));
    }

    @Test
    public void testOf() throws Exception {
        Function<Object, Integer> uf = new Function<Object, Integer>() {
            public Integer evaluate(Object obj) {
                if (obj instanceof Integer) {
                    return (((Integer) obj).intValue()) + 1;
                } else {
                    return 1;
                }
            }
        };
        CompositeFunction<Object, Integer> f = null;
        for (int i = 0; i < 10; i++) {
            f = f == null ? new CompositeFunction<Object, Integer>(uf) : f.of(uf);
            assertEquals(Integer.valueOf(i+1),f.evaluate(null));
        }
    }

    @Test
    public void testEquals() throws Exception {
        CompositeFunction<Object, String> f = new CompositeFunction<Object, String>(Constant.of("x"));
        assertEquals(f,f);

        CompositeFunction<Object, String> g = new CompositeFunction<Object, String>(Constant.of("x"));
        assertObjectsAreEqual(f,g);

        for (int i=0;i<3;i++) {
            f = f.of(Constant.of("y")).of(Constant.of("z"));
            System.out.println(f);
            System.out.println(f.hashCode());
            System.out.println(g);
            System.out.println(g.hashCode());
            assertObjectsAreNotEqual(f,g);
            g = g.of(Constant.of("y")).of(Constant.of("z"));
            assertObjectsAreEqual(f,g);
        }

        assertObjectsAreNotEqual(f, Constant.of("y"));
        assertObjectsAreNotEqual(Constant.of("y"), f);
        assertObjectsAreNotEqual(f, Identity.instance());
        assertTrue(!f.equals(null));
    }

}
