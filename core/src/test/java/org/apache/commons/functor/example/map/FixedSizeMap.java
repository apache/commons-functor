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
import java.util.Map;

import org.apache.commons.functor.BinaryFunction;
import org.apache.commons.functor.BinaryProcedure;
import org.apache.commons.functor.adapter.BinaryProcedureBinaryFunction;
import org.apache.commons.functor.core.algorithm.GeneratorContains;
import org.apache.commons.functor.core.composite.Not;
import org.apache.commons.functor.generator.loop.IteratorToGeneratorAdapter;

/**
 * @version $Revision$ $Date$
 */
@SuppressWarnings("unchecked")
public class FixedSizeMap<K, V> extends FunctoredMap<K, V> {
    public FixedSizeMap(Map<K, V> map) {
        super(map);
        setOnPut(new BinaryFunction<Map<K,V>, Object[], V>() {
            public V evaluate(Map<K,V> map, Object[] b) {
                K key = (K)Array.get(b,0);
                V value = (V)Array.get(b,1);
                if (map.containsKey(key)) {
                    return map.put(key,value);
                } else {
                    throw new IllegalArgumentException();
                }
            }
        });

        setOnPutAll(new BinaryProcedure<Map<K,V>, Map<K,V>>() {
            public void run(Map<K,V> a, Map<K,V> b) {
                Map<K,V> dest = a;
                Map<K,V> src = b;

                if (GeneratorContains.instance().test(IteratorToGeneratorAdapter.adapt(src.keySet().iterator()),Not.not(new ContainsKey(dest)))) {
                    throw new IllegalArgumentException();
                } else {
                    dest.putAll(src);
                }
            }
        });

        setOnRemove(new BinaryProcedureBinaryFunction<Map<K, V>, K, V>((BinaryProcedure<? super Map<K, V>, ? super K>) new Throw<K, V>(new UnsupportedOperationException())));
        setOnClear(new Throw<K, V>(new UnsupportedOperationException()));
    }
}
