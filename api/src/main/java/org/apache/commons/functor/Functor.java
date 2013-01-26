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
package org.apache.commons.functor;

/**
 * Functor marker interface.  All provided functor interfaces extend this interface.
 * <p>
 * While not strictly required,
 * it is <em>encouraged</em> that functor implementations:
 * <ul>
 *   <li>Implement {@link java.io.Serializable Serializable}</li>
 *   <li>Override {@link java.lang.Object#equals(java.lang.Object)}</li>
 *   <li>Override {@link java.lang.Object#hashCode()}</li>
 *   <li>Override {@link java.lang.Object#toString()}</li>
 * </ul>
 * </p>
 *
 * @since 1.0
 * @version $Revision$ $Date$
 */
public interface Functor {
}
