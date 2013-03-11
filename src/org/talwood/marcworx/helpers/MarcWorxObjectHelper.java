package org.talwood.marcworx.helpers;

public class MarcWorxObjectHelper {
    public static boolean equals(Object lhs, Object rhs) {
        if (lhs != null) {
            return lhs.equals(rhs);
        }
        else {
            return (rhs == null);
        }
    }
    
    public static int getHashCode(Object o){
        int hashCode = 0;
        if(o != null) {
            hashCode = o.hashCode();
        }
        return hashCode;
    }
    
    public static boolean intInArray(int[] array, int value) {
        boolean result = false;
        for (int i = 0; i < array.length; i++) {
            if(array[i] == value) {
                result = true;
                break;
            }
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public static <T> T cast(Object x) {
        return (T) x;
    }

}
