/*
 * $Header: /home/jerenkrantz/tmp/commons/commons-convert/cvs/home/cvs/jakarta-commons-sandbox//functor/src/test/org/apache/commons/functor/adapter/Attic/TestUnaryFunctionFunction.java,v 1.1 2003/01/28 12:54:37 rwaldhoff Exp $
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
package org.apache.commons.functor.adapter;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.commons.functor.BaseFunctorTest;
import org.apache.commons.functor.Function;
import org.apache.commons.functor.UnaryFunction;
import org.apache.commons.functor.core.ConstantFunction;
import org.apache.commons.functor.core.IdentityFunction;

/**
 * @version $Revision: 1.1 $ $Date: 2003/01/28 12:54:37 $
 * @author Rodney Waldhoff
 */
public class TestUnaryFunctionFunction extends BaseFunctorTest {

    // Conventional
    // ------------------------------------------------------------------------

    public TestUnaryFunctionFunction(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(TestUnaryFunctionFunction.class);
    }

    // Functor Testing Framework
    // ------------------------------------------------------------------------

    protected Object makeFunctor() {
        return new UnaryFunctionFunction(new IdentityFunction(),"xyzzy");
    }

    // Lifecycle
    // ------------------------------------------------------------------------

    public void setUp() throws Exception {
        super.setUp();
    }

    public void tearDown() throws Exception {
        super.tearDown();
    }

    // Tests
    // ------------------------------------------------------------------------    

    public void testEvaluate() throws Exception {
        Function f = new UnaryFunctionFunction(new IdentityFunction(),"xyzzy");
        assertEquals("xyzzy",f.evaluate());
    }
    
    public void testEquals() throws Exception {
        Function f = new UnaryFunctionFunction(new IdentityFunction(),"xyzzy");
        assertEquals(f,f);
        assertObjectsAreEqual(f,new UnaryFunctionFunction(new IdentityFunction(),"xyzzy"));
        assertObjectsAreNotEqual(f,new ConstantFunction("xyzzy"));
        assertObjectsAreNotEqual(f,new UnaryFunctionFunction(new IdentityFunction(),"foo"));
        assertObjectsAreNotEqual(f,new UnaryFunctionFunction(new ConstantFunction("xyzzy"),"foo"));
        assertObjectsAreNotEqual(f,new UnaryFunctionFunction(null,"xyzzy"));
        assertObjectsAreNotEqual(f,new UnaryFunctionFunction(new IdentityFunction(),null));
        assertObjectsAreEqual(new UnaryFunctionFunction(null,null),new UnaryFunctionFunction(null,null));
    }

    public void testAdaptNull() throws Exception {
        assertNull(UnaryFunctionFunction.adapt(null,"xyzzy"));
    }

    public void testAdapt() throws Exception {
        assertNotNull(UnaryFunctionFunction.adapt(new IdentityFunction(),"xyzzy"));
        assertNotNull(UnaryFunctionFunction.adapt(new IdentityFunction(),null));
    }
}