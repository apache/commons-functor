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

import org.apache.commons.functor.NullaryPredicate;
import org.apache.commons.functor.NullaryProcedure;


/**
 * A {@link NullaryProcedure} implementation of a while loop. Given a {@link NullaryPredicate}
 * <i>c</i> and an {@link NullaryProcedure} <i>p</i>, {@link #run runs}
 * <code>while(c.test()) { p.run(); }</code>.
 * @version $Revision: 1345136 $ $Date: 2012-06-01 09:47:06 -0300 (Fri, 01 Jun 2012) $
 */
public class WhileDoNullaryProcedure extends AbstractLoopNullaryProcedure {
    /**
     * Create a new WhileDoNullaryProcedure.
     * @param condition while
     * @param action to do
     */
    public WhileDoNullaryProcedure(NullaryPredicate condition, NullaryProcedure action) {
        super(condition, action);
    }

    /**
     * {@inheritDoc}
     */
    public void run() {
        while (getCondition().test()) {
            getAction().run();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "WhileDoNullaryProcedure<while(" + getCondition() + ") do(" + getAction() + ")>";
    }
}
