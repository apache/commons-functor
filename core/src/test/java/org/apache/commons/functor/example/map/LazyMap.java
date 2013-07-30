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

import java.util.Map;

import org.apache.commons.functor.BinaryFunction;
import org.apache.commons.functor.Function;

/**
 * @version $Revision$ $Date$
 */
public class LazyMap<K, V> extends FunctoredMap<K, V> {
    public LazyMap(Map<K, V> map, final Function<K, V> factory) {
        super(map);
        setOnGet(new BinaryFunction<Map<K,V>, K, V>() {
            public V evaluate(Map<K, V> map, K key) {
                if (map.containsKey(key)) {
                    return map.get(key);
                } else {
                    V value = factory.evaluate(key);
                    map.put(key,value);
                    return value;
                }
            }
        });
    }
}
