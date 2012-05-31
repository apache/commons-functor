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
import static org.junit.Assert.assertTrue;

import org.apache.commons.functor.Procedure;
import org.apache.commons.functor.core.Offset;
import org.apache.commons.functor.core.algorithm.UntilDo;
import org.junit.Test;

/**
 * Tests {@link UntilDo} algorithm.
 */
public class TestUntilDo {

    @Test
    public final void testObjectEquals() throws Exception {
        Counter counter = new Counter();
        Object obj = new UntilDo(new Offset(10), counter);
        assertEquals("equals must be reflexive",obj,obj);
        assertEquals("hashCode must be reflexive",obj.hashCode(),obj.hashCode());
        assertTrue(! obj.equals(null) ); // should be able to compare to null

        Object obj2 = new UntilDo(new Offset(10), counter);
        if (obj.equals(obj2)) {
            assertEquals("equals implies hash equals",obj.hashCode(),obj2.hashCode());
            assertEquals("equals must be symmetric",obj2,obj);
        } else {
            assertTrue("equals must be symmetric",! obj2.equals(obj));
        }
    }

    @Test
    public void testUntilDo() {
        for (int i=0;i<3;i++){
            Counter counter = new Counter();
            new UntilDo(new Offset(i), counter).run();
            assertEquals(i,counter.count);
        }
    }

    // Classes
    // ------------------------------------------------------------------------

    static class Counter implements Procedure {
        public void run() {
            count++;
        }
        public int count = 0;
    }

}
