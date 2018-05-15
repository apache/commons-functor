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

/**
 * <p>
 * This package contains the interfaces and utilities needed to implement
 * an aggregation service.
 * </p>
 * <p>
 * Aggregation refers to being able to add data to a data "sink" which
 * "aggregates" them automatically (e.g. sum them up or average, median etc).
 * </p>
 * <p>
 * An example of an aggregation service is being able to average a series
 * of <code>int</code> values (perhaps generated from a monitoring component)
 * and be able to constantly report on the average (or mean) value of
 * this series. There is no specification about the maximum amount of data that
 * can be aggregated -- some subclasses might impose such restrictions,
 * if that is the case then that will be specified in their Javadoc's.
 * </p>
 */
package org.apache.commons.functor.aggregator;
