package com.inlinecomparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BooleanSort {

	public static void main(String[] args) {
		List<Boolean> bools = new ArrayList<Boolean>();
		bools.add(false);
		bools.add(false);
		bools.add(true);
		bools.add(false);
		bools.add(true);
		
        Collections.sort(bools, new Comparator<Boolean>() {
            @Override
            public int compare(Boolean i1, Boolean i2) {
                //return Boolean.compare(i1.booleanValue(), i2.booleanValue());
                return Boolean.compare(i2.booleanValue(), i1.booleanValue());
                //return (i1.booleanValue() == i2.booleanValue() ? 0 : (i2.booleanValue() ? 1 : -1)); //might need to do something like this
            }
        });
		
        System.out.println(bools);
		
	}

}
