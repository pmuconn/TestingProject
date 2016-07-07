package com;

import java.lang.reflect.Field;

public class CopyProperties {

    /**
     * Deep Copy of like object properties using reflection.
     * @param from
     * @param to
     * @return
     */
    @SuppressWarnings("unused")
    private Object copyAllowedProperties(Object from, Object to) {
        Object fromObj = null;
        Field srcField = null;
        for (Field f : to.getClass().getDeclaredFields()) {
            boolean isPrivate = false;
            if (!f.isAccessible()) {
                f.setAccessible(true);
                isPrivate = true;
            }
            try {
                try {
                    srcField = from.getClass().getDeclaredField(f.getName());
                } catch (NoSuchFieldException e) {
                    //Field wasn't found on the source object, just continue.
                    continue;
                }
                srcField.setAccessible(true);
                if (isCopyAllowed(srcField.getType())) {
                    if (srcField.get(from) != null) {
                        //If the types are the same or the field is a primitive (String incl), just do a regular set.
                        if (srcField.getType().equals(f.getType()) || isStraightGetandSet(f.getType()) ) {
                            f.set(to, srcField.get(from));
                        }
                        else {
                            fromObj = srcField.get(from);
                            Class<?> clazz = f.getType();
                            Object toObj = clazz.newInstance();
                            copyAllowedProperties(fromObj, toObj);
                            f.set(to, toObj);
                        }
                    }
                }
            }
            catch (Exception e) {
                //Field wasn't able to be copied, log and continue.
                continue;
            }
            if (isPrivate) {
                f.setAccessible(false);
            }
        }
        return to;
    }
	
	
    /**
     * Check the class to see if it is allowed to be copied using straight get/set reflection.
     * @param c
     * @return
     */
    private boolean isStraightGetandSet(Class<?> c) {
          return c.isPrimitive() || c == String.class || c == Boolean.class
            || c == Byte.class || c == Short.class || c == Character.class
            || c == Integer.class || c == Float.class || c == Double.class
            || c == Long.class;
    }

    /**
     * Check the class to see if it is allowed to be copied. Some class types will have to be done via manual get/set.
     * @param c
     * @return
     */
    private boolean isCopyAllowed(Class<?> c) {
        boolean returnValue = true;
        if (c == java.util.Date.class || c == java.sql.Date.class) {
            returnValue = false;
        }
        return returnValue;
    }
	
}
