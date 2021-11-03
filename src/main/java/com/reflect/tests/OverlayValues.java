package com.reflect.tests;

import java.lang.reflect.Field;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class OverlayValues {

	String firstString = "";
	String secondString = "";
	String thirdString = "";
	
	   /**
     * Overlays the current object with String and Date fields from the passed object.
     * Fields are only overlayed if the source object String isn't empty or the Date is not null
     * @param sourceUnifierPaymentRequest
     */
    public void overlay(OverlayValues sourceOverlayValues) {
        Field srcField = null;
        for (Field field : this.getClass().getDeclaredFields()) {
            boolean isPrivate = false;
            if (!field.isAccessible()) {
                field.setAccessible(true);
                isPrivate = true;
            }
            if( field.getType().isAssignableFrom(String.class) ){
                try {
                    srcField = sourceOverlayValues.getClass().getDeclaredField(field.getName());
                    if (StringUtils.isNotEmpty((String)srcField.get(sourceOverlayValues))) {
                        field.set(this, srcField.get(sourceOverlayValues));
                    }
                }
                catch (Exception e) {}
            } else if( field.getType().isAssignableFrom(Date.class) ){
                try {
                    srcField = sourceOverlayValues.getClass().getDeclaredField(field.getName());
                    if (srcField.get(sourceOverlayValues) != null) {
                        field.set(this, srcField.get(sourceOverlayValues));
                    }
                }
                catch (Exception e) {}
            }
            if (isPrivate) {
                field.setAccessible(false);
            }

        }
    }
    
    @Override
    public String toString() {
    	return ToStringBuilder.reflectionToString(this);
    }
    
    
    public static void main(String[] args) {
    	OverlayValues c1 = new OverlayValues();
    	c1.firstString = "first";
    	
    	OverlayValues c2 = new OverlayValues();
    	c2.firstString = "newFirst";
    	c2.secondString = "second";
    	
    	System.out.println(c1);
    	c1.overlay(c2);
    	System.out.println(c1);
    	
	}
}
