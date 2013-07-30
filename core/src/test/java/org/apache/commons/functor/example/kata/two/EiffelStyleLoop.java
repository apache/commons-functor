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
package org.apache.commons.functor.example.kata.two;

import org.apache.commons.functor.NullaryFunction;
import org.apache.commons.functor.NullaryPredicate;
import org.apache.commons.functor.NullaryProcedure;
import org.apache.commons.functor.core.Constant;
import org.apache.commons.functor.core.NoOp;

/**
 * Supports an Eiffel style loop construct.
 * <pre>
 * new EiffelStyleLoop()
 *   .from(new NullaryProcedure() { public void run() {} }) // init code
 *   .invariant(new NullaryPredicate() { public boolean test() {} }) // invariants
 *   .variant(new NullaryProcedure() { public Object evaluate() {} }) // diminishing comparable value
 *   // or
 *   // .variant(new NullaryPredicate() { public boolean test() {} }) // more invariants
 *   .until(new NullaryPredicate() { public boolean test() {} }) // terminating condition
 *   .loop(new NullaryProcedure() { public void run() {} }) // the acutal loop
 *   .run();
 * </pre>
 *
 * Note that <tt>new EiffelStyleLoop().run()</tt> executes just fine.
 * You only need to set the parts of the loop you want to use.
 *
 * @version $Revision$ $Date$
 */
public class EiffelStyleLoop implements NullaryProcedure {
    public EiffelStyleLoop from(NullaryProcedure procedure) {
        from = procedure;
        return this;
    }

    public EiffelStyleLoop invariant(NullaryPredicate predicate) {
        invariant = predicate;
        return this;
    }

    public EiffelStyleLoop variant(NullaryPredicate predicate) {
        variant = predicate;
        return this;
    }

    @SuppressWarnings("unchecked")
    public EiffelStyleLoop variant(final NullaryFunction<Object> function) {
        return variant(new NullaryPredicate() {
            public boolean test() {
                boolean result = true;
                Comparable<Object> next = (Comparable<Object>)(function.evaluate());
                if (null != last) {
                    result = last.compareTo(next) > 0;
                }
                last = next;
                return result;
            }
            private Comparable<Object> last = null;
        });
    }

    public EiffelStyleLoop until(NullaryPredicate predicate) {
        until = predicate;
        return this;
    }

    public EiffelStyleLoop loop(NullaryProcedure procedure) {
        loop = procedure;
        return this;
    }

    public void run() {
        from.run();
        assertTrue(invariant.test());
        while(! until.test() ) {
            loop.run();
            assertTrue(variant.test());
            assertTrue(invariant.test());
        }

        // Note that:
        //   assertTrue(until.test());
        // holds here, but isn't necessary since that's
        // the only way we could get out of the loop

        // Also note that:
        //   assertTrue(invariant.test());
        // holds here, but was the last thing called
        // before until.test()
    }

    private void assertTrue(boolean value) {
        if (!value) {
            throw new IllegalStateException("Assertion failed");
        }
    }

    private NullaryProcedure from = NoOp.instance();
    private NullaryPredicate invariant = Constant.truePredicate();
    private NullaryPredicate variant = Constant.truePredicate();
    private NullaryPredicate until = Constant.falsePredicate();
    private NullaryProcedure loop = NoOp.instance();

}