/*
 * Created on Jul 7, 2008
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.misc;

/**
 * @author pmorano
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TestObjectReference {

    public static void main(String[] args) {
        
        TestReferenceBean bean1 = new TestReferenceBean();
        bean1.setCode("10");
        
        TestReferenceBean bean2 = new TestReferenceBean();
        bean2.setTb1(bean1.getTb1());
        bean2.setCode("50");
        
        System.out.println("bean 1: " + bean1.getCode() + " bean2: " + bean2.getCode());
        System.out.println("done.");
        
    }
}
