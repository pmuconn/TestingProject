package com.junit4;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
public class TestHelloWorld {

	//when I created this class, I added this at the top:
	//import static org.junit.Assert.*;

	
	private Collection collection;
	
	@Before
	public void theSetup() {
		collection = new ArrayList();
		System.out.println("Setup complete");
	}
	
    @BeforeClass
    public static void oneTimeSetUp() {
        // one-time initialization code   
    	System.out.println("@BeforeClass - oneTimeSetUp");
    }

    @AfterClass
    public static void oneTimeTearDown() {
        // one-time cleanup code
    	System.out.println("@AfterClass - oneTimeTearDown");
    }	
	
    @After
    public void tearDown() {
        collection.clear();
        System.out.println("@After - tearDown");
    }

	@Test
	public void testHello() {
		String h = "helloWorld";
		assertEquals("helloWorld",h);
	}
	
	@Test
    public void testOneItemCollection() {
        collection.add("itemA");
        assertEquals(1, collection.size());
        System.out.println("@Test - testOneItemCollection");
    }
	
}
