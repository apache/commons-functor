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
package org.apache.commons.functor.example.kata.one;

import org.apache.commons.functor.Function;
import org.apache.commons.functor.core.composite.Composite;

/**
 */
public class ToMoney implements Function<Number, Money> {

    public Money evaluate(Number cents) {
        return new Money(cents.intValue());
    }

    public static ToMoney instance() {
        return INSTANCE;
    }

    public static <X> Function<X, Money> from(Function<? super X, ? extends Number> fn) {
        return Composite.function(INSTANCE, fn);
    }

    private static ToMoney INSTANCE = new ToMoney();
}
