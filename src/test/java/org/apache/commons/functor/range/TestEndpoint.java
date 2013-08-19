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

package org.apache.commons.functor.range;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Tests for endpoint.
 *
 * @since 1.0
 * @version $Revision: $ $Date: $
 */
public class TestEndpoint {

    private final Endpoint<Integer> openEndpoint = new Endpoint<Integer>(1, BoundType.OPEN);
    private final Endpoint<Integer> closedEndpoint = new Endpoint<Integer>(2, BoundType.CLOSED);

    @Test
    public void testValue() {
        assertEquals(Integer.valueOf(1), openEndpoint.getValue());
        assertEquals(Integer.valueOf(2), closedEndpoint.getValue());
    }

    @Test
    public void testBoundType() {
        assertEquals(BoundType.OPEN, openEndpoint.getBoundType());
        assertEquals(BoundType.CLOSED, closedEndpoint.getBoundType());
    }

    @Test
    public void testToString() {
        assertEquals("Endpoint<1, OPEN>", openEndpoint.toString());
        assertEquals("Endpoint<2, CLOSED>", closedEndpoint.toString());
        assertEquals("(1", openEndpoint.toLeftString());
        assertEquals("[2", closedEndpoint.toLeftString());
        assertEquals("1)", openEndpoint.toRightString());
        assertEquals("2]", closedEndpoint.toRightString());
    }

    @Test
    public void testEquals()
        throws Exception {
        // equals basic properties
        Endpoint<Integer> endpoint = new Endpoint<Integer>(1, BoundType.OPEN);
        assertEquals("equals must be reflexive", endpoint, endpoint);
        assertEquals("hashCode must be reflexive", endpoint.hashCode(),
                     endpoint.hashCode());
        assertTrue(!endpoint.equals(null)); // should be able to compare to null

        Object endpoint2 = new Endpoint<Integer>(1, BoundType.OPEN);
        if (endpoint.equals(endpoint2)) {
            assertEquals("equals implies hash equals", endpoint.hashCode(),
                         endpoint2.hashCode());
            assertEquals("equals must be symmetric", endpoint2, endpoint);
        } else {
            assertTrue("equals must be symmetric", !endpoint2.equals(endpoint));
        }

        Object differentEndpoint = new Endpoint<Integer>(1, BoundType.CLOSED);
        assertTrue(!differentEndpoint.equals(endpoint));
        assertTrue(differentEndpoint.hashCode() != endpoint.hashCode());
    }

}
