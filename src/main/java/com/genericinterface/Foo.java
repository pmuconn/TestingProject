package com.genericinterface;

public class Foo <T extends Toto> {

	private T t;
	
    public void setObject(T t) {
        this.t = t;
    }
    
    public T getObject() {
        return t;
    }
    
    public void doSomething() {
        t.execute(); 
    }
    
}
