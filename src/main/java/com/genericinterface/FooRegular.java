package com.genericinterface;

public class FooRegular {

	private Toto t;
	
    public void setObject(Toto t) {
        this.t = t;
    }
    
    public Toto getObject() {
        return t;
    }
    
    public void doSomething() {
        t.execute(); 
    }
    
}
