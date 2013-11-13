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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.functor.BaseFunctorTest;
import org.apache.commons.functor.Predicate;
import org.apache.commons.functor.adapter.LeftBoundPredicate;
import org.apache.commons.functor.core.IsEqual;
import org.apache.commons.functor.core.algorithm.GeneratorContains;
import org.apache.commons.functor.generator.loop.IteratorToGeneratorAdapter;
import org.junit.Test;

/**
 * Tests {@link GeneratorContains} algorithm.
 */
public class TestGeneratorContains extends BaseFunctorTest {

    @Override
    protected Object makeFunctor() throws Exception {
        return new GeneratorContains<Integer>();
    }

    @Test
    public void testContains() {
        assertTrue(new GeneratorContains<Integer>()
            .test(IteratorToGeneratorAdapter.adapt(list.iterator()), equalsThree));
        assertFalse(new GeneratorContains<Integer>().test(IteratorToGeneratorAdapter.adapt(list.iterator()),
            equalsTwentyThree));
    }

    // Attributes
    // ------------------------------------------------------------------------

    private List<Integer> list = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
    private Predicate<Integer> equalsThree = LeftBoundPredicate.bind(IsEqual.instance(), Integer.valueOf(3));
    private Predicate<Integer> equalsTwentyThree = LeftBoundPredicate.bind(IsEqual.instance(), Integer.valueOf(23));

}
