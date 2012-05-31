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
package org.apache.commons.functor.aggregator.functions;

import org.apache.commons.functor.BinaryFunction;

/**
 * Aggregator function to be used with subclasses of
 * {@link org.apache.commons.functor.aggregator.AbstractNoStoreAggregator} which
 * simply increments the first argument by 1 and returns it. The reason behind
 * this class is to provide a simple counter implementation: users call
 * {@link org.apache.commons.functor.aggregator.AbstractNoStoreAggregator#add(Object)}
 * with some data (which will be ignored) and the class will simply count how
 * many times this call was made. Such an implementation can be used for
 * instance to keep track for instance of number of transactions going through a
 * system, whereas every time a transaction was made we just increment a
 * counter. This can be achieved still using a
 * {@link org.apache.commons.functor.aggregator.AbstractNoStoreAggregator} and a
 * {@link DoubleSumAggregatorBinaryFunction}-like function (but using int) and always
 * supplying the second parameter as 1 (one). However, using this might make the
 * code clearer.
 */
public final class IntegerCountAggregatorBinaryFunction implements BinaryFunction<Integer, Integer, Integer> {
    /**
     * Increments <code>left</code> by one and returns it.
     *
     * @param left
     *            Value to be incremented by 1 and returned. If
     *            <code>null</code>, <code>null</code> is returned.
     * @param right
     *            ignored
     * @return <code>left + 1</code> if <code>left != null</code> otherwise it
     *         returns <code>null</code>.
     */
    public Integer evaluate(Integer left, Integer right) {
        if (left == null) {
            return null;
        }
        return left + 1;
    }

    @Override
    public String toString() {
        return IntegerCountAggregatorBinaryFunction.class.getName();
    }
}
