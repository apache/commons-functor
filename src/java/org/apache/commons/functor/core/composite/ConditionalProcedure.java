/* 
 * $Header: /home/jerenkrantz/tmp/commons/commons-convert/cvs/home/cvs/jakarta-commons-sandbox//functor/src/java/org/apache/commons/functor/core/composite/ConditionalProcedure.java,v 1.2 2003/01/28 12:00:29 rwaldhoff Exp $
 * ====================================================================
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 2003 The Apache Software Foundation.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution,
 *    if any, must include the following acknowledgment:
 *       "This product includes software developed by the
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowledgment may appear in the software itself,
 *    if and wherever such third-party acknowledgments normally appear.
 *
 * 4. The names "The Jakarta Project", "Commons", and "Apache Software
 *    Foundation" must not be used to endorse or promote products derived 
 *    from this software without prior written permission. For written
 *    permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache",
 *    nor may "Apache" appear in their name, without prior written
 *    permission of the Apache Software Foundation.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */
package org.apache.commons.functor.core.composite;

import java.io.Serializable;

import org.apache.commons.functor.Procedure;
import org.apache.commons.functor.Predicate;

/**
 * A {@link Procedure Procedure} 
 * similiar to Java's "ternary" 
 * or "conditional" operator (<code>? :</code>).
 * Given a {@link Predicate predicate}
 * <i>p</i> and {@link Procedure procedures}
 * <i>q</i> and <i>r</i>, {@link #run runs}
 * <code>if(p.test()) { q.run(); } else { r.run(); }</code>.
 * <p>
 * Note that although this class implements 
 * {@link Serializable}, a given instance will
 * only be truly <code>Serializable</code> if all the
 * underlying functors are.  Attempts to serialize
 * an instance whose delegates are not all 
 * <code>Serializable</code> will result in an exception.
 * </p>
 * @version $Revision: 1.2 $ $Date: 2003/01/28 12:00:29 $
 * @author Rodney Waldhoff
 */
public final class ConditionalProcedure implements Procedure, Serializable {

    // constructor
    // ------------------------------------------------------------------------

    public ConditionalProcedure(Predicate ifPred, Procedure thenPred, Procedure elsePred) {
        this.ifPred = ifPred;
        this.thenProc = thenPred;
        this.elseProc = elsePred;
    }
    
    // predicate interface
    // ------------------------------------------------------------------------
    public void run() {
        if(ifPred.test()) {
            thenProc.run();
        } else {
            elseProc.run();
        }
    }

    public boolean equals(Object that) {
        if(that instanceof ConditionalProcedure) {
            return equals((ConditionalProcedure)that);
        } else {
            return false;
        }
    }
    
    public boolean equals(ConditionalProcedure that) {
        return null != that && 
                (null == ifPred ? null == that.ifPred : ifPred.equals(that.ifPred)) &&
                (null == thenProc ? null == that.thenProc : thenProc.equals(that.thenProc)) &&
                (null == elseProc ? null == that.elseProc : elseProc.equals(that.elseProc));
    }
    
    public int hashCode() {
        int hash = "ConditionalProcedure".hashCode();
        if(null != ifPred) {
            hash <<= 4;
            hash ^= ifPred.hashCode();            
        }
        if(null != thenProc) {
            hash <<= 4;
            hash ^= thenProc.hashCode();            
        }
        if(null != elseProc) {
            hash <<= 4;
            hash ^= elseProc.hashCode();            
        }
        return hash;
    }
    
    public String toString() {
        return "ConditionalProcedure<" + ifPred + "?" + thenProc + ":" + elseProc + ">";
    }

    // attributes
    // ------------------------------------------------------------------------
    private Predicate ifPred = null;
    private Procedure thenProc = null;
    private Procedure elseProc = null;
}
