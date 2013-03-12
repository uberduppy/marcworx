/* MarcWorx MARC Library - Utilities for manipulation of MARC records
    Copyright (C) 2013  Todd Walker, Talwood Solutions

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
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
