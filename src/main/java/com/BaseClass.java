/*
 * Created on Mar 31, 2008
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com;

/**
 * @author pmorano
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class BaseClass {
    
    public static void helloWorld() {
        System.out.println("Hello from our public method");
        privateHello();
    }
    
    private static void privateHello() {
        System.out.println("Hello from out private method");
    }

}
