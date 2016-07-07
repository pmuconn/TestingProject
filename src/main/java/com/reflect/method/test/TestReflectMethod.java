package com.reflect.method.test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;

public class TestReflectMethod {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Object obj;
		Method method;
		String sret = "";
		
		obj = new InheritProcessor();
		//String methodName = "helloWorld2";
		//Class cls = Class.forName("Processor");
		
		try {
		//invoke the method that has no params.	
		method = obj.getClass().getDeclaredMethod("helloWorld2", new Class[]{});
		sret = (String)method.invoke(obj, new Object[] {});
		System.out.println("ReturnValue: " + sret);

		//invoke the method that has a String param
		Class partypes[] = new Class[1];
        partypes[0] = String.class;
		method = obj.getClass().getMethod("helloWorld", partypes);
		
		Object arglist[] = new Object[1];
        arglist[0] = new String("Paul");
		sret = (String)method.invoke(obj, arglist);
		System.out.println("ReturnValue: " + sret);
			
		} catch (SecurityException e) {
		  System.out.println("Security Exception");
		} catch (NoSuchMethodException e) {
		  System.out.println("NoSuchMethod Exception");
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
