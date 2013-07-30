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
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.apache.commons.functor.BinaryFunction;
import org.apache.commons.functor.BinaryProcedure;
import org.apache.commons.functor.NullaryProcedure;
import org.apache.commons.functor.Predicate;
import org.apache.commons.functor.Procedure;

/**
 * @version $Revision$ $Date$
 */
@SuppressWarnings("unchecked")
public class FunctoredMap<K, V> implements Map<K, V> {
    public FunctoredMap(Map<? super K, ? super V> map) {
        this.map = (Map<K, V>)map;
    }

    @Override
    public int hashCode() {
        return map.hashCode();
    }

    @Override
    public String toString() {
        return map.toString();
    }

    public Collection<V> values() {
        return map.values();
    }

    public Set<K> keySet() {
        return map.keySet();
    }

    public V get(Object key) {
        return onget.evaluate(map, (K)key);
    }

    public void clear() {
        onclear.run(map);
    }

    public int size() {
        return map.size();
    }

    public Object put(Object key, Object value) {
        return onput.evaluate(map, new Object[] { key, value });
    }

    public void putAll(Map<? extends K, ? extends V> src) {
        onputall.run(map, (Map<K, V>)src);
    }

    public Set<Map.Entry<K, V>> entrySet() {
        return map.entrySet();
    }

    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    public boolean isEmpty() {
        return map.isEmpty();
    }

    public V remove(Object key) {
        return onremove.evaluate(map, (K)key);
    }

    @Override
    public boolean equals(Object obj) {
        return map.equals(obj);
    }

    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    // protected

    protected void setOnClear(Procedure<Map<K, V>> procedure) {
        onclear = procedure;
    }

    protected void setOnPut(BinaryFunction<Map<K, V>, Object[], V> function) {
        onput = function;
    }

    protected void setOnGet(BinaryFunction<Map<K, V>, K, V> function) {
        onget = function;
    }

    protected void setOnPutAll(BinaryProcedure<Map<K, V>, Map<K, V>> procedure) {
        onputall = procedure;
    }

    protected void setOnRemove(BinaryFunction<Map<K, V>, K, V> function) {
        onremove = function;
    }

    // attributes

    protected BinaryFunction<Map<K, V>, Object[], V> DEFAULT_ON_PUT = new BinaryFunction<Map<K, V>, Object[], V>() {
        public V evaluate(Map<K, V> a, Object[] b) {
            Map<K, V> map = a;
            K key = (K)Array.get(b,0);
            V value = (V)Array.get(b,1);
            return map.put(key,value);
        }
    };

    private BinaryFunction<Map<K, V>, Object[], V> onput = DEFAULT_ON_PUT;

    protected BinaryFunction<Map<K, V>, K, V> DEFAULT_ON_GET = new BinaryFunction<Map<K, V>, K, V>() {
        public V evaluate(Map<K, V> map, K key) {
            return map.get(key);
        }
    };

    private BinaryFunction<Map<K, V>, K, V> onget = DEFAULT_ON_GET;
    
    protected BinaryProcedure<Map<K, V>, Map<K, V>> DEFAULT_ON_PUT_ALL = new BinaryProcedure<Map<K, V>, Map<K, V>>() {
        public void run(Map<K, V> a, Map<K, V> b) {
            Map<K, V> dest = a;
            Map<K, V> src = b;
            dest.putAll(src);
        }
    };

    private BinaryProcedure<Map<K, V>, Map<K, V>> onputall = DEFAULT_ON_PUT_ALL;

    protected BinaryFunction<Map<K, V>, K, V> DEFAULT_ON_REMOVE = new BinaryFunction<Map<K, V>, K, V>() {
        public V evaluate(Map<K, V> a, K key) {
            Map<K, V> map = a;
            return map.remove(key);
        }
    };

    private BinaryFunction<Map<K, V>, K, V> onremove = DEFAULT_ON_REMOVE;

    protected Procedure<Map<K, V>> DEFAULT_ON_CLEAR = new Procedure<Map<K, V>>() {
        public void run(Map<K, V> map) {
            map.clear();
        }
    };

    private Procedure<Map<K, V>> onclear = DEFAULT_ON_CLEAR;

    private Map<K, V> map = null;

    // inner classes

    protected static class ContainsKey implements Predicate<Object> {
        ContainsKey(Map<?, ?> map) {
            this.map = map;
        }

        public boolean test(Object obj) {
            return map.containsKey(obj);
        }

        private Map<?, ?> map = null;
    }

    protected static class Throw<K, V> implements NullaryProcedure, Procedure<Map<K, V>>, BinaryProcedure<K, V> {
        Throw(RuntimeException e) {
            this.klass = e.getClass();
        }

        public void run() {
            try {
                throw (RuntimeException)(klass.newInstance());
            } catch(IllegalAccessException e) {
                throw new RuntimeException();
            } catch (InstantiationException e) {
                throw new RuntimeException();
            }
        }

        public void run(Map<K, V> obj) {
            run();
        }

        public void run(K a, V b) {
            run();
        }

        private Class<?> klass = null;
    }
}
