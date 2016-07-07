/*
 * Created on Jul 15, 2008
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author pmorano
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TestLinkedHashMap {

    public static void main(String[] args) {
        
        Map map = null;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        
        linkedHashMap.put("1",new HashMap());
        linkedHashMap.put("2","2");
        linkedHashMap.put("3","3");
        linkedHashMap.put("4","4");
        
        System.out.println(linkedHashMap);
        
        map = linkedHashMap;
        
        System.out.println(map);
        
        HashMap hashMap = linkedHashMap;
        
        System.out.println(hashMap);

        System.out.println("done.");
        
    }
}
