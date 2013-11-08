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

import org.apache.commons.functor.BinaryFunction;
import org.apache.commons.functor.BinaryPredicate;
import org.apache.commons.functor.Function;
import org.apache.commons.functor.Predicate;
import org.apache.commons.functor.Procedure;

/**
 * Utility/fluent methods for creating composite functors.
 * @version $Revision$ $Date$
 */
public final class Composite {

    /**
     * Hidden constructor as this only is a helper class with static methods.
     */
    private Composite() {
    }

    /**
     * Create a composite Procedure.
     * @param <A> the procedure argument type.
     * @param procedure Procedure to execute against output of <code>f</code>
     * @return CompositeProcedure<A>
     */
    public static <A> CompositeProcedure<A> procedure(Procedure<? super A> procedure) {
        return new CompositeProcedure<A>(procedure);
    }

    /**
     * Create a composite Procedure.
     * @param <A> the function argument type.
     * @param <T> the the procedure argument type and function returned value type.
     * @param procedure Procedure to execute against output of <code>f</code>
     * @param function Function to apply
     * @return CompositeProcedure<A>
     */
    public static <A, T> CompositeProcedure<A> procedure(Procedure<? super T> procedure,
            Function<? super A, ? extends T> function) {
        return new CompositeProcedure<T>(procedure).of(function);
    }

    /**
     * Create a composite Predicate.
     * @param <A> the predicate argument type.
     * @param pred Predicate to test the output of <code>f</code>
     * @return CompositePredicate<A>
     */
    public static <A> CompositePredicate<A> predicate(Predicate<? super A> pred) {
        return new CompositePredicate<A>(pred);
    }

    /**
     * Create a composite Predicate.
     * @param <A> the function argument type.
     * @param <T> the predicate argument type and the function returned value type.
     * @param predicate Predicate to test the output of <code>f</code>
     * @param function Function to apply
     * @return CompositePredicate<A>
     */
    public static <A, T> CompositePredicate<A> predicate(Predicate<? super T> predicate,
            Function<? super A, ? extends T> function) {
        return new CompositePredicate<T>(predicate).of(function);
    }

    /**
     * Create a composite BinaryPredicate.
     * @param <L> the output predicate left argument type.
     * @param <R> the output predicate right argument type.
     * @param <G> the input functions left argument type.
     * @param <H> the input functions right argument type.
     * @param p BinaryPredicate to test <i>output(</i><code>f</code><i>), output(</i><code>g</code><i>)</i>
     * @param g left Function
     * @param h right Function
     * @return BinaryPredicate
     */
    public static <L, R, G, H> CompositeBinaryPredicate<L, R> predicate(
            BinaryPredicate<? super G, ? super H> p, Function<? super L, ? extends G> g,
            Function<? super R, ? extends H> h) {
        return new CompositeBinaryPredicate<L, R>(p, g, h);
    }

    /**
     * Create a composite Function.
     * @param <A> the function argument type.
     * @param <T> the function returned value type.
     * @param f Function to apply to the output of <code>g</code>
     * @return Function
     */
    public static <A, T> CompositeFunction<A, T> function(Function<? super A, ? extends T> f) {
        return new CompositeFunction<A, T>(f);
    }

    /**
     * Create a composite Function.
     * @param <A> the function argument type.
     * @param <X> the function argument type.
     * @param <T> the function returned value type.
     * @param f Function to apply to the output of <code>g</code>
     * @param g Function to apply first
     * @return Function
     */
    public static <A, X, T> CompositeFunction<A, T> function(Function<? super X, ? extends T> f,
            Function<? super A, ? extends X> g) {
        return new CompositeFunction<X, T>(f).of(g);
    }

//    /**
//     * Chain a BinaryFunction to a Function.
//     * @param <L>
//     * @param <R>
//     * @param <X>
//     * @param <T>
//     * @param f Function to apply to the output of <code>g</code>
//     * @param g BinaryFunction to apply first
//     * @return BinaryFunction<L, R, T>
//     */
//    public static <L, R, X, T> BinaryFunction<L, R, T> function(Function<? super X, ? extends T> f,
//             BinaryFunction<? super L,
//             ? super R, ? extends X> g) {
//        return new CompositeFunction<X, T>(f).of(g);
//    }

    /**
     * Create a composite<Function> BinaryFunction.
     * @param <L> the output predicate left argument type.
     * @param <R> the output predicate right argument type.
     * @param <G> the input functions left argument type.
     * @param <H> the input functions right argument type.
     * @param <T> the function returned value type.
     * @param f BinaryFunction to apply to <i>output(</i><code>f</code><i>), output(</i><code>g</code><i>)</i>
     * @param g left Function
     * @param h right Function
     * @return BinaryFunction
     */
    public static <L, R, G, H, T> CompositeBinaryFunction<L, R, T> function(
            BinaryFunction<? super G, ? super H, ? extends T> f, Function<? super L, ? extends G> g,
            Function<? super R, ? extends H> h) {
        return new CompositeBinaryFunction<L, R, T>(f, g, h);
    }

    /**
     * Create a composite<BinaryFunction> BinaryFunction.
     * @param <L> the output predicate left argument type.
     * @param <R> the output predicate right argument type.
     * @param <G> the input functions left argument type.
     * @param <H> the input functions right argument type.
     * @param <T> the function returned value type.
     * @param f BinaryFunction to apply to <i>output(</i><code>f</code><i>), output(</i><code>g</code><i>)</i>
     * @param g left BinaryFunction
     * @param h right BinaryFunction
     * @return BinaryFunction
     */
    public static <L, R, G, H, T> BinaryCompositeBinaryFunction<L, R, T> function(
            BinaryFunction<? super G, ? super H, ? extends T> f, BinaryFunction<? super L, ? super R, ? extends G> g,
            BinaryFunction<? super L, ? super R, ? extends H> h) {
        return new BinaryCompositeBinaryFunction<L, R, T>(f, g, h);
    }
}
