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
package org.apache.commons.functor.generator.loop;

import org.apache.commons.functor.UnaryPredicate;
import org.apache.commons.functor.UnaryProcedure;
import org.apache.commons.functor.generator.Generator;
import org.apache.commons.lang3.Validate;

/**
 * Wrap another {@link Generator} such that {@link #run(UnaryProcedure)} continues
 * as long as a condition is true (test after).
 *
 * @param <E> the type of elements held in this generator.
 * @version $Revision$ $Date$
 */
public class GenerateWhile<E> extends PredicatedGenerator<E> {

    /**
     * Create a new GenerateWhile.
     * @param wrapped {@link Generator}
     * @param test {@link UnaryPredicate}
     */
    public GenerateWhile(Generator<? extends E> wrapped, UnaryPredicate<? super E> test) {
        super(Validate.notNull(wrapped, "Generator argument was null"),
        Validate.notNull(test, "UnaryPredicate argument was null"), Behavior.TEST_AFTER);
    }

}
