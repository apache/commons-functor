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

package org.apache.commons.functor.example.aggregator.nostore;

import org.apache.commons.functor.BinaryFunction;
import org.apache.commons.functor.aggregator.AbstractNoStoreAggregator;

/**
 * Shows how to implement own aggregated function to use with
 * {@link AbstractNoStoreAggregator}.
 */
public class AggregatedFunctionSample {
    /**
     * Uses a custom function together with a nostore aggregator to provide a
     * continuous logical OR in between all the values added to the aggregator.
     */
    public void useOwnFunction() throws Exception {
        AbstractNoStoreAggregator<Boolean> or = new AbstractNoStoreAggregator<Boolean>( new OwnBinaryFunction() ) {
            @Override
            protected Boolean initialValue() {
                return false;
            }
        };
        or.add( false );
        System.out.println( "OR : " + or.evaluate() );
        or.add( true );
        System.out.println( "OR : " + or.evaluate() );
        or.add( false );
        System.out.println( "OR : " + or.evaluate() );
    }

    /**
     * This class implements a logical OR: it OR's the 2 parameters passed in
     * and returns the result.
     * (There are similar implementations already in functor, this is just to
     * be used as an example for doing this with a nostore aggregator.
     */
    static class OwnBinaryFunction implements BinaryFunction<Boolean, Boolean, Boolean> {
        public Boolean evaluate(Boolean left, Boolean right) {
            return left.booleanValue() || right.booleanValue();
        }
    }
}
