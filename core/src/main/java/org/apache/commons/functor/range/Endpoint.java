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
package org.apache.commons.functor.range;

import org.apache.commons.lang3.Validate;

/**
 * Represent an endpoint of a range. This can be the left endpoint or the right
 * endpoint. It is also called left limit or right limit, and can be open
 * (exclusive, unbounded) or closed (inclusive, bounded).
 *
 * @param <T> type of the value held by this endpoint
 * @since 1.0
 * @version $Revision$ $Date$
 */
public class Endpoint<T extends Comparable<?>> {

    /**
     * The endpoint value.
     */
    private final T value;

    /**
     * The endpoint bound type.
     */
    private final BoundType boundType;

    /**
     * Create an endpoint.
     *
     * @param value value
     * @param boundType bound type
     */
    public Endpoint(T value, BoundType boundType) {
        this.value = value;
        this.boundType = Validate.notNull(boundType, "bound type must not be null");
    }

    /**
     * @return Object
     */
    public T getValue() {
        return value;
    }

    /**
     * @return BoundType
     */
    public BoundType getBoundType() {
        return boundType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        String boundType = this.boundType == BoundType.OPEN ? "OPEN" : "CLOSED";
        return "Endpoint<" + this.value + ", " + boundType + ">";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Endpoint)) {
            return false;
        }
        Endpoint<?> that = (Endpoint<?>) obj;
        return this.boundType == that.boundType
                && this.value.equals(that.value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = "Endpoint".hashCode();
        hash <<= 2;
        hash ^= this.value.toString().hashCode();
        hash <<= 2;
        hash ^= Boolean.valueOf(this.boundType == BoundType.OPEN).hashCode();
        return hash;
    }

    /**
     * Print the left endpoint and bound type.
     *
     * @return String
     */
    public String toLeftString() {
        return (this.boundType == BoundType.OPEN ? "(" : "[") + this.value;
    }

    /**
     * Print the right endpoint and bound type.
     *
     * @return String
     */
    public String toRightString() {
        return this.value + (this.boundType == BoundType.OPEN ? ")" : "]");
    }

}
