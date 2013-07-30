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
package org.apache.commons.functor.example.map;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.functor.BinaryPredicate;
import org.apache.commons.functor.BinaryProcedure;
import org.apache.commons.functor.Predicate;
import org.apache.commons.functor.adapter.BinaryProcedureBinaryFunction;
import org.apache.commons.functor.core.composite.ConditionalBinaryFunction;

/**
 * @version $Revision$ $Date$
 */
public class PredicatedMap<K, V> extends FunctoredMap<K, V> {
    public PredicatedMap(Map<K, V> map, final Predicate<K> keyPredicate, final Predicate<V> valuePredicate) {
        super(map);
        setOnPut(new ConditionalBinaryFunction<Map<K,V>, Object[], V>(
            new BinaryPredicate<Map<K, V>, Object[]>() {
                @SuppressWarnings("unchecked")
                public boolean test(Map<K, V> a, Object[] b) {
                    return keyPredicate.test((K)Array.get(b,0)) &&
                        valuePredicate.test((V)Array.get(b,1));
                }
            },
            DEFAULT_ON_PUT,
            BinaryProcedureBinaryFunction.<Map<K,V>, Object[], V>adapt(new Throw<Map<K,V>, Object>(new IllegalArgumentException()))));

        setOnPutAll(new BinaryProcedure<Map<K, V>, Map<K, V>>() {
            public void run(Map<K, V> dest, Map<K, V> src) {
                for (Iterator<Map.Entry<K, V>> iter = src.entrySet().iterator(); iter.hasNext(); ) {
                    Map.Entry<K, V> pair = iter.next();
                    if (keyPredicate.test(pair.getKey()) &&
                        valuePredicate.test(pair.getValue())) {
                        dest.put(pair.getKey(),pair.getValue());
                    }
                }
            }
        });
    }
}
