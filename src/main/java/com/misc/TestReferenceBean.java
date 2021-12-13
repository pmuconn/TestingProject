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
public class TestReferenceBean {
    
    private TestReferenceBean1 tb1 = new TestReferenceBean1();
    /**
     * @return Returns the code.
     */
    public String getCode() {
        return this.tb1.getCode();
    }
    /**
     * @param code The code to set.
     */
    public void setCode(String code) {
        this.tb1.setCode(code);
    }
    /**
     * @return Returns the tb1.
     */
    public TestReferenceBean1 getTb1() {
        return tb1;
    }
    /**
     * @param tb1 The tb1 to set.
     */
    public void setTb1(TestReferenceBean1 tb1) {
        this.tb1 = tb1;
    }
}
