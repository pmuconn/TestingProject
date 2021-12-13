package com.misc;

import java.util.ArrayList;
import java.util.List;

public class ListBreakOut {

	public static void main(String[] args) {
		List<Integer> stringList = new ArrayList<>();
        stringList.add(0);
        stringList.add(1);
        stringList.add(2);
        stringList.add(3);
        stringList.add(4);
        stringList.add(5);
        stringList.add(6);
        stringList.add(7);
        stringList.add(8);
        stringList.add(9);

        List<List<Integer>> chunkList = getChunkList(stringList, 2);
	}
	
	
    /**
     * Returns List of the List argument passed to this function with size = chunkSize
     *
     * @param largeList input list to be portioned
     * @param chunkSize maximum size of each partition
     * @param <T> Generic type of the List
     * @return A list of Lists which is portioned from the original list
     */
    private static <T> List<List<T>> getChunkList(List<T> largeList , int chunkSize) {
        List<List<T>> chunkList = new ArrayList<>();
        for (int i = 0 ; i <  largeList.size() ; i += chunkSize) {
            chunkList.add(largeList.subList(i , i + chunkSize >= largeList.size() ? largeList.size() : i + chunkSize));
        }
        return chunkList;
    }
	

}
