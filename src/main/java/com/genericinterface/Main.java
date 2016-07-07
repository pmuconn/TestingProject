package com.genericinterface;

import java.util.ArrayList;
import java.util.List;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Toto toto = new TotoImpl();
        Foo<Toto> foo = new Foo<Toto>();
        foo.setObject(toto);
        Toto notCastedToto = foo.getObject();
        foo.doSomething();
        
        //without generic interface
        FooRegular fooreg = new FooRegular();
        fooreg.setObject(toto);
        Toto notCasted = fooreg.getObject();
        fooreg.doSomething();
        
        //Thinking
        //below the List will only accept strings
        List <String> stringList = new ArrayList<String>();
        //stringList.add(new Integer(1)); //wont work
        stringList.add("string");
        
	}

	
	
}
