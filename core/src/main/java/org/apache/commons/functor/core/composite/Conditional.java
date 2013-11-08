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
import org.apache.commons.functor.BinaryProcedure;
import org.apache.commons.functor.NullaryFunction;
import org.apache.commons.functor.NullaryPredicate;
import org.apache.commons.functor.NullaryProcedure;
import org.apache.commons.functor.Function;
import org.apache.commons.functor.Predicate;
import org.apache.commons.functor.Procedure;

/**
 * Utility methods for creating conditional functors.
 * @version $Revision$ $Date$
 */
public final class Conditional {

    /**
     * Hidden constructor as this only is a helper class with static methods.
     */
    private Conditional() {
    }

    // ------------------------------------------------------------------------

    /**
     * Create a guarded NullaryProcedure.
     * @param q if
     * @param r then
     * @return NullaryProcedure
     */
    public static NullaryProcedure procedure(NullaryPredicate q, NullaryProcedure r) {
        return new ConditionalNullaryProcedure(q, r);
    }

    /**
     * Create a conditional NullaryProcedure.
     * @param q if
     * @param r then
     * @param s else
     * @return NullaryProcedure
     */
    public static NullaryProcedure procedure(NullaryPredicate q, NullaryProcedure r, NullaryProcedure s) {
        return new ConditionalNullaryProcedure(q, r, s);
    }

    /**
     * Create a conditional NullaryFunction.
     * @param <T> the input functions parameter type
     * @param q if
     * @param r then
     * @param s else
     * @return NullaryFunction<T>
     */
    public static <T> NullaryFunction<T> function(NullaryPredicate q,
            NullaryFunction<? extends T> r, NullaryFunction<? extends T> s) {
        return new ConditionalNullaryFunction<T>(q, r, s);
    }

    /**
     * Create a conditional NullaryPredicate.
     * @param q if
     * @param r then
     * @param s else
     * @return NullaryPredicate
     */
    public static NullaryPredicate predicate(NullaryPredicate q, NullaryPredicate r, NullaryPredicate s) {
        return new ConditionalNullaryPredicate(q, r, s);
    }

    /**
     * Create a guarded Procedure.
     *
     * @param <A> the predicates argument type.
     * @param q if
     * @param r then
     * @return Procedure<A>
     */
    public static <A> Procedure<A> procedure(Predicate<? super A> q, Procedure<? super A> r) {
        return new ConditionalProcedure<A>(q, r);
    }

    /**
     * Create a conditional Procedure.
     *
     * @param <A> the predicates argument type.
     * @param q if
     * @param r then
     * @param s else
     * @return Procedure<A>
     */
    public static <A> Procedure<A> procedure(Predicate<? super A> q, Procedure<? super A> r,
            Procedure<? super A> s) {
        return new ConditionalProcedure<A>(q, r, s);
    }

    /**
     * Create a conditional Function.
     * @param <A> the predicates argument type.
     * @param <T> the output function returned value type.
     * @param q if
     * @param r then
     * @param s else
     * @return Function<A, T>
     */
    public static <A, T> Function<A, T> function(Predicate<? super A> q,
            Function<? super A, ? extends T> r, Function<? super A, ? extends T> s) {
        return new ConditionalFunction<A, T>(q, r, s);
    }

    /**
     * Create a conditional Predicate.
     * @param <A> the predicates argument type.
     * @param q if
     * @param r then
     * @param s else
     * @return Predicate<A>
     */
    public static <A> Predicate<A> predicate(Predicate<? super A> q, Predicate<? super A> r,
            Predicate<? super A> s) {
        return new ConditionalPredicate<A>(q, r, s);
    }

    /**
     * Create a guarded BinaryProcedure.
     * @param <L> the left argument type.
     * @param <R> the right argument type.
     * @param q if
     * @param r then
     * @return BinaryProcedure<L, R>
     */
    public static <L, R> BinaryProcedure<L, R> procedure(BinaryPredicate<? super L, ? super R> q,
            BinaryProcedure<? super L, ? super R> r) {
        return new ConditionalBinaryProcedure<L, R>(q, r);
    }

    /**
     * Create a conditional BinaryProcedure.
     *
     * @param <L> the left argument type.
     * @param <R> the right argument type.
     * @param q if
     * @param r then
     * @param s else
     * @return BinaryProcedure<L, R>
     */
    public static <L, R> BinaryProcedure<L, R> procedure(BinaryPredicate<? super L, ? super R> q,
            BinaryProcedure<? super L, ? super R> r, BinaryProcedure<? super L, ? super R> s) {
        return new ConditionalBinaryProcedure<L, R>(q, r, s);
    }

    /**
     * Create a conditional BinaryFunction.
     *
     * @param <L> the left argument type.
     * @param <R> the right argument type.
     * @param <T> the output function returned value type.
     * @param q if
     * @param r then
     * @param s else
     * @return BinaryFunction<L, R, T>
     */
    public static <L, R, T> BinaryFunction<L, R, T> function(BinaryPredicate<? super L, ? super R> q,
            BinaryFunction<? super L, ? super R, ? extends T> r, BinaryFunction<? super L, ? super R, ? extends T> s) {
        return new ConditionalBinaryFunction<L, R, T>(q, r, s);
    }

    /**
     * Create a conditional BinaryPredicate.
     *
     * @param <L> the left argument type.
     * @param <R> the right argument type.
     * @param q if
     * @param r then
     * @param s else
     * @return BinaryPredicate<L, R>
     */
    public static <L, R> BinaryPredicate<L, R> predicate(BinaryPredicate<? super L, ? super R> q,
            BinaryPredicate<? super L, ? super R> r, BinaryPredicate<? super L, ? super R> s) {
        return new ConditionalBinaryPredicate<L, R>(q, r, s);
    }

}
