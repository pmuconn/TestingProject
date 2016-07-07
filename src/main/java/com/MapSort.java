/*
 * Created on Apr 4, 2008
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

/**
 * @author pmorano
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MapSort {

    public static void main(String[] args) {
        Map testMap = new HashMap();
        Vector v = null;
        List list = new ArrayList();
        
        //fill map
        testMap.put("1","SB001");
        testMap.put("2","SB002");
        testMap.put("3","SB003");
        testMap.put("4","SB004");
        testMap.put("5","SB005");
        testMap.put("6","SB006");
        
        //show the unsorted map by keys
        System.out.println("Map keyset: " + testMap.keySet());
        
        //create a vector to sort the keys
        v = new Vector(testMap.keySet());
        Collections.sort(v);
        Iterator vectIter = v.iterator();
        while (vectIter.hasNext()) {
          String key = (String) vectIter.next();
          list.add(key);
        }
        
        System.out.println("Sorted map keys from list: " + list);
        
        TreeMap tm = new TreeMap(testMap);
        
        System.out.println("TreeMap sorted " + tm.keySet());
        
        //test if you get a value from a map and modify it, will it be reflected in the map or
        //do you need to do a put again?
        Map testRef = new HashMap();
        testRef.put("100", new ArrayList());
        
        List refList = (List)testRef.get("100");
        refList.add("poopy Head");
        
        List refList2 = (ArrayList)testRef.get("100");
        System.out.println("Modified List? " + refList2);
        
        //test the map.putall
        Map<String, String> firstMap = new HashMap();
        firstMap.put("1", "hello");
        firstMap.put("2", "world");

        Map<String, String> secMap = new HashMap();
        secMap.put("1", "hello");
        secMap.put("2", "universe");
        secMap.put("3", "Me");
        
        firstMap.putAll(secMap);
        System.out.println("firstmap: " + firstMap);
        
        
    }
}
