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

import org.apache.commons.functor.NullaryPredicate;
import org.apache.commons.functor.NullaryProcedure;

/**
 * Base class for predicated procedure algorithms.
 */
abstract class PredicatedLoop implements NullaryProcedure {
    /**
     * The procedure body to execute.
     */
    private final NullaryProcedure body;
    /**
     * The test wether to keep going.
     */
    private final NullaryPredicate test;

    /**
     * Create a new PredicatedLoop.
     * @param body to execute
     * @param test whether to keep going
     */
    protected PredicatedLoop(NullaryProcedure body, NullaryPredicate test) {
        this.body = body;
        this.test = test;
    }

    /**
     * Get the body of this loop.
     * @return NullaryProcedure
     */
    protected NullaryProcedure getBody() {
        return body;
    }

    /**
     * Get the test for this loop.
     * @return NullaryPredicate
     */
    protected NullaryPredicate getTest() {
        return test;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || !obj.getClass().equals(getClass())) {
            return false;
        }
        PredicatedLoop other = (PredicatedLoop) obj;
        return other.body.equals(body) && other.test.equals(test);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        String classname = getClass().getName();
        int dot = classname.lastIndexOf('.');
        int result = classname.substring(dot + 1).hashCode();
        result <<= 2;
        result ^= body.hashCode();
        result <<= 2;
        result ^= test.hashCode();
        return result;
    }
}
