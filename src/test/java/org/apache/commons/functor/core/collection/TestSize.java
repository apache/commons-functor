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
package org.apache.commons.functor.core.collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.functor.BaseFunctorTest;
import org.apache.commons.functor.UnaryFunction;
import org.apache.commons.functor.core.Constant;
import org.junit.Test;

/**
 * @version $Revision$ $Date$
 * @author Rodney Waldhoff
 */
@SuppressWarnings("unchecked")
public class TestSize extends BaseFunctorTest {

    // Functor Testing Framework
    // ------------------------------------------------------------------------

    protected Object makeFunctor() {
        return new Size();
    }

    // Tests
    // ------------------------------------------------------------------------

    @Test
    public void testEvaluate() throws Exception {
        assertEquals(new Integer(0),Size.instance().evaluate(Collections.EMPTY_LIST));
        assertEquals(new Integer(0),Size.instance().evaluate(Collections.EMPTY_SET));
        {
            List list = new ArrayList();
            assertEquals(new Integer(0),Size.instance().evaluate(list));
            for (int i=0;i<2;i++) {
                assertEquals(new Integer(i),Size.instance().evaluate(list));
                list.add(new Integer(i));
                assertEquals(new Integer(i+1),Size.instance().evaluate(list));
            }
        }
        {
            Set set = new HashSet();
            assertEquals(new Integer(0),Size.instance().evaluate(set));
            for (int i=0;i<2;i++) {
                assertEquals(new Integer(i),Size.instance().evaluate(set));
                set.add(new Integer(i));
                assertEquals(new Integer(i+1),Size.instance().evaluate(set));
            }
        }
    }

    @Test
    public void testEvaluateNull() throws Exception {
        try {
            Size.instance().evaluate(null);
            fail("Expected IllegalArgumentException");
        } catch(IllegalArgumentException e) {
            // expected
        }
    }

    @Test
    public void testEvaluateNonCollection() throws Exception {
        try {
            Size.instance().evaluate(new Integer(3));
            fail("Expected IllegalArgumentException");
        } catch(IllegalArgumentException e) {
            // expected
        }
    }

    @Test
    public void testEvaluateArray() throws Exception {
        assertEquals(new Integer(10),Size.instance().evaluate(new int[10]));
        assertEquals(new Integer(7),Size.instance().evaluate(new String[7]));
    }

    @Test
    public void testEvaluateString() throws Exception {
        assertEquals(new Integer("xyzzy".length()),Size.instance().evaluate("xyzzy"));
    }

    @Test
    public void testEquals() throws Exception {
        UnaryFunction f = new Size();
        assertEquals(f,f);
        assertObjectsAreEqual(f,new Size());
        assertObjectsAreEqual(f,Size.instance());
        assertSame(Size.instance(),Size.instance());
        assertObjectsAreNotEqual(f,new Constant(null));
        assertTrue(! f.equals((Size) null) );
    }

}
